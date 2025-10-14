package es.upsa.programacion.piece;

import es.upsa.programacion.Color;
import es.upsa.programacion.piece.kind.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Piece[][] board = new Piece[8][8];
    List<Piece> capturedWhitePieces = new ArrayList<>();
    List<Piece> capturedBlackPieces = new ArrayList<>();
    private int[] enPassantTarget = null;

    public Board() {
        setup();
    }

    private void setup() {
        // peones
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Color.BLACK);
            board[6][i] = new Pawn(Color.WHITE);
        }

        // torres
        board[0][0] = new Rook(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);
        board[7][0] = new Rook(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);
        // caballos
        board[0][1] = new Knight(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[7][1] = new Knight(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        // alfiles
        board[0][2] = new Bishop(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        // rey/reina
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
    }

    public void print() {
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                System.out.print((piece == null ? '.' : piece) + " ");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
        System.out.println("Captured by white: " + capturedBlackPieces);
        System.out.println("Captured by black: " + capturedWhitePieces);
    }

    public boolean move(String moveStr, Color turn) {
        moveStr = moveStr.replaceAll("\\s+", "");
        if (moveStr.length() != 4) return false;

        int fromCol = moveStr.charAt(0) - 'a';
        int fromRow = 8 - Character.getNumericValue(moveStr.charAt(1));
        int toCol = moveStr.charAt(2) - 'a';
        int toRow = 8 - Character.getNumericValue(moveStr.charAt(3));

        if (fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7 ||
                fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7)
            return false;

        Piece piece = board[fromRow][fromCol];
        if (piece == null) {
            System.out.println("No piece at that square!");
            return false;
        }
        if (piece.getColor() != turn) {
            System.out.println("That’s not your piece!");
            return false;
        }

        if (!piece.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
            System.out.println("That move is not valid for that piece!");
            return false;
        }

        Piece target = board[toRow][toCol];
        if (target != null && target.getColor() != turn) {
            System.out.println(turn + " captures " + target.getSymbol() + "!");
            if (turn == Color.WHITE) capturedBlackPieces.add(target);
            else capturedWhitePieces.add(target);
        }

        if (piece instanceof Pawn && enPassantTarget != null &&
                toRow == enPassantTarget[0] && toCol == enPassantTarget[1]) {
            int capturedRow = (turn == Color.WHITE) ? toRow + 1 : toRow - 1;
            Piece capturedPawn = board[capturedRow][toCol];
            board[capturedRow][toCol] = null;
            System.out.println(turn + " performs en passant capture!");
            if (turn == Color.WHITE) capturedBlackPieces.add(capturedPawn);
            else capturedWhitePieces.add(capturedPawn);
        }

        if (piece instanceof King && Math.abs(toCol - fromCol) == 2) {
            if (toCol == 6) {
                board[toRow][5] = board[toRow][7];
                board[toRow][7] = null;
                ((Rook) board[toRow][5]).setMoved(true);
            } else if (toCol == 2) {
                board[toRow][3] = board[toRow][0];
                board[toRow][0] = null;
                ((Rook) board[toRow][3]).setMoved(true);
            }
        }

        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;

        if (piece instanceof Pawn) {
            if ((piece.getColor() == Color.WHITE && toRow == 0) ||
                    (piece.getColor() == Color.BLACK && toRow == 7)) {
                board[toRow][toCol] = new Queen(piece.getColor());
                System.out.println("Pawn promoted to Queen!");
            }
        }

        if (piece instanceof Pawn && Math.abs(toRow - fromRow) == 2) {
            enPassantTarget = new int[]{(fromRow + toRow) / 2, fromCol};
        } else {
            enPassantTarget = null;
        }

        if (piece instanceof Rook rook) rook.setMoved(true);

        return true;
    }

    public boolean isGameOver() {
        boolean whiteKingExists = false;
        boolean blackKingExists = false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King) {
                    if (piece.getColor() == Color.WHITE) whiteKingExists = true;
                    else blackKingExists = true;
                }
            }
        }

        Color winner = null;
        Color loser = null;

        if (!whiteKingExists) {
            winner = Color.BLACK;
            loser = Color.WHITE;
        } else if (!blackKingExists) {
            winner = Color.WHITE;
            loser = Color.BLACK;
        }

        if (winner != null) {
            System.out.println("\n♟♟♟ GAME OVER ♟♟♟");
            System.out.println("Congratulations " + winner + "! You have captured the " + loser + " King and won the game!");
            System.out.println("🏆🏆🏆");
            return true;
        }

        return false;
    }
}
