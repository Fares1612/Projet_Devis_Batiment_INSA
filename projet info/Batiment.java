public abstract class Batiment {
    protected String idBatiment;
    protected String adresse;

    public Batiment(String idBatiment, String adresse) {
        this.idBatiment = idBatiment;
        this.adresse = adresse;
    }

    public String getIdBatiment() { return this.idBatiment; }
    
    public abstract double devisBatiment();
    public abstract void afficher();
}