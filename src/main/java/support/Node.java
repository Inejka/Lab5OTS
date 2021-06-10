package main.java.support;

public class Node {
    String info;
    double X, Y;

    public double getX() {
        return X;
    }

    public String getInfo() {
        return info;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public Node(String info) {
        this.info = info;
    }
}
