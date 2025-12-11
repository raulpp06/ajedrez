package es.upsa.programacion.models.pieces;

import es.upsa.programacion.Color;
import es.upsa.programacion.utils.Pair;

import java.util.Map;

public abstract class AbstractPiece {

    private final Color color;
    private final char symbol;

    public AbstractPiece(Color color, char symbol) {
        this.color = color;
        this.symbol = symbol;
    }

    public Color getColor() {return color;}

    public char getSymbol() {return symbol;}

    public String getDisplaySymbol() {
        return this.color == Color.BLACK ? "\u001B[30m" + symbol : "\u001B[37m" + symbol;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", symbol=" + symbol +
                '}';
    }

    public abstract Map<Pair<Integer, Integer>, Boolean> isValidMove(int pieceRow, int pieceCol, AbstractPiece[][] board);

    public abstract AbstractPiece copy();
}
