package pew.pew.topdown;

public class FactorySaveData extends SaveData {

	FactoryEditor fE;
	
	public FactorySaveData(FactoryEditor fE) {
		super("factory");
		this.fE = fE;
		levelType = 1;
	}

}
