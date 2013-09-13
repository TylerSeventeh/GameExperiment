package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class FactoryElement extends LayerElement {
	
	public static final int TOTAL_FE_INFO = 8;
	ArrayList<Boolean> feInfo;//like typeArray for fes
	FactorySquare fs;//i REALLY should've made a modifier method for this; with changeposition
	TopDown td;
	Image bgImage;
	
	//the image width/height stay constant while these change and the image is rendered depending on these
	int rotation;//0 left, 1 up, etc
	long value;
	float width,height;
	Vector2f fsPos;
	boolean isSimulating;
	
	public FactoryElement(int id, Image pic, int layer, FactorySquare fs, TopDown td) {
		super(id, pic, null, layer);
		this.td = td;
		rotation = 0;//left by default
		/**
		 * 
		 * 0	is a conveyor belt; absolutely neccisary for any type of conveyorbelt that uses its holding and movingElement variables
		 * 1	able to be put on a conveyor belt; to prevent things like conveyorbelts on conveyorbelts
		 * 2	if it cannot be moved; in cases where it is meant to be in the way or something like an input
		 * 3	takes clicking input; is a clickableFE
		 * 4	can take items from a conveyor belt; can be a target for a conveyor belt
		 * 5    prompt button
		 * 6	factoryPart; something that gives money when it goes in an output element; just calling them "parts"
		 * 7	"modifier"; something to give different value to a FactoryPart
		 * 	Dont forget to update totalfeinfo
		 */
		
		feInfo = new ArrayList<Boolean>();
		for(int i = 0;i < TOTAL_FE_INFO;i++){
			feInfo.add(false);
		}
		this.fs = fs;
		position = new Vector2f();
		width = 0;
		height = 0;
		changePosition();
	}
	
	/**
	 * 
	 * uses the current position of the fs, should be executed every time the fs is changed.
	 * also updates bgImage
	 * didnt think of a modifier method for fs at the time of creation
	 * 
	 */
	
	public void checkRotation(){
		if(image != null){
			while(image.getRotation() != rotation*90){
				image.rotate(90);
				changePosition();
			}
		}
	}
	
	public void changePosition(){
		isRenderable = image != null;
		if(fs == null && this.isRenderable){
			width = image.getWidth();
			height = image.getHeight();
			return;
		}else if(fs == null){
			return;
		}
		fsPos = fs.pos;
		bgImage = fs.backgroundImage;
		if(this.isRenderable){
			width = makeFit(true);
			height = makeFit(false);
			position.x = center(true);//adjusts position so that it is centered
			position.y = center(false);
		}
	}
	
	//no need for these two methods, was just easier for me to make them that way
	public float center(boolean isX){
		float pos;
		if(isX && (rotation == 0 || rotation == 2)){//if horiz
			pos = fsPos.x + ((fs.width - width)/2);
		}else if(rotation == 0 || rotation == 2){
			pos = fsPos.y + ((fs.height - height)/2);
			
		}else if(isX && (rotation == 1 || rotation == 3)){
			pos = fsPos.x + ((fs.width - height)/2);
		}else{
			pos = fsPos.y + ((fs.height - width)/2);
		}
		//TODO make sure any centering has this rounding in it; the fractional position is messy apparently
		return Math.round(pos);
	}
	
	public float makeFit(boolean  isWidth){//just dont make fe textures bigger than 39x39 if they rotate, it gets really wacky
		//actually rotations are wacky anyways but if they are bigger than that it gets more noticable
		
		float i;
		if(isWidth && (rotation == 0 || rotation == 2)){
			i = image.getWidth();
			while(i+2 > fs.width){//probably shouldve made these for-loops but it wouldnt cause problems either way
				i--;
			}
		}else if(rotation == 0 || rotation == 2){
			i = image.getHeight();
			while(i+2 > fs.height){
				i--;
			}
			
		}else if(isWidth && (rotation == 1 || rotation == 3)){
			i = image.getWidth();
			while(i+2 > fs.height){
				i--;
			}
		}else{
			i = image.getHeight();
			while(i+2 > fs.width){
				i--;
			}
		}
		return i;
	}
	
	@Override
	public void update(int delta){
		checkRotation();
	}
	
	@Override
	public void render(Graphics g){
		if(fs != null && fsPos != null && fs.doesExist){
			this.bgImage.draw(fsPos.x,fsPos.y,fs.width,fs.height);
		}
		
		if(isRenderable){
			this.image.draw(position.x, position.y, width, height);
			
		}
	}
	
	public String getSaveData(){
		int posX,posY;//the numbers to get the fs in the grid in the factory block
		if(fs == null){
			posX = -1;
			posY = -1;
		}else{
			 posX = (int)(fs.pos.x/51);
			 posY = (int)((fs.pos.y - 41)/41);
		}
		if(image != null){
			return ("default" + ";" + id + ";" + image.getResourceReference() +
					";" + layer + ";" + SavesManager.getFeInfo(feInfo) + posX + "," + posY);
		}else if(feInfo.contains(true)){//doesnt have image but has feInfo
			return ("default" + ";" + id + ";" + "null" +
					";" + layer + ";" + SavesManager.getFeInfo(feInfo) + posX + "," + posY);
		}
		return "";
	}
	
	/**
	 * removes this element and any sub-elements from the game completely(ish)
	 */
	
	public void remove(boolean changeValue){//TODO should probably move this to the base Element class
		td.queue.get(0).add(this);
		if(changeValue)
			td.fE.moneyButton.actualMoney += value;
	}
	
	public void startSim(){
		isSimulating = true;
	}
	public void endSim(){
		isSimulating = false;
	}
	
	/**
	 * 
	 * i from
	 * 0 whole block
	 * 1 left
	 * 2 top
	 * 3 right
	 * 4 bottom
	 */
	
	public void notified(int from, FactoryElement fe){
		//to be overrided
		System.out.println("normal fe notified");
	}
	
	public static int reverseDirection(int previousDirection){
		switch(previousDirection){
		case 0: return 2;
		case 1: return 3;
		case 2: return 0;
		case 3: return 1;
		}
		return -1;
	}

}
