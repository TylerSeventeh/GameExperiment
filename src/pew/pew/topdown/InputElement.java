package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class InputElement extends ConveyorBelt {
	
	/**	FIXED
	 *  	   minimize this if not reveiwing
	 * 

	 * figure out what is happening in simulations with this (done)
	 * 
	 * edit 1: when notifying, the adjacents to the targets images aren't rendering correctly, and the image for this isn't rendered
	 * correctly as soon as the simulation button is pressed
	 * 
	 * edit 2: it appears that the backgrounds are being rendered over everything, so maybe its creating
	 * 
	 * fixed: when i was creating new elements in holding or movingElement i was setting it's fs to whatever fs
	 * was local at that time, which was causing it to render them, replaced that with null so it doesnt
	 * 
	 */
	
	/**
	 * doesnt cost until the held element leaves this one
	 * 0	default factory part
	 * 1	fp with value of 3
	 */
	
	Image heldImage;
	int eleType;
	private ArrayList<Integer> data;
	//boolean isMoving; served the same purpose as isSimlating

	public InputElement(int id, Image pic, int layer, FactorySquare fs, int inputElementType, TopDown td, Image heldIm) {
		super(id, pic, layer, fs,td);
		this.feInfo.set(4, false);//dont want this to take input like a conveyorbelt
		eleType = inputElementType;
		movementSpeed = 20;//slightly slower than the regular conveyorbelts
		heldImage = heldIm;
		holding = this.getElement();
		td.updateManagers(holding);
	}
	
	public InputElement(int id, Image pic, int layer, FactorySquare fs, TopDown td, Image heldIm, ArrayList<Integer> data) {
		super(id, pic, layer, fs,td);
		this.feInfo.set(4, false);//dont want this to take input like a conveyorbelt
		eleType = 5;
		movementSpeed = 20;//slightly slower than the regular conveyorbelts
		heldImage = heldIm;
		this.data = data;
		holding = this.getElement();
		td.updateManagers(holding);
	}
	
	@Override
	public void startSim(){
		isSimulating = true;
		if(movingElement == null){
			
			if(td.fE.moneyButton.actualMoney - 5 < 0){ 
				return;
			}
			movingElement = this.getElement();
			movingElement.position.x = Math.round(position.x + ((image.getWidth() - movingElement.width)/2));
			movingElement.position.y = Math.round(position.y + ((image.getHeight() - movingElement.height)/2));
			td.queue.get(1).add(movingElement);
		}
	}
	
	@Override
	public void endSim(){
		isSimulating = false;
		td.queue.get(0).add(movingElement);
		movingElement = null;
		target = null;
		this.modifying = new boolean[4];
	}
	
	@Override
	public void render(Graphics g){
		if(fs != null){
			if(fs.doesExist) {
				this.bgImage.draw(fsPos.x, fsPos.y, fs.width, fs.height);
				//something was going to go here...?
				
			}
		}
		if(isRenderable){
			this.image.draw(position.x, position.y, width, height);
		}
	}
	
	@Override
	public void update(int delta){
		if(!isSimulating){
			if(holding != null){
				if(holding.layer < 2) holding.layer = 2;
				holding.position.x = Math.round(position.x + ((image.getWidth() - holding.width)/2));
				holding.position.y = Math.round(position.y + ((image.getHeight() - holding.height)/2));
			}
			checkRotation();//rotation should never change while simulating, at least for now.
		}else{//is simulating
			
			if(moveElement(delta,movingElement,target)) return;//moves movingElement
			
			//to check if should start moving
			if(fs.adjacent.get(rotation).subEle.feInfo.get(4)){//checks if to-be target is a conveyor belt
				target = (ConveyorBelt) fs.adjacent.get(rotation).subEle;
				
				for(FactorySquare fs : target.fs.adjacent){//checks if there is already another conveyorbelt moving to the target
					if(fs.subEle.feInfo.get(0)){
						ConveyorBelt temp = (ConveyorBelt) fs.subEle;
						if(temp != this && temp.movingElement != null && temp.target == target){
							target = null;
							return;
						}
					}
				}
				if(target.holding != null){
					target = null;
					return;
				}
			}else{
				return;
			}
			if(movingElement != null) startMovement();
		}
	}
	
	
	@Override
	public void endMovement(){
		FactoryPart tempPart = (FactoryPart) movingElement;
		td.fE.moneyButton.actualMoney -= MoneyInfoButton.getValue(tempPart);   //MEH 
		target.notified(FactoryElement.reverseDirection(this.rotation), movingElement);
		movingElement = null;
		target = null;

		if(td.fE.moneyButton.actualMoney - 5 < 0){ 
			return;
		}
		movingElement = this.getElement();
		movingElement.position.x = Math.round(position.x + ((image.getWidth() - movingElement.width)/2));
		movingElement.position.y = Math.round(position.y + ((image.getHeight() - movingElement.height)/2));
		td.queue.get(1).add(movingElement);
	}
	
	public FactoryElement getElement(){
		switch(eleType){
		case 1:	
			return new FactoryPart(id,new Image(heldImage.getTexture()),3,null,td);
		case 2:
			FactoryPart temp = new FactoryPart(id,new Image(heldImage.getTexture()),2,null,td);
			temp.valueData = 2;
			return temp;
		case 3:
			FactoryPart temp2 = new FactoryPart(id,new Image(heldImage.getTexture()),2,null,td);
			temp2.valueData = 4;
			return temp2;
		case 4:
			FactoryPart temp3 = new FactoryPart(id,new Image(heldImage.getTexture()),2,null,td);
			temp3.valueData = 8;
			return temp3;
		case 5:
			FactoryPart temp4 = new FactoryPart(id,new Image(heldImage.getTexture()),2,null,td);
			temp4.baseValue = data.get(0);
			temp4.valueData = data.get(1);
			return temp4;//forgot to do this at one point; remembering fixed bugs
		default: case(0):
			return null;
		}
	}
	
	@Override
	public void remove(boolean changeValue){
		super.remove(false);//so that it will not subtract the value of the element that is being "held" by this
		td.fE.moneyButton.actualMoney += value;
		//again, should not be removed when simulating, so movingElement should never be non-null
	}
	
	@Override
	public String getSaveData(){
		String dat = super.getSaveData();
		dat = dat.replace("conveyorbelt", "input");
		dat = dat.concat(";" + eleType);//should be the 9th thing seperated by ";" accessed by [8]
		//add "data" if eleType == 5
		return dat;
	}
	
}
