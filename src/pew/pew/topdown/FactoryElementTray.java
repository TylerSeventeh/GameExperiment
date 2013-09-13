package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

/**
 * 
 * TODO set up an overflow system so there is actually a point to the scrolling system
 * 		assuming i even make enough templates...
 *  
 *  also TODO, make the templates look more like what they are supposed to be
 */

public class FactoryElementTray {
	
	ArrayList<FactorySquare> factSquares;
	ArrayList<FactoryTemplate> templates;
	TrayArrow leftArrow,rightArrow;
	TopDown td;
	
	public FactoryElementTray(TopDown td){
		this.td = td;
	}
	
	//should probably move towards loading this in from a file so i dont have to spend so much time setting it in the code
	public int initTray(ArrayList<Element> elements,int lastUnusedID,GameContainer gc,TopDown td){
		//fe height 41, width 51
		int x = 102,y = 515;
		factSquares = new ArrayList<FactorySquare>();
		templates = new ArrayList<FactoryTemplate>();
		
		//fs loading
		for(int i = 0;i <17; i++){
			factSquares.add(new FactorySquare(x,y,51,41,true,gc,GetImage.get("res\\fsbackground.png")));
			x += 51;
			
		}
		//TODO balance prices
		
		// look to bottom to see what was removed
		
		//FactoryTemplate loading

		
		long value = 0;//easier to organize balancing this way, or at least it seemed like it would
		//output ele
		factSquares.get(0).subEle = new FactoryTemplate(lastUnusedID,GetImage.get("res\\outputbg.png"),2,factSquares.get(0),4,
				td,null,"",value);
		templates.add((FactoryTemplate) factSquares.get(0).subEle);
		elements.add(templates.get(0));
		lastUnusedID++;
		
		value = 5;
		//conveyor belt
		factSquares.get(1).subEle = new FactoryTemplate(lastUnusedID,GetImage.get("res\\conveyorbelt.png"),2,factSquares.get(1),1,
				td,null,"",value);
		templates.add((FactoryTemplate) factSquares.get(1).subEle);
		FactoryTemplate tempConveyor = templates.get(1);
		elements.add(tempConveyor);
		lastUnusedID++;
		
		//leaving a space in the lists for the custom one
		factSquares.get(2).subEle = new FactoryTemplate(lastUnusedID,null,2,factSquares.get(2),0, td,null,"",value);
		templates.add((FactoryTemplate) factSquares.get(2).subEle);
		elements.add(templates.get(2));
		lastUnusedID++;
				
		//value = 10;
		//basic input ele
		factSquares.get(3).subEle = new FactoryTemplate(lastUnusedID,GetImage.get("res\\input.png"),2,factSquares.get(3),3,
				td,GetImage.get("res\\factory_tray_test.png"),"1",value);//TODO figure out if this had something set that was removed
		templates.add((FactoryTemplate) factSquares.get(3).subEle);
		elements.add(templates.get(3));
		lastUnusedID++;
		
		
		//leaving space for 3 modifier and 1 input elements  (was "make 3 templates"... a template for a template :P)
		
		value = 0;
		//load blank template spaces
		for(int i = 4;i<17; i++){		//TODO update this
			factSquares.get(i).subEle = new FactoryTemplate(lastUnusedID,null,2,factSquares.get(i),0, td,null,"",value);
			templates.add((FactoryTemplate) factSquares.get(i).subEle);
			elements.add(templates.get(i));
			lastUnusedID++;
		}
		
		//arrow loading
		leftArrow = new TrayArrow(lastUnusedID++,GetImage.get("res\\leftarrow.png"),new Vector2f(51,515),1,gc,td,this,true);
		elements.add(leftArrow);
		rightArrow = new TrayArrow(lastUnusedID++,GetImage.get("res\\rightarrow.png"),new Vector2f(969,515),1,gc,td,this,false);
		elements.add(rightArrow);
		return lastUnusedID;
	}
	
	public void updateCustom(int pos, FactoryTemplate ft){
		td.removeElement(templates.set(pos, ft));
		factSquares.get(pos).subEle = ft;
		ft.fs = factSquares.get(pos);
		ft.changePosition();
		td.updateManagers(ft);
	}
	
	//so the tray can contain more templates than currently shown, pointless without the overflow system it doesnt have 
	//there are infinitely easier and more efficient ways of doing this 
	public void cycle(boolean left){
		int i = 0;
		FactorySquare nextFS = null;
		//get buffer before cycle
		FactoryElement buffer[] = new FactoryElement[2];
		buffer[0] = factSquares.get(16).subEle;
		
		for(FactorySquare fs : factSquares){
			//takes the subEle from this FS to the next one, whether it be to the left or right
			if(left){
				if(i == 0){
					nextFS = factSquares.get(factSquares.size()-1);
				}else{
					nextFS = factSquares.get(i-1);
				}
			}else{
				if(i == 16){// if last fs...
					nextFS = factSquares.get(0);//get first element
				}else{
					nextFS = factSquares.get(i+1);
					buffer[1] = nextFS.subEle;
				}
			}
			
			
			if((i > 0 && !left) || i == 16){//check if buffer should be used
				nextFS.subEle = buffer[0];
				
			}else{//if not, push subEle from this FS
				nextFS.subEle = fs.subEle;
			}
			if(!left){
				buffer[0] = buffer [1];
			}
			//update the new subEle in nextFS
			nextFS.subEle.fs = nextFS;
			nextFS.subEle.changePosition();
			i++;
		}	
	}
	
	
	
}


/*		REMOVED CODE THAT MAY NEED TO BE RE-USED
 * 
 * 
 * 
 * 		value = 10;
		//faster conveyor belt
		factSquares.get(2).subEle = new FactoryTemplate(lastUnusedID,GetImage.get("res\\fconveyorbelt.png"),2,
				factSquares.get(2),1,td,null,"",value);
		templates.add((FactoryTemplate) factSquares.get(2).subEle);
		FactoryTemplate temp = templates.get(2);
		//temp.data.add(5);//speed
		//temp.data.add(2);//rotation, right
		temp.rotation = 2;
		elements.add(temp);
		//factSquares.get(2).subEle = temp;
		lastUnusedID++;
		
  		value = 250;
		//value 2 Modifier
		factSquares.get(4).subEle = new FactoryTemplate(lastUnusedID,GetImage.get("res\\modifier.png"),2,factSquares.get(4),
				5, td,GetImage.get("res\\factory_tray_test.png"), "2",value);
		templates.add((FactoryTemplate) factSquares.get(4).subEle);
		elements.add(templates.get(4));
		lastUnusedID++;
		
		value = 150;
		//value 4 modifier
		factSquares.get(5).subEle = new FactoryTemplate(lastUnusedID,GetImage.get("res\\modifier.png"),2,factSquares.get(5),
				6, td,GetImage.get("res\\factory_tray_test.png"),"4",value);  //bleh
		templates.add((FactoryTemplate) factSquares.get(5).subEle);
		elements.add(templates.get(5));
		lastUnusedID++;
		
		value = 100;
		//value 8 modifier
		factSquares.get(6).subEle = new FactoryTemplate(lastUnusedID,GetImage.get("res\\modifier.png"),2,factSquares.get(6),
				7, td,GetImage.get("res\\factory_tray_test.png"),"8",value);  //bleh
		templates.add((FactoryTemplate) factSquares.get(6).subEle);
		elements.add(templates.get(6));
		lastUnusedID++;
 * 
 * 
 */

