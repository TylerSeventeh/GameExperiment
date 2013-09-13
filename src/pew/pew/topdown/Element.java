package pew.pew.topdown;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.geom.Vector2f;

public abstract class Element {
	
	protected Vector2f position;
	public float speed;
	protected float rotation;
	public float direction;
	public int id;
	protected Image image;
	protected Renderable renderable;
	protected List<Boolean> typeArray;
	public boolean isRenderable;
	public boolean reversed;
	
	/**
	 *  !!! Make sure to increase typeArray capacity
	 *  
	 * type Ids
	 * 0 clickable
	 * 1 CollidableElement
	 * 2 LayerElement
	 * 3 InteractableElement
	 * 4 Interactor
	 * 5 promptButton
	 * 6 !!!
	 * 7 !!!
	 */
	
	public Element(int id, Image pic,Vector2f position){
		this.id = id;
		this.position = position;
		image = pic;
		if(image == null){
			isRenderable = false;
		}else{
			isRenderable = image.getHeight() > 0 || image.getWidth() > 0;
			renderable = image;
		}
		rotation = 0;
		direction = 0;
		typeArray = new ArrayList<Boolean>();
		for(int i = 0;i<6;i++){ //typearray capacity
			typeArray.add(false);
		}
	}
	
	public int getId(){
		return id;
	}
	
	public boolean getType(int type){
		return typeArray.get(type);
	}
	
	public void render(Graphics g){
		if(isRenderable){
			//g.drawImage(image, position.x, position.y);
			renderable.draw(position.x, position.y);
		}
	}
	
	protected void defaultUpdate(int delta){
		double sine = Math.sin(Math.toRadians(direction));
		double cosine = Math.cos(Math.toRadians(direction));
		if(sine == 0.0D && cosine == 0.0D) cosine = 1;
		if(!reversed){
			position.x -= delta * speed * sine;
			position.y += delta * speed * cosine;
		}else{
			position.x += delta * speed * sine;
			position.y -= delta * speed * cosine;
			
		}
	}
	
	public void remove(TopDown td){
		td.queue.get(0).add(this);
	}
	
	public abstract void update(int delta);
	
	
}
