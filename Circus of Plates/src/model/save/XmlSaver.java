package model.save;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;

public class XmlSaver implements SaverIF {

    private static XmlSaver saver;

    private XmlSaver() {

    }

    public static SaverIF getInstance() {
        if (saver == null) {
            saver = new XmlSaver();
        }
        return saver;
    }

    @Override
    public boolean save(Snapshot saved, String path, String name) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path + File.separator + name + ".xml")));
            encoder.writeObject(saved);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        encoder.close();
        return true;
    }

    @Override
    public Snapshot load(String path, String name) {
        Snapshot load;
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path + File.separator + name + ".xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        load = (Snapshot) decoder.readObject();
        decoder.close();
        return load;
    }

}
