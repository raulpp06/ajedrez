package es.upsa.programacion.controllers.pieces;

import es.upsa.programacion.Color;

import java.util.List;

public class BotMinimax {

    private int maxDepth = 2;

    public String chooseMove(Board board, Color botColor) {
        List<String> moves = board.getAllLegalMoves(botColor);
        if (moves.isEmpty()) return null;

        String bestMove = null;
        int bestScore = (botColor == Color.WHITE) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (String move : moves) {
            Board copy = board.clone();
            copy.moveSilent(move, botColor);

            int score = minimax(copy, maxDepth - 1, botColor.opposite(),
                    Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (botColor == Color.WHITE) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }


    private int minimax(Board board, int depth, Color turn, int alpha, int beta) {
        if (depth == 0) return board.evaluate();

        List<String> moves = board.getAllLegalMoves(turn);
        if (moves.isEmpty()) return board.evaluate();

        boolean maximizing = (turn == Color.WHITE);

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (String move : moves) {
                Board copy = board.clone();
                copy.moveSilent(move, turn);
                int eval = minimax(copy, depth - 1, turn.opposite(), alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (String move : moves) {
                Board copy = board.clone();
                copy.moveSilent(move, turn);
                int eval = minimax(copy, depth - 1, turn.opposite(), alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }
}

