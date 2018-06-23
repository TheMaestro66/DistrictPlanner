package model;

import static model.Tile.Terrain.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.building.Placeable;
import model.yields.SimpleYieldProducer;
import model.yields.YieldBundle;
import model.yields.YieldProducer;

public class Tile {
	
	private Terrain terrain;
	private Set<Feature> features;
	private Set<Placeable> structures;
	
	public Tile(Terrain terrain) {
		this.terrain = terrain;
		features = new HashSet<>();
		structures = new HashSet<>();
	}
	
	public void addFeature(Feature feature) {
		if (!feature.isPlaceable(terrain))
			throw new RuntimeException("Feature not placeable"); // FIXME improve this
		features.add(feature);
	}
	
	public void place(Placeable building) {
		structures.add(building);
	}
	
	public Collection<YieldProducer> getContents() {
		Collection<YieldProducer> contents = new ArrayList<>();
		
		contents.add(terrain);
		contents.addAll(features);
		contents.addAll(structures);
		
		return contents;
	}
	
	public YieldBundle evaluate(Collection<Tile> adjacentTiles) {
		YieldBundle yields = new YieldBundle();
		
		Collection<YieldProducer> contents = getContents();
		List<YieldProducer> adjacentContents = new ArrayList<>();
		for (Tile tile : adjacentTiles)
			adjacentContents.addAll(tile.getContents());
		
		for (YieldProducer producer : contents) {
			if (producer instanceof SimpleYieldProducer) {
				yields.absorb(((SimpleYieldProducer) producer).produce());
			} else {
				yields.absorb(producer.produce(contents, adjacentContents));
			}
		}
		
		return yields;
	}
	
	public enum Terrain implements SimpleYieldProducer {
		
		COAST		(0,0,1,0,1,0), 
		DESERT		(0,0,0,0,0,0), 
		GRASSLAND	(0,0,2,0,0,0), 
		LAKE		(0,0,1,0,1,0), 
		OCEAN		(0,0,1,0,0,0), 
		PLAINS		(0,0,1,1,0,0), 
		TUNDRA		(0,0,1,0,0,0), 
		SNOW		(0,0,0,0,0,0), 
		;
		
		private double culture, faith, food, production, gold, science;
		
		Terrain(int culture, int faith, int food, int production, int gold, int science) {
			this.culture = culture;
			this.faith = faith;
			this.food = food;
			this.production = production;
			this.gold = gold;
			this.science = science;
		}
		
		@Override
		public YieldBundle produce() {
			return new YieldBundle(culture, faith, food, production, gold, science);
		}

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
	
	public enum Feature implements SimpleYieldProducer {
//		CATARACT, // TODO
//		CLIFFS, // TODO 
		FLOODPLAINS	(0,0,3,0,0,0, DESERT), 
		HILLS		(0,0,0,1,0,0, DESERT, GRASSLAND, PLAINS, TUNDRA, SNOW), // TODO mix with other Features (woods, rainforest...)
		ICE			(0,0,0,0,0,0, COAST, OCEAN), 
		MARSH		(0,0,1,0,0,0, GRASSLAND), 
		MOUNTAINS	(0,0,0,0,0,0, DESERT, GRASSLAND, PLAINS, TUNDRA, SNOW), 
		OASIS		(0,0,3,0,1,0, DESERT), 
		RAINFOREST	(0,0,1,0,0,0, PLAINS), 
		REEF		(0,0,1,1,0,0, COAST), 
		RIVER		(0,0,0,0,0,0, DESERT, GRASSLAND, PLAINS, TUNDRA, SNOW), // TODO check these
		WOODS		(0,0,0,1,0,0, GRASSLAND, PLAINS, TUNDRA);
		
		Set<Terrain> placeableTerrain = new HashSet<>();
		
		private double culture, faith, food, production, gold, science;
		
		Feature(int culture, int faith, int food, int production, int gold, int science, Terrain... supported) {
			this.culture = culture;
			this.faith = faith;
			this.food = food;
			this.production = production;
			this.gold = gold;
			this.science = science;
			
			for (Terrain terr : supported)
				placeableTerrain.add(terr);
		}
		
		@Override
		public YieldBundle produce() {
			return new YieldBundle(culture, faith, food, production, gold, science);
		}

		public boolean isPlaceable(Terrain terrain) {
			return placeableTerrain.contains(terrain);
			// TODO account for possible Feature interactions
		}
	}
	
	// Is the water system even necessary currently? Oh well, here it is :)
	public Water getWater(Collection<YieldProducer> adjacencies) {
		if (features.contains(Feature.RIVER))
			return Water.FRESH_WATER;
		if (adjacencies.contains(Terrain.LAKE)) // (or lake coast? how does that work?)
			return Water.FRESH_WATER;
		if (adjacencies.contains(Terrain.OCEAN) || adjacencies.contains(Terrain.COAST)) // (however that works)
			return Water.SALT_WATER;
		else
			return Water.NO_WATER;
	}
	
	public enum Water {
		NO_WATER, SALT_WATER, FRESH_WATER;
	}
}