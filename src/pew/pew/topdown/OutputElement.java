package pew.pew.topdown;

import org.newdawn.slick.Image;
public class OutputElement extends ConveyorBelt {
	
	TopDown td;

	
	/**
	 * will delete any element sent to it in notified and keep the count of the number of elements deleted
	 */
	public OutputElement(int id, Image pic, int layer, FactorySquare fs, TopDown td) {
		super(id, pic, layer, fs, td);
		this.td = td;
		feInfo.set(3,false);//don't want this to rotate, would be pointless and messy, not that this is anything but messy anyway
	}
	
	@Override
	public void update(int delta){
		//do nothing
	}
	
	@Override
	public void endSim(){
		isSimulating = false;
	}
	
	@Override
	public void startSim(){
		isSimulating = true;
	}
	
	@Override
	public void notified(int from, FactoryElement fe){
		fe.remove(true);
		
		if(!fe.feInfo.get(1) || !fe.feInfo.get(6)){
			System.out.println("tried to pass an invalid fe to an outputElement");
		}
		
		
	}
	
	@Override
	public String getSaveData(){
		String dat = super.getSaveData();
		dat = dat.replace("conveyorbelt", "output");
		return dat;
	}
}
