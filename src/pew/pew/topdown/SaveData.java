package pew.pew.topdown;

public class SaveData {
	
	/**
	 * 0 	regular level to be loaded with the level loader
	 * 1	Factory editor level, loaded with factory editor
	 */

	int levelType;// only used when loading in a regular level
	String levelName;
	
	public SaveData(String curLvl){
		levelName = curLvl;
		levelType = 0;
	}
}
