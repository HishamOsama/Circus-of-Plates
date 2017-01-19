package controller.util;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class StackRemover {

    private static Pane mPane;

    public StackRemover() {

    }

    public static void setPane(Pane mnPane) {
        mPane = mnPane;
    }

    public static boolean remove(List<Node> nodes) {
        if (mPane == null)
            return false;
        return mPane.getChildren().removeAll(nodes);
    }

}
