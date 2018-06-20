package model;

import java.util.ArrayList;
import java.util.List;

public class HexMap {
	
	boolean firstCornerRounded = true;
	
	public List<List<Tile>> rows;

	public HexMap(int height, int width) {
		this.rows = new ArrayList<>();
		for (int i=0; i<height; i++) {
			List<Tile> row = new ArrayList<>();
			rows.add(row);
			for (int j=0; j<width; j++) {
				row.add(new Tile(Tile.Terrain.OCEAN));
			}
		}
	}
	
	// TODO remove this method?
	public Tile getTile(int row, int column) {
		return rows.get(row).get(column);
	}

	public Tile translate(Tile from, HexDirection direction) {
		return translate(from, direction, 1);
	}
	
	public Tile translate(Tile from, HexDirection direction, int distance) {
		if (distance == 0)
			return from;
		if (distance < 0)
			return translate(from, direction, -distance);
		
		List<Tile> originRow = null;
		for (List<Tile> row : rows) {
			if (row.contains(from)) {
				originRow = row;
				break;
			}
		}
		if (originRow == null)
			throw new RuntimeException("Where'd you get that Tile, friend?");
		
		int originIndex = originRow.indexOf(from);
		int originRowIndex = rows.indexOf(originRow);
		int destinationRowIndex = originRowIndex - (direction.verticalAdjustment()*distance);
		double horizontalAdj = direction.horizontalAdjustment()*distance;
		if ((destinationRowIndex - originRowIndex) % 2 != 0) {
			boolean shiftRight = (originRowIndex % 2 == 0) == firstCornerRounded;
			horizontalAdj += shiftRight ? 0.5 : -0.5;
		}
		int destinationIndex = (int) (originIndex + horizontalAdj);
		
		List<Tile> row = null;
		Tile tile = null;
		try {
			row = rows.get(destinationRowIndex);
		} catch (IndexOutOfBoundsException e) {
			boolean top = destinationRowIndex < 0;
			throw new RuntimeException("Would run off " + (top ? "top" : "bottom") + " edge of map.");
		}
		try {
			tile = row.get(destinationIndex);
		} catch (IndexOutOfBoundsException e) {
			boolean left = destinationIndex < 0;
			throw new RuntimeException("Would run off " + (left ? "left" : "right") + " edge of map.");
		}
		return tile;
	}
	
	public void consoleOutput() {
		int index = 0;
		for (List<Tile> row : rows) {
			if ((index++ % 2 == 0) == firstCornerRounded)
				System.out.print(" ");
			for (Tile tile : row) {
				System.out.print(tile.getConsoleString() + " ");
			}
			System.out.println();
		}
	}
	
	public enum HexDirection {
		RIGHT, TOP_RIGHT, TOP_LEFT,LEFT, BOTTOM_LEFT, BOTTOM_RIGHT;
		
		public HexDirection opposite() {
			return rotate(3);
		}
		
		public HexDirection rotate(int directionsClockwise) {
			HexDirection[] hexDirections = values();
			int numDirections = hexDirections.length;
			int index = (((ordinal() - directionsClockwise) % numDirections) + numDirections) % numDirections; // positive number
			return hexDirections[index];
		}
		
		/**
		 * @return 1 for RIGHT, 0.5 for TOP_RIGHT/BOTTOM_RIGHT, 
		 * and negative values thereof for all LEFT directions
		 */
		public double horizontalAdjustment() {
			int base = this.name().toUpperCase().contains("LEFT") ? -1 : 1;
			return verticalAdjustment() == 0 ? base : base / 2.0;
		}
		
		/**
		 * @return -1 for all directions that extend any amount toward the BOTTOM,
		 * or 1 for all directions that extend any amount toward the TOP,
		 * or else 0 for directions that do not have a vertical component
		 */
		public int verticalAdjustment() {
			return this.name().toUpperCase().contains("BOTTOM") ? -1 :
				this.name().toUpperCase().contains("TOP") ? 1 : 0;
		}
	}
	
}
