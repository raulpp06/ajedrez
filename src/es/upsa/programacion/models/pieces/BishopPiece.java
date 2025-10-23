package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;

public class BishopPiece extends AbstractPiece {

    public BishopPiece(Color color) {
        super(color, '♝');
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, AbstractPiece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (rowDiff != colDiff)
            return false;

        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int r = fromRow + rowStep;
        int c = fromCol + colStep;

        while (r != toRow && c != toCol) {
            if (board[r][c] != null)
                return false;
            r += rowStep;
            c += colStep;
        }

        return board[toRow][toCol] == null || board[toRow][toCol].getColor() != this.getColor();
    }
}
