package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.utils.BoardHelper;
import es.upsa.programacion.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class QueenPiece extends AbstractPiece {

    public QueenPiece(Color color) {
        super(color, '♛');
    }

    @Override
    public Map<Pair<Integer, Integer>, Boolean> isValidMove(int row, int col, AbstractPiece[][] board) {
        Map<Pair<Integer, Integer>, Boolean> moves = new HashMap<>();

        int[] dirs = {-1, 0, 1};

        for (int dr : dirs) {
            for (int dc : dirs) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = col + dc;

                while (BoardHelper.inBounds(r, c)) {
                    if (board[r][c] == null) {
                        moves.put(Pair.of(r, c), true);
                    } else {
                        if (board[r][c].getColor() != this.getColor()) {
                            moves.put(Pair.of(r, c), true);
                        }
                        break;
                    }
                    r += dr;
                    c += dc;
                }
            }
        }

        return moves;
    }

    @Override
    public AbstractPiece copy() {
        return new QueenPiece(this.getColor());
    }
}
