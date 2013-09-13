package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class ClickableTest extends ClickableElement {
	
	int clickCount;

	public ClickableTest(int id, Image pic, Vector2f pos, int layer,
			GUIContext container) {
		super(id, pic, pos, layer, container);
		clickCount = 0;
	}

	@Override
	public void handleClick() {
		System.out.println(clickCount++);
	}

}
