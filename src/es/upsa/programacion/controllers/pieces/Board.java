package es.upsa.programacion.controllers.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.models.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final AbstractPiece[][] board = new AbstractPiece[8][8];
    private final List<AbstractPiece> capturedWhitePieces = new ArrayList<>();
    private final List<AbstractPiece> capturedBlackPieces = new ArrayList<>();
    private int[] enPassantTarget = null;

    public Board() {
        // peones
        for (int i = 0; i < 8; i++) {
            board[1][i] = new PawnPiece(Color.BLACK);
            board[6][i] = new PawnPiece(Color.WHITE);
        }

        // torres
        board[0][0] = new RookPiece(Color.BLACK);
        board[0][7] = new RookPiece(Color.BLACK);
        board[7][0] = new RookPiece(Color.WHITE);
        board[7][7] = new RookPiece(Color.WHITE);
        // caballos
        board[0][1] = new KnightPiece(Color.BLACK);
        board[0][6] = new KnightPiece(Color.BLACK);
        board[7][1] = new KnightPiece(Color.WHITE);
        board[7][6] = new KnightPiece(Color.WHITE);
        // alfiles
        board[0][2] = new BishopPiece(Color.BLACK);
        board[0][5] = new BishopPiece(Color.BLACK);
        board[7][2] = new BishopPiece(Color.WHITE);
        board[7][5] = new BishopPiece(Color.WHITE);
        // rey/reina
        board[0][3] = new QueenPiece(Color.BLACK);
        board[0][4] = new KingPiece(Color.BLACK);
        board[7][3] = new QueenPiece(Color.WHITE);
        board[7][4] = new KingPiece(Color.WHITE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            sb.append((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                AbstractPiece piece = board[row][col];
                sb.append((piece == null ? '.' : piece.getDisplaySymbol()) + " ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("  a b c d e f g h" + System.lineSeparator());
        sb.append("Capturado por blanco: " + capturedBlackPieces + System.lineSeparator());
        sb.append("Capturado por negro: " + capturedWhitePieces + System.lineSeparator());
        return sb.toString();
    }

    public void print() {
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                AbstractPiece piece = board[row][col];
                System.out.print((piece == null ? '.' : piece.getDisplaySymbol()) + " ");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
        System.out.println("Capturado por blanco: " + capturedBlackPieces);
        System.out.println("Capturado por negro: " + capturedWhitePieces);
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

        AbstractPiece piece = board[fromRow][fromCol];
        if (piece == null) {
            System.out.println("No existe pieza en esa coordenada");
            return false;
        }
        if (piece.getColor() != turn) {
            System.out.println("No es tu pieza!");
            return false;
        }

        if (!piece.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
            System.out.println("Es un movimiento invalido para tu pieza!");
            return false;
        }

        AbstractPiece target = board[toRow][toCol];
        if (target != null && target.getColor() != turn) {
            System.out.println(turn + " captures " + target.getSymbol() + "!");
            if (turn == Color.WHITE) capturedBlackPieces.add(target);
            else capturedWhitePieces.add(target);
        }

        if (piece instanceof PawnPiece && enPassantTarget != null &&
                toRow == enPassantTarget[0] && toCol == enPassantTarget[1]) {
            int capturedRow = (turn == Color.WHITE) ? toRow + 1 : toRow - 1;
            AbstractPiece capturedPawn = board[capturedRow][toCol];
            board[capturedRow][toCol] = null;
            System.out.println(turn + " hace una captura en passant!");
            if (turn == Color.WHITE) capturedBlackPieces.add(capturedPawn);
            else capturedWhitePieces.add(capturedPawn);
        }

        if (piece instanceof KingPiece && Math.abs(toCol - fromCol) == 2) {
            if (toCol == 6) {
                if (!(board[toRow][7] instanceof RookPiece)) {
                    board[toRow][5] = board[toRow][7];
                    board[toRow][7] = null;
                    ((RookPiece) board[toRow][5]).setMoved(true);
                }
            } else if (toCol == 2) {
                if (!(board[toRow][7] instanceof RookPiece)) {
                    board[toRow][3] = board[toRow][0];
                    board[toRow][0] = null;
                    ((RookPiece) board[toRow][3]).setMoved(true);
                }
            }
        }

        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;

        if (piece instanceof PawnPiece) {
            if ((piece.getColor() == Color.WHITE && toRow == 0) ||
                    (piece.getColor() == Color.BLACK && toRow == 7)) {
                board[toRow][toCol] = new QueenPiece(piece.getColor());
                System.out.println("Peón se transforma en reina!");
            }
        }

        if (piece instanceof PawnPiece && Math.abs(toRow - fromRow) == 2) {
            enPassantTarget = new int[]{(fromRow + toRow) / 2, fromCol};
        } else {
            enPassantTarget = null;
        }

        if (piece instanceof RookPiece rookPiece) rookPiece.setMoved(true);

        return true;
    }

    public boolean isGameOver() {
        boolean whiteKingExists = false;
        boolean blackKingExists = false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                AbstractPiece piece = board[row][col];
                if (piece instanceof KingPiece) {
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
            System.out.println("Felicidades " + winner + "! has capturado a el rey de " + loser + " y ganado el juego!");
            System.out.println("🏆🏆🏆");
            return true;
        }

        return false;
    }
}
