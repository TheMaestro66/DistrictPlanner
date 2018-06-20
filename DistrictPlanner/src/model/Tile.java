package model;

import java.util.HashSet;
import java.util.Set;

import static model.Tile.Terrain.*;

public class Tile {
	
	// TODO
	private Terrain terrain;
	private Feature feature;
	
	/** for debugging purposes */
	private String consoleString = "T";
	
	public Tile(Terrain terrain) {
		this.terrain = terrain;
	}
	
	// TODO
	
	public String getConsoleString() {
		return consoleString;
	}
	public void setConsoleString(String string) {
		this.consoleString = string;
	}
	
	public enum Terrain {
		COAST, DESERT, GRASSLAND, LAKE, OCEAN, PLAINS, TUNDRA, SNOW;
		
		public boolean isWater() {
			switch(this) {
				case COAST:
				case LAKE:
				case OCEAN:
					return true;
				default:
					return false;
			}
		}
	}
	
	public enum Feature {
//		CATARACT, // TODO
//		CLIFFS, // TODO 
		FLOODPLAINS(DESERT), 
		HILLS(DESERT, GRASSLAND, PLAINS, TUNDRA, SNOW), 
		ICE(COAST, OCEAN), 
		MARSH(GRASSLAND), 
		MOUNTAINS(DESERT, GRASSLAND, PLAINS, TUNDRA, SNOW), 
		OASIS(DESERT), 
		RAINFOREST(PLAINS), 
		REEF(COAST), 
//		RIVER, // TODO
		WOODS(GRASSLAND, PLAINS, TUNDRA);
		
		Set<Terrain> placeableTerrain = new HashSet<>();
		
		Feature(Terrain... supported) {
			for (Terrain terr : supported)
				placeableTerrain.add(terr);
		}
		
		public boolean placeable(Terrain terrain) {
			return placeableTerrain.contains(terrain);
			// TODO account for possible Feature interactions
		}
	}
	
	public enum Yield { // TODO use this
		CULTURE, FAITH, FOOD, PRODUCTION, GOLD, SCIENCE;
	}
}