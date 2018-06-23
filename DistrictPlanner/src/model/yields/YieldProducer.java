package model.yields;

import java.util.Collection;

public interface YieldProducer {
	
	public YieldBundle produce(Collection<YieldProducer> sameTileMembers, Collection<YieldProducer> adjacencies);
	
}
