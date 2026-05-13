import java.util.ArrayList;

public class Immeuble extends Batiment {
    private ArrayList<Niveau> niveaux;

    public Immeuble(String idBatiment, String adresse) {
        super(idBatiment, adresse);
        this.niveaux = new ArrayList<>();
    }

    public void ajouterNiveau(Niveau n) {
        this.niveaux.add(n);
    }

    @Override
    public double devisBatiment() {
        double total = 0;
        for (Niveau n : niveaux) {
            total += n.devisNiveau();
        }
        return total;
    }

    @Override
    public void afficher() {
        System.out.println("Type : Immeuble d'habitation");
        System.out.println("ID : " + getIdBatiment() + " | Adresse : " + adresse);
        System.out.println("Nombre d'étages : " + niveaux.size());
        System.out.println("Montant estimatif total : " + devisBatiment() + " €");
    }
}