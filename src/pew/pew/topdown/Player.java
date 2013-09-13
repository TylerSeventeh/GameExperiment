package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;


public class Player extends CollidableElement implements Interactor{
	
	boolean[] movement;
	public int interactions;
	
	public Player(int id, Image pic,Vector2f pos, int colType){
		super(id,pic,pos,colType);
		typeArray.set(4, true);
	}
	

	
	private void playerUpdate(int delta){
		lastDirection = direction;
		if((movement[0] || movement[1] || movement[2] || movement[3])){
			boolean leftTest = movement[1] && !movement[3];
			boolean rightTest = movement[3] && !movement[1];
			//efficiency reasons
			
			if(speed == 0){
				speed = 0.15F;
			}
			if(rightTest){
				direction = 270F;
			}
			if(leftTest){
				direction = 90F;
			}
			
			if(movement[0]){
				if(leftTest){
					direction += 45;
				}else if(rightTest){
					direction -= 45;
				}else{
					direction = 180;
				}
			}
			if(movement[2]){
				if(leftTest){
					direction -= 45;
				}else if(rightTest){
					direction += 45;
				}else{
					direction = 360;
				}
			}
			
		}else{
			speed = 0;
		}
		
		
	}
	
	public void update(int delta, boolean[] movement){
		this.movement = movement;
		
		playerUpdate(delta);
	}




	@Override
	public boolean isInteracting() {
		if(interactions > 0){
			interactions--;
			return true;
		}
		return false;
	}
	
	@Override
	public Shape getShape(){
		return new Rectangle(position.x,position.y,image.getWidth(),image.getHeight());
	}

	@Override
	public Shape getIntShape() {
		return new Rectangle(position.x - 2,position.y - 2,image.getWidth() + 4,image.getHeight() + 4);
	}
	
	
}
