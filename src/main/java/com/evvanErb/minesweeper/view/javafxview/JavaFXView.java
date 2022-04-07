import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JavaFXView extends Application {

    @Override
    public void start(Stage stage) {

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(100, 100);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: BEIGE;");

        for(int row = 0; row < 10; row++) {
            for(int column = 0; column < 10; column++) {

                Button cell = new Button("X");
                final int rowSaved = row;
                final int columnSaved = column;

                cell.setOnMouseClicked(event -> {
                    System.out.println(String.format("name is %d, %d", columnSaved, rowSaved));
                });

                cell.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
                gridPane.add(cell, column, row);
            }
        }

        Scene scene = new Scene(gridPane);
        stage.setTitle("JavaFX Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}