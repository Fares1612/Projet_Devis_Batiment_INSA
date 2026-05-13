import java.util.ArrayList;

public class Maison extends Batiment {
    

    private ArrayList<Piece> pieces;


    public Maison(String idBatiment, String adresse) {
        super(idBatiment, adresse);
        this.pieces = new ArrayList<>();
        
    }
    // On DOIT implémenter la méthode ici pour enlever l'erreur rouge
    @Override
    public double devisBatiment() {
        double total = 0;
        for (Piece p : pieces) {
            total += p.devisPiece();
        }
        return total;
    }


    @Override
    public void afficher() {
        System.out.println("Type : Maison Individuelle");
        System.out.println("ID : " + idBatiment + " | Adresse : " + adresse);
        System.out.println("Montant estimatif total : " + devisBatiment() + " €");
    }
}