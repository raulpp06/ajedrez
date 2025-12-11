package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.utils.BoardHelper;
import es.upsa.programacion.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class KingPiece extends AbstractPiece {

    private boolean hasMoved;

    public KingPiece(Color color, boolean hasMoved) {
        super(color, '♚');
        this.hasMoved = hasMoved;
    }

    public KingPiece(Color color) {
        this(color,  false);
    }

    @Override
    public Map<Pair<Integer, Integer>, Boolean> isValidMove(int row, int col, AbstractPiece[][] board) {
        Map<Pair<Integer, Integer>, Boolean> moves = new HashMap<>();

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = col + dc;

                if (!BoardHelper.inBounds(r, c)) continue;

                if (board[r][c] == null || board[r][c].getColor() != this.getColor()) {
                    moves.put(Pair.of(r, c), true);
                }
            }
        }

        // Castling
        if (!hasMoved) {
            if (board[row][5] == null && board[row][6] == null) {
                if (board[row][7] instanceof RookPiece rook && !rook.hasMoved()) {
                    moves.put(Pair.of(row, 6), true);
                }
            }

            if (board[row][1] == null && board[row][2] == null && board[row][3] == null) {
                if (board[row][0] instanceof RookPiece rook && !rook.hasMoved()) {
                    moves.put(Pair.of(row, 2), true);
                }
            }
        }

        return moves;
    }

    @Override
    public AbstractPiece copy() {
        return new KingPiece(this.getColor(), this.hasMoved);
    }
}
