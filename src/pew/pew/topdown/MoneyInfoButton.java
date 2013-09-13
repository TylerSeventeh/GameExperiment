package pew.pew.topdown;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class MoneyInfoButton extends MenuButton {
	//TODO make the prompt display more info
	
	//TODO balance

	private int buttonMoney;
	int promptId, overflow;
	long actualMoney;
	String suffix;
	Font font;
	Vector2f stringPos;
	
	
	
	public MoneyInfoButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown td, long startingMoney) {
		super(id, pic, pos, layer, container, td);
		actualMoney = startingMoney;
		stringPos = new Vector2f();
	}

	@Override
	public void render(Graphics g){
		if(font == null){
			font = g.getFont();
			return;
		}
		
		if(isRenderable){
			if(isGrayed){
				image.draw(position.x,position.y,Color.lightGray.brighter());
			}else{
				image.draw(position.x,position.y);
			}
			font.drawString(stringPos.x, stringPos.y, "$" + String.valueOf(buttonMoney) + suffix,Color.black);
		}
		
	}
	
	@Override
	public void update(int delta){
		
		if(td.fE.sim.isSimulating){//TODO make depend on what there is to run
			overflow += delta;
			if(overflow > 6000){//"running" fees, - one monies every 6 seconds
				overflow -= 6000;
				actualMoney -= 1;
			}
		}else{
			overflow = 0;
		}
		//not sure of balancing yet; this could be pointless, but it makes (most) big numbers fit in the button
		if(actualMoney > 999_999_999_999L){
			buttonMoney = (int) (actualMoney/1_000_000_000_000L);
			suffix = "T";
		}else if(actualMoney > 999_999_999L){
			buttonMoney = (int) (actualMoney/1_000_000_000L);
			suffix = "B";
		}else if(actualMoney > 999_999L){
			buttonMoney = (int) (actualMoney/1_000_000L);
			suffix = "M";
		}else if(actualMoney > 9999L){
			buttonMoney = (int) (actualMoney/1_000L);
			suffix = "K";
		}else{
			buttonMoney = (int) actualMoney;
			suffix = "";
		}
		//gets the centered position of string
		if(font != null){//bleh
			stringPos.y = this.position.y + ((image.getHeight() - font.getHeight("$" + String.valueOf(buttonMoney)+ suffix)) /2);
			stringPos.x = this.position.x + ((image.getWidth() - font.getWidth("$" + String.valueOf(buttonMoney)+ suffix)) /2);
		}
	}
	
	public static int getValue(FactoryPart part){
		int value = 0;
		if(part.getValueData(1)){
			value = part.baseValue;
			if(value == 0) value = 5;
		}
		if(part.getValueData(8)){
			if(part.getValueData(4)){
				value = Math.round((float)value * 1.5F);
			}else{
				int tenth = Math.round((float)value * 0.1F);
				if(tenth < 1) tenth = 1;
				value -= tenth;
			}
		}else{
			if(part.getValueData(4)){
				value += 1;
			}
		}
		if(part.getValueData(2)){
			value *= 2;
		}
		return value;
	}
	
	@Override
	public void handleClick() {
		if(isGrayed) return;
		promptId = td.promptMan.promptId + 1;
		if(td.queue.get(2).isEmpty()) td.queue.get(2).add(this);//the conditional is to prevent loading more than one prompts
		//per update, which would cause problems with the id system (not that it's used for anything..)
	}
	
	/**
	 * called when the queue in td needs to make a new prompt for this;
	 * obsolete but i dont feel like replacing it with the new version
	 */
	public String getData(){// I dont know why I made a method for this
		return "moneydata 300 200";
	}
}
