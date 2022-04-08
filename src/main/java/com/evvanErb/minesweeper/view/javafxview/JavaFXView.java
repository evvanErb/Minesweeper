package com.evvanErb.minesweeper.view.javafxview;

import com.evvanErb.minesweeper.model.board.GameStatus;
import com.evvanErb.minesweeper.viewmodel.gamemanager.GameManager;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class JavaFXView extends Application {

    private int size = 25;
    private TextField boardSizeInput;
    private GameManager game;
    private ArrayList<ArrayList<Button>> cellsView;

    @Override
    public void start(Stage stage) {

        this.game = new GameManager();
        this.game.startGame(this.size);

        GridPane boardView = this.initGridPane(100, 100);
        GridPane controlView = this.initGridPane(100, 20);

        this.buildControls(controlView, boardView);

        //add items to the overall view
        VBox mainView = new VBox();
        mainView.getChildren().addAll(
                controlView,
                boardView
        );

        this.drawInitBoardView(boardView);

        //run view
        Scene scene = new Scene(mainView);
        stage.setTitle("JavaFX Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    private void updateBoardView() {

        String boardAsAPI = this.game.getBoardAsAPI();
        String[] rows = boardAsAPI.split("\n");
        ArrayList<ArrayList<String>> boardAsStringArray = new ArrayList<ArrayList<String>>();

        for(int row = 0; row < this.size; row++) {

            boardAsStringArray.add(new ArrayList<String>());
            String[] thisRow = rows[row].split(",");

            for (int column = 0; column < this.size; column++) {

                Button currentCellView = this.cellsView.get(row).get(column);
                currentCellView.setText((thisRow[column]));
                switch(thisRow[column]) {
                    case "*":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: red;");
                        break;
                    case "F":
                        currentCellView.setStyle("-fx-background-color: grey; -fx-text-fill: red;");
                        break;
                    case "X":
                        currentCellView.setText(" ");
                        currentCellView.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
                        break;
                    case "1":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        break;
                    case "2":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: teal;");
                        break;
                    case "3":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: lime;");
                        break;
                    case "4":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: green;");
                        break;
                    case "5":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: blue;");
                        break;
                    case "6":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: slateblue;");
                        break;
                    case "7":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: pink;");
                        break;
                    case "8":
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: purple;");
                        break;
                    default:
                        currentCellView.setText(" ");
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        break;
                }
            }
        }
    }

    private void drawInitBoardView(GridPane boardView) {

        this.cellsView = new ArrayList<ArrayList<Button>>();
        for(int row = 0; row < this.size; row++) {

            this.cellsView.add(new ArrayList<Button>());
            for(int column = 0; column < this.size; column++) {

                Button cell = new Button(" ");
                final int rowSaved = row;
                final int columnSaved = column;

                cell.setOnMouseClicked(event -> {

                    if(event.getButton() == MouseButton.PRIMARY) {

                        GameStatus resultingStatusAfterReveal = null;
                        try {
                            resultingStatusAfterReveal = this.game.revealCell(columnSaved, rowSaved);
                            this.updateBoardView();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else {
                        this.game.changeCellFlag(columnSaved, rowSaved);
                        this.updateBoardView();
                    }
                });

                cell.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
                boardView.add(cell, column, row);
                this.cellsView.get(row).add(cell);
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

    private void buildControls(GridPane controlView, GridPane boardView) {

        Button newGameButton = new Button("NewGame");
        newGameButton.setOnMouseClicked(event -> {
            this.game.startGame(this.size);
            this.drawInitBoardView(boardView);
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