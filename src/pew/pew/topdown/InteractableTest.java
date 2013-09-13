package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;


public class InteractableTest extends InteractableElement {

	boolean flop;
	
	public InteractableTest(int id, Image pic, Vector2f pos, int colType) {
		super(id, pic, pos, colType);
		flop = false;
	}

	@Override
	public void interactableUpdate(int delta) {
		if(interactions > 0){
			flop = !flop;
			interactions--;
			curIntr.directionChanged = true;
		}
		if(flop){
			this.parentElement = curIntr;
			isPushed = true;
		}
		collidableUpdate(delta);
	}
	
	@Override
	public void update(int delta){
		interactableUpdate(delta);
	}

}
