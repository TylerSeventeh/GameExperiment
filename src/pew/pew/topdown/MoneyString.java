package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class MoneyString extends StringElement {
	
	MoneyInfoButton mib;

	public MoneyString(int id, Image pic, Vector2f pos, int layer, MoneyInfoButton pancakes) {
		super(id, pic, pos, layer, "");
		mib = pancakes;
	}
	
	@Override
	public void update(int delta){
		super.update(delta);//TODO no idea if the superclass actually uses this
		this.displayString = String.valueOf(mib.actualMoney);
	}
	
	//MEH
	
}
