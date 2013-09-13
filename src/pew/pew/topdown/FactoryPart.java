package pew.pew.topdown;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class FactoryPart extends FactoryElement {
	
	/* TODO figure out other stuff to add to valueData
	 * might as well be a byte but i'm not sure if i'm going to need more room
	 * 1	has baseValue; if baseValue is 0, use 5 as the value; if a part doesnt have this then its value is 0
	 * 2	multiply end value by 2 (should probably have made this 8)
	 * 4	if also value & 8, add 10 instead of 1, otherwise subtracts a tenth of value or 1 (whichever is higher) from itslef 
	 * 8	adds 1 to base value (or should it be a tenth?(then that would make 4 just double value
	 * 									(maybe something to do with squares/square roots)))
	 * 
	 *  TODO make this display in a different format; colors maybe?
	 */
	//Vector2f upperLeft, upperRight, lowerLeft, lowerRight;
	Vector2f displayPos;
	int baseValue, valueData;
	String display;
	
	
	public FactoryPart(int id, Image pic, int layer, FactorySquare fs,TopDown td) {
		super(id, pic, layer, fs, td);
		feInfo.set(1, true);
		feInfo.set(6, true);
		valueData = 1;
		displayPos = new Vector2f();
		
		/*upperLeft = new Vector2f();
		upperRight = new Vector2f();
		lowerLeft = new Vector2f();
		lowerRight = new Vector2f();*/
	}
	
	@Override
	public void update(int delta){
		
		
		display = String.valueOf(valueData);
		
		/*Vector2f temp = new Vector2f(position);
		if(getValueData(1)){
			upperLeft = temp;
		}
		temp = new Vector2f(position);
		if(getValueData(2)){
			upperRight = temp.add(new Vector2f(10,0));
		}
		temp = new Vector2f(position);
		if(getValueData(4)){
			lowerLeft = temp.add(new Vector2f(0,10));
		}
		temp = new Vector2f(position);
		if(getValueData(8)){
			lowerRight = temp.add(new Vector2f(10,10));
		}*/
	}
	
	
	@Override
	public void render(Graphics g){
		if(this.isRenderable){
			image.draw(position.x, position.y, width, height);
			if(display != null){
				displayPos.x = Math.round(position.x + ((image.getWidth() - g.getFont().getWidth(display))/2));//bleh
				displayPos.y = Math.round(position.y + ((image.getHeight() - g.getFont().getHeight(display))/2));
				g.getFont().drawString(displayPos.x, displayPos.y, display, Color.white);
			}
		}
		
		
		
		/*if(valuePic != null){
			if(getValueData(1)){
				valuePic.draw(upperLeft.x, upperLeft.y);
			}
			if(getValueData(2))
				valuePic.draw(upperRight.x,upperLeft.y);
			if(getValueData(4))
				valuePic.draw(lowerLeft.x,lowerLeft.y);
			if(getValueData(8))
				valuePic.draw(lowerRight.x, lowerRight.y);
		}*/
	}
	
	/**
	 * setter method for valueData
	 */
	public void setValueData(boolean add,int bit){
		if(!add && ((valueData & bit) == bit)){ 
			valueData -= bit;
		}else if(add){
			valueData += bit;
		}
	}
	@Override
	public void remove(boolean changeValue){
		super.remove(false);
		if(changeValue)
			td.fE.moneyButton.actualMoney += MoneyInfoButton.getValue(this);
		
	}
	
	public boolean getValueData(int bit){
	return ((bit & valueData) == bit);
	}
	
	
	
}
