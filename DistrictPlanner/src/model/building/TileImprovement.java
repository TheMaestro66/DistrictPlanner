package model.building;

import java.util.Collection;

import model.yields.YieldBundle;
import model.yields.YieldProducer;

public enum TileImprovement implements Placeable {

	// TODO yields
	FARM, 
	MINE, 
	QUARRY, 
	PLANTATION, 
	CAMP, 
	PASTURE, 
	FISHING_BOATS, 
	LUMBER_MILL, 
	FORT, 
	AIRSTRIP, 
	SEASIDE_RESORT, 
	OIL_WELL, 
	MISSILE_SILO, 
	OFFSHORE_OIL_RIG, 
	;

	@Override
	public YieldBundle produce(Collection<YieldProducer> sameTileMembers, Collection<YieldProducer> adjacencies) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
