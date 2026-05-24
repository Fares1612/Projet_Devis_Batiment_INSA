package fr.insa.batiment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogueRevetement {

    private List<Revetement> revetements;

    public CatalogueRevetement() {
        this.revetements = new ArrayList<>();
    }

    public List<Revetement> getRevetements() { return revetements; }

    public void setRevetements(List<Revetement> revetements) {
        this.revetements = revetements;
    }

    public Optional<Revetement> trouverParId(int id) {
        return revetements.stream()
                .filter(r -> r.getIdRevetement() == id)
                .findFirst();
    }

    public List<Revetement> getRevetementsPourMur() {
        return revetements.stream().filter(Revetement::isPourMur).collect(Collectors.toList());
    }

    public List<Revetement> getRevetementsPourSol() {
        return revetements.stream().filter(Revetement::isPourSol).collect(Collectors.toList());
    }

    public List<Revetement> getRevetementsPourPlafond() {
        return revetements.stream().filter(Revetement::isPourPlafond).collect(Collectors.toList());
    }
}
