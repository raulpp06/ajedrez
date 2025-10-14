package es.upsa.programacion.piece.kind;

import es.upsa.programacion.Color;
import es.upsa.programacion.piece.Piece;

public class Rook extends Piece {

    private boolean hasMoved = false;

    public Rook(Color color) {
        super(color, '♜');
    }

    public boolean hasMoved() { return hasMoved; }

    public void setMoved(boolean moved) { this.hasMoved = moved; }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        if (fromRow != toRow && fromCol != toCol)
            return false;

        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int r = fromRow + rowStep;
        int c = fromCol + colStep;

        while (r != toRow || c != toCol) {
            if (board[r][c] != null)
                return false;
            r += rowStep;
            c += colStep;
        }

        return board[toRow][toCol] == null || board[toRow][toCol].getColor() != this.getColor();
    }
}
