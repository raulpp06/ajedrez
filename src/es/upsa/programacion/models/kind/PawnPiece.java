package es.upsa.programacion.models.kind;

import es.upsa.programacion.Color;
import es.upsa.programacion.models.Piece;

public class PawnPiece extends Piece {

    public PawnPiece(Color color) {
        super(color, '♟');
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int direction = (this.getColor() == Color.WHITE) ? -1 : 1;
        int startRow = (this.getColor() == Color.WHITE) ? 6 : 1;

        if (fromCol == toCol && toRow == fromRow + direction && board[toRow][toCol] == null)
            return true;

        if (fromCol == toCol && fromRow == startRow && toRow == fromRow + 2 * direction
                && board[fromRow + direction][toCol] == null && board[toRow][toCol] == null)
            return true;

        if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction
                && board[toRow][toCol] != null && board[toRow][toCol].getColor() != this.getColor())
            return true;

        return false;
    }
}
