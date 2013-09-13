package pew.pew.topdown;

import java.util.ArrayList;
import java.util.HashMap;


public class NCollisionManager {
	
	boolean updated;//says if any element has changed so the CollisionCases can be reset
	ArrayList<CollidableElement> elements; // raw  list of collidable elements
	HashMap<Integer, ArrayList<CollidableElement>> sortedElements;// sorted elements to be used in handleCollisions()
	
	ArrayList<CollisionCase> cases;//list of every CollisionCase in effect
	
	public NCollisionManager(){
		updated = true;
		elements = new ArrayList<CollidableElement>();
		cases = new ArrayList<CollisionCase>();
		sortedElements = new HashMap<Integer, ArrayList<CollidableElement>>();
		
		//Collision case types init goes here
		//BasicCollision bc = new BasicCollision();
		cases.add(new BasicCollision());
	}
	
	public boolean add(CollidableElement ele){
		if(!elements.contains(ele)){
			elements.add(ele);
			return true;
		}else{
			System.out.println("tried to add an already recognized collidable");
			return false;
		}
	}
	
	public void sortElements(){
		updated = true;
		ArrayList<ArrayList<CollidableElement>> tempEles = new ArrayList<ArrayList<CollidableElement>>();
		ArrayList<CollidableElement> tempEle = new ArrayList<CollidableElement>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		int type;
		for(CollidableElement ele: elements){
			type = ele.getCollideType();
			if(!sortedElements.keySet().contains(type)){
				tempEles.add(new ArrayList<CollidableElement>());
				indices.add(type);
				tempEles.get(indices.indexOf(type)).add(ele);
				sortedElements.put(type, tempEles.get(indices.indexOf(type)));
			}else{
				tempEle = tempEles.get(indices.indexOf(type));
				tempEle = sortedElements.get(type);
				tempEle.add(ele);
				sortedElements.put(type, tempEles.get(indices.indexOf(type)));
			}
		}
	}
	
	public boolean remove(CollidableElement ele){
		updated = true;
		int index = elements.indexOf(ele);
		if(index != -1){
			elements.remove(index);
			return true;
		}else{
			System.out.println("tried to remove nonexistant collidable");
			return false;
		}
	}

	private void setCollisionCases(){
		ArrayList<ArrayList<CollidableElement>> potentialCollidables = new ArrayList<ArrayList<CollidableElement>>();
		ArrayList<CollidableElement> currentArray;
		
		for(CollisionCase colCase : cases){
			int i2 = 0;//current loop number
			for(int i1 : colCase.getNeccesaryTypes()){
				currentArray = new ArrayList<CollidableElement>();
				potentialCollidables.add(currentArray);
				if(sortedElements.containsKey(i1) && !sortedElements.get(i1).isEmpty()){
					for(CollidableElement ele : sortedElements.get(i1)){
						currentArray.add(ele);
					}
				}
				colCase.setElements(potentialCollidables.get(i2), i1);
				i2++;
			}
		}
		updated = false;
	}
	
	public void handleCollisions(){
		
		if(updated) setCollisionCases();
		for(CollisionCase  colCase : cases){
			colCase.detectCollisions();
			
		}
	}
	
}
