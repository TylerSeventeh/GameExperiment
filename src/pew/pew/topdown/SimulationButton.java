package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class SimulationButton extends MenuButton {

	boolean isSimulating;
	
	/**
	 * @param td the td that holds the factoryeditor to be simulated
	 */
	public SimulationButton(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown td) {
		super(id, pic, pos, layer, container, td);
		
	}
	
	@Override
	public void handleClick() {
		if(isSimulating){
			td.fE.stopSimulation();
		}else{
			td.fE.startSimulation();
			isSimulating = true;
		}
	}

}
