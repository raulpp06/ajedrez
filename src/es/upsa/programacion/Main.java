package es.upsa.programacion;

import es.upsa.programacion.view.UserMenu;

public class Main {
    public static void main(String[] args) {

        UserMenu menu = new UserMenu();
        menu.getUserController().loadUsers();
        menu.showMenu();
        menu.getUserController().finish();

//        Scanner scanner = new Scanner(System.in);
//        Board board = new Board();
//        Color turn = Color.WHITE;
//
//        while (true) {
//            board.print();
//            System.out.print(turn + " to move (e.g. e2 e4): ");
//            String move = scanner.nextLine().trim();
//
//            if (move.equalsIgnoreCase("quit")) break;
//
//            if (board.move(move, turn)) {
//                turn = (turn == Color.WHITE) ? Color.BLACK : Color.WHITE;
//            } else {
//                System.out.println("Invalid move, try again.");
//            }
//            if (board.isGameOver()) break;
//        }
    }
}