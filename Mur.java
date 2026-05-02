public class Mur {
    private int idMur;
    private Coin debut;
    private Coin fin;

    public Mur(int idMur, Coin debut, Coin fin) {
        this.idMur = idMur;
        this.debut = debut;
        this.fin = fin;
    }

    // Calcul de la longueur du mur (Pythagore)
    public double longueur() {
        return Math.sqrt(Math.pow(fin.getCx() - debut.getCx(), 2) + 
                         Math.pow(fin.getCy() - debut.getCy(), 2));
    }

    // Calcul de la surface du mur (Longueur x Hauteur du niveau)
    public double surface(double hauteurSousPlafond) {
        return longueur() * hauteurSousPlafond;
    }

    @Override
    public String toString() {
        return "Mur " + idMur + " [Longueur: " + String.format("%.2f", longueur()) + "m]";
    }
}