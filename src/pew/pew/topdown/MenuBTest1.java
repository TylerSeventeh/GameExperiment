package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.GUIContext;

public class MenuBTest1 extends MenuButton {

	int i;
	boolean wasPrompting;
	Image hovIm,notHovIm;
	
	public MenuBTest1(int id, Image pic, Vector2f pos, int layer,
			GUIContext container, TopDown ga,Image hoveringPic) {
		super(id, pic, pos, layer, container, ga);
		moa.setMouseOverImage(hoveringPic);
		i = 0;
		notHovIm = pic;
		hovIm = hoveringPic;
		wasPrompting = false;
	}

	@Override
	public void update(int delta){
		if(td.isPrompting == wasPrompting) return;
		if(td.isPrompting){
			moa.setMouseOverImage(notHovIm);
			wasPrompting = true;
		}else{
			moa.setMouseOverImage(hovIm);
			wasPrompting = false;
		}
		
	}
	
	@Override
	public void handleClick() {
		
		td.promptMan.newPrompt("this shouldnt be showing", "test", new Vector2f(450 + i,150 + i));
		i += 10;
	}
	
}
