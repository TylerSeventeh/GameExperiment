package pew.pew.topdown;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Image;

//TODO THIS, make it work
//saving is broken, dont know if loading is

public class SavesManager {
	
	TopDown td;
	
	public SavesManager(TopDown topD){
		td = topD;
		File saveFile = new File("saves");//makes saves folder
		if(!saveFile.exists()){
			saveFile.mkdir();
		}
	}
	/**
	 * i now never need to update this method for factories, i only need to update the element that was saved
	 * @param saveName name of save file, include file extension. this method will overwrite without warning
	 */
	public void saveGame(String saveName, SaveData savDat){
		System.out.println("saving: " + saveName);
		BufferedWriter bw = null;
		File saveFile = new File("saves\\"+saveName);
		if(savDat.levelType == 0){//loaded with levelLoader
			
			try {
				if(!saveFile.exists()){
					saveFile.createNewFile();
				}
				bw = new BufferedWriter(new FileWriter(saveFile));
				bw.write(savDat.levelName);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}finally{ try{ bw.close(); } catch(IOException e){ e.printStackTrace(); } }//close bufferedwriter
			
		}else if(savDat.levelType == 1){//loaded into factoryEditor
			try{
				if(!saveFile.exists()){
					saveFile.createNewFile();
				}
				bw = new BufferedWriter(new FileWriter(saveFile));
				FactorySaveData fsd = (FactorySaveData) savDat;
				ArrayList<String> dataStrings = new ArrayList<String>();
				//saved in the format: saveType;id;resDir;layer;feData(1),etc,etc;x,y where x,y is to be used in grid.get(y).get(x)
				//any additional info will be saved after x,y;
				String currentData = "";
				for(ArrayList<FactorySquare> row : fsd.fE.fb.grid){
					for(FactorySquare fs : row){
						currentData = fs.subEle.getSaveData();
						if(currentData == null || currentData.equals("")) continue;
						dataStrings.add(currentData);
						if(fs.subEle.feInfo.get(0)){
							ConveyorBelt cb = (ConveyorBelt) fs.subEle;
							if(cb.holding != null){
								dataStrings.add(cb.holding.getSaveData());
								System.out.println("getting held element data: " + dataStrings.get(dataStrings.size()-1));
							}
						}
					}
				}
				
				
				//write stuffs
				bw.write(fsd.levelName);
				for(String s : dataStrings){
					bw.newLine();
					bw.write(s);
				}
				bw.newLine();
				bw.write("end");
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{bw.close();}catch(Exception e){System.out.println(e.getMessage());}
			}
		}
		
	}
	/*		tests getFeInfo
	public static void main(String[] ar){
		SavesManager sm = new SavesManager(null);
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		list.add(false);list.add(true);list.add(true);
		System.out.println(sm.getFeInfo(list));
	}
	*/
	
	// so i dont have to update this class everytime i add another case in feInfo
	public static String getFeInfo(ArrayList<Boolean> feInfo){//this is probably more complicated than it needs to be
		String dataString = "";
		for(int i = 0;i<feInfo.size();i++){
			dataString = dataString.concat(Boolean.toString(feInfo.get(i)));
			if(i != feInfo.size()-1){
				dataString = dataString.concat(",");
			}
		}
		dataString = dataString.concat(";");
		return dataString;
	}
	
	/**
	 * uses the factory editor in the current TD if type 1
	 * @param saveName name of save file, include file extension
	 */
	public void loadGame(String saveName,int levelType){
		BufferedReader br = null;
		File saveFile = new File("saves\\"+saveName);
		String levelName = null;
		if(levelType == 0){//load in with levelLoader
			try {
				if(!saveFile.exists()){
					System.out.println("tried to load non-existant level");
					return;
				}
				br = new BufferedReader(new FileReader(saveFile));
				levelName = br.readLine();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}finally{ try{ br.close(); } catch(IOException e){ e.printStackTrace(); } }
			td.changeLevel(levelName);
		}else if(levelType == 1){//loaded into the factoryEditor
			try{
				if(!saveFile.exists()){
					System.out.println("tried to load non-existant file");
					return;
				}
				br = new BufferedReader(new FileReader(saveFile));
				br.readLine();//to get rid of levelName TODO should make it so that it doesnt even save levelName in the case of a factory
				while(true){
					FactoryElement fe = loadFactoryElement(br);
					if(fe == null){
						break;
					}
					td.updateManagers(fe);
					
					
				}
				
			}catch(Exception e){
				System.out.println("failed to load factoryeditor");
				e.printStackTrace();
			}finally{
				try {
					br.close();
				} catch (IOException e) {System.out.println(e.getMessage());}
			}
		}//else{//other loading type
		
	}
	
	//TODO figure out why there are factoryparts being created at the topleft corner of the screen
	public FactoryElement loadFactoryElement(BufferedReader br) throws IOException{
		FactoryElement fe;
		String currentLine = br.readLine();
		
		if(currentLine.equalsIgnoreCase("end")){
			return null;
		}
		
		String[] loadData = currentLine.split(";");
		String[] subArray = loadData[5].split(",");//gets position of the fs that should be holding this
		if(Integer.parseInt(subArray[0]) >= 0){
			FactorySquare fs = td.fE.fb.grid.get(Integer.parseInt(subArray[1])).get(Integer.parseInt(subArray[0]));//gets the fs
			td.removeElement(fs.subEle);//clears the fs
			fs.subEle =	makeFe(loadData[0],Integer.parseInt(loadData[1]),GetImage.get(loadData[2]),Integer.parseInt(loadData[3]),fs,loadData,br);
			fe = fs.subEle;
		}else{
			fe = makeFe(loadData[0],Integer.parseInt(loadData[1]),GetImage.get(loadData[2]),Integer.parseInt(loadData[3]),null,loadData,br);
		}
		
		return fe;
	}
	
	
	public FactoryElement makeFe(String type, int id,Image im,int layer,FactorySquare fs,String[] fullData,BufferedReader br) throws IOException{
		/*FactoryElement temp = null;
		
		 TODO will have to put this back when i fix this; commented so i could compile without problems, because i'm not using it anyways
		switch(type){
		case("default"):
			temp = new FactoryElement(id,im,layer,fs);
			break;
		case("conveyorbelt"):
			ConveyorBelt tempCB = new ConveyorBelt(id,im,layer,fs,td);
			temp = tempCB;
			break;
		case "input"://FIXED: this is ending up as just a factoryelement with the image and probably the feInfo
			//I forgot to break;... it was ending as an output ele
			//also fixed: i had a wrong conditional that didnt remove the held element when i expected it to; was == instead of !=
			InputElement tempIn = new InputElement(id,im,layer,fs,Integer.parseInt(fullData[8]),td);
			temp = tempIn;
			break;
		case "output":
			OutputElement tempOut = new OutputElement(id,im,layer,fs,td);
			temp = tempOut;
			break;
		}
		
		String[] subArray = fullData[4].split(",");//gets the feInfo
		for(int i = 0;i < FactoryElement.TOTAL_FE_INFO;i++){
			temp.feInfo.set(i, Boolean.valueOf(subArray[i]));
		}
		
		
		if(temp.feInfo.get(0)){//if a conveyorbelt
			ConveyorBelt tempCB = (ConveyorBelt) temp;
			tempCB.rotation = Integer.parseInt(fullData[6]);
			if(fullData[7].equalsIgnoreCase("true")){//if has a holding element
				if(tempCB.holding != null){//if it already created its defaultly held element
					td.removeElement(tempCB.holding);
				}
				tempCB.holding = loadFactoryElement(br);
				td.updateManagers(tempCB.holding);
			}
		}
		*/
		return null;
	}
	
	public File[] getSavedGames(){
		return new File("saves").listFiles();
	}
}
