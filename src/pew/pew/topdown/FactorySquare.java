package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.MouseOverArea;

//container for FactoryElements
//NEEDS to have a sub-Element to be visible as a container
public class FactorySquare {
	
	MouseOverArea moa;
	Image backgroundImage;
	ArrayList<FactorySquare> adjacent; //the adjacent factorysquares in the FactoryBlock, doesn't include diagonals
	FactoryElement subEle;
	Vector2f pos;
	int width,height;
	boolean doesExist;
	
	public FactorySquare(int x, int y, int width, int height,boolean exists, GameContainer gc,Image bgIm){
		if(exists) moa = new MouseOverArea(gc,null,x-1, y,width+1,height+1);
		adjacent = new ArrayList<FactorySquare>();// is set in the order, left, above, right, below
		pos = new Vector2f((float)x,(float)y);
		backgroundImage = bgIm;
		doesExist = exists;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * i from
	 * 0 whole block
	 * 1 left
	 * 2 top
	 * 3 right
	 * 4 bottom
	 */
	
}
