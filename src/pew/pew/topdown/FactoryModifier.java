package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

//TODO make option for simultaneous modifying (make movingElement an array in conveyorBelt first)

public class FactoryModifier extends InputElement {

	boolean[] waitingList = new boolean[4];
	int nextToModify;

	public FactoryModifier(int id, Image pic, int layer, FactorySquare fs, int modifyType,TopDown td, Image subIm) {
		super(id, pic, layer, fs, modifyType, td,subIm);
		feInfo.set(3, false);
		feInfo.set(7, true);//i forgot to set this at one point; there were many bugs
		this.movementSpeed = 4;//TODO balance, was 10

		movingElement = this.getElement();
		td.updateManagers(movingElement);//this should never be removed,
	}
	
	public FactoryModifier(int id, Image pic, int layer, FactorySquare fs,TopDown td, Image subIm, ArrayList<Integer> data) {
		super(id, pic, layer, fs, td, subIm, data);
		feInfo.set(3, false);
		feInfo.set(7, true);//i forgot to set this at one point; there were many bugs
		this.movementSpeed = 4;//TODO balance, was 10
		
		movingElement = this.getElement();
		td.updateManagers(movingElement);//this should never be removed,
	}
	
	//TODO something is very wrong in this
	
	@Override
	public void update(int delta){
		if(!isSimulating){
			if(holding != null){
				holding.position.x = Math.round(position.x + ((image.getWidth() - holding.width)/2));
				holding.position.y = Math.round(position.y + ((image.getHeight() - holding.height)/2));
				movingElement.position =  new Vector2f(holding.position);
			}
			
		}else{
			if(moveElement(delta,movingElement,target)) return;
			checkWaitingList();
			if(target != null){
				startMovement();//i derped
			}
			//this.rotation = direction;//shouldn't be reversed
			//target = cb;
			
		}
	}
	
	//TODO impliment/polish this
	public void checkWaitingList(){
		int checkingDir = this.nextToModify;
		for(int i = 0; i < 4;i++){
			if(checkingDir + i > 3){
				checkingDir = -i;//essentially, makes this check the zeroth index of waitinglist
			}
			if(waitingList[checkingDir+i]){
				this.rotation = checkingDir+i;//shouldn't be reversed; has already been reversed by the waiting conveyor
				target = (ConveyorBelt) this.fs.adjacent.get(rotation).subEle;
				nextToModify = checkingDir + i + 1;
				waitingList[checkingDir+i] = false;
				return;
			}
		}
		
	}
	
	@Override
	public void endMovement(){
		FactoryPart thatFp = (FactoryPart) target.holding, thisFp = (FactoryPart) movingElement;
		
		for(int i = 0; i < 4;i++){//changes the value of the target
			if(thisFp.getValueData((int) Math.pow(2, i)) && !thatFp.getValueData((int) Math.pow(2, i))){
				thatFp.valueData += (int) Math.pow(2, i);
			}
		}
		
		movingElement.position = new Vector2f(holding.position);
		target.modifying[0] = true;
		target.modifying[1] = false;
		target = null;
	}
	
	@Override
	public void remove(boolean changeValue){
		super.remove(changeValue);
		movingElement.remove(false);//movingElement should always be non-null in these classes, so this is neccissary
	}
	
	@Override 
	public void startSim(){
		isSimulating = true;//forgot this at one point
		
		
	}
	
	public void endSim(){
		isSimulating = false;
		target = null;
		this.waitingList = new boolean[4];
	}
	
	
	@Override
	public void notified(int direction,FactoryElement fe){
		if(fe.feInfo.get(0)){//if a conveyor was passed
			ConveyorBelt cb = (ConveyorBelt) fe;
			if(cb.holding.feInfo.get(6)){//if it passes this point, gets ready to start moving
				FactoryPart thatFp = (FactoryPart) cb.holding, thisFp = (FactoryPart) movingElement;
				for(int i = 0; i < 4;i++){//checks if this fp has a value that potential target doesnt
					//prevents attempts to modify a target that would be uneffected from a modification
					if(thisFp.getValueData((int) Math.pow(2, i)) && !thatFp.getValueData((int) Math.pow(2, i))){
						
						waitingList[direction] = true;
						cb.modifying[1] = true;
						//below was before the waiting list
						//this.rotation = direction;//shouldn't be reversed
						//target = cb;
						return;
					}
				}
			}
			
			cb.modifying[0] = true;
		}
	}
}
