public class Sol {
    private double surface;
    private Revetement revetement;

    public Sol(double surface) {
        this.surface = surface;
    }

    public void setRevetement(Revetement r) { this.revetement = r; }
    public Revetement getRevetement() { return this.revetement; }
    public double surface() { return this.surface; }
}