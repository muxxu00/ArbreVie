package Main;

import Model.Noeud;
import Vue.Affichage;
import Controler.Mouse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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

        Button likeButton = new Button();
        likeButton.setLayoutX(10); // Positionner le bouton en dessous du bouton de retour
        likeButton.setLayoutY(50);
        Image heartImage = new Image("C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\coeur.png"); // Remplacez par le chemin vers votre image
        ImageView heartImageView = new ImageView(heartImage);
        heartImageView.setFitWidth(30); // Ajustez la taille de l'image comme vous le souhaitez
        heartImageView.setPreserveRatio(true);
        likeButton.setGraphic(heartImageView);

        layout.getChildren().addAll(scrollPane, backButton, infoArea, likeButton);
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
