package main.java.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class View extends Application {

    HBox rootsRoot = new HBox();

    VBox root = new VBox();

    TreeView<String> rootTree;
    TreeItem<String> rootItem;

    TreeItem<String> selectedItem;
    Pane content = new Pane();
    //ScrollPane scrollPane = new ScrollPane();
    //Pane content = new Pane();

    public void beg() {
        launch("");
    }

    private void getNewItem(String name) {
        TreeItem<String> toReturn = new TreeItem<>(name);
        rootTree.getSelectionModel().getSelectedItem().getChildren().add(toReturn);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox first = new HBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.prefWidthProperty().bind(rootsRoot.widthProperty());
        scrollPane.setContent(content);
        TextField textField = new TextField();
        Button button = new Button("Add");
        Button draw = new Button("Draw");
        first.getChildren().addAll(textField, button, draw);
        primaryStage.setScene(new Scene(rootsRoot));
        rootsRoot.getChildren().addAll(root, scrollPane);
        root.getChildren().addAll(first);
        button.setOnAction(e -> {
            if (rootTree == null) {
                rootItem = new TreeItem<>(textField.getText());
                rootTree = new TreeView<>(rootItem);
                rootTree.prefHeightProperty().bind(root.heightProperty());
                root.getChildren().addAll(rootTree);
            } else getNewItem(textField.getText());
        });
        draw.setOnAction(e->{
            new Drawer(content,rootItem).draw();
        });
        primaryStage.show();
        primaryStage.setFullScreen(true);
        root.setMinWidth(267);
    }
}
