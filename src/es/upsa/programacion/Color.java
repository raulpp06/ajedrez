package es.upsa.programacion;

public enum Color {
    WHITE,
    BLACK;

    @Override
    public String toString() {
        return this == WHITE ? "White" : "Black";
    }

    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}
