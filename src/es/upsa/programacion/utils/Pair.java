package es.upsa.programacion.utils;

public class Pair<R, L> {
    private R right;
    private L left;

    public Pair(R right, L left) {
        this.right = right;
        this.left = left;
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }
}
