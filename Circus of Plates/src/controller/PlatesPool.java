package controller;

import java.util.ArrayList;

import model.shapes.interfaces.Shape;
import model.shapes.util.PlatesFactory;

public class PlatesPool {

	private final ArrayList<Shape> plates;
	private final int MAX_SHAPE_COUNT = 20;
	private final int indexOfCurrentServed;
	public PlatesPool() {
		plates = new ArrayList<>();
		indexOfCurrentServed = -1;
		fillPlatesPool();
	}

	private void fillPlatesPool() {
		final PlatesFactory factory = PlatesFactory.getInstance();
		for (final String key : factory.getRegisteredShapes()) {
			for (int i = 0; i < MAX_SHAPE_COUNT; i++) {
				plates.add(factory.getShape(key));
			}
		}
 	}

	public Shape getPlate() {
		if (plates.size() < indexOfCurrentServed) {
			throw new RuntimeException();
		}
		final Shape requestedPlate = plates.remove(indexOfCurrentServed);
		return requestedPlate;
	}

	public void returnPlate(final Shape finishedShape) {
		plates.add(finishedShape);
	}
}
