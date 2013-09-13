package pew.pew.topdown;

import org.newdawn.slick.Image;

public class TestFacEle extends FactoryElement implements ClickableFE{

	public TestFacEle(int id, Image pic, int layer,FactorySquare fs, TopDown td) {
		super(id, pic, layer, fs,td);//just a test
		feInfo.set(3, true);
	}
	
	@Override
	public void update(int delta){
		checkRotation();
	}

	@Override
	public void handleClick(int clickCount) {
		if(rotation <= 2){
			rotation++;
		}else{
			rotation = 0;
		}
	}

}
