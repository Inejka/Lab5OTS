package main.java.app;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.java.support.CoolLine;
import main.java.support.Node;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Drawer {

    double YSTEP = 100;
    double SizeX;


    Pane pane;
    Map<Node, List<Node>> getChildes = new LinkedHashMap<>();
    List<List<Node>> levels = new LinkedList<>();

    public Drawer(Pane pane, TreeItem<String> tree) {
        this.pane = pane;
        pane.getChildren().clear();
        levels.add(new LinkedList<>());
        Node initialNode = new Node(tree.getValue());
        levels.get(0).add(initialNode);
        genGraphFromTreeItem(0, tree, initialNode);

    }


    private void genGraphFromTreeItem(int level, TreeItem<String> treeItem, Node father) {
        getChildes.put(father, new LinkedList<>());
        for (TreeItem<String> i : treeItem.getChildren()) {
            try {
                levels.get(level + 1);
            } catch (IndexOutOfBoundsException e) {
                levels.add(new LinkedList<>());
            }
            Node node = new Node(i.getValue());
            levels.get(level + 1).add(node);
            getChildes.get(father).add(node);
            genGraphFromTreeItem(level + 1, i, node);
        }
    }

    public void draw() {
        /*int maxCount = 0 ;
        for(int i = 0 ; i < levels.size() ; i++)
            maxCount = Math.max(levels.get(i).size(),maxCount);
        SizeX = maxCount*50;*/
        SizeX = 1000;
        calculateX();
        calculateY();
        drawLines();
        drawNodes();
        drawNames();
    }

    private void drawNames() {
        for (int i = 0; i < levels.size(); i++)
            for (int j = 0; j < levels.get(i).size(); j++) {
                Text label = new Text(levels.get(i).get(j).getInfo());
                label.setLayoutX(levels.get(i).get(j).getX()-label.getLayoutBounds().getWidth()/2);
                label.setLayoutY(levels.get(i).get(j).getY()-20);
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.rgb(244,244,244));
                rectangle.setX(levels.get(i).get(j).getX()-label.getLayoutBounds().getWidth()/2);
                rectangle.setY(levels.get(i).get(j).getY()-20-label.getLayoutBounds().getHeight());
                rectangle.setWidth(label.getLayoutBounds().getWidth());
                rectangle.setHeight(label.getLayoutBounds().getHeight()+5);
                pane.getChildren().addAll(rectangle,label);
            }
    }

    private void calculateX() {
        levels.get(0).get(0).setX(SizeX / 2);
        for (int i = 0; i < levels.size(); i++) {
            double leftBoundary = 40, rightBoundary = 40;
            for (int j = 0; j < levels.get(i).size(); j++) {
                leftBoundary = rightBoundary;
                if (getChildes.get(levels.get(i).get(j)).size() == 0) rightBoundary = leftBoundary;
                else {
                    int a_node = isLastNodeWithChildesOnLevel(i, j);
                    if (a_node == levels.get(i).size()) rightBoundary = SizeX;
                    else {
                        double space = levels.get(i).get(a_node).getX() - levels.get(i).get(j).getX();
                        int mySons = getChildes.get(levels.get(i).get(j)).size(), aSons = getChildes.get(levels.get(i).get(a_node)).size();
                        rightBoundary = space * ((double) mySons / (double) (mySons + aSons)) + levels.get(i).get(j).getX();
                    }
                }
                List<Node> childes = getChildes.get(levels.get(i).get(j));
                if (childes.size() == 0) continue;
                if (childes.size() == 1) childes.get(0).setX(levels.get(i).get(j).getX());
                else {
                    for (int k = 0; k < childes.size(); k++)
                        childes.get(k).setX(leftBoundary + (rightBoundary - leftBoundary) * ((double) k / (double) childes.size()));
                }
            }
        }
    }

    private void calculateY() {
        for (int i = 0; i < levels.size(); i++)
            for (int j = 0; j < levels.get(i).size(); j++)
                levels.get(i).get(j).setY(YSTEP * (i + 1));
    }

    private void drawNodes() {
        for (int i = 0; i < levels.size(); i++)
            for (int j = 0; j < levels.get(i).size(); j++)
                pane.getChildren().add(genCircle(levels.get(i).get(j).getX(), levels.get(i).get(j).getY()));
    }

    private void drawLines() {
        for (int i = 0; i < levels.size(); i++)
            for (int j = 0; j < levels.get(i).size(); j++)
                for (Node k : getChildes.get(levels.get(i).get(j)))
                    pane.getChildren().add(new CoolLine(levels.get(i).get(j).getX(), levels.get(i).get(j).getY(),
                            k.getX(), k.getY()));
    }

    private Circle genCircle(double X, double Y) {
        Circle toReturn = new Circle();
        toReturn.setRadius(10);
        toReturn.setCenterY(Y);
        toReturn.setCenterX(X);
        toReturn.setFill(Color.GRAY);
        toReturn.setStroke(Color.BLACK);
        toReturn.setStrokeWidth(2);
        return toReturn;
    }

    private Line getLine(double x1, double y1, double x2, double y2) {
        Line toReturn = new Line(x1, y1, x2, y2);
        toReturn.setStroke(Color.BLACK);
        toReturn.setStrokeWidth(2);
        return toReturn;
    }


    private int isLastNodeWithChildesOnLevel(int i, int j) {
        for (int k = j + 1; k < levels.get(i).size(); k++)
            if (getChildes.get(levels.get(i).get(k)).size() != 0) return k;
        return levels.get(i).size();
    }

}
