import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JavaFXView extends Application {

    @Override
    public void start(Stage stage) {

        GridPane boardView = new GridPane();
        boardView.setMinSize(100, 100);
        boardView.setPadding(new Insets(10, 10, 10, 10));
        boardView.setVgap(2);
        boardView.setHgap(2);
        boardView.setAlignment(Pos.CENTER);
        boardView.setStyle("-fx-background-color: BEIGE;");

        GridPane controlView = new GridPane();
        controlView.setMinSize(100, 20);
        controlView.setPadding(new Insets(10, 10, 10, 10));
        controlView.setVgap(2);
        controlView.setHgap(2);
        controlView.setAlignment(Pos.CENTER);
        controlView.setStyle("-fx-background-color: BEIGE;");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                controlView,
                boardView
        );

        Button newGameButton = new Button("NewGame");
        newGameButton.setOnMouseClicked(event -> {
            System.out.println("New Game");
        });
        newGameButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        controlView.getChildren().add(newGameButton);

        for(int row = 0; row < 10; row++) {
            for(int column = 0; column < 10; column++) {

                Button cell = new Button("X");
                final int rowSaved = row;
                final int columnSaved = column;

                cell.setOnMouseClicked(event -> {
                    System.out.println(String.format("name is %d, %d", columnSaved, rowSaved));
                });

                cell.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
                boardView.add(cell, column, row);
            }
        }

        Scene scene = new Scene(vbox);
        stage.setTitle("JavaFX Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}