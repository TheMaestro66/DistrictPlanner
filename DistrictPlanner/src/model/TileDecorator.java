package model;

import java.util.Collection;

import model.building.Placeable;
import model.yields.SimpleYieldProducer;
import model.yields.Yield;
import model.yields.YieldBundle;
import model.yields.YieldProducer;

public class TileDecorator {
	
	public String decorate(Tile tile) {
		String string = "*";
		
		// TODO
		YieldProducer lead = null;
		int found = 0;
		Collection<YieldProducer> contents = tile.getContents();
		for (YieldProducer producer : contents) {
			if (producer instanceof Placeable) {
				found = 3;
				lead = producer;
			} else if (found < 2 && producer instanceof Tile.Feature) {
				found = 2;
				lead = producer;
			} else if (found < 1) { // Terrain
				found = 1;
				lead = producer;
			}
		}
		
		if (lead instanceof SimpleYieldProducer) {
			return "" + sumOfYields((SimpleYieldProducer) lead);
		}
		
		return found == 3 ? "B" : found == 2 ? "F" : found == 1 ? "T" : string;
	}
	
	private int sumOfYields(SimpleYieldProducer producer) {
		YieldBundle bundle = producer.produce();
		int sum = 0;
		for (Yield yield : Yield.values())
			sum += bundle.get(yield);
		return sum;
	}
	
}
