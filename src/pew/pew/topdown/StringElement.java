package pew.pew.topdown;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class StringElement extends LayerElement {
	
	String displayString;
	Vector2f stringPos;
	Font font;
	Color drawColor;
	
	//can have a background, but isn't required 
	public StringElement(int id, Image pic, Vector2f pos, int layer, String string) {
		super(id, pic, pos, layer);
		this.displayString = string;
		stringPos = new Vector2f();
		drawColor = Color.black;
	}
	
	@Override
	public void render(Graphics g){
		if(font == null){
			font = g.getFont();
		}
		if(this.isRenderable){
			image.draw(position.x, position.y);
		}
		font.drawString(stringPos.x, stringPos.y, displayString, drawColor);
	}
	
	@Override
	public void update(int delta){
		if(this.isRenderable && font != null){
			stringPos.y = this.position.y + ((image.getHeight() - font.getHeight(displayString))/2);
			stringPos.x = this.position.x + ((image.getWidth() - font.getWidth(displayString))/2);
		}else{
			stringPos.y = this.position.y;
			stringPos.x = this.position.x;
		}
	}

}
