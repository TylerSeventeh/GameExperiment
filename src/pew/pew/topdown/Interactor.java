package pew.pew.topdown;

import org.newdawn.slick.geom.Shape;

public interface Interactor{

	public boolean isInteracting();
	
	/**
	 * 
	 * @return area to test interactivity, should be bigger than rendering shape 
	 */
	public Shape getIntShape();
}
