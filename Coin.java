public class Coin {
    private int idCoin;
    private double cx;
    private double cy;

    public Coin(int id, double x, double y) {
        this.idCoin = id;
        this.cx = x;
        this.cy = y;
    }

    public double getCx() { return cx; }
    public double getCy() { return cy; }

    @Override
    public String toString() {
        return "Coin " + idCoin + " (" + cx + ", " + cy + ")";
    }
}