package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class NewGameButton extends MenuButton {

	public NewGameButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga, Image hoverImage) {
		super(id, pic, pos, layer, container, ga);
		moa.setMouseOverImage(hoverImage);
	}

	@Override
	public void handleClick() {
		System.out.println("newgame clicked");
		//game.changeLevel("defaultfactory.lvl");
	}
}
