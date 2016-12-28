package model.shapes.dynamicloading;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import model.shapes.interfaces.Shape;

public class TestLoader {

    public static void main(final String[] args) {
        final Loader loader = new Loader();
        final String path = "D:\\College\\2ndYear1stTerm\\OOP\\CircusOfPlatesRepository\\circus-of-plates\\Circus of Plates\\shapesJARS\\";
        loader.setPath(path);
        final ArrayList<Constructor<Shape>> loaded = loader.invokeClassMethod();
        for(int i = 0 ; i < loaded.size() ; i++){
        	try {
				Class.forName(loaded.get(i).getName());
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
            System.out.println(loaded.get(i).getName());
        }

    }

}
