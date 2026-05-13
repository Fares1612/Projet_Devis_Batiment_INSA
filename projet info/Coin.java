public class Coin {
    private int idCoin;
    private double x;
    private double y;

    public Coin(int idCoin, double x, double y) {
        this.idCoin = idCoin;
        this.x = x;
        this.y = y;
    }

    public int getIdCoin() { return idCoin; }
    public double getX() { return x; }
    public double getY() { return y; }
}