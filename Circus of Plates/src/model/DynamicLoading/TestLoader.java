package model.DynamicLoading;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import model.shapes.interfaces.Shape;

public class TestLoader {

    public static void main(String[] args) {
        Loader loader = new Loader();
        loader.setPath("C:\\Users\\Muhammed\\git\\circus-of-plates\\Circus of Plates\\test.jar");
        ArrayList<Constructor<Shape>> loaded = loader.invokeClassMethod();
        for(int i = 0 ; i < loaded.size() ; i++){
            System.out.println(loaded.get(i).getName());
        }

    }

}
