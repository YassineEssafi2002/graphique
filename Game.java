package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Game extends Application {
    private static final int BOARD_SIZE = 10;
    private static final int NUM_SHIPS = 3;
    private Label[][] boardLabels;
    private int[][] board;
    private int shipsRemaining;
    private HashSet<String> guesses;
    @Override
    public void start(Stage primaryStage) {
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = 0;
            }

        }

        Random random = new Random();
        int shipsPlaced = 0;
        while (shipsPlaced < NUM_SHIPS) {
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            if (this.board[x][y] == 0) {
                this.board[x][y] = 1;
                shipsPlaced++;
            }
        }
        this.shipsRemaining = NUM_SHIPS;
        this.guesses = new HashSet<>();

        GridPane grid = new GridPane();
        this.boardLabels = new Label[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Label label = new Label(" ");
                label.setMinSize(70, 70);
                label.setMaxSize(70, 70);
                label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: red;");
                final int x = col;
                final int y = row;
                label.setOnMouseClicked(event -> handleClick(x, y));
                grid.add(label, col, row);
                this.boardLabels[row][col] = label;

            }
        }

        Scene scene = new Scene(grid, 692, 692);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Battle Ship Game");
        primaryStage.show();

    }

    private void handleClick(int x, int y) {
        String guess = x + " " + y;
        if (this.guesses.contains(guess)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Déjà deviné");
            alert.setContentText("Vous avez déjà deviné cette position. Veuillez sélectionner une nouvelle case.");
            alert.showAndWait();
            return;
        }
        this.guesses.add(guess);

        if (this.board[y][x] == 1) {
            this.board[y][x] = 2;
            this.boardLabels[y][x].setStyle("-fx-background-color: white");
            this.shipsRemaining--;
            // Show "Congratulations, you hit a ship!" message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Félicitations");
            alert.setHeaderText("Vous avez touché un navire!");
            alert.setContentText("Félicitations! Vous avez touché un navire!");
            alert.showAndWait();
        } else {
            this.boardLabels[y][x].setStyle("-fx-background-color: green");
        }

        if (this.shipsRemaining == 0) {
            showWinDialog();
        }
    }


    private void showWinDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Félicitations");
        alert.setHeaderText("Tous les navires ont été coulés !");
        alert.setContentText("Vous gagnez!");
        alert.showAndWait();
        Platform.exit();
    }}