package pew.pew.topdown;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class FactorySaveButton extends MenuButton {
	
	boolean isSimulating;
	FactoryEditor fE;

	public FactorySaveButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown td) {
		super(id, pic, pos, layer, container, td);
		fE = td.fE;
		this.isGrayed = true;
	}

	@Override
	public void render(Graphics g){
		isGrayed = true;
		super.render(g);
	}
	
	@Override
	public void handleClick() {
		/*
		if(!isSimulating && !fE.inputInfo.contains(1) && !fE.inputInfo.contains(0)){
			fE.inputInfo.add(0);
			
			//td.savManager.saveGame("test.factory", new FactorySaveData("Factory",td.fE));
		}
		*/
	}

}
