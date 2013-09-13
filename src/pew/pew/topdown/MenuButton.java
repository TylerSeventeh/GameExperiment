package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;


public abstract class MenuButton extends ClickableElement {

	TopDown td;
	
	public MenuButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container,TopDown td) {
		super(id, pic, pos, layer, container);
		this.td = td;
		
	}
	
}
