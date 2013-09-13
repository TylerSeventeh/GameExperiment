package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class NewPromptButton extends PromptButton implements PromptCreator{
	
	String promptType;
	boolean disposeCurrentPrompt;
	Vector2f promptPosition;

	public NewPromptButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga,String promptType,boolean disposeCurrentPrompt) {
		super(id, pic, pos, layer, container, ga);
		this.disposeCurrentPrompt = disposeCurrentPrompt;
		this.promptType = promptType;
	}

	@Override
	public void handleClick() {
		
		//assumes the second element added to a prompt is always the window
		
		promptPosition = td.promptMan.prompts.get(id).eles.get(1).position;
		if(disposeCurrentPrompt){
			td.queue.get(3).add(this);
		}
		td.queue.get(4).add(this);
	}



	@Override
	public void makePrompt(PromptManager promptMan) {
		td.promptMan.newPrompt("thisispointless",promptType,promptPosition);
		
	}

}
