package pew.pew.topdown;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class CollisionCase {
	
	// No element collides with itself
	
	HashMap<Integer,ArrayList<CollidableElement>> collidables;
	HashMap<Integer,ArrayList<Integer>> caseTypes;
	
	public CollisionCase(){
		collidables = new HashMap<Integer,ArrayList<CollidableElement>>();
		caseTypes = new HashMap<Integer,ArrayList<Integer>>();
	}
	
	
	public ArrayList<Integer> getNeccesaryTypes(){
		ArrayList<Integer> neccTypes = new ArrayList<Integer>();
		for(int i:caseTypes.keySet()){
			if(!neccTypes.contains(i)) neccTypes.add(i);
			for(int num : caseTypes.get(i)){
				if(!neccTypes.contains(num)) neccTypes.add(num);
			}
		}
		return neccTypes;
	} 
	
	public void setElements(ArrayList<CollidableElement> eles, int type){
		collidables.put(type, eles);
	}
	
	public void detectCollisions(){
		
		int caseType = 0;// so the CollisionCase knows what case got passed to it
		
		for(int baseType : caseTypes.keySet()){//each type that is a key 
			for(int testingType : caseTypes.get(baseType)){//each type to test against each element of the base type
				caseType++;
				for(CollidableElement baseElement : collidables.get(baseType)){//each element of the base type
					for(CollidableElement testingElement : collidables.get(testingType)){//each element of the test type
						if(baseElement.getShape().intersects(testingElement.getShape()) && baseElement.id != testingElement.id){
							handleCase(baseElement,testingElement,caseType);
						}
					}
				}
			}
		}
	}
	
	private void backTrackLoop(CollidableElement baseEle, CollidableElement testEle){
		//assumes the direction is already set
		float tempSpeed = baseEle.speed;
		baseEle.speed = 0.1F;
		int i = 0;
		do{
			baseEle.update(1);
			System.out.println("meh " + i);
			i++;
		}while(baseEle.getShape().intersects(testEle.getShape()));
		baseEle.speed = tempSpeed;
	}

	public void parentBackTrack(CollidableElement baseEle, CollidableElement testEle, boolean reversed){
		float tempDirection = baseEle.direction;
		if(reversed){
			baseEle.direction = baseEle.parentElement.lastDirection;
		}else{
			baseEle.direction = baseEle.parentElement.direction;
		}
		backTrackLoop(baseEle,testEle);
		baseEle.direction = tempDirection;
	}
	
	@Deprecated
	public void stillBackTrack(CollidableElement baseEle, CollidableElement testEle){
		//the base element is always the ele pushed back
				float baseX = baseEle.getShape().getCenterX();
				float baseY = baseEle.getShape().getCenterY();
				float testX = testEle.getShape().getCenterX();
				float testY = testEle.getShape().getCenterY();
				//get the slope and convert it to degrees
				float slope = (float) Math.toDegrees( Math.atan( (double)((baseY - testY) / (baseX - testX)) ) );
				//boolean leftmostEle = baseX == Math.min(baseX, testX); if true, base is leftmost, if false, test is.
				float tempDirection = baseEle.direction;
				if(baseX == Math.min(baseX, testX)) {
					baseEle.direction = slope;
					baseEle.reversed = true;
					backTrackLoop( baseEle,  testEle);
					baseEle.reversed = false;
				}else{
					baseEle.direction = slope;
					backTrackLoop( baseEle,  testEle);
				}
				baseEle.direction = tempDirection;
	}
	
	public void backTrack(CollidableElement baseEle, CollidableElement testEle) {
		if(testEle.directionChanged){
			parentBackTrack(baseEle,testEle,true);
		}else if(testEle.speed == 0){
			System.out.println("this");
			parentBackTrack(baseEle,testEle,false);
		}
	}
	
	/**
	 * 
	 * @param baseType the smaller (if possible) element that is tested
	 * @param testType the larger (if possible) element that is tested
	 * @param caseNumber the type of case; the type of the base and test element;
	 * 
	 * self descriptive
	 */
	public abstract void handleCase(CollidableElement baseElement,CollidableElement testType,int caseNumber);
	
}
