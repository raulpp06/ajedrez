package es.upsa.programacion.controllers.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.models.pieces.*;
import es.upsa.programacion.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 8;
    private static final AbstractPieceSorter SORTER = new AbstractPieceSorter();
    private final AbstractPiece[][] board = new AbstractPiece[BOARD_SIZE][BOARD_SIZE];
    private final List<AbstractPiece> capturedWhitePieces = new ArrayList<>();
    private final List<AbstractPiece> capturedBlackPieces = new ArrayList<>();
    private int[] enPassantTarget = null;

    private Color winner = null;
    private Color loser = null;

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
        final String LIGHT_BG = "\u001B[48;2;240;217;181m";
        final String DARK_BG  = "\u001B[48;2;181;136;99m";
        final String RESET = "\u001B[0m";

        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                AbstractPiece piece = board[row][col];
                String bg = (row + col) % 2 == 0 ? LIGHT_BG : DARK_BG;
                String pieceSymbol = piece == null ? " " : piece.getDisplaySymbol();
                System.out.print(bg + " " + pieceSymbol + " " + RESET);
            }
            System.out.println();
        }

        System.out.println("   a  b  c  d  e  f  g  h");
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

        if (piece.isValidMove(fromRow, fromCol, board).getOrDefault(Pair.of(toRow, toCol), false)) {
            System.out.println("Es un movimiento invalido para tu pieza!");
            return false;
        }

        AbstractPiece target = board[toRow][toCol];
        if (target != null && target.getColor() != turn) {
            System.out.println(turn + " captures " + target.getSymbol() + "!");
            if (turn == Color.WHITE) {
                capturedBlackPieces.add(target);
                capturedBlackPieces.sort(SORTER);
            }
            else {
                capturedWhitePieces.add(target);
                capturedWhitePieces.sort(SORTER);
            }
        }

        if (piece instanceof PawnPiece && enPassantTarget != null &&
                toRow == enPassantTarget[0] && toCol == enPassantTarget[1]) {
            int capturedRow = (turn == Color.WHITE) ? toRow + 1 : toRow - 1;
            AbstractPiece capturedPawn = board[capturedRow][toCol];
            board[capturedRow][toCol] = null;
            System.out.println(turn + " hace una captura en passant!");
            if (turn == Color.WHITE) {
                capturedBlackPieces.add(capturedPawn);
                capturedBlackPieces.sort(SORTER);
            }
            else {
                capturedWhitePieces.add(capturedPawn);
                capturedWhitePieces.sort(SORTER);
            }
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

    public List<String> getAllLegalMoves(Color turn) {
        List<String> legalMoves = new ArrayList<>();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                AbstractPiece piece = board[row][col];
                if (piece != null && piece.getColor() == turn) {

                    var movs = piece.isValidMove(row, col, board);

                    for (var entry : movs.entrySet()) {
                        var to = entry.getKey();
                        boolean valid = entry.getValue(); // true = válido

                        if (valid) {
                            int toRow = to.getRight();
                            int toCol = to.getLeft();

                            String move = "";
                            move += (char) ('a' + col);
                            move += (8 - row);
                            move += (char) ('a' + toCol);
                            move += (8 - toRow);

                            legalMoves.add(move);
                        }
                    }
                }
            }
        }

        return legalMoves;
    }

    public int evaluate() {
        int score = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                AbstractPiece piece = board[row][col];
                if (piece != null) {
                    int val = switch (piece.getSymbol()) {
                        case '♟' -> 100;
                        case '♞' -> 320;
                        case '♝' -> 330;
                        case '♜' -> 500;
                        case '♛' -> 900;
                        case '♚' -> 20000;
                        default -> 0;
                    };

                    score += (piece.getColor() == Color.WHITE ? val : -val);
                }
            }
        }

        return score;
    }

    public void moveSilent(String moveStr, Color turn) {
        moveStr = moveStr.replaceAll("\\s+", "");
        if (moveStr.length() != 4) return;

        int fromCol = moveStr.charAt(0) - 'a';
        int fromRow = 8 - Character.getNumericValue(moveStr.charAt(1));
        int toCol = moveStr.charAt(2) - 'a';
        int toRow = 8 - Character.getNumericValue(moveStr.charAt(3));

        AbstractPiece piece = board[fromRow][fromCol];
        if (piece == null) return;
        if (piece.getColor() != turn) return;

        var validMoves = piece.isValidMove(fromRow, fromCol, board);
        if (validMoves.getOrDefault(Pair.of(toRow, toCol), true)) return;

        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;
    }

    @Override
    public Board clone() {
        Board copy = new Board();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (this.board[row][col] != null) {
                    copy.board[row][col] = this.board[row][col].copy();
                } else {
                    copy.board[row][col] = null;
                }
            }
        }

        return copy;
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

    public Color getWinner() {
        return winner;
    }
}
