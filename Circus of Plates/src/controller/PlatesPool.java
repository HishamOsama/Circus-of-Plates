package controller;

import java.util.ArrayList;
import java.util.Random;

import controller.util.Container;
import controller.util.Iterator;
import model.shapes.interfaces.Shape;
import model.shapes.util.PlatesFactory;

public class PlatesPool implements Container{

	private final ArrayList<Shape> plates;
	private final int MAX_SHAPE_COUNT = 2;
	private int indexOfCurrentServed;

	public PlatesPool() {
		plates = new ArrayList<>();
		indexOfCurrentServed = -1; // Should be 0
		fillPlatesPool();
	}

	private void fillPlatesPool() {
		final PlatesFactory factory = PlatesFactory.getInstance();
		for (final String key : factory.getRegisteredShapes()) {
			for (int i = 0; i < MAX_SHAPE_COUNT; i++) {
				plates.add(factory.getShape(key));
			}
		}
		indexOfCurrentServed = plates.size() - 1;
	}

	public Shape getPlate() {
		if (plates.size() < indexOfCurrentServed) {
		    final PlatesFactory factory = PlatesFactory.getInstance();
		    for (final String key : factory.getRegisteredShapes()) {
		        final Shape requestedPlate = factory.getShape(key);
		        requestedPlate.setDelayTime(new Random().nextInt(100));
		        return requestedPlate;
		    }
		}
		final Shape requestedPlate = plates.remove(indexOfCurrentServed--);
		requestedPlate.setDelayTime(new Random().nextInt(100));
		return requestedPlate;
	}

	public void returnPlate(final Shape finishedShape) {
		plates.add(finishedShape);
	}

	@Override
	public Iterator getIterator() {
		return new PoolIterator();
	}

	private class PoolIterator implements Iterator{

		int index;

	      @Override
	      public boolean hasNext() {

	         if(index < plates.size()){
	            return true;
	         }
	         return false;
	      }

	      @Override
	      public Shape next() {

	         if(this.hasNext()){
	            return plates.get(index++);
	         }
	         return null;
	      }

	}
}
