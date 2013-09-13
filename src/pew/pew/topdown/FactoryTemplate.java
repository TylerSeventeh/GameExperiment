package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class FactoryTemplate extends FactoryElement {
	/**
	 * 0 	doesnt return anything
	 * 1	ConveyorBelt
	 * 2	replace this with anything
	 * 3	Input element
	 * 4	output element
	 * 5	modifier element
	 */
	int eleType;
	Image subImage;//the holding-element of a conveyorbelt TODO make work
	Vector2f conveyorPos;//position of pseudo holding-element
	TopDown td;
	String display;
	private ArrayList<Integer> data;
	//TODO stop depending on psuedoimages in subImage/conveyorImage
	
	/**
	 * 
	 * @param subImage image to be used as a (not so) pseudo holding of a conveyorBelt
	 */
	public FactoryTemplate(int id, Image pic,int layer, FactorySquare fs,int elementType, TopDown td,Image subImage,
			String display,long value) {
		super(id, pic, layer, fs,td);
		eleType = elementType;
		this.value = value;
		this.td = td;
		this.subImage = subImage;
		this.display = display;
		conveyorPos = new Vector2f();
		switch(elementType){
		case(3):case(5):case(6):case(7):
			this.subImage = GetImage.get("res\\factory_tray_test.png");
		break;
		}
	}
	
	public FactoryTemplate(int id, Image pic,int layer, FactorySquare fs,int elementType, TopDown td,Image subImage,String display,long value,
			ArrayList<Integer> data) {
		super(id, pic, layer, fs,td);
		eleType = elementType;
		this.value = value;
		this.td = td;
		this.subImage = subImage;
		this.display = display;
		conveyorPos = new Vector2f();
		this.data = data;
		switch(elementType){
		case(3):case(5):case(6):case(7):
			this.subImage = GetImage.get("res\\factory_tray_test.png");
		break;
		}
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
		if(subImage != null){
			subImage.draw(conveyorPos.x, conveyorPos.y);//TODO conveyorPos must be null, find out why
			if(!display.equalsIgnoreCase("")){
				Vector2f displayPos = new Vector2f();
				displayPos.x = Math.round(conveyorPos.x + ((subImage.getWidth() - g.getFont().getWidth(display))/2));//bleh
				displayPos.y = Math.round(conveyorPos.y + ((subImage.getHeight() - g.getFont().getHeight(display))/2));
				g.getFont().drawString(displayPos.x, displayPos.y, display, Color.white);
			}
		}
	}
	
	@Override
	public void update(int delta){
		super.update(delta);
		if(subImage != null){
			conveyorPos.x = Math.round(position.x + ((image.getWidth() - subImage.getWidth())/2));
			conveyorPos.y = Math.round(position.y + ((image.getHeight() - subImage.getWidth())/2));
		}
	}
	
	public FactoryElement getCopy(int id){
		
		if(td.fE.moneyButton.actualMoney - value < 0){
			return new FactoryElement(-1,null,-1,null,td);
		}
		
		td.fE.moneyButton.actualMoney -= value;
		switch(eleType){
		case 1:
			ConveyorBelt tempBelt = new ConveyorBelt(id,new Image(image.getTexture()),2,null,td);
			tempBelt.width = width;//dont think this is neccisary
			tempBelt.height = height;
			tempBelt.value = value;
			if(data != null){
				tempBelt.movementSpeed = data.get(0);
				tempBelt.rotation = data.get(1);
			}//else use the default settings set in the constructor
			return tempBelt;
		case 2://dont know why i chose 2 for this;
			return null;
		case 3:
			InputElement tempInput;
			
			//TODO Figure whats wrong in the constructor of InputElement
			
			if(data != null){
				tempInput = new InputElement(id,new Image(image.getTexture()),2,null,td,subImage,
						new ArrayList<Integer>(data.subList(2, 4)));
				
				tempInput.movementSpeed = data.get(0);
				tempInput.rotation = data.get(1);
			}else{
				tempInput = new InputElement(id,new Image(image.getTexture()),2,null,1,td,subImage);
			}
			tempInput.value = value;
			return tempInput;
		case 4:
			OutputElement tempOutput = new OutputElement(id,new Image(image.getTexture()),2,null,td);
			tempOutput.value = value;
			return tempOutput;
		case 5:
			FactoryModifier tempModifier;
			if(data != null){
				tempModifier = new FactoryModifier(id,new Image(image.getTexture()),2,null,td,subImage,
						new ArrayList<Integer>(data.subList(2, 4)));
				tempModifier.movementSpeed = data.get(0);
			}else{
				tempModifier = new FactoryModifier(id,new Image(image.getTexture()),2,null,2,td,subImage);
			}
			
			tempModifier.value = value;
			return tempModifier;
		case 6:
			FactoryModifier tempModifier2 = new FactoryModifier(id,new Image(image.getTexture()),2,null,3,td,subImage);
			tempModifier2.value = value;
			return tempModifier2;
		case 7:
			FactoryModifier tempModifier3 = new FactoryModifier(id,new Image(image.getTexture()),2,null,4,td,subImage);
			tempModifier3.value = value;
			return tempModifier3;
		}
		return new FactoryElement(id,this.image,2,null,td);
	}

}
