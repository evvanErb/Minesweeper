package com.evvanErb.minesweeper.view.javafxview;

import com.evvanErb.minesweeper.model.board.GameStatus;
import com.evvanErb.minesweeper.viewmodel.gamemanager.GameManager;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class JavaFXView extends Application {

    private int size = 25;
    private TextField boardSizeInput;
    private GameManager game;
    private ArrayList<ArrayList<Button>> cellsView;
    private Label gameStatusLabel;
    private HashMap<String, String> numMinesColors = new HashMap<String, String>() {{
        put("*", "red");put("1", "black"); put("2", "teal");put("3", "lime");put("4", "green");put("5", "blue");put("6", "slateblue");put("7", "pink");put("8", "purple");
    }};

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
                currentCellView.setStyle(String.format("-fx-background-color: white; -fx-text-fill: %s;", this.numMinesColors.get(thisRow[column])));
                switch(thisRow[column]) {
                    case "F":
                        currentCellView.setStyle("-fx-background-color: lightsteelblue; -fx-text-fill: red;");
                        break;
                    case "X":
                        currentCellView.setText(" ");
                        currentCellView.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
                        break;
                    case "0":
                        currentCellView.setText(" ");
                        currentCellView.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        break;
                }
            }
        }
    }

    private void reveal(MouseEvent event, int rowSaved, int columnSaved) {

        if (this.game.getGameStatus() != GameStatus.RUNNING) { return; }

        if(event.getButton() == MouseButton.PRIMARY) {

            GameStatus resultingStatusAfterReveal = null;
            try {
                resultingStatusAfterReveal = this.game.revealCell(columnSaved, rowSaved);

                if (resultingStatusAfterReveal == GameStatus.LOST) {
                    this.gameStatusLabel.setText("YOU LOST!");
                    this.gameStatusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
                }
                else if (resultingStatusAfterReveal == GameStatus.VICTORY) {
                    this.gameStatusLabel.setText("YOU WON!");
                    this.gameStatusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold");
                }

                this.updateBoardView();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        else {
            this.game.changeCellFlag(columnSaved, rowSaved);
            this.updateBoardView();
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

                cell.setOnMouseClicked(event -> { this.reveal(event, rowSaved, columnSaved); });

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

        this.boardSizeInput = new TextField();
        this.boardSizeInput.appendText("25");

        this.gameStatusLabel = new Label("");
        this.gameStatusLabel.setMinWidth(100);

        Button newGameButton = new Button("New Game");
        newGameButton.setMinWidth(100);
        newGameButton.setOnMouseClicked(event -> {

            boardView.getChildren().clear();
            this.gameStatusLabel.setText("");

            int sizeEntered = 25;
            try {
                sizeEntered = Integer.parseInt(this.boardSizeInput.getText());
            } catch (NumberFormatException e) { System.out.println(e.getMessage()); }

            this.game.startGame(sizeEntered);
            this.size = this.game.getBoardSize();
            this.drawInitBoardView(boardView);
        });

        controlView.add(this.gameStatusLabel, (this.size * 2), 0);
        controlView.add(newGameButton, (this.size * 3), 0);
        controlView.add(this.boardSizeInput, (this.size * 5), 0);

        newGameButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    }

    public static void main(String[] args) {
        launch();
    }

}