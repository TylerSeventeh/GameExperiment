package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Counter extends LabelElement {

	public Counter(int id, Image pic, Vector2f pos, int layer,
			String labelString) {
		super(id, pic, pos, layer, labelString);
		label = "0";
		typeArray.set(6, true);
	}
	
	public void increment(){
		label = String.valueOf( Integer.parseInt(label) + 1);
	}

}
