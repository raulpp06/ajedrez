package es.upsa.programacion.view;

import es.upsa.programacion.Color;
import es.upsa.programacion.controllers.pieces.Board;

import java.util.Scanner;

public class BoardMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Board board = new Board();
    private Color turn = Color.WHITE;

    public void start() {
        while (true) {
            board.print();
            System.out.print(turn + " to move (e.g. e2 e4): ");
            String move = scanner.nextLine().trim();
            if (move.equalsIgnoreCase("quit")) break;
            if (board.move(move, turn)) {
                turn = (turn == Color.WHITE) ? Color.BLACK : Color.WHITE;
            } else {
                System.out.println("Invalid move, try again.");
            }
            if (board.isGameOver()) break;
        }
    }
}
