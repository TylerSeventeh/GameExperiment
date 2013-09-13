package pew.pew.topdown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;


public class LayerRenderer {
	
	HashMap<Integer,ArrayList<LayerElement>> posElements;
	HashMap<Integer,ArrayList<LayerElement>> negElements;
	
	public LayerRenderer(){
		posElements = new HashMap<Integer,ArrayList<LayerElement>>();
		negElements = new HashMap<Integer,ArrayList<LayerElement>>();
	}
	public void reset(){
		posElements.clear();
		negElements.clear();
		for(int i = 0;i>-2;i--){
			negElements.put(i, new ArrayList<LayerElement>());
		}
		for(int i = 1;i < 4;i++){
			posElements.put(i, new ArrayList<LayerElement>());
		}
	}
	
	//to be used in the update loop
	public void checkLayerChanges(){
		HashMap<Integer,ArrayList<LayerElement>> changedEles = new HashMap<Integer,ArrayList<LayerElement>>();
		//find changed elements
		int currentLayer = 1;
		for(ArrayList<LayerElement> layer : posElements.values()){
			changedEles.put(currentLayer, new ArrayList<LayerElement>());
			for(LayerElement ele : layer){
				if(ele.layer != currentLayer){
					changedEles.get(currentLayer).add(ele);
				}
			}
			currentLayer++;
		}
		currentLayer = 0;
		for(ArrayList<LayerElement> layer : negElements.values()){
			changedEles.put(currentLayer, new ArrayList<LayerElement>());
			for(LayerElement ele : layer){
				if(ele.layer != currentLayer){
					changedEles.get(currentLayer).add(ele);
				}
			}
			currentLayer--;
		}
		//update them in the hashmaps
		for(Entry<Integer,ArrayList<LayerElement>> layer : changedEles.entrySet()){
			for(LayerElement ele : layer.getValue()){
				//remove from old array
				if(layer.getKey() > 0){
					posElements.get(layer.getKey()).remove(ele);
				}else{
					negElements.get(layer.getKey()).remove(ele);
				}
				//add to new array
				if(ele.layer > 0){
					posElements.get(ele.layer).add(ele);
				}else{
					negElements.get(ele.layer).add(ele);
				}
			}
		}
	}
	
	public void add(LayerElement ele){
		if(ele.layer > 0){
			posElements.get(ele.layer).add(ele);
		}else{
			negElements.get(ele.layer).add(ele);
		}
	}
	
	public void remove(LayerElement ele){
		if(ele.layer > 0){
			posElements.get(ele.layer).remove(ele);
		}else{
			negElements.get(ele.layer).remove(ele);
		}
	}
	
	public void renderPos(Graphics g){
		//so that it renders them in order, if i used values() it would render them in the order they were added
		for(int i : posElements.keySet()){
			for(LayerElement ele : posElements.get(i)){
				ele.render(g);
			}
		}
	}
	
	public void renderNeg(Graphics g){
		for(int i : negElements.keySet()){
			for(LayerElement ele : negElements.get(i)){
				ele.render(g);
			}
		}
	}
	
}
