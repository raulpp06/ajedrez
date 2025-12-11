package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.utils.BoardHelper;
import es.upsa.programacion.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class RookPiece extends AbstractPiece {

    private boolean hasMoved = false;

    public RookPiece(Color color, boolean hasMoved) {
        super(color, '♜');
        this.hasMoved = hasMoved;
    }

    public RookPiece(Color color) {
        this(color, false);
    }

    public boolean hasMoved() {return hasMoved;}

    public void setMoved(boolean moved) {this.hasMoved = moved;}

    @Override
    public Map<Pair<Integer, Integer>, Boolean> isValidMove(int row, int col, AbstractPiece[][] board) {
        Map<Pair<Integer, Integer>, Boolean> moves = new HashMap<>();

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] d : dirs) {
            int r = row + d[0];
            int c = col + d[1];

            while (BoardHelper.inBounds(r, c)) {
                if (board[r][c] == null) {
                    moves.put(Pair.of(r, c), true);
                } else {
                    if (board[r][c].getColor() != this.getColor()) {
                        moves.put(Pair.of(r, c), true);
                    }
                    break;
                }
                r += d[0];
                c += d[1];
            }
        }

        return moves;
    }

    @Override
    public AbstractPiece copy() {
        return new RookPiece(this.getColor(), this.hasMoved());
    }
}
