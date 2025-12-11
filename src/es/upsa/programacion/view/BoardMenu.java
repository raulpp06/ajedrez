package es.upsa.programacion.view;

import es.upsa.programacion.Color;
import es.upsa.programacion.controllers.pieces.Board;
import es.upsa.programacion.controllers.pieces.BotMinimax;
import es.upsa.programacion.models.users.AbstractUser;
import es.upsa.programacion.models.users.User;

import java.util.Scanner;

public class BoardMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Board board = new Board();
    private final BotMinimax bot = new BotMinimax();
    private Color turn = Color.WHITE;
    private AbstractUser user;

    public BoardMenu(AbstractUser user) {
        this.user = user;
    }

    @Override
    public void start() {
        System.out.println("Tipos de partidas:");
        System.out.println("1. Juego solitario");
        System.out.println("2. Juego bot");

        int opc;
        do {
            opc = scanner.nextInt();
        } while(opc < 1 || opc > 2);
        switch (opc) {
            case 1:
                normalGame();
                break;
            case 2:
                botGame();
                break;
        }
    }

    public void normalGame() {
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

    public void botGame() {
        while (true) {

            board.print();

            if (turn == Color.WHITE) {
                System.out.print(turn + " to move (e.g. e2e4 or e2 e4): ");
                String move = scanner.nextLine().trim();
                if (move.equalsIgnoreCase("quit")) break;

                if (board.move(move, turn)) {
                    turn = Color.BLACK;
                } else {
                    System.out.println("Invalid move, try again.");
                }
            }

            else {
                System.out.println("Bot is thinking...");

                String botMove = bot.chooseMove(board, Color.BLACK);

                if (botMove == null) {
                    System.out.println("Bot has no legal moves!");
                    break;
                }

                System.out.println("Bot plays: " + botMove);
                board.move(botMove, Color.BLACK);

                turn = Color.WHITE;
            }

            if (board.isGameOver()) {
                if (board.getWinner() == Color.WHITE && user instanceof User uuser) {
                    uuser.addWin();
                }
                break;
            }
        }
    }
}
