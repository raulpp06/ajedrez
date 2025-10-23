package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;

public class KingPiece extends AbstractPiece {

    private boolean hasMoved = false;

    public KingPiece(Color color) {
        super(color, '♚');
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, AbstractPiece[][] board) {
        if (!hasMoved && fromRow == toRow) {
            if (toCol - fromCol == 2) {
                AbstractPiece piece = board[fromRow][7];
                if (piece instanceof RookPiece rookPiece && rookPiece.hasMoved()) {
                    if (board[fromRow][5] == null && board[fromRow][6] == null) {
                        // TODO: mirar que rey no este en mate
                        return true;
                    }
                }
            }
            else if (fromCol - toCol == 2) {
                AbstractPiece rook = board[fromRow][0];
                if (rook instanceof RookPiece && !((RookPiece) rook).hasMoved()) {
                    if (board[fromRow][1] == null && board[fromRow][2] == null && board[fromRow][3] == null) {
                        // TODO: mirar que rey no este en mate
                        return true;
                    }
                }
            }
        }

        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (rowDiff > 1 || colDiff > 1)
            return false;

        return board[toRow][toCol] == null || board[toRow][toCol].getColor() != this.getColor();
    }
}
