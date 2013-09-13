package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class MenuBTest2 extends MenuButton {
	
	TopDown td;

	public MenuBTest2(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga, Image hoveringPic) {
		super(id, pic, pos, layer, container, ga);
		moa.setMouseOverImage(hoveringPic);
		td = ga;
	}

	@Override
	public void handleClick() {
		System.out.println("clickity");

	}

}
