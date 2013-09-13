package pew.pew.topdown;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class FactoryLoadButton extends MenuButton {
	
	boolean isSimulating;
	GameContainer gc;//so the fE has somewhere to get the gc from
	FactoryEditor fE;

	public FactoryLoadButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown td) {
		super(id, pic, pos, layer, container, td);
		gc = (GameContainer) container;
		this.fE = td.fE;
	}
	
	@Override
	public void render(Graphics g){
		isGrayed = true;
		super.render(g);
	}

	@Override
	public void handleClick() {
		/*
		//should only load once per update and shouldnt load and save at the same time
		if(!isSimulating && !fE.inputInfo.contains(1) && !fE.inputInfo.contains(0)){
			fE.inputInfo.add(1);
		}
		*/
	}

}
