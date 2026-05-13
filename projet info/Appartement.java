import java.util.ArrayList;

public class Appartement {
    private int idAppart;
    private ArrayList<Piece> pieces;

    public Appartement(int idAppart) {
        this.idAppart = idAppart;
        this.pieces = new ArrayList<>();
    }

    public void ajouterPiece(Piece p) { this.pieces.add(p); }

    public double surfaceTotale() {
        double total = 0;
        for (Piece p : pieces) {
            total += p.surface();
        }
        return total;
    }

    public double devisAppartement() {
        double total = 0;
        for (Piece p : pieces) {
            total += p.devisPiece();
        }
        return total;
    }
}