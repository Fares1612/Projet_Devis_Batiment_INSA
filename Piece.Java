import java.util.ArrayList;

public class Piece {
    private int idPiece;
    private String usage; // ex: "Chambre", "Cuisine"
    private ArrayList<Mur> murs;

    public Piece(int idPiece, String usage) {
        this.idPiece = idPiece;
        this.usage = usage;
        this.murs = new ArrayList<>();
    }

    public void ajouterMur(Mur m) {
        this.murs.add(m);
    }

    // Surface des murs de la pièce (pour la peinture/isolation)
    public double surfaceMurs(double hauteurSousPlafond) {
        double total = 0;
        for (Mur m : murs) {
            total += m.surface(hauteurSousPlafond);
        }
        return total;
    }

    public double devisPiece() {
        // Cette méthode sera complétée plus tard avec les prix d'Elias
        return 0;
    }
}