<<<<<<< HEAD
# Catalogue Revetement

Application JavaFX developpee dans le cadre du module **Informatique 2** (INSA). Elle permet de gerer un catalogue de revetements et de calculer un **devis estimatif** pour un batiment d'habitation.

## Description du projet

Un batiment contient des niveaux, chaque niveau des appartements, chaque appartement des pieces. Une piece rectangulaire possede quatre murs, un sol et eventuellement un plafond. Les murs peuvent comporter des portes et des fenetres. Les revetements du catalogue sont compatibles avec le mur, le sol ou le plafond selon leurs attributs.

L'application calcule pour chaque revetement :
- la **surface totale** a recouvrir ;
- le **prix total** (surface × prix unitaire) ;
- le **devis global**.

## Architecture MVC

Le projet suit le modele **Model - View - Controller**, inspire de [JavaFX_Catalogue_Demo](https://github.com/NadirGuermoudi/JavaFX_Catalogue_Demo) :

| Couche | Role | Packages |
|--------|------|----------|
| **Model** | Donnees et calculs metier | `fr.insa.batiment.model` |
| **View** | Interface graphique (sans logique) | `fr.insa.batiment.view` |
| **Controller** | Evenements et orchestration | `fr.insa.batiment.controller` |
| **Service** | Chargement fichiers, calcul devis | `fr.insa.batiment.service` |

```
src/main/java/fr/insa/batiment/
├── MainApp.java
├── model/          10 classes (Coin, Sol, Plafond, Porte, Fenetre, LigneDevis en classes internes)
├── view/           MainView (4 onglets dans une seule vue)
├── controller/     MainController
└── service/        CatalogueService, DevisService
```

**15 classes Java au total**, maximum **4 commentaires** par classe.

## Fonctionnalites

1. **Onglet Catalogue** — TableView des revetements ; charger / sauvegarder le fichier CSV.
2. **Onglet Creation piece** — Formulaire pour une piece rectangulaire (dimensions, revetements, portes, fenetres).
3. **Onglet Devis** — Lignes par revetement, total general, export texte.
4. **Onglet Plan 2D** — Visualisation simple des pieces sur un Canvas.

## Catalogue de revetements

Fichier : `catalogue_revetements.txt` (separateur **point-virgule**).

```
idRevetement;designation;pourMur;pourSol;pourPlafond;prixUnitaire
```

- `pourMur`, `pourSol`, `pourPlafond` : `1` = compatible, `0` = non compatible.
- `prixUnitaire` : prix au m² en euros.

Une copie par defaut se trouve dans `src/main/resources/` et a la racine du projet.

## Comment lancer le projet

### Prerequis

- **JDK 17** ou superieur
- **Maven 3.6+**

### Compilation et execution

Maven n'est pas obligatoire : le projet inclut **mvnw.cmd** (Maven Wrapper).

**PowerShell** (dans le dossier du projet) :



Si `java` n'est pas reconnu, installez le JDK 17 :

```powershell
winget install -e Microsoft.OpenJDK.17
```

Puis **fermez et rouvrez** le terminal, ou définissez `JAVA_HOME` :

```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.19.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
```

**Avec Maven installé globalement** (si `mvn` fonctionne) :

```bash
mvn clean compile
mvn javafx:run
```

Ou depuis un IDE (IntelliJ, Eclipse, VS Code) : lancer la classe `fr.insa.batiment.MainApp` avec le module JavaFX configure.

### Fichiers de sauvegarde

| Fichier | Usage |
|---------|--------|
| `catalogue_revetements.txt` | Catalogue des revetements (chargement / sauvegarde) |
| `devis.txt` (au choix) | Export du devis calcule |

## Captures d'ecran

*(A ajouter dans le rapport : onglets Catalogue, Creation piece, Devis, Plan 2D.)*

## Limites du projet

- Un seul niveau et un seul appartement sont utilises dans l'interface (structure complete en modele, simplifiee a l'ecran).
- Les pieces sont forcement **rectangulaires** ; les portes et fenetres sont placees sur le premier mur.
- Pas de sauvegarde complete du batiment (seulement catalogue et devis).
- Le plan 2D est une **vue indicative**, sans echelle georeferenciee ni edition graphique.
- Pas de gestion multi-utilisateurs ni de base de donnees.

## Auteur

Projet pedagogique — Informatique 2.
=======
# Projet_Devis_Batiment_INSA
>>>>>>> 225f7fa9b6f3002ddf9abc1314f87eaa53c82969
