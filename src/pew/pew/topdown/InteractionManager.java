package pew.pew.topdown;

import java.util.ArrayList;
import java.util.List;


public class InteractionManager {
	
	List<Interactor> interactors;
	List<Element> interactorEles;
	List<InteractableElement> interactees;
	
	public InteractionManager(){
		interactors = new ArrayList<Interactor>();
		interactees = new ArrayList<InteractableElement>();
		interactorEles = new ArrayList<Element>();
	}
	
	public void add(Element ele, boolean type){
		if(type){
			interactors.add((Interactor)ele);
			interactorEles.add(ele);
		}else{
			interactees.add((InteractableElement)ele);
		}
	}
	public boolean remove(Element ele){
		if(interactors.contains(ele)){
			interactorEles.remove(interactors.indexOf((Interactor)ele));
			interactors.remove((Interactor)ele);
			return true;
		}else if(interactees.contains(ele)){
			interactees.remove(ele);
			return true;
		}else
			return false;
	}
	
	public void handleInteractions(){ //tests each interactor that is currently interacting against each interactee
		for(Interactor ele: interactors){
			if(ele.isInteracting()){
				Element currentElement;
				for(InteractableElement ele2 : interactees){
					currentElement = interactorEles.get(interactors.indexOf(ele));
					if(ele2.id != currentElement.id){ //to prevent testing against itself
						if(ele.getIntShape().intersects(ele2.getShape())){
							ele2.addInteraction(currentElement);
						}
					}
				}
			}
		}
	}
}
