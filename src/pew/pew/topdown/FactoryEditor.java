package pew.pew.topdown;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

//fE, fb, fs, fe
public class FactoryEditor implements MouseListener, ComponentListener{
	FactoryBlock fb;
	FactoryElementTray fet;
	TopDown td;
	FactoryElement onMouse;
	SimulationButton sim;
	int lastUnusedID;
	boolean acceptingInput;
	ArrayList<Integer> inputInfo;//TODO make all input use this to avoid concurrent modifications exceptions
	//UI elements
	FactorySaveButton saveButton;
	FactoryLoadButton loadButton;
	MoneyInfoButton moneyButton;
	ResearchButton researchButton;
	
	
	public FactoryEditor(int sHeight, int sWidth,TopDown topd,GameContainer gc){
		fb = new FactoryBlock(sHeight,sWidth,this,gc);
		td = topd;
		fet = new FactoryElementTray(td);
		acceptingInput = true;
		inputInfo = new ArrayList<Integer>();
	}
	
	public void updateFactory(){
		/**
		 * inputInfo
		 * 0 save
		 * 1 load
		 */
		for(int i : inputInfo){
			switch(i){
			case 0:
				td.savManager.saveGame("test.factory", new FactorySaveData(this));
				break;
			case 1:
				td.removeElement(sim);
				td.removeElement(saveButton);
				td.removeElement(loadButton);
				td.removeElement(moneyButton);
				td.fE.loadEditor(loadButton.gc);
				td.savManager.loadGame("test.factory", 1);
				break;
			}
		}
		inputInfo.clear();
		
	}
	
	public void loadEditor(GameContainer gc){
		ArrayList<Element> elementList = new ArrayList<Element>();
		Image im = null;//this is now pointless, if it ever wasn't, except it isnt, so i have no idea
		
		try {im = new Image("res\\desktop.png");} catch (SlickException e1) {System.out.println(e1.getMessage());}
		
		Vector2f pos = new Vector2f(1,1);
		elementList.add((Element) new LayerElement(1,im,pos,0));//background element
		
		try {im = new Image("res\\fsbackground.png");} catch (SlickException e) {System.out.println(e.getMessage());}
		
		int id = 2;
		for(ArrayList<FactorySquare> row : fb.grid){
			for(FactorySquare fs : row){
				fs.subEle = new FactoryElement(id,null,1,fs,td);
				elementList.add(fs.subEle);
				id++;
			}
		}
		lastUnusedID = fet.initTray(elementList,id,gc,td);
		im = GetImage.get("res\\Sim button.png");
		sim = new SimulationButton(lastUnusedID++,im, new Vector2f(0,515), 2, gc, td);
		elementList.add(sim);
		im = GetImage.get("res\\factorysave.png");
		saveButton = new FactorySaveButton(lastUnusedID++,im,new Vector2f(),2,gc,td);
		elementList.add(saveButton);
		im = GetImage.get("res\\factoryload.png"); 
		loadButton = new FactoryLoadButton(lastUnusedID++,im,new Vector2f(51,0),2,gc,td);
		elementList.add(loadButton);
		im = GetImage.get("res\\fsbackground.png");
		moneyButton = new MoneyInfoButton(lastUnusedID++,im,new Vector2f(102,0),2,gc,td,1000L);//TODO set back
		elementList.add(moneyButton);
		im = GetImage.get("res\\research.png");
		researchButton = new ResearchButton(lastUnusedID++,im,new Vector2f(153,0),2,gc,td);
		elementList.add(researchButton);
		td.elements = elementList;
		//td.player = null;
		td.resetManagers();
	}
	
	public void startSimulation(){
		simFEs(true);
		acceptingInput = false;
		this.researchButton.isGrayed = true;
		researchButton.moa.setAcceptingInput(false);
	}
	
	public void stopSimulation(){
		simFEs(false);
		acceptingInput = true;
		sim.isSimulating = false;//in case something else stops the simulation; nothing else stops the simulation
		this.researchButton.isGrayed = false;
		researchButton.moa.setAcceptingInput(true);
	}
	
	public void simFEs(boolean isStarting){//should probably get rid of this method ...?
		/*saveButton.isSimulating = isStarting;TODO fix
		saveButton.isGrayed = isStarting;
		loadButton.isSimulating = isStarting;
		loadButton.isGrayed = isStarting;*/
		for(ArrayList<FactorySquare> row : fb.grid){
			for(FactorySquare fs : row){
				if(isStarting){
					fs.subEle.startSim();
				}else{
					fs.subEle.endSim();
				}
			}
		}
	}
	
	

	//all the input methods are below
	
	@Override
	public void inputEnded() {
		
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		return acceptingInput;
	}

	@Override
	public void setInput(Input input) {
		
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if(td.isPrompting) return;
		
		for(ArrayList<FactorySquare> row : fb.grid){
			for(FactorySquare fs : row){
				if(fs.subEle.feInfo.get(3)){
					if(fs.moa.isMouseOver()){
						ClickableFE clickable = (ClickableFE) fs.subEle;
						clickable.handleClick(clickCount);
					}
				}
			}
		}
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(td.isPrompting) return;
		if(onMouse == null){
			FactorySquare clickedFS = null; // should only be possible for one fs to be moused over at a time
			for(ArrayList<FactorySquare> row : fb.grid){//check grid
				for(FactorySquare fs : row){
					if(fs.moa.isMouseOver() && !fs.subEle.feInfo.get(2)){
						clickedFS = fs;
					}
				}
			}
			if(clickedFS != null){
				onMouse = clickedFS.subEle;
			}else{
				for(FactorySquare fs : fet.factSquares){//check fet
					if(fs.moa.isMouseOver()){
						clickedFS = fs;
					}
				}
				if(clickedFS != null){
					//onMouse = clickedFS.subEle;
					FactoryTemplate tempTemplate = (FactoryTemplate) clickedFS.subEle;
					onMouse = tempTemplate.getCopy(lastUnusedID);
					td.updateManagers(onMouse);
					lastUnusedID++;
				}
			}
			
			//either way, deal with if something was picked up or not
			if(clickedFS == null){
				onMouse = new FactoryElement(-1,null,-1,null,td);//so it doesnt pick up something I drag over after the click
			}else{
				onMouse.layer = 2;
				if(onMouse.feInfo.get(0)){
					ConveyorBelt tempCB = (ConveyorBelt) onMouse;
					if(tempCB.holding != null)
						tempCB.holding.layer = 3;
				}
				onMouse.position = new Vector2f(Math.round(newx - (onMouse.width / 2)),
						Math.round(newy - (onMouse.height / 2)));
			}
		}else{//there is already an onMouse
			onMouse.position = new Vector2f(Math.round(newx - (onMouse.width / 2)),
					Math.round(newy - (onMouse.height / 2)));
		}
		
		
		
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		
	}

	
	@Override
	public void mousePressed(int button, int x, int y) {
		
		
	}

	
	@Override
	public void mouseReleased(int button, int x, int y) {
		if(td.isPrompting) return;
		//check if over grid, if onMouse is non-null
		if(onMouse != null){
			for(ArrayList<FactorySquare> row : fb.grid){//checking if mouse is over the grid
				for(FactorySquare fs : row){
					if(fs.moa.isMouseOver()){
						if(fs.subEle.feInfo.get(2) || onMouse.id  <= 0) break; 
						if(onMouse.fs != null){//if from the grid, swap
							onMouse.fs.subEle = fs.subEle;//set the previous fs' subele to new fs' subele
							onMouse.fs.subEle.fs = onMouse.fs;//resets subele in above swap
							onMouse.fs.subEle.changePosition();
							fs.subEle = onMouse;
							onMouse.fs = fs;
							onMouse.layer = 1;
							if(onMouse.feInfo.get(0)){//should have made a method for this
								ConveyorBelt tempCB = (ConveyorBelt) onMouse;
								if(tempCB.holding != null)
									tempCB.holding.layer = 2;
							}
							onMouse.changePosition();
							break;
						}else{//if from the tray...
							if(fs.subEle.feInfo.get(4) && onMouse.feInfo.get(1)){//if over conveyor.. (that takes conveyor input)
								System.out.println("conveyor belting");
								ConveyorBelt temp = (ConveyorBelt) fs.subEle;
								temp.holding = onMouse;
								onMouse.layer = 1;
								if(onMouse.feInfo.get(0)){
									ConveyorBelt tempCB = (ConveyorBelt) onMouse;
									if(tempCB.holding != null)
										tempCB.holding.layer = 2;
								}
								onMouse = null;
								return;
							}
							fs.subEle.remove(true);
							fs.subEle = onMouse;//replace with what is onMouse
							onMouse.fs = fs;
							onMouse.layer = 1;
							if(onMouse.feInfo.get(0)){
								ConveyorBelt tempCB = (ConveyorBelt) onMouse;
								if(tempCB.holding != null)
									tempCB.holding.layer = 2;
							}
							onMouse.changePosition();
							onMouse = null;
							return;
						}
					}
				}
			}//check if over tray
			for(FactorySquare fs : fet.factSquares){
				if(fs.moa.isMouseOver()){
					if(onMouse.fs != null){
						//resetting
						onMouse.remove(true);
						onMouse = new FactoryElement(onMouse.id,null,2,onMouse.fs,td);
						onMouse.fs.subEle = onMouse;
						td.updateManagers(onMouse);
						onMouse = null;
						return;
					}
				}
			}
			
			//nothing was found under mouse 
			if(onMouse.fs != null){
				onMouse.changePosition();//resets position to origin
				onMouse.layer = 1;
				if(onMouse.feInfo.get(0)){
					ConveyorBelt tempCB = (ConveyorBelt) onMouse;
					if(tempCB.holding != null)
						tempCB.holding.layer = 2;
				}
				onMouse = null;
			}else{
				onMouse.remove(true);
				onMouse = null;
			}
		}
		
		//handle other mouseReleased cases
	}
	

	@Override
	public void mouseWheelMoved(int change) {
		
	}

	
	@Override
	public void componentActivated(AbstractComponent source) {//only happens when enter is pressed while tf has focus
		
		
	}
}
