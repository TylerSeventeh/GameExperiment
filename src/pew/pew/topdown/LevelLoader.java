package pew.pew.topdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.gui.GUIContext;


public class LevelLoader {
	
	int x,y, lastUnusedId;
	List<Element> elements;
	BufferedReader br;
	InputStream in;
	String level;
	Player player;
	ElementFactory eleF;
	
	public LevelLoader(String levelDir, int x, int y,GUIContext guic,TopDown td){
		level = levelDir;
		this.x = x;
		this.y = y;
		eleF = new ElementFactory(guic,td);
		lastUnusedId = 4;
	}
	
	private void addBorders(ElementFactory eleF){
		Element colEle;
		String[] borderInfo = new  String[3];
		borderInfo[0] = String.valueOf(x);
		borderInfo[1] = String.valueOf(y);
		borderInfo[2] = "3";
		for(int i = 0;i<4; i++){
			colEle = eleF.createElement("Border", i, "null", borderInfo);
			elements.add(colEle);
		}
	}
	
	
	
	public boolean loadLevel(){
		elements = new ArrayList<Element>();
		boolean passed = true;
		if(level == null){
			return false;
		}
		
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("res\\"+level))));
			String currentLine = "";
			String[] lineInfo = new String[3];
			int id = 4;
			addBorders(eleF);
			while(true){
				currentLine = br.readLine();
				if(currentLine.equalsIgnoreCase("end")){
					this.player = eleF.player;
					break;
				}
				lineInfo = currentLine.split(";");
				elements.add(eleF.createElement(lineInfo[0], id, "res\\"+lineInfo[1], lineInfo[2].split(",")));
				++id;
			}
			lastUnusedId = id++;
		}catch(IOException e){
			System.out.println("level loading falure: "+e.getMessage());
			passed = false;
		}finally{
			try{
				br.close();
			}catch(IOException e){
				System.out.println("problem closing BufferedReader: "+e.getMessage());
			}
		}
		return passed;
	}
}
