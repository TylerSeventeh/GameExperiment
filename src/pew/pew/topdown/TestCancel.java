package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class TestCancel extends TestOk {

	public TestCancel(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga, Image hoverImage) {
		super(id, pic, pos, layer, container, ga, hoverImage);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handleClick() {
		td.queue.get(3).add(this);
	}
}
