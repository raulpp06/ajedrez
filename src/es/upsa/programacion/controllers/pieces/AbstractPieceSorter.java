package es.upsa.programacion.controllers.pieces;

import es.upsa.programacion.models.pieces.AbstractPiece;

import java.util.Comparator;

public class AbstractPieceSorter implements Comparator<AbstractPiece> {

    private int valueOf(AbstractPiece p) {
        return switch (p.getSymbol()) {
            case '♚' -> 6;
            case '♛' -> 5;
            case '♜' -> 4;
            case '♝' -> 3;
            case '♞' -> 2;
            case '♟' -> 1;
            default -> 0;
        };
    }

    @Override
    public int compare(AbstractPiece o1, AbstractPiece o2) {
        return Integer.compare(valueOf(o2), valueOf(o1));
    }
}
