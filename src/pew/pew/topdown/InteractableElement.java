package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;


public abstract class InteractableElement extends CollidableElement {

	protected int interactions;
	CollidableElement curIntr;
	
	public InteractableElement(int id, Image pic, Vector2f pos, int colType) {
		super(id, pic, pos, colType);
		typeArray.set(3, true);
		
	}
	
	public void addInteraction(Element interactor){
		interactions++;
		curIntr = (CollidableElement)interactor;
	}
	
	
	public abstract void interactableUpdate(int delta);
}
