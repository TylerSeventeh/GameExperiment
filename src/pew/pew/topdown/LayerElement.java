package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

//for use of basic backgrounds or overlays so i dont have to make a ton of new elements pointlessly
public class LayerElement extends Element {
	
	public int layer;
	
	public LayerElement(int id, Image pic,Vector2f pos, int layer){
		super(id,pic,pos);
		this.layer = layer;
		typeArray.set(2, true);
	}
	
	public void setLayer(int layer){
		this.layer = layer;
	}
	
	@Override
	public void update(int delta) {
		//nothing to update, except maybe animations later...
	}

}
