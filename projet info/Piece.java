import java.util.ArrayList;

public class Piece {
    private int idPiece;
    private String usage;
    private ArrayList<Mur> murs;
    private Sol sol;
    private Plafond plafond;

    public Piece(int idPiece, String usage) {
        this.idPiece = idPiece;
        this.usage = usage;
        this.murs = new ArrayList<>();
    }

    public void ajouterMur(Mur m) { this.murs.add(m); }
    public void setSol(Sol s) { this.sol = s; }
    public void setPlafond(Plafond p) { this.plafond = p; }

    public double surface() {
        if (murs.size() >= 2) {
            // Calcul simplifié pour une pièce rectangulaire (Étape 1)
            return murs.get(0).longueur() * murs.get(1).longueur();
        }
        return 0;
    }

    public double devisPiece() {
        double total = 0;
        for (Mur m : murs) {
            if (m.getRevetement() != null) {
                total += m.surfaceNette() * m.getRevetement().getPrixUnitaire();
            }
        }
        if (sol != null && sol.getRevetement() != null) {
            total += sol.surface() * sol.getRevetement().getPrixUnitaire();
        }
        if (plafond != null && plafond.getRevetement() != null) {
            total += plafond.surface() * plafond.getRevetement().getPrixUnitaire();
        }
        return total;
    }
}