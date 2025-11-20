package es.upsa.programacion.utils;

import es.upsa.programacion.controllers.pieces.Board;

public class BoardHelper {

    public static boolean inBounds(int r, int c) {
        return r >= 0 && r < Board.BOARD_SIZE && c >= 0 && c < Board.BOARD_SIZE;
    }
}
