package model.shapes.util;

import java.util.Collection;
import java.util.HashMap;

import model.shapes.interfaces.Shape;

public class PlatesFactory {

	private static PlatesFactory factory
					= new PlatesFactory();
	private final HashMap<String, Class<? extends Shape>>
					registeredShapes;

	public PlatesFactory() {
		registeredShapes = new HashMap<>();
	}

	public static PlatesFactory getInstance() {
		return factory;
	}

	public void registerShape(final String key,
			final Class<? extends Shape> shapeClass) {
		registeredShapes.put(key, shapeClass);
	}

	// There should be the creation method here but it
	// depends on the constructor of the Shape class
	public Collection<String> getRegisteredShapes() {
        return registeredShapes.keySet();
    }
}
