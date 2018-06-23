package model.building;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import model.yields.Yield;
import model.yields.YieldBundle;
import model.yields.YieldProducer;

public enum District implements Placeable {

	// TODO add bonuses
	CITY_CENTER, 
	CAMPUS, 
	HOLY_SITE, 
	THEATER_SQUARE, 
	ENCAMPMENT, 
	HARBOR, 
	COMMERCIAL_HUB, 
	INDUSTRIAL_ZONE, 
	ENTERTAINMENT_COMPLEX, 
	AQUEDUCT, 
	NEIGHBORHOOD, 
	AERODROME, 
	SPACEPORT, 
	GOVERNMENT_PLAZA, 
	WATER_PARK, 
	;
	
//	private Set<AdjacencyBonus> sameTileBonuses; // TODO Commercial Hubs and Rivers
	private Set<AdjacencyBonus> adjacencyBonuses;
	
	District(AdjacencyBonus... bonuses) {
		this.adjacencyBonuses = new HashSet<>();
		for (AdjacencyBonus bonus : bonuses)
			this.adjacencyBonuses.add(bonus);
	}

	@Override
	public YieldBundle produce(Collection<YieldProducer> sameTileMembers, Collection<YieldProducer> adjacencies) {
		YieldBundle yields = new YieldBundle();
		
		// TODO sameTileMembers ???
		
		for (AdjacencyBonus bonus : adjacencyBonuses)
			yields.absorb(bonus.get(adjacencies));
		
		return yields;
	}
	
	public static class AdjacencyBonus {
		
		private final YieldProducer type;
		private final Yield yield;
		private final AdjacencyBonusDegree degree;
		
		public AdjacencyBonus(YieldProducer adjacencyType, Yield yield, AdjacencyBonusDegree degree) {
			this.type = adjacencyType;
			this.yield = yield;
			this.degree = degree;
		}
		
		public YieldBundle get(Collection<YieldProducer> adjacencies) {
			YieldBundle yields = new YieldBundle();
			
			Class<? extends YieldProducer> typeClass = type.getClass();
			double quantity = degree.getQuantity();
			for (YieldProducer adjacency : adjacencies)
				if (typeClass.isInstance(adjacency))
					yields.add(yield, quantity);
			
			return yields;
		}
	}
	
	public enum AdjacencyBonusDegree {
		
		MINOR(0.5), MAJOR(1), DOUBLE(2);
		
		double quantity;
		
		AdjacencyBonusDegree(double yieldQuantity) {
			this.quantity = yieldQuantity;
		}
		
		public double getQuantity() {
			return quantity;
		}
	}

}
