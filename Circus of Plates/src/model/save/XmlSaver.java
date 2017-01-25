package model.save;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

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

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element
            Element rootElement = doc.createElement(name);
            doc.appendChild(rootElement);
            /************* Game State *********************/
            saveGameState(doc, rootElement, saved);
            /********************************************/
            /*************** Stacks ***********************/
            saveGameStacks(doc, rootElement, saved);
            /********************************************/
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path + File.separator + name + ".xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void saveGameState(Document doc, Element rootElement, Snapshot saved) {
        Element GameState = doc.createElement("GameState");
        rootElement.appendChild(GameState);

        Element diff = doc.createElement("Difficulty");
        diff.appendChild(doc.createTextNode(Integer.toString(saved.getGameState().getDiff())));
        GameState.appendChild(diff);

        Element time = doc.createElement("Time");
        time.appendChild(doc.createTextNode(Integer.toString(saved.getGameState().getElapsedTime())));
        GameState.appendChild(time);

        Element players = doc.createElement("players");
        for (int i = 0; i < saved.getGameState().getPsLength(); i++) {
            Element player = doc.createElement("player");
            players.appendChild(player);
            Element x = doc.createElement("x");
            player.appendChild(x);
            x.appendChild(doc.createTextNode(Double.toString(saved.getGameState().getP(i)[0])));
            Element y = doc.createElement("y");
            player.appendChild(y);
            y.appendChild(doc.createTextNode(Double.toString(saved.getGameState().getP(i)[1])));

        }
        GameState.appendChild(players);
        Element scores = doc.createElement("Scores");
        for (int i = 0; i < saved.getGameState().getScores().length; i++) {
            Element score = doc.createElement("Score");
            score.appendChild(doc.createTextNode(Integer.toString(saved.getGameState().getScores()[i])));
            scores.appendChild(score);
        }
        GameState.appendChild(scores);
    }

    private void saveGameStacks(Document doc, Element rootElement, Snapshot saved) {
        Element stacks = doc.createElement("Stacks");
        rootElement.appendChild(stacks);
        for (int i = 0; i < saved.getDateSize(); i++) {
            Element stack = doc.createElement("Stack");
            //// Right hand
            Element right = doc.createElement("right");
            PlayersStacksData data = saved.getDate(i);
            saveHands(doc, right, data.getRightHandshapes());

            Element left = doc.createElement("left");
            saveHands(doc, left, data.getlefttHandshapes());
            //// Left Hand
            // End
            stack.appendChild(right);
            stack.appendChild(left);
            stacks.appendChild(stack);

        }
    }

    private void saveHands(Document doc, Element rootElement, ArrayList<SaveShapeNode> saved) {

        for (int i = 0; i < saved.size(); i++) {
            Element element = doc.createElement("element");
            // x y name color
            Element x = doc.createElement("x");
            x.appendChild(doc.createTextNode(Double.toString(saved.get(i).getX())));
            Element y = doc.createElement("y");
            y.appendChild(doc.createTextNode(Double.toString(saved.get(i).getY())));
            Element color = doc.createElement("color");
            color.appendChild(doc.createTextNode((saved.get(i).getColor()).toString()));
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(saved.get(i).getName()));
            element.appendChild(name);
            element.appendChild(color);
            element.appendChild(x);
            element.appendChild(y);
            rootElement.appendChild(element);
        }
    }

    @Override
    public Snapshot load(final String path, final String name) {
        Snapshot load = new Snapshot();
        GameState g;
        PlayersStacksData[] data = new PlayersStacksData[2];

        String fullPath = path + File.separator + name + ".xml";
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            File inputFile = new File(fullPath);
            InputStream inputStream = new FileInputStream(inputFile);
            org.w3c.dom.Document doc = builder.parse(fullPath);
            doc.getDocumentElement().normalize();
            int diff = Integer.valueOf(doc.getElementsByTagName("Difficulty").item(0).getTextContent());
            int time = Integer.valueOf(doc.getElementsByTagName("Time").item(0).getTextContent());
            int[] scores = new int[] {-1, -1};
            NodeList nList = doc.getElementsByTagName("Score");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    scores[i] = Integer.valueOf(eElement.getTextContent());
                }
            }
            double Ps[][] = new double[2][];
            nList = doc.getElementsByTagName("player");
            for (int i = 0; i < nList.getLength(); i++) {
                double[] p = new double[2];
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    p[0] = Double.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
                    p[1] = Double.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
                }
                Ps[i] = p;
            }
            g = new GameState(scores, time, diff, Ps[0], Ps[1]);
            nList = doc.getElementsByTagName("Stack");
            for (int i = 0; i < nList.getLength(); i++) {
                ArrayList<SaveShapeNode> left = new ArrayList<>();
                ArrayList<SaveShapeNode> right = new ArrayList<>();
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList nList2 = eElement.getElementsByTagName("right");
                    for (int j = 0; j < nList2.getLength(); j++) {
                        Node nNode2 = nList2.item(j);
                        if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement2 = (Element) nNode2;
                            NodeList nList3 = eElement2.getElementsByTagName("element");
                            for (int k = 0; k < nList3.getLength(); k++) {
                                Node nNode3 = nList3.item(k);
                                if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement3 = (Element) nNode3;
                                    String className = eElement3.getElementsByTagName("name").item(0).getTextContent();
                                    double x = Double
                                            .valueOf(eElement3.getElementsByTagName("x").item(0).getTextContent());
                                    double y = Double
                                            .valueOf(eElement3.getElementsByTagName("y").item(0).getTextContent());

                                    Color color = Color.RED; // eElement2.getElementsByTagName("color").item(0).getTextContent();
                                    right.add(new SaveShapeNode(className, color, x, y));
                                }
                            }
                        }

                    }
                    nList2 = eElement.getElementsByTagName("left");
                    for (int j = 0; j < nList2.getLength(); j++) {
                        Node nNode2 = nList2.item(j);
                        if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement2 = (Element) nNode2;
                            NodeList nList3 = eElement2.getElementsByTagName("element");
                            for (int k = 0; k < nList3.getLength(); k++) {
                                Node nNode3 = nList3.item(k);
                                if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement3 = (Element) nNode3;
                                    String className = eElement3.getElementsByTagName("name").item(0).getTextContent();
                                    double x = Double
                                            .valueOf(eElement3.getElementsByTagName("x").item(0).getTextContent());
                                    double y = Double
                                            .valueOf(eElement3.getElementsByTagName("y").item(0).getTextContent());

                                    Color color = Color.RED; // eElement2.getElementsByTagName("color").item(0).getTextContent();
                                    left.add(new SaveShapeNode(className, color, x, y));
                                }
                            }
                        }

                    }

                    data[i] = new PlayersStacksData(right, left);
                }
            }
            load.buildGameState(g);
            load.buildPlayer(data);

        } catch (Exception e) {

        }
        return load;
    }

}
