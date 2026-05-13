import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        
        // ==========================================
        // 1. LE CATALOGUE DES REVÊTEMENTS (Énoncé)
        // ==========================================
        // Un revêtement a un prix unitaire en euros/m² [cite: 157]
        // Les booléens indiquent où on peut les poser : (Mur, Sol, Plafond) [cite: 158]
        Peinture peintureBlanche = new Peinture(1, "Peinture Blanche (Murs/Plafond)", 15.50, true, false, true);
        Carrelage carrelageGris = new Carrelage(2, "Carrelage Gris (Sol)", 35.00, false, true, false);
        Isolation isolantExterieur = new Isolation(3, "Isolant Rapporté (Façade)", 45.00, true, false, false);

        // ==========================================
        // 2. GÉOMÉTRIE DE LA PIÈCE RECTANGULAIRE (Ta partie)
        // ==========================================
        // Pièce rectangulaire de 4m x 5m 
        Coin c1 = new Coin(1, 0, 0);
        Coin c2 = new Coin(2, 5, 0);
        Coin c3 = new Coin(3, 5, 4);
        Coin c4 = new Coin(4, 0, 4);

        // Hauteur sous plafond identique pour le niveau : 2.50m 
        double hauteurPlafond = 2.50; 
        
        // Murs définis par les coordonnées de leurs coins [cite: 123]
        Mur m1 = new Mur(1, c1, c2, hauteurPlafond);
        Mur m2 = new Mur(2, c2, c3, hauteurPlafond);
        Mur m3 = new Mur(3, c3, c4, hauteurPlafond);
        Mur m4 = new Mur(4, c4, c1, hauteurPlafond); // Mur de façade

        // Application des revêtements sur les murs (isolation sur la façade m4) [cite: 153]
        m1.setRevetement(peintureBlanche);
        m2.setRevetement(peintureBlanche);
        m3.setRevetement(peintureBlanche);
        m4.setRevetement(isolantExterieur);

        // ==========================================
        // 3. LES OUVERTURES IMPOSÉES (Énoncé)
        // ==========================================
        // Le code de la classe Porte fixera la taille à 0.90x2.10 automatiquement 
        Porte porteEntree = new Porte(1); 
        m1.ajouterOuverture(porteEntree);

        // Le code de la classe Fenetre fixera la taille à 1.20x1.20 automatiquement 
        Fenetre fenetreSalon = new Fenetre(2); 
        m4.ajouterOuverture(fenetreSalon);

        // ==========================================
        // 4. ASSEMBLAGE DE LA PIÈCE (Ta partie)
        // ==========================================
        Piece salon = new Piece(1, "Salon");
        salon.ajouterMur(m1);
        salon.ajouterMur(m2);
        salon.ajouterMur(m3);
        salon.ajouterMur(m4);

        // Ajout du sol et du plafond avec leurs revêtements [cite: 144, 152]
        Sol solSalon = new Sol(salon.surface());
        solSalon.setRevetement(carrelageGris);
        salon.setSol(solSalon);

        Plafond plafondSalon = new Plafond(salon.surface());
        plafondSalon.setRevetement(peintureBlanche);
        salon.setPlafond(plafondSalon);

        // ==========================================
        // 5. STRUCTURE DU BÂTIMENT (Partie d'Idrique)
        // ==========================================
        Appartement monAppart = new Appartement(101);
        monAppart.ajouterPiece(salon);

        Niveau niveau1 = new Niveau(1, hauteurPlafond);
        niveau1.ajouterAppartement(monAppart);

        Immeuble monImmeuble = new Immeuble("BAT-INSA", "Campus Esplanade, Strasbourg");
        monImmeuble.ajouterNiveau(niveau1);

        // ==========================================
        // 6. CALCUL ET SAUVEGARDE (Partie de Rime)
        // ==========================================
        System.out.println("\n--- DÉTAIL DU DEVIS ---");
        monImmeuble.afficher();

        // Sauvegarde dans un fichier texte comme exigé [cite: 161, 167]
        sauvegarderDevis("Devis_Batiment_INSA.txt", monImmeuble);
    }

    public static void sauvegarderDevis(String nomFichier, Batiment b) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomFichier))) {
            writer.println("PROJET INSA - MODULE M2 INFORMATIQUE");
            writer.println("====================================");
            writer.println("Identifiant du bâtiment : " + b.getIdBatiment());
            writer.println("Devis Total estimé      : " + String.format("%.2f", b.devisBatiment()) + " Euros");
            System.out.println("\n✅ Les données ont été sauvegardées dans le fichier : " + nomFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier texte : " + e.getMessage());
        }
    }
}