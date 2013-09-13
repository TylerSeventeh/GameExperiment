package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public abstract class PromptButton extends MenuButton {
	
	boolean hasFocus;
	
	public PromptButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga) {
		super(id, pic, pos, layer, container, ga);
		typeArray.set(5, true);
		isGrayed = false;
	}
}
