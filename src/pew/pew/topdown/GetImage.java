package pew.pew.topdown;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GetImage {

	private GetImage() {
		
	}
	
	public static Image get(String resDir){
		if(resDir.equalsIgnoreCase("null")){
			System.out.println("null passed to getImage");
			return null;
		}
		Image im = null;
		try {
			im = new Image(resDir);
			im.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("returning null image for reason of SlickException");
			System.out.println(e.getMessage());
		}
		return im;
	}

}
