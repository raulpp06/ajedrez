package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.utils.BoardHelper;
import es.upsa.programacion.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class KnightPiece extends AbstractPiece {

    public KnightPiece(Color color) {
        super(color, '♞');
    }

    @Override
    public Map<Pair<Integer, Integer>, Boolean> isValidMove(int row, int col, AbstractPiece[][] board) {
        Map<Pair<Integer, Integer>, Boolean> moves = new HashMap<>();

        int[][] offsets = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] o : offsets) {
            int r = row + o[0];
            int c = col + o[1];

            if (!BoardHelper.inBounds(r, c)) continue;

            if (board[r][c] == null || board[r][c].getColor() != this.getColor()) {
                moves.put(Pair.of(r, c), true);
            }
        }

        return moves;
    }

}
