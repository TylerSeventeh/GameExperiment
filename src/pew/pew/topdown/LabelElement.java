package pew.pew.topdown;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class LabelElement extends LayerElement {
	
	String label;
	Vector2f labelPos;
	
	/**
	 * 
	 * @param labelString just for the default label, not neccisarily neccisary
	 */
	
	public LabelElement(int id, Image pic, Vector2f pos, int layer,String labelString) {
		super(id, pic, pos, layer);
		label = labelString; 
		labelPos = new Vector2f();
		updateLabelPos();
	}
	
	@Override
	public void render(Graphics g){
		if(isRenderable) g.drawImage(image, position.x, position.y);
		g.drawString(label, labelPos.x, labelPos.y);
	}
	
	public void updateLabelPos(){
		if(isRenderable){
			labelPos.x = position.x + ((image.getWidth() / 2) - (label.length() * 4.35F));
			labelPos.y = position.y +(image.getHeight()/2) - 10;
		}else{
			labelPos = position;
		}
	}
	
	//TODO detect changes with update(int delta)
	
	
}
