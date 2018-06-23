package model.yields;

import java.util.HashMap;
import java.util.Map;

public class YieldBundle {

	private Map<Yield, Double> yields;
	
	public YieldBundle() {
		yields = new HashMap<>();
	}
	
	/**
	 * CULTURE, FAITH, FOOD, PRODUCTION, GOLD, SCIENCE
	 * 
	 * @param yieldsInOrder integer values for each {@link Yield}, taken in order
	 */
	public YieldBundle(Double... yieldsInOrder) {
		this();
		Yield[] yieldTypes = Yield.values();
		if (yieldsInOrder.length > yieldTypes.length)
			throw new IllegalArgumentException("Only " + yieldTypes.length + " yields are supported.");
		for (int i=0; i<yieldsInOrder.length; i++)
			yields.put(yieldTypes[i], (double) yieldsInOrder[i]);
	}
	
	public double get(Yield type) {
		return yields.get(type);
	}
	
	public void add(Yield type, double quantity) {
		yields.put(type, yields.get(type) + quantity);
	}
	
	/**
	 * @param other YieldBundle to be combined with this one
	 */
	public void absorb(YieldBundle other) {
		for (Yield key : other.yields.keySet())
			this.yields.put(key, this.yields.get(key) + other.yields.get(key));
	}
	
}
