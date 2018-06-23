package model.yields;

import java.util.Collection;

public interface SimpleYieldProducer extends YieldProducer {
	
	@Override
	public default YieldBundle produce(Collection<YieldProducer> sameTileMembers, Collection<YieldProducer> adjacencies) {
		return produce();
	}
	
	public YieldBundle produce();
	
}
