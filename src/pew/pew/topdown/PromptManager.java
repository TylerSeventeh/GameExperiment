package pew.pew.topdown;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class PromptManager {
	
	ElementFactory eleF;
	TopDown td;
	GameContainer con;
	Graphics g;
	PromptGroup currentPromptGroup;
	ArrayList<Element> currentPrompt;
	HashMap<Integer,PromptGroup> prompts;
	int promptId;
	
	public PromptManager(TopDown td,GameContainer contain){
		eleF = new ElementFactory(null,null);
		this.td = td;
		con = contain;
		currentPrompt = new ArrayList<Element>();
		currentPromptGroup = null;
		prompts = new HashMap<Integer,PromptGroup>();
		
	}
	
	
	public static void main(String[] args) {//TODO remove...? nah
		TopDown.main(args);
	}
	
	//I really should have made this read from some sort of file
	public void newPrompt(String text, String promptType, Vector2f pos){
		
		
		switch(promptType){ 
		case("test"):
			currentPrompt.add(new TestCancel(++promptId, GetImage.get("res\\cancelbutton1.png"),new Vector2f(pos.x + 120,pos.y + 30),
					1, (GUIContext) con, td, GetImage.get("cancelbutton2.png")));
			currentPrompt.add(new TestOk(promptId, GetImage.get("res\\okbutton.png"),new Vector2f(pos.x + 30,pos.y + 30),
					1, (GUIContext) con, td, GetImage.get("okbutton2.png")));
			currentPrompt.add(new LayerElement(promptId, GetImage.get("res\\promptwindow.png"),new Vector2f(pos.x,pos.y),1));
			
			break;
		case "moneydata":
			currentPrompt.add(new TestCancel(++promptId,GetImage.get("res\\okbutton.png"),new Vector2f(pos.x + 310,pos.y + 145),
					1, (GUIContext) con, td, GetImage.get("res\\okbutton.png")));
			currentPrompt.add(new LayerElement(promptId, GetImage.get("res\\promptwindow.png"),new Vector2f(pos.x,pos.y),1));
			currentPrompt.add(new MoneyString(promptId,null, new Vector2f(pos.x + 30,pos.y + 30),1, td.fE.moneyButton));
			break;
			
			
			/*
			 * TODO figure out how long (or not) these are going to take to finish and how im going to get them to the tray
			 * and how the tray is going to read them.. 
			 * 
			 * current plans:
			 *  	have it cost a certain amount to create the customised item, but have it be created instantly
			 *  	and put into the tray
			 *  still have no idea how im going to get them to the tray though
			 *  balancing will also be difficult
			 *  
			 *  	thinking there should be a default type and only one, over-writable, custom type
			 *  
			 */
			
			
		case "research":
			currentPrompt.add(new TestCancel(++promptId,GetImage.get("res\\okbutton2.png"),new Vector2f(pos.x + 310,pos.y + 145),
					1, (GUIContext) con, td, GetImage.get("res\\okbutton2.png")));
			currentPrompt.add(new LayerElement(promptId, GetImage.get("res\\promptwindow.png"),new Vector2f(pos),1));
			currentPrompt.add(new NewPromptButton(promptId, GetImage.get("res\\rconveyor.png"),new Vector2f(pos.x+50,pos.y+40),
					1, (GUIContext) con, td,"r conveyor", true));
			currentPrompt.add(new NewPromptButton(promptId, GetImage.get("res\\rinput.png"),new Vector2f(pos.x+120,pos.y+40),
					1, (GUIContext) con, td,"r input", true));
			currentPrompt.add(new NewPromptButton(promptId, GetImage.get("res\\rmodifier.png"),new Vector2f(pos.x+190,pos.y+40),
					1, (GUIContext) con, td,"r modifier", true));
			break;
		case "r conveyor":
			currentPrompt.add(new NewPromptButton(++promptId, GetImage.get("res\\backbutton.png"),new Vector2f(pos.x+310,pos.y+145),
					1, (GUIContext) con, td,"research", true));
			currentPrompt.add(new LayerElement(promptId, GetImage.get("res\\promptwindow.png"),new Vector2f(pos),1));
			//Speed
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 20),1,"Speed:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),
					new Vector2f(pos.x + 65,pos.y + 20), 1, con,this.td,30,50));
			//projected value per part 
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 50),1,"price per part:"));
			currentPrompt.add(new ConveyorPValue(promptId,null,new Vector2f(pos.x + 148,pos.y + 50),1,currentPrompt));
			//cost to save 
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 70),1,"price to save:"));
			currentPrompt.add(new ConveyorValue(promptId,null,new Vector2f(pos.x + 135,pos.y + 70),1,currentPrompt));
			//button to save 
			currentPrompt.add(new ConveyorSave(promptId, GetImage.get("res\\promptsave.png"),new Vector2f(pos.x+238,pos.y+145),
					1, (GUIContext) con, td,currentPrompt));
			//TODO make save button
			
			break; 
			
		case "r input":
			currentPrompt.add(new NewPromptButton(++promptId, GetImage.get("res\\backbutton.png"),new Vector2f(pos.x+310,pos.y+145),
					1, (GUIContext) con, td,"research", true));
			currentPrompt.add(new LayerElement(promptId, GetImage.get("res\\promptwindow.png"),new Vector2f(pos),1));
			//Speed
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 20),1,"Speed:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),
					new Vector2f(pos.x + 65,pos.y + 20), 1, con,this.td,30,50));
			//baseValue of FP
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 7,pos.y + 45),1,"fp Value:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),
					new Vector2f(pos.x + 88,pos.y + 45), 1, con,this.td,1,Integer.MAX_VALUE));
			((TextElement)currentPrompt.get(5)).nf.setText("10");
			//data of FP
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 7,pos.y + 75),1,"fp Data:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),
					new Vector2f(pos.x + 80,pos.y + 75), 1, con,this.td,1,15));
			//projected value per part 
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 100),1,"price per part:"));
			currentPrompt.add(new InputPValue(promptId,null,new Vector2f(pos.x + 148,pos.y + 100),1,currentPrompt));
			//cost to save 
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 120),1,"price to save:"));
			currentPrompt.add(new InputValue(promptId,null,new Vector2f(pos.x + 135,pos.y + 120),1,currentPrompt));
			//button to save 
			currentPrompt.add(new InputSave(promptId, GetImage.get("res\\promptsave.png"),new Vector2f(pos.x+238,pos.y+145),
					1, (GUIContext) con, td,currentPrompt));
			
			break;
		case "r modifier": //TODO this
			currentPrompt.add(new NewPromptButton(++promptId, GetImage.get("res\\backbutton.png"),new Vector2f(pos.x+310,pos.y+145),
					1, (GUIContext) con, td,"research", true));
			currentPrompt.add(new LayerElement(promptId, GetImage.get("res\\promptwindow.png"),new Vector2f(pos),1));
			//Speed
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 20),1,"Speed:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),
					new Vector2f(pos.x + 65,pos.y + 20), 1, con,this.td,30,50));
			//slot for modifier
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 7,pos.y + 45),1,"Slot:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),
					new Vector2f(pos.x + 55,pos.y + 45), 1, con,this.td,1,3));
			//data of FP
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 7,pos.y + 75),1,"fp Data:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),
					new Vector2f(pos.x + 80,pos.y + 75), 1, con,this.td,2,14));
			//projected value per part
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 100),1,"price per part:"));
			currentPrompt.add(new ModifierPValue(promptId,null,new Vector2f(pos.x + 148,pos.y + 100),1,currentPrompt));
			//cost to save 
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 120),1,"price to save:"));
			currentPrompt.add(new ModifierValue(promptId,null,new Vector2f(pos.x + 135,pos.y + 120),1,currentPrompt));
			//button to save 
			currentPrompt.add(new ModifierSave(promptId, GetImage.get("res\\promptsave.png"),new Vector2f(pos.x+238,pos.y+145),
					1, (GUIContext) con, td,currentPrompt));
			
			break;
		}
		
		/*
		 * template for new "research" element
		 * 
		   currentPrompt.add(new NewPromptButton(++promptId, GetImage.get("res\\backbutton.png"),new Vector2f(pos.x+310,pos.y+145),
					1, (GUIContext) con, td,"research", true));
			currentPrompt.add(new LayerElement(promptId, GetImage.get("res\\promptwindow.png"),new Vector2f(pos),1));
			//element specific value
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 20),1,"name of value:"));
			currentPrompt.add(new TextElement(promptId, GetImage.get("res\\fsbackground.png"),//value field
					new Vector2f(pos.x + 65,pos.y + 20), 1, con,this.td,3,50));
			//projected value per part 
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 50),1,"price per part:"));
			//element specific calculation goes here
			//cost to save 
			currentPrompt.add(new StringElement(promptId,null,new Vector2f(pos.x + 10,pos.y + 70),1,"price to save:"));
			//element specific calculation goes here
			//button to save 
			//element specific save button goes here
		 * 
		 * 
		 */
		
		
		
		if( currentPromptGroup != null && !currentPromptGroup.isInit){
			currentPromptGroup.lostFocus();
		}else{
			for(ClickableElement ele : td.clickables){
				ele.isGrayed = true;
				ele.moa.setAcceptingInput(false);
			}
			if(currentPromptGroup == null){//if it is and the placeholder doesn't exist yet
				currentPromptGroup = new PromptGroup(new ArrayList<Element>(),true,null);//this is supposed to be a placeholder
			}
		}
		
		PromptGroup tempPrompt = new PromptGroup(currentPrompt,false,currentPromptGroup);
		currentPromptGroup = tempPrompt;
		currentPromptGroup.gotFocus();
		prompts.put(currentPrompt.get(0).id,currentPromptGroup);
		for(Element ele : currentPrompt){
			td.updateManagers(ele);
		}
		td.isPrompting = true;
	}
	
	public void disposePrompt(int id){
		if(!currentPromptGroup.lastPromptGroup.isInit){
			currentPromptGroup = currentPromptGroup.lastPromptGroup;
			currentPromptGroup.gotFocus();
			currentPrompt = currentPromptGroup.eles;
		}else{
			for(ClickableElement ele : td.clickables){//ungrays clickables so td doesnt have to do it every update
				ele.isGrayed = false;
				ele.moa.setAcceptingInput(true);
			}
			currentPrompt = new ArrayList<Element>();
			currentPromptGroup = currentPromptGroup.lastPromptGroup;
		}
		ArrayList<Element> eles = prompts.get(id).eles;
		prompts.remove(id);
		for(Element ele : eles){
			ele.remove(td);
		}
		if(currentPromptGroup.isInit) td.isPrompting = false;
	}
}

class PromptGroup{
	
	boolean isInit;//if it is the first opened prompt; it will be the last closed
	PromptGroup lastPromptGroup;
	ArrayList<Element> eles;
	int id;// id of pb1; used in disposePrompt()
	
	public PromptGroup(ArrayList<Element> promptEles,boolean isInitial,PromptGroup lastpg){
		if(!isInitial){
			eles = promptEles;
			id = eles.get(0).id;
			lastPromptGroup = lastpg;
		}else{//if it is the placeholder
			id = -1;
		}
		isInit = isInitial;
	}
	
	public void lostFocus(){
		for(Element ele : eles){
			if(ele.typeArray.get(5)){
				PromptButton pb = (PromptButton) ele;
				pb.hasFocus = false;
			}
			LayerElement lEle = (LayerElement) ele;
			lEle.layer = 1;
		}
	}
	
	public void gotFocus(){
		for(Element ele : eles){
			if(ele.typeArray.get(5)){
				PromptButton pb = (PromptButton) ele;
				pb.hasFocus = true;
				pb.layer = 3;
			}else{
				LayerElement lEle = (LayerElement) ele;
				lEle.layer = 2;
			}
		}
	}
	
}



//abstract class to display the overall value of proposed element
abstract class ValueText extends StringElement{

	ArrayList<Element> cp;//current prompt
	public ValueText(int id, Image pic, Vector2f pos, int layer, ArrayList<Element> cp) {
		super(id, pic, pos, layer, "");
		this.cp = cp;
	}
	
	@Override
	public final void update(int delta){
		super.update(delta);
		this.displayString = calculateValue(displayString);
	}
	
	public Integer roundUp(float num){
		if(num - (int)num != 0){
			num = (int)num + 1;
		}
		return (int)num;
	}
	
	public abstract String calculateValue(String preValue);
}



//displays the overall value of proposed conveyor
class ConveyorPValue extends ValueText{

	TextElement speedVal;
	float actualVal;
	
	public ConveyorPValue(int id, Image pic, Vector2f pos, int layer,
			ArrayList<Element> cp) {
		super(id, pic, pos, layer, cp);
		speedVal = (TextElement) cp.get(3);
	}

	@Override
	public String calculateValue(String preValue) {
		
		if(!speedVal.nf.hasFocus()){
			speedVal.nf.enforceBoundaries();//just to be sure
			
			float value = (Float.parseFloat(speedVal.nf.getText()) - 3)/3F;//assumes min/max was set
			if(value == 0){
				value = 0.01F;
			}
			actualVal = value;
			value = roundUp(value);
			
			return String.valueOf(value);
		}
		
		
		return preValue;
	}
	
}


//cost to create/save conveyor
class ConveyorValue extends ValueText{
	
	ConveyorPValue cpv;
	int saveValue;

	public ConveyorValue(int id, Image pic, Vector2f pos, int layer,
			ArrayList<Element> cp) {
		super(id, pic, pos, layer, cp);
		cpv = (ConveyorPValue) cp.get(5);
		
	}

	@Override
	public String calculateValue(String preValue) {
		
		if(cpv.actualVal == 0){
			saveValue = 0;
			return "0";
		}
		double value = Math.pow(cpv.actualVal,2);
		if(value < 1){
			saveValue = 10;
			return "10";
		}else{
			saveValue = (int) Math.round(value + 10);
			return String.valueOf(saveValue);
		}
	}
	
}


class ConveyorSave extends PromptButton{
	
	ArrayList<Element> cp;

	public ConveyorSave(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga,ArrayList<Element> cp) {
		super(id, pic, pos, layer, container, ga);
		this.cp = cp;
	}

	@Override
	public void handleClick() {
		for(Element ele : cp){
			ele.update(1);
			//it is likely that these will need to be updated beforehand becuase of them waiting for everything to be deselected	
		}
		int totalValue = (int) ((ConveyorValue) cp.get(7)).saveValue;
		if( td.fE.moneyButton.actualMoney - totalValue < 0){
			return;
		}
		ArrayList<Integer> data = new ArrayList<Integer>();
		TextElement speedVal = (TextElement) cp.get(3);
		data.add(speedVal.nf.maximum - Integer.parseInt(speedVal.nf.getText()) + 3);
		data.add(0);//dont really need to set the direction to anything else
		td.fE.moneyButton.actualMoney -= totalValue;
		td.fE.fet.updateCustom(2, new FactoryTemplate(1,GetImage.get("res\\fconveyorbelt.png"),2,null,1,
				td,null,"",(long) ((ConveyorPValue) cp.get(5)).actualVal,data));
		
	}
	
}



class InputPValue extends ValueText{
	
	NumberField speed,baseVal,valData;
	double actualVal;

	public InputPValue(int id, Image pic, Vector2f pos, int layer,
			ArrayList<Element> cp) {
		super(id, pic, pos, layer, cp);
		speed = (NumberField)((TextElement) cp.get(3)).nf;
		baseVal = (NumberField)((TextElement) cp.get(5)).nf;
		valData = (NumberField)((TextElement) cp.get(7)).nf;
	}

	@Override
	public String calculateValue(String preValue) {//TODO figure out how to put the fp data in here somewhere
		if(!baseVal.hasFocus() && !speed.hasFocus() && !valData.hasFocus()){
			if((Integer.parseInt(valData.getText()) & 1) == 1 ){
				
				float value = (((Float.parseFloat(speed.getText()) - 3)/6F) * 
						roundUp((float) Math.pow(Float.parseFloat(baseVal.getText()),-1.2F)) + Float.parseFloat(baseVal.getText()));
				value *= Math.sqrt(Integer.parseInt(valData.getText()));
				actualVal = value;
				return String.valueOf(roundUp(value));
			}
			actualVal = 0;
			return "0.0";
		}
		return preValue;
	}
	
	
	
}

class InputValue extends ValueText{
	
	InputPValue cpv;
	int saveValue;

	public InputValue(int id, Image pic, Vector2f pos, int layer,
			ArrayList<Element> cp) {
		super(id, pic, pos, layer, cp);
		cpv = (InputPValue) cp.get(9);
		
	}

	@Override
	public String calculateValue(String preValue) {
		
		if(cpv.actualVal == 0){
			saveValue = 0;
			return "0";
		}
		double value = Math.pow(cpv.actualVal,1.3F);
		if(value < 1){
			saveValue = 10;
			return "10";
		}else{
			saveValue = (int) Math.round(value + 10);
			return String.valueOf(saveValue);
		}
	}
	
}

class InputSave extends PromptButton{
	
	ArrayList<Element> cp;

	public InputSave(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga,ArrayList<Element> cp) {
		super(id, pic, pos, layer, container, ga);
		this.cp = cp;
	}

	@Override
	public void handleClick() {
		System.out.println("input save clicked");
		for(Element ele : cp){
			ele.update(1);
			//it is likely that these will need to be updated beforehand becuase of them waiting for everything to be deselected
		}
		InputValue iv = (InputValue) cp.get(11);
		System.out.println(iv.saveValue);
		if(td.fE.moneyButton.actualMoney - iv.saveValue < 0){
			return;
		}
		td.fE.moneyButton.actualMoney -= iv.saveValue;
		TextElement speedVal = (TextElement) cp.get(3);//base - data
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(speedVal.nf.maximum - Integer.parseInt(speedVal.nf.getText()) + 3);
		data.add(0);//dont really need to set the direction to anything else
		data.add(Integer.parseInt(((TextElement)cp.get(5)).nf.getText()));
		data.add(Integer.parseInt(((TextElement)cp.get(7)).nf.getText()));
		td.fE.fet.updateCustom(4, new FactoryTemplate(1,GetImage.get("res\\inputc.png"),2,null,3,
				td,null,String.valueOf(data.get(3)),(long) ((InputPValue) cp.get(9)).actualVal,data));
		
	}
	
}

			//TODO all below
//TODO instead of setting the value of invalid elements to zero, round them up/down to the nearest valid one

class ModifierPValue extends ValueText{
	
	NumberField speed,slot,valData;//TODO this 
	double actualVal;

	public ModifierPValue(int id, Image pic, Vector2f pos, int layer,
			ArrayList<Element> cp) {
		super(id, pic, pos, layer, cp);
		speed = ((TextElement) cp.get(3)).nf;
		slot = ((TextElement) cp.get(5)).nf;
		valData = ((TextElement) cp.get(7)).nf;
	}

	@Override
	public String calculateValue(String preValue) {
		if(!speed.hasFocus() && !valData.hasFocus()){
			if((Integer.parseInt(valData.getText()) & 1) != 1 ){
				
				float value = 30 + (((Float.parseFloat(speed.getText()) - 3)/10F));
				int data = Integer.parseInt(valData.getText());
				value += Math.pow(value, (data & 2) * (data & 4) * (data & 8));
				//value += Math.pow(value, Math.sqrt(Integer.parseInt(valData.getText())));
				actualVal = value;
				return String.valueOf(roundUp(value));
			}
			actualVal = 0;
			return "0.0";
		}
		return preValue;
	}
	
	
	
}

class ModifierValue extends ValueText{
	
	ModifierPValue mpv;
	int saveValue;//TODO make this serve purpose

	public ModifierValue(int id, Image pic, Vector2f pos, int layer,
			ArrayList<Element> cp) {
		super(id, pic, pos, layer, cp);
		mpv = (ModifierPValue) cp.get(9);
		
	}

	@Override
	public String calculateValue(String preValue) {
		
		if(mpv.actualVal == 0){
			saveValue = 0;
			return "0";
		}
		double value = Math.pow(mpv.actualVal,1.5F);
		if(value < 10){
			saveValue = 10;
			return "10";
		}else{
			saveValue = this.roundUp((float) (value + 10));
			return String.valueOf(String.valueOf(Math.round(value + 10)));
		}
		
	}
	
}

class ModifierSave extends PromptButton{
	
	ArrayList<Element> cp;

	public ModifierSave(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga,ArrayList<Element> cp) {
		super(id, pic, pos, layer, container, ga);
		this.cp = cp;
	}

	@Override
	public void handleClick() {//TODO remove sysout
		System.out.println("was clicked");
		for(Element ele : cp){
			ele.update(1);
		}
		ModifierValue mv = (ModifierValue) cp.get(11);
		if(td.fE.moneyButton.actualMoney - mv.saveValue < 0){
			System.out.println("saveValue was zero");//TODO remove sysout
			return;
		}
		
		td.fE.moneyButton.actualMoney -= mv.saveValue;
		TextElement speedVal = (TextElement) cp.get(3);//base - data; no idea what this meant
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(speedVal.nf.maximum - Integer.parseInt(speedVal.nf.getText()) + 3);
		data.add(0);//dont really need to set the direction to anything else
		data.add(Integer.parseInt(((TextElement)cp.get(5)).nf.getText()));
		data.add(Integer.parseInt(((TextElement)cp.get(7)).nf.getText()));
		td.fE.fet.updateCustom(4 + Integer.parseInt(((TextElement)cp.get(5)).nf.getText()), new FactoryTemplate(5,GetImage.get("res\\modifier.png"),2,null,5,
				td,null,String.valueOf(data.get(3)),(long) ((ModifierPValue) cp.get(9)).actualVal,data));
		
	}
	
}


//TODO more stuffs go here
