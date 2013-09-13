package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class TestOk extends PromptButton {
	
	int okclicks;

	public TestOk(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga, Image hoverImage) {
		super(id, pic, pos, layer, container, ga);
		this.moa.setMouseOverImage(hoverImage);
		okclicks = 1;
	}
	

	@Override
	public void handleClick() {
		System.out.println("OKed" + okclicks);
		okclicks++;
	}

}
