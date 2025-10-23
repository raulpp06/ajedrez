package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;

public class PawnPiece extends AbstractPiece {

    public PawnPiece(Color color) {
        super(color, '♟');
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, AbstractPiece[][] board) {
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
