package Main;

import Model.Noeud;
import Vue.Affichage;
import Controler.Mouse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Stack;

public class Main extends Application {

    private Mouse mouse ;
    private Noeud noeudActuel = null; // Ajouter un champ pour garder une trace du noeud actuel
    //private Stack<Noeud> noeudHistorique = new Stack<>();

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(3000, 3000);

        // Créer un ScrollPane pour contenir le Canvas
        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setPannable(true); // Permettre le déplacement par glissement
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Désactive la barre de défilement horizontale
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Désactive la barre de défilement verticale



        // Gestionnaire d'événements de la souris pour le ScrollPane
        /*scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                mouse.onMouseScroll(event); // Appelle votre gestionnaire de souris pour le zoom
                event.consume(); // Consomme l'événement pour éviter le défilement par défaut
            }
        });*/

        Noeud racine = new Noeud("ressources/treeoflife_nodes.csv", "ressources/treeoflife_links.csv", 1, null);
        noeudActuel = racine; // Mettre à jour le noeud actuel

        Pane layout = new Pane();
        mouse = new Mouse(layout);
        Scene scene = new Scene(layout, 1920, 1000);

        TextFlow infoArea = new TextFlow();
        infoArea.setLayoutX(1600); // Positionner le TextArea en haut à droite
        infoArea.setLayoutY(10);
        infoArea.setPrefWidth(300);
        infoArea.setPrefHeight(200);
        infoArea.setStyle("-fx-background-color: #E5D2B8;");


        Button backButton = new Button("Retour");
        backButton.setLayoutX(10); // Positionner le bouton en haut à gauche
        backButton.setLayoutY(10);
        backButton.setOnAction(event -> {
            Stack<Noeud> noeudHistorique = Affichage.getNoeudHistorique();
            if (!noeudHistorique.isEmpty()) {
                noeudActuel = noeudHistorique.pop(); // Retirer le noeud du sommet de l'historique
                Affichage.dessinerArbre(canvas, noeudActuel, scene, infoArea); // Dessiner le cluster parent
                for(Noeud affiche : noeudHistorique){
                    System.out.print(affiche.getName() + ", ");
                }
                System.out.println();
            }
        });

        Image buttonImage = new Image("file:ressources/coeur.png");
        ImageView imageView = new ImageView(buttonImage);
        imageView.setFitWidth(30); // Réduire la largeur de l'image
        imageView.setFitHeight(30); // Réduire la hauteur de l'image
        Button favButton = new Button();
        favButton.setGraphic(imageView);
        favButton.setLayoutX(1852); // Positionner le bouton à la même position x que la fenêtre d'information
        favButton.setLayoutY(10 + infoArea.getPrefHeight() + 10);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setMinWidth(200); // Définir la largeur du ComboBox
        comboBox.setPromptText("espèces Favorites"); // Texte d'invite
        comboBox.setLayoutX(1700); // Positionner le ComboBox à côté du bouton like
        comboBox.setLayoutY(300);

        // Ajouter un gestionnaire d'événements de clic au bouton like
        favButton.setOnAction(event -> {
            if (noeudActuel != null && noeudActuel.getParent() != null) {
                String name = Affichage.getNoeudActuel().getName();
                int id = Affichage.getNoeudActuel().getId();
                comboBox.getItems().add(name + "," + id);
            }
        });

        comboBox.setOnAction(event -> {
            String selectedItem = comboBox.getSelectionModel().getSelectedItem();
            String[] parts = selectedItem.split(",");
            String name = parts[0];
            String id = parts[1];
            int idInt = Integer.parseInt(id);

            String encodedName = name.replaceAll(" ", "%20");
            String toLorgLink = String.format("http://tolweb.org/%s/%d", encodedName, idInt);
            System.out.println(toLorgLink);

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    // Ouvrir la page web dans le navigateur par défaut
                    Desktop.getDesktop().browse(new URI(toLorgLink));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }


        });

        layout.getChildren().addAll(scrollPane, backButton, infoArea, favButton, comboBox);
        layout.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                mouse.onMouseScroll(event); // Appelle votre gestionnaire de souris pour le zoom
                event.consume(); // Consomme l'événement pour éviter le défilement par défaut
            }
        });
        Affichage.dessinerArbre(canvas, racine, scene, infoArea);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Arbre de Vie");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
