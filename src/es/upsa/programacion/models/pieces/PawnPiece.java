package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.utils.BoardHelper;
import es.upsa.programacion.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class PawnPiece extends AbstractPiece {

    public PawnPiece(Color color) {
        super(color, '♟');
    }

    @Override
    public Map<Pair<Integer, Integer>, Boolean> isValidMove(int row, int col, AbstractPiece[][] board) {
        Map<Pair<Integer, Integer>, Boolean> moves = new HashMap<>();

        int dir = (this.getColor() == Color.WHITE) ? -1 : 1;
        int startRow = (this.getColor() == Color.WHITE) ? 6 : 1;

        // 1-step forward
        if (BoardHelper.inBounds(row + dir, col) && board[row + dir][col] == null) {
            moves.put(Pair.of(row + dir, col), true);

            // 2-step forward from starting row
            if (row == startRow && board[row + 2 * dir][col] == null) {
                moves.put(Pair.of(row + 2 * dir, col), true);
            }
        }

        // Captures
        for (int dc : new int[]{-1, 1}) {
            int r = row + dir;
            int c = col + dc;
            if (BoardHelper.inBounds(r, c) && board[r][c] != null && board[r][c].getColor() != this.getColor()) {
                moves.put(Pair.of(r, c), true);
            }
        }

        return moves;
    }

}
