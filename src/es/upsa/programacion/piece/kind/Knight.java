package es.upsa.programacion.piece.kind;

import es.upsa.programacion.Color;
import es.upsa.programacion.piece.Piece;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, '♞');
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        boolean isLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        if (!isLShape)
            return false;

        return board[toRow][toCol] == null || board[toRow][toCol].getColor() != this.getColor();
    }
}
