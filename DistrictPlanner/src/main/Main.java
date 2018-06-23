package main;

import model.HexMap;
import model.Tile;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HexMap map = new HexMap(10, 10);
		
		
		// debugging, POC:
//		Tile tile = map.getTile(0,0);
//		while (true) {
//			tile.setConsoleString("X");
//			try {
//				tile = map.translate(tile, HexMap.HexDirection.BOTTOM_RIGHT);
//			} catch (Exception e) {
////				System.out.println("Ran off, presumably");
//				break;
//			}
//		}
		map.consoleOutput();
	}

}
