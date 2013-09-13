package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class CollidableElement extends Element {
	
   /**
    * Collision types:
    * neg infinity to 0: no collide; placeholder value
    * 1: smallest BasicCollision; pushes nothing
    * 2: middle BasicCollision; pushes 1s
    * 3: largest BasicCollision; doesnt move and therefor pushes nothing, but cannot be pushed
    * 
    */
	
	public int  collisionType;
	public CollidableElement parentElement;
	public float lastDirection;
	public boolean isPushed;
	public boolean directionChanged;
	public boolean isPushing;
	
	public CollidableElement(int id, Image pic,Vector2f pos, int colType){
		super(id,pic,pos);
		collisionType = colType;
		typeArray.set(1, true);
		if(id != -1){
			parentElement = new CollidableElement(-1,pic,new Vector2f(0,0),0);
			parentElement.direction = direction;
		}
			
	}
	
	public void collidableUpdate(int delta){
		if(collisionType == 3) return;
		directionChanged = lastDirection != direction && isPushing;
		isPushing = false;
		
		if(isPushed){
			//match parameters to object being pushed by while keeping previous values
			float prevSpeed = speed;
			float prevDirection = direction;
			speed = parentElement.speed;
			direction = parentElement.direction;
			isPushed = false;
			defaultUpdate(delta);
			speed = prevSpeed;
			direction = prevDirection;
			return;
		}
		defaultUpdate(delta);
	}
	
	@Override
	public void update(int delta) {
		collidableUpdate(delta);
	}
		
	
	public Shape getShape(){
		return new Rectangle(position.x,position.y,image.getWidth(),image.getHeight());
	}
	
	public int getCollideType(){
		return collisionType;
	}
	public void setColideType(int type){
		collisionType = type;
	}

}
