package pew.pew.topdown;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

//base transportation element

public class ConveyorBelt extends FactoryElement implements ClickableFE{
	//TODO add ability to move multiple elements at a time; make movingElement an array
	
	ConveyorBelt target;
	FactoryElement holding,movingElement;
	int movementSpeed;
	TopDown td;
	
	/*
	 * 0	if done modifying
	 * 1	if is on waiting list
	 */
	boolean[] modifying = new boolean[2];
	
	/*
	 * direction
	 * 0	left
	 * 1	up
	 * 2	right
	 * 3	down
	 */
	
	public ConveyorBelt(int id, Image pic, int layer, FactorySquare fs,TopDown td){
		super(id, pic, layer, fs,td);
		this.td = td;
		feInfo.set(0, true);
		feInfo.set(3, true);
		feInfo.set(4, true);
		rotation = 0;//goes left by default
		movementSpeed = 15;
	}
	
	@Override
	public void update(int delta){					 //TODO FIX *******************************
		if(!isSimulating){
			if(holding != null){
				holding.layer = 2;
				holding.position.x = Math.round(position.x + ((image.getWidth() - holding.width)/2));
				holding.position.y = Math.round(position.y + ((image.getHeight() - holding.height)/2));
			}
			checkRotation();
			
			
		}else{//is simulating
			if(moveElement(delta,movingElement,target)) return;
			
			if(holding != null){
				
				if(!modifying[0]){ //notifies any factorymodifiers that a factorypart is here
					if(!modifying[1]){
						for(FactorySquare fs : this.fs.adjacent){
							if(holding.feInfo.get(6) && fs.subEle.feInfo.get(7)){
								fs.subEle.notified(reverseDirection(this.fs.adjacent.indexOf(fs)), this);
								return;
							}
						}
						modifying[0] = true;
					}
					if(!modifying[0])return;//will return here when either on waiting list or is being modified
				}
				
				
				if(fs.adjacent.get(rotation).subEle.feInfo.get(4)){
					target = (ConveyorBelt) fs.adjacent.get(rotation).subEle;
					
						for(FactorySquare fs : target.fs.adjacent){//checks if there is already another conveyorbelt moving to the target
							if(fs.doesExist && fs.subEle.feInfo.get(0)){
								ConveyorBelt temp = (ConveyorBelt) fs.subEle;
								if(temp.movingElement != null && temp.target == target){
									return;
								}
								
							}
						}
					
					if(target.holding != null){//returns if the target is currently holding something 
						return;
					}
					
					
					movingElement = holding;
					startMovement();
					holding = null;
				}
				
				modifying[0] = false;
			}
		}
	}
	
	
	//helper method for update
	Vector2f  overflow = new Vector2f();
	public final boolean moveElement(int delta, FactoryElement movingEle, ConveyorBelt targetBelt){
		
		if(isSimulating && targetBelt != null && movingEle != null){ //tests if movingElement is at the center of the targetFS
			
			if(checkIfPassed(movingEle,targetBelt)){
				endMovement();
				return false;
			}
			
			//overflow = new Vector2f(); Derp; completely removes the point of the overflow 
			
			switch(rotation){//so much casting...
			case 0:
				overflow.x += (-1*((float)delta/(float)movementSpeed));
				break;
			case 1:
				overflow.y += ((-0.803921568627451F*(float)delta)/(float)movementSpeed);//different number because the width is inequal to the height,
				//it would've traveled faster going vertically instead of horizontally; still not perfect though
				break;
			case 2:
				overflow.x += ((float)delta/(float)movementSpeed);
				break;
			case 3:
				overflow.y += ((0.803921568627451F*(float)delta)/(float)movementSpeed);
			}
			Vector2f incr = new Vector2f(Math.round(overflow.x),Math.round(overflow.y));
			if(!incr.equals(new Vector2f())){
				movingEle.position = movingEle.position.add(incr);
				overflow = overflow.sub(incr);
			}
			
			movingEle.layer = 3;
			return true;
		}
		return false;
		
	}
	
	public void endMovement(){
		target.notified(FactoryElement.reverseDirection(this.rotation), movingElement);
		movingElement = null;
	}
	
	public final void startMovement(){  
		Vector2f tPos = target.position;
		switch(rotation){
		case 0:
			targetPos = tPos.x + (target.image.getWidth()/2) - (movingElement.width/2);
			break;
		case 2:
			targetPos = tPos.x + (target.image.getWidth()/2) - (movingElement.width/2);
			break;
		case 1://up
			targetPos = tPos.y + (target.image.getHeight()/2) - (movingElement.height/2);
			break;
		case 3://down
			targetPos = tPos.y + (target.image.getHeight()/2) - (movingElement.height/2);
			break;
		}
	}
	
	@Override
	public void remove(boolean changeValue){
		super.remove(changeValue);
		if(this.holding != null){
			holding.remove(changeValue);
		}
		if(this.isSimulating) System.out.println("removed a conveyor while simulating");
		//movingElement should never be removed as it is always null when not simulating, and this should never be called
		//while simulating
	}
	
	float targetPos;
	
	public final boolean checkIfPassed(FactoryElement movingEle, ConveyorBelt targetBelt){//TODO fix
		switch(rotation){ //TODO I'm think whatever the modifier problem is might have to do with this
		case 0:
			return targetPos  >= movingEle.position.x;
		case 1:
			return targetPos  >= movingEle.position.y;
		case 2:
			return targetPos  <= movingEle.position.x;
		case 3:
			return targetPos  <= movingEle.position.y;
		}
		return false;
	}
	
	//helper method for the only other helper method in this class
	
	public boolean oldCheckIfPassed(FactoryElement movingEle, ConveyorBelt targetBelt){
		Vector2f mPos = movingEle.position, tPos = targetBelt.position;
		switch(rotation){
		case 0:
			return tPos.x + (targetBelt.image.getWidth()/2) >= mPos.x + (movingEle.width/2);
		case 1:
			return tPos.y + (targetBelt.image.getHeight()/2) >= mPos.y + (movingEle.height/2);
		case 2:
			return tPos.x + (targetBelt.image.getWidth()/2) <= mPos.x + (movingEle.width/2);
		case 3:
			return tPos.y + (targetBelt.image.getHeight()/2) <= mPos.y + (movingEle.height/2);
		}
		System.out.println("invalid direction while checking if passed in a conveyorbelt: " + rotation);
		return false;
		
	}
	
	@Override
	public void startSim(){
		isSimulating = true;
		
	}
	
	@Override
	public void endSim(){
		this.modifying = new boolean[2];
		isSimulating = false;
		target = null;
		if(movingElement != null){
			if(holding != null){
				movingElement.remove(true);
			}else{
				holding = movingElement;
				movingElement = null;
			}
		}
	}
	
	@Override
	public void render(Graphics g){//not sure there's a point to this being overrided
		if(fs != null && fs.doesExist){
			this.bgImage.draw(fsPos.x, fsPos.y, fs.width, fs.height);
			//something was going to go here...?
				
		}
		if(isRenderable){
			this.image.draw(position.x, position.y, width, height);
		}
	}
	
	
	@Override
	public void notified(int from,FactoryElement ele){//assumes the only thing passed in this is something for it to move
		if(holding == null && ele.feInfo.get(1)){
			
			holding = ele;
			holding.layer = 2;
			holding.position.x = (position.x + ((image.getWidth() - holding.width)/2));
			holding.position.y = (position.y + ((image.getHeight() - holding.height)/2));
			
			modifying[0] = false;
			
		}else{
			if(!ele.feInfo.get(1)){
				System.out.println("tried to give an un-conveyorbeltable element to a conveyorbelt");
			}else{
				System.out.println("tried to give an element to a conveyor belt that is already occupied from: " + from);
			}
			td.queue.get(0).add(ele);
			td.queue.get(0).add(holding);
			holding = null;
		}
		checkRotation();
	}
	
	@Override
	public String getSaveData(){//upon seeing the type of a conveyorbelt, the savesmanager will automatically save  its 
		//sub-element(s) in the next line and will load from the next line
		String resRef = image.getResourceReference();
		if(resRef == null) resRef = "null";
		
		int posX,posY;//the numbers to get the fs in the grid in the factory block
		if(fs == null){
			posX = -1;
			posY = -1;
		}else{
			 posX = (int)(fs.pos.x/51);
			 posY = (int)((fs.pos.y - 41)/41);
		}

		//Expects movingElement and holding never to be both non-null
		return ("conveyorbelt" + ";" + id + ";" + resRef +
				";" + layer + ";" + SavesManager.getFeInfo(feInfo) + posX + "," + posY + ";"
				+ rotation + ";" + Boolean.valueOf((this.holding != null)));
	}
	

	@Override
	public void handleClick(int clickCount) {
		rotation++;
		if(rotation > 3){
			rotation = 0;
		}
	}
}
