package com.evvanErb.minesweeper.view.javafxview;

import com.evvanErb.minesweeper.viewmodel.gamemanager.GameManager;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JavaFXView extends Application {

    private int size = 25;
    private TextField boardSizeInput;
    private GameManager game;

    @Override
    public void start(Stage stage) {

        this.game = new GameManager();
        this.game.startGame(this.size);

        GridPane boardView = this.initGridPane(100, 100);
        GridPane controlView = this.initGridPane(100, 20);

        this.buildControls(controlView);

        //add items to the overall view
        VBox mainView = new VBox();
        mainView.getChildren().addAll(
                controlView,
                boardView
        );

        this.drawBoard(boardView, "");

        //run view
        Scene scene = new Scene(mainView);
        stage.setTitle("JavaFX Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    private void drawBoard(GridPane boardView, String boardAsAPI) {

        for(int row = 0; row < this.size; row++) {
            for(int column = 0; column < this.size; column++) {

                Button cell = new Button("X");
                final int rowSaved = row;
                final int columnSaved = column;

                cell.setOnMouseClicked(event -> {
                    System.out.println(String.format("Cell is at %d, %d", columnSaved, rowSaved));

                    try {
                        this.game.revealCell(columnSaved, rowSaved);
                        System.out.println(this.game.getBoardAsString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                cell.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
                boardView.add(cell, column, row);
            }
        }
    }

    private GridPane initGridPane(int xLength, int yLength) {

        GridPane newGridPane = new GridPane();
        newGridPane.setMinSize(xLength, yLength);
        newGridPane.setPadding(new Insets(10, 10, 10, 10));
        newGridPane.setVgap(2);
        newGridPane.setHgap(2);
        newGridPane.setAlignment(Pos.CENTER);
        newGridPane.setStyle("-fx-background-color: BEIGE;");

        return newGridPane;
    }

    private void buildControls(GridPane controlView) {

        Button newGameButton = new Button("NewGame");
        newGameButton.setOnMouseClicked(event -> {
            this.game.startGame(this.size);
        });

        this.boardSizeInput = new TextField();
        this.boardSizeInput.appendText("25");

        controlView.add(newGameButton, (this.size * 12 / 2), 0);
        controlView.add(this.boardSizeInput, (this.size * 9), 0);

        newGameButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    }

    public static void main(String[] args) {
        launch();
    }

}