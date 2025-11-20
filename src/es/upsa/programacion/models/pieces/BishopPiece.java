package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.utils.BoardHelper;
import es.upsa.programacion.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class BishopPiece extends AbstractPiece {

    public BishopPiece(Color color) {
        super(color, '♝');
    }

    @Override
    public Map<Pair<Integer, Integer>, Boolean> isValidMove(int row, int col, AbstractPiece[][] board) {
        Map<Pair<Integer, Integer>, Boolean> moves = new HashMap<>();

        int[] directions = {-1, 1};

        for (int dr : directions) {
            for (int dc : directions) {
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

}
