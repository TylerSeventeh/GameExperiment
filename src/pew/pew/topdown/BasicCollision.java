package pew.pew.topdown;

import java.util.ArrayList;


public class BasicCollision extends CollisionCase {

	public BasicCollision() {
		super();
		ArrayList<Integer> typeA = new ArrayList<Integer>();
		for(int i = 1; i<=3;i++){
			typeA.add(i);
		}
		caseTypes.put(1, typeA);
		ArrayList<Integer> typeB = new ArrayList<Integer>();
		for(int i = 2; i<=3;i++){
			typeB.add(i);
		}
		caseTypes.put(2, typeB);
	}
	

	@Override
	public void handleCase(CollidableElement baseEle,CollidableElement testEle,int caseNumber){
		//System.out.println("caseNumber: " + caseNumber + " baseEle: " + baseEle.getId() + " testEle: " + testEle.getId());
		switch(caseNumber){
		case 2: case 3: case 5:
			/**
			 * if baseEle is already pushed (and it's initial pusher is lesser than baseEles current
			 * pusher) then push baseEles inital pusher and stop baseEle
			 */
			if(baseEle.isPushed  /*&& baseEle.parentElement.getCollideType() > testEle.getCollideType()*/){
				baseEle.parentElement.parentElement = testEle;
				baseEle.parentElement.isPushed = true;
				backTrack(baseEle.parentElement, baseEle);
				baseEle.isPushed = false;
			}else{
				baseEle.isPushed = true;
			}
			baseEle.parentElement = testEle;
			testEle.isPushing = true;
		    break;
			
		case 1: case 4:
			//do nothing, as the collidable types are equal so nothing is pushed, they are only stopped and will be 
			//handled after the switch
			break;
		default: 
			System.out.println("invalid case tried in a BasicCollision");
			return;
		}
		backTrack(baseEle, testEle);
	}

}
