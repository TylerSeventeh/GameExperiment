package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class TrayArrow extends MenuButton {

	FactoryElementTray fet;
	boolean isLeft;
	
	public TrayArrow(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga,FactoryElementTray fet,boolean isL) {
		super(id, pic, pos, layer, container, ga);
		isLeft = isL;
		this.fet = fet;
	}

	@Override
	public void handleClick() {
		fet.cycle(isLeft);
	}

}
