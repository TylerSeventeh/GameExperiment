package pew.pew.topdown;

import org.newdawn.slick.BigImage;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;


public class ElementFactory {
	
	public Player player;
	GUIContext guic;
	TopDown td;
	
	public ElementFactory(GUIContext guiContext, TopDown topD){
		try {
			player = new Player(4,new Image(0,0),new Vector2f(0,0),0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		guic = guiContext;
		td = topD;
	}
	
	public Image getImage(String resDir){
		if(resDir.equalsIgnoreCase("null") || resDir.equalsIgnoreCase("res\\null")){
			return null;
		}
		if(!resDir.contains("res\\")){
			resDir = "res\\" + resDir;
		}
		Image image = null;
		try{
			image = new Image(resDir);
			return image;
		}catch(SlickException e){
			System.out.println("getImage failure, "+e.getMessage());
			return image;
		}
	}
	
	//was used for collision, pointless now
	public Element createBorder(int id,String[] elementData){
		CollidableElement ele;
		Vector2f borderpos = new Vector2f(0,0);
		float direction = 0.0F;
		Image image = null;
		/**
		 * w=1
		 * h=2
		 * w,h,w,h
		 * 
		 * top: 0,0,1,0
		 * left: 0,0,0,2
		 * right: 1,0,0,2
		 * bottom: 0,2,1,0
		 * 
		 *    WRONG, if they collide with each other, but they don't, because i'm far too lazy to fix it that way
		 * 
		 * w=x
		 * h=y
		 * smallest possible while being nonzero = 1
		 * 
		 * top:0,0,x,1
		 * left:0,2,1,y-3
		 * right:x,2,1,y-3
		 * bottom:2,y-3,x-4,1
		 * 
		 *  still wrong?
		 */
		try{
			switch(id){
				case(0)://top
					image = new Image(Integer.valueOf(elementData[0]), 1);
					borderpos.y = -1;
					break;
				case(1)://left
					image = new Image(1, Integer.valueOf(elementData[1]));
					direction = -90.0F;
					borderpos.x = -1;
					break;
				case(2)://right
					image = new Image(1, Integer.valueOf(elementData[1]));
					borderpos.x = Integer.valueOf(elementData[0]);
					direction = 90.0F;
					break;
				case(3)://bottom
					image = new Image(Integer.valueOf(elementData[0]), 1);
					borderpos.y = Integer.valueOf(elementData[1]);
				    direction = 180.0F;
					break;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		//System.out.println(image.getWidth() + " " + image.getHeight() + " : " + borderpos.x + " " + borderpos.y + " ; " + id);
		ele = new CollidableElement(id,image,borderpos,3);
		ele.isRenderable = false;
		ele.direction = direction;
		return ele;
		
	}
	
	public Element createElement(String type, int id, String resDir,String[] elementData){
		System.out.println("creating: " + type);
		
		switch(type){
		case("Border"):{
			return createBorder(id,elementData);
		}
		case("LayerElement"):
			return new LayerElement(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]));
		case("CollidableElement"):
			return new CollidableElement(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]));
		case("Player"):
			player = new Player(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]));
			return player;
		case("InteractableTest"):
			return new InteractableTest(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]));
		case("MenuBTest2"):
			return new MenuBTest2(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]),guic,td,getImage("res\\" + elementData[3]));
		case("MenuBTest1"):
			return new MenuBTest1(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]),guic,td,getImage("res\\" + elementData[3]));
		case("NewGameButton"):
			return new NewGameButton(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]),guic,td,getImage("res\\" + elementData[3]));
		case("LabelElement"):
			return new LabelElement(id,getImage(resDir),new Vector2f(Integer.valueOf(elementData[0]),Integer.valueOf(elementData[1])),
					Integer.valueOf(elementData[2]),elementData[3]);
		
		}
			
		
		BigImage image = null;
		return new LayerElement(id,image, new Vector2f(),-1);
	}
}
