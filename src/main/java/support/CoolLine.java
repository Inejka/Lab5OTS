package main.java.support;

import com.sun.javafx.scene.shape.ArcToHelper;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class CoolLine extends Path {

    private int RADIUSX = 10, RADIUSY = 10;

    public CoolLine(double x1, double y1, double x2, double y2) {
        setStroke(Color.BLACK);
        if (x1 == x2)
            getElements().addAll(new MoveTo(x1, y1), new LineTo(x1, y1 + (y2 - y1) / 2), new LineTo(x2, y1 + (y2 - y1) / 2), new LineTo(x2, y2));
        else if (x1 > x2) {
            ArcTo arcTo1 = new ArcTo();
            arcTo1.setX(x1-RADIUSX);
            arcTo1.setSweepFlag(true);
            arcTo1.setY(y1 + (y2 - y1)/2);
            arcTo1.setRadiusX(RADIUSX);
            arcTo1.setRadiusY(RADIUSY);
            ArcTo arcTo2 = new ArcTo();
            arcTo2.setY(y1 + (y2 - y1)/2+RADIUSY);
            arcTo2.setX(x2);
            arcTo2.setRadiusX(RADIUSX);
            arcTo2.setRadiusY(RADIUSY);
            getElements().addAll(new MoveTo(x1, y1), new LineTo(x1, y1 + (y2 - y1) / 2 - RADIUSY),arcTo1,
                    new LineTo(x2+RADIUSX, y1 + (y2 - y1) / 2), arcTo2,new LineTo(x2, y2));
        } else
        {
            ArcTo arcTo1 = new ArcTo();
            arcTo1.setX(x1+RADIUSX);
            arcTo1.setY(y1 + (y2 - y1)/2);
            arcTo1.setRadiusX(RADIUSX);
            arcTo1.setRadiusY(RADIUSY);
            ArcTo arcTo2 = new ArcTo();
            arcTo2.setY(y1 + (y2 - y1)/2+RADIUSY);
            arcTo2.setX(x2);
            arcTo2.setSweepFlag(true);
            arcTo2.setRadiusX(RADIUSX);
            arcTo2.setRadiusY(RADIUSY);
            getElements().addAll(new MoveTo(x1, y1), new LineTo(x1, y1 + (y2 - y1) / 2 - RADIUSY),arcTo1,
                    new LineTo(x2-RADIUSX, y1 + (y2 - y1) / 2),arcTo2, new LineTo(x2, y2));
        }
    }

}
