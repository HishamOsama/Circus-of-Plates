package model.shapes.dynamicloading;

import java.io.File;

public class TestLoader {

    public static void main(final String[] args) {
        final Loader loader = Loader.getInstance();
        final String path = System.getProperty("user.dir") + File.separator + "shapesJARS";
        final File file = new File(path);
        for (File f : file.listFiles()) {
            loader.invokeClassMethod(f);
        }

    }

}
