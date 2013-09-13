package pew.pew.topdown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


public class TopDown extends BasicGame {
	
	
	NCollisionManager colManager;
	InteractionManager intManager;
	SavesManager savManager;
	PromptManager promptMan;
	LayerRenderer layRend;
	LevelLoader levelLoader;
	List<Element> elements;
	List<Element> simpleRender;
	ArrayList<ClickableElement> clickables;
	HashMap<Integer,ArrayList<Element>> queue;
	
	/*
	 * 		QUEUE
	 * 0	remove element
	 * 1	add element
	 * 2	new moneyinfo Prompt
	 * 3	dispose prompt
	 * 4	new research prompt
	 */
	
	FactoryEditor fE;
	int x,y, mouseReleased;
	//Player player;
	boolean isPrompting;
	boolean[] playerMovement;
	
	TopDown(int x,int y){
		super("Topdown Test");
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		container.setAlwaysRender(false);
		container.getGraphics().setAntiAlias(false);
		colManager = new NCollisionManager();
		intManager = new InteractionManager();
		savManager = new SavesManager(this);
		promptMan = new PromptManager(this, container);
		layRend = new LayerRenderer();
		simpleRender = new ArrayList<Element>();
		clickables = new ArrayList<ClickableElement>();
		queue = new HashMap<Integer,ArrayList<Element>>();
		//just add elements directly to these arrays, a method seems excessive
		for(int i = 0; i < 5;i++){
			queue.put(i, new ArrayList<Element>());
		}
		
		//levelLoader = new LevelLoader("MenuLevelTest2.lvl",x,y,container,this);
		fE = new FactoryEditor(20,12,this,container);
		container.getInput().addMouseListener(fE);
		fE.loadEditor(container);
		
		playerMovement = new boolean[4];
		/*
		if(levelLoader.loadLevel()){
			this.elements = levelLoader.elements;
			resetManagers();
			this.player = levelLoader.player;
		}
		*/
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		layRend.renderNeg(g);
		for(Element ele : simpleRender){
			ele.render(g);
		}
		layRend.renderPos(g);
	}
	

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		for(Element ele : queue.get(0)){
			this.removeElement(ele);
		}
		queue.get(0).clear();
		for(Element ele : queue.get(1)){
			this.updateManagers(ele);
		}
		queue.get(1).clear();
		for(Element ele : queue.get(2)){
			
			MoneyInfoButton tempMB = (MoneyInfoButton) ele;
			String data[] = tempMB.getData().split(" ");
			//so messy...
			promptMan.newPrompt("$" + String.valueOf(tempMB.actualMoney), data[0], new Vector2f(Integer.parseInt(data[1]),Integer.parseInt(data[2])));
		}
		queue.get(2).clear();
		for(Element ele : queue.get(3)){
			promptMan.disposePrompt(ele.id);
		}
		queue.get(3).clear();
		for(Element ele : queue.get(4)){
			((PromptCreator) ele).makePrompt(promptMan);
		}
		queue.get(4).clear();
		//REMEMBER TO CLEAR THE LIST
		
		
		for(Element ele : elements) ele.update(delta);
		//player.update(delta, playerMovement);
		fE.updateFactory();
		intManager.handleInteractions();
		//colManager.handleCollisions();  there are no collisions anymore
		
		
		if(!isPrompting){//TODO get rid of this system / make a better one, I expect to not get to this
			for(ClickableElement ele : clickables){
				ele.checkReleased();
			}
		}else{
			for(ClickableElement ele : clickables){
				if(ele.typeArray.get(5)){
					PromptButton pb = (PromptButton)ele;
					if(pb.hasFocus) pb.checkReleased();
				}
			}
		}
		layRend.checkLayerChanges();
	}
	
	public void changeLevel(String newLevel){
		
		
		levelLoader.level = newLevel;
		
		if(levelLoader.loadLevel()){
			this.elements = levelLoader.elements;
			resetManagers();
			//this.player = levelLoader.player;
		}else{
			System.out.println("failed to load level: " + newLevel);
		}
		
		//promptMan = new PromptManager(this, gc); TODO this
	}
	
	public void removeElement(Element ele){
		if(!elements.contains(ele)) return;
		
		if(ele.getType(0)){
			clickables.remove(ele);
		}
		if(ele.getType(1)){
			CollidableElement temp = (CollidableElement) ele;
			colManager.elements.remove(ele);
			colManager.sortedElements.get(temp.collisionType).remove(ele);
		}
		if(ele.getType(2)){//make sure the layer was updated in the renderer if layer was changed
			layRend.remove((LayerElement) ele);
		}else{
			simpleRender.remove(ele);
		}
		//if(ele.getType(3)){} im not going to be using interactables anyways, or interactees
		elements.remove(ele);
		
	}
	
	//adds any new Elements, ignores them if they are already in elements
	public void updateManagers(Element ele){
		if(elements.contains(ele)) return;
		if(ele.getType(0)){
			clickables.add((ClickableElement) ele);
		}
		if(ele.getType(1)){
			colManager.add((CollidableElement) ele);
		}
		if(ele.getType(2)){
			layRend.add((LayerElement) ele);
		}else{
			simpleRender.add(ele);
		}
		if(ele.getType(3)){
			System.out.println(ele.id + "interactable");
			intManager.add(ele,false);
		}
		if(ele.getType(4)){
			System.out.println(ele.id + " interactor ");
			intManager.add(ele,true);
		}
		elements.add(ele);
	}	
	
	@Deprecated
	public void updateManagers(ArrayList<Element> eles){
		for(Element ele : eles){
			if(elements.contains(ele)) continue;
			if(ele.getType(0)){
				clickables.add((ClickableElement) ele);
			}
			if(ele.getType(1)){
				colManager.add((CollidableElement) ele);
			}
			if(ele.getType(2)){
				layRend.add((LayerElement) ele);
			}else{
				simpleRender.add(ele);
			}
			if(ele.getType(3)){
				System.out.println(ele.id + "interactable");
				intManager.add(ele,false);
			}
			if(ele.getType(4)){
				System.out.println(ele.id + " interactor ");
				intManager.add(ele,true);
			}
			elements.add(ele);
		}
	}
	
	public void resetManagers(){
		colManager.elements.clear();
		colManager.sortedElements.clear();
		layRend.reset();
		simpleRender.clear();
		intManager.interactees.clear();
		intManager.interactorEles.clear();
		intManager.interactors.clear();
		
		for(Element ele : elements){
			if(ele.getType(0)){
				clickables.add((ClickableElement) ele);
			}
			if(ele.getType(1)){
				colManager.add((CollidableElement) ele);
			}
			if(ele.getType(2)){
				layRend.add((LayerElement) ele);
			}else{
				simpleRender.add(ele);
			}
			if(ele.getType(3)){
				System.out.println(ele.id + "interactable");
				intManager.add(ele,false);
			}
			if(ele.getType(4)){
				System.out.println(ele.id + " interactor ");
				intManager.add(ele,true);
			}
			//5 is promptbutton, used elsewhere
			//if(ele.getType(6)){}
		}
		colManager.sortElements();
	}

	
	//Resolution locked at a 1024x576 window
	public static void main(String[] args) {
		
		try{
			AppGameContainer app = new AppGameContainer(new TopDown(1024, 576), 1020, 576, false);
			app.setShowFPS(false);
			app.start();
		}catch (SlickException e){
			System.out.println("error in main function:\n" + e.getMessage());
		}

	}

	
}

