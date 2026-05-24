package fr.insa.batiment.model;

import java.util.ArrayList;
import java.util.List;

public class Appartement {

    private int idAppartement;
    private List<Piece> pieces;

    public Appartement(int idAppartement) {
        this.idAppartement = idAppartement;
        this.pieces = new ArrayList<>();
    }

    public int getIdAppartement() { return idAppartement; }
    public List<Piece> getPieces() { return pieces; }

    public void ajouterPiece(Piece piece) {
        pieces.add(piece);
    }
}
