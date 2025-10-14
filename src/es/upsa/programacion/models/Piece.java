package es.upsa.programacion.models;

import es.upsa.programacion.Color;

// Las piezas deberían de tener su posicion en cada una de ellas y isValidMove()
// deberia de ser cambiado por otro método
// que te de una lista o mapa de posiciones a las que se puede mover una pieza
public abstract class Piece {

    private final Color color;
    private final char symbol;

    public Piece(Color color, char symbol) {
        this.color = color;
        this.symbol = symbol;
    }

    public Color getColor() { return color; }

    public char getSymbol() { return symbol; }

    public String getDisplaySymbol() {
        return this.color == Color.BLACK ? "\033[0;30m" + symbol + "\033[0m" : "\u001B[37m" + symbol + "\u001B[0m";
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", symbol=" + symbol +
                '}';
    }

    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board);
}
