package pew.pew.topdown;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class ResearchButton extends MenuButton implements PromptCreator{

	public ResearchButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown td) {
		super(id, pic, pos, layer, container, td);
		
	}

	@Override
	public void handleClick() {
		if(isGrayed) return;
		if(td.queue.get(4).isEmpty()) td.queue.get(4).add(this);
	}
	
	@Override
	public void render(Graphics g){
		//TODO System.out.println("is grayed: " + isGrayed);
		super.render(g);
	}

	@Override
	public void makePrompt(PromptManager promptMan) {
		promptMan.newPrompt("becuaseican", "research", new Vector2f(200,100));
	}
	
}
