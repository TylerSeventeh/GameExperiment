package pew.pew.topdown;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class CollisionData {
	
	List<Integer> small, large;
	
	public CollisionData(){
		small = new ArrayList<Integer>();
		large = new ArrayList<Integer>();
	}
	
	public void addCase(int id1, int id2){
		small.add(id1);
		large.add(id2);
	}
	
	public int getTotalCases(){
		return small.size();
	}
	
	public int[] getCase(int index){
		int[] colCase = new int[2];
		colCase[0] = small.get(index);
		colCase[1] = large.get(index);
		return colCase;
	}
	
	
	public void clear(){
		small.clear();
		large.clear();
		//small= new ArrayList<Integer>(1);
		//large = new ArrayList<Integer>(1);
	}
	
}
