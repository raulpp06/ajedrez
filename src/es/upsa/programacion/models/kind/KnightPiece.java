package es.upsa.programacion.models.kind;

import es.upsa.programacion.Color;
import es.upsa.programacion.models.Piece;

public class KnightPiece extends Piece {

    public KnightPiece(Color color) {
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
