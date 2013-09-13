package pew.pew.topdown;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

public class TextElement extends LayerElement {
	
	NumberField nf;
	TopDown td;
	GUIContext g;
	

	public TextElement(int id, Image pic, Vector2f position,int layer, GUIContext g,TopDown td) {
		super(id, pic, position, layer);
		this.td = td;
		this.g = g;
		nf = new NumberField(g,g.getDefaultFont(),(int)position.x,(int)position.y,g.getDefaultFont().getWidth("100000000"),
				g.getDefaultFont().getLineHeight());
		nf.setTextColor(Color.black);
		nf.setBackgroundColor(Color.white);
		nf.setBorderColor(Color.black);
		nf.setMaxLength(8);
	}
	
	public TextElement(int id, Image pic, Vector2f position,int layer, GUIContext g,TopDown td,int min, int max) {
		super(id, pic, position, layer);
		this.td = td;
		this.g = g;
		nf = new NumberField(g,g.getDefaultFont(),(int)position.x,(int)position.y,g.getDefaultFont().getWidth("100000000"),
				g.getDefaultFont().getLineHeight(),min,max);
		nf.setTextColor(Color.black);
		nf.setBackgroundColor(Color.white);
		nf.setBorderColor(Color.black);
		nf.setMaxLength(8);
	}
	
	
	@Override
	public void update(int delta) {
		if(!nf.hasFocus()) nf.enforceBoundaries();
	}
	
	@Override
	public void render(Graphics g){
		nf.render(this.g, g);
	}

	@Override
	public void remove(TopDown td){
		super.remove(td);
		nf.deactivate();//not sure if this is needed
		nf.setAcceptingInput(false);
	}

	

}

/* 			FIXED, MINIMIZE
 * 
 *  figure out why this isnt working after first instance
 *  wasn't telling the tf to stop accepting input before I "let go" of it, the input must've thought it was still under
 *  my control and responded as if it were, as it should have.
 */

class NumberField extends TextField{
	
	int minimum, maximum;

	public NumberField(GUIContext container, Font font, int x, int y, int width, int height) {
		super(container, font, x, y, width, height);
		minimum = Integer.MIN_VALUE;
		maximum = Integer.MAX_VALUE;
	}
	
	public NumberField(GUIContext container, Font font, int x, int y, int width, int height, int min,int max) {
		super(container, font, x, y, width, height);
		minimum = min;
		maximum = max;
	}

	@Override
	public void keyPressed(int key, char c){//filters out anything but numbers, backspace, and the left and right keys
		if(input.isKeyDown(Input.KEY_LSHIFT) || input.isKeyDown(Input.KEY_RSHIFT)) return;
		
		if(key <= Input.KEY_0 || key == Input.KEY_BACK || key == Input.KEY_LEFT || key == Input.KEY_RIGHT ){
			super.keyPressed(key, c);
		}
	}
	
	public void enforceBoundaries(){
		if(this.getText().equals("")){
			this.setText(String.valueOf(minimum));
			this.setCursorPos(this.getText().length());
			return;
		}
		int num = Integer.parseInt(this.getText());
		if(num >= maximum){
			num = maximum;
			this.setText(String.valueOf(num));
		}else if(num <= minimum){
			num = minimum;
			this.setText(String.valueOf(num));
		}
	}
}
