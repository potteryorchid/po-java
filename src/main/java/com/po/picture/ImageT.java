package com.po.picture;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * Created by ZJ on 15/01/2019.
 */
public class ImageT  extends Application {

    private static final String flowerURL = "file:/Users/zj/Desktop/data.jpeg";
    private static final String dotsURL = "file:/Users/zj/Desktop/data.jpeg";

    @Override public void start(Stage stage) {
      stage.setTitle("Image Pattern");
      Group root = new Group();
      Scene scene = new Scene(root, 600, 450);

      Image dots = new Image(dotsURL);
      Image flower = new Image(flowerURL);

      Polygon p = new Polygon();

      p.setLayoutX(10);
      p.setLayoutY(10);
      p.getPoints().add(50.0);
      p.getPoints().add(0.0);
      p.getPoints().add(100.0);
      p.getPoints().add(100.0);
      p.getPoints().add(0.0);
      p.getPoints().add(100.0);

      p.setFill(new ImagePattern(flower, 0, 0, 1, 1, true));

      root.getChildren().add(p);

      Polygon p2 = new Polygon();

      p2.setLayoutX(10);
      p2.setLayoutY(120);
      p2.getPoints().add(50.0);
      p2.getPoints().add(0.0);
      p2.getPoints().add(100.0);
      p2.getPoints().add(100.0);
      p2.getPoints().add(0.0);
      p2.getPoints().add(100.0);

      p2.setFill(new ImagePattern(flower, 0, 0, 100, 100, false));

      root.getChildren().add(p2);

      Circle circ = new Circle(50);
      circ.setTranslateX(120);
      circ.setTranslateY(10);
      circ.setCenterX(50);
      circ.setCenterY(50);
      circ.setFill(new ImagePattern(dots, 0.2, 0.2, 0.4, 0.4, true));

      root.getChildren().add(circ);

      Circle circ2 = new Circle(50);
      circ2.setTranslateX(120);
      circ2.setTranslateY(10);
      circ2.setCenterX(50);
      circ2.setCenterY(50);
      circ2.setFill(new ImagePattern(dots, 20, 20, 40, 40, false));

      root.getChildren().add(circ2);
      stage.setScene(scene);
      stage.show();
    }

}
