package pew.pew.topdown;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;


public abstract class ClickableElement extends LayerElement {

	public MyMouseOverArea moa;
	GUIContext contain;
	boolean isGrayed;
	
	public ClickableElement(int id, Image pic, Vector2f pos, int layer,GUIContext container) {
		super(id, null, null, layer);
		typeArray.set(0, true);
		contain = container;
		position = pos;
		if(pic != null){
			image = pic;
			isRenderable = true;
		}
		if(container != null) moa = new MyMouseOverArea( container,image,(int)pos.x,(int)pos.y);
	}
	
	@Override
	public void render(Graphics g){
		if(isRenderable){
			if(isGrayed){
				image.draw(position.x,position.y,Color.lightGray.brighter());
			}else{
				image.draw(position.x,position.y);
			}
		}
	}
	
	//for use in update(int delta), made seperate so I dont override this
	public void checkReleased(){
		if(moa.mouseReleases > 0){
			handleClick();
			moa.mouseReleases--;
		}
	}
	
	public abstract void handleClick();
	
}

//to be used only by this class or subclasses
class MyMouseOverArea extends MouseOverArea{
	
	int mouseReleases;

	public MyMouseOverArea(GUIContext container, Image image, int x, int y) {
		super(container, image, x, y);
	}
	
	@Override
	public void mouseReleased(int button, int mx, int my){
		if(this.isMouseOver()){
			mouseReleases++;
		}
	}
}