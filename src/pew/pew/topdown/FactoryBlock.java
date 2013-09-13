package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


//probably should've called this GameFactoryBlock but i hadn't thought of it at the time
public class FactoryBlock {
	
	int w,h;
	static int pixelH = 41, pixelW = 51;
	FactoryEditor fE;
	ArrayList<ArrayList<FactorySquare>> grid; // each internal list is a horizontal row of FS'; use .get(y).get(x);
	//starts at top-left corner
	
	/**
	 * 
	 * @param sWidth number of FactorySquare wide
	 * @param sHeight number of FactorySquare high
	 * @param width total pixel width
	 * @param height pixel height
	 */
	public FactoryBlock(int sWidth, int sHeight,FactoryEditor fE,GameContainer gc){
		this.fE = fE;
		Image im = null;
		try {
			im = new Image("res\\fsbackground.png");
		} catch (SlickException e) {System.out.println(e.getMessage());}
		
		grid = new ArrayList<ArrayList<FactorySquare>>();
		ArrayList<FactorySquare> temp;
		w = sWidth;
		h = sHeight;
		
		for(int i = 1; i<h;i++){
			temp = new ArrayList<FactorySquare>();
			for(int j = 0;j<w ;j++){
				temp.add(new FactorySquare(j * pixelW,i * pixelH,pixelW,pixelH,true, gc,im));
			}
			grid.add(temp);
		}
		initGrid(gc);
		test();
	}
	
	public void test(){
		FactorySquare fs1 = grid.get(0).get(1), fs2 = grid.get(1).get(1);
		Rectangle shape1 = new Rectangle(fs1.pos.x,fs1.pos.y,fs1.backgroundImage.getWidth(),fs1.backgroundImage.getHeight());
		Rectangle shape2 = new Rectangle(fs2.pos.x,fs2.pos.y,fs2.backgroundImage.getWidth(),fs2.backgroundImage.getHeight());
		System.out.println(Boolean.toString(shape1.intersects(shape2)));
		System.out.println(shape1.getMaxY() + " " + shape2.getMaxY());
		System.out.println(shape1.getHeight() + " " + shape2.getHeight());
	}
	
	public void notifyAll(FactorySquare fromFs){
		for(ArrayList<FactorySquare> row : grid){
			for(FactorySquare fs : row){
				fs.subEle.notified(0, fromFs.subEle);
			}
		}
	}
	
	public void initGrid(GameContainer gc){
		FactorySquare left = new FactorySquare(0,1,0,0,false,gc,null),right = grid.get(0).get(1);
		left.subEle = new FactoryElement(-1,null,0,null,fE.td);
		ArrayList<FactorySquare> aboveL = makeBorder(gc),belowL = grid.get(1);

		int i = 1, j = 0;
		for(ArrayList<FactorySquare> pie : grid){
			
			left = new FactorySquare(0,1,0,0,false,gc,null);
			left.subEle = new FactoryElement(-1,null,0,null,fE.td);
			if(grid.size() > i)belowL = grid.get(i);
			else belowL = makeBorder(gc);
			
			for(FactorySquare fs : pie){
				

				if(pie.size() > j+1)right = pie.get(j+1);
				else right = new FactorySquare(0,w+1,0,0,false,gc,null);
				
				fs.adjacent.add(left);
				fs.adjacent.add(aboveL.get(j));
				fs.adjacent.add(right);
				fs.adjacent.add(belowL.get(j));
				
				left = fs;
				j++;
			}
			aboveL = pie;
			i++;
			j = 0;
		}

		
	}
	
	public ArrayList<FactorySquare> makeBorder(GameContainer gc){
		ArrayList<FactorySquare> tempL = new ArrayList<FactorySquare>();
		
		for(int i = 0; i < w;i++){
			tempL.add(new FactorySquare(0,0,0,0,false,gc,null));
			tempL.get(i).subEle = new FactoryElement(-1,null,0,null,fE.td);
		}
		return tempL;
	}
	
}
