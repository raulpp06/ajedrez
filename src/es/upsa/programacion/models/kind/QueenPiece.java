package es.upsa.programacion.models.kind;

import es.upsa.programacion.Color;
import es.upsa.programacion.models.Piece;

public class QueenPiece extends Piece {

    public QueenPiece(Color color) {
        super(color, '♛');
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (rowDiff != 0 && colDiff != 0 && rowDiff != colDiff)
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
