package Vue;

import Model.Noeud;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Stack;

import static java.awt.Font.BOLD;

public class Affichage {

    private static Noeud noeudActuel = null; // Ajouter un champ pour garder une trace du noeud actuel
    private static Stack<Noeud> noeudHistorique = new Stack<>();
    private static final int RAYON_NOEUD = 20;
    private static final int ESPACEMENT_HORIZONTAL = 80;
    private static final int ESPACEMENT_VERTICAL = 80;

    public static void dessinerArbre(Canvas canvas, Noeud racine, Scene scene, TextFlow infoArea) {
        noeudActuel = racine; // Mettre à jour le noeud actuel
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Changer la couleur de fond du Canvas
        gc.setFill(Color.web("#809671")); // Définir la couleur de remplissage
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Remplir le canvas avec la couleur

        dessinerNoeuds(gc, racine, scene.getWidth()/2, scene.getHeight()/2 , scene, canvas, infoArea);
        VBox infoBox = noeudActuel.afficheNoeud();
        infoArea.getChildren().clear();
        infoArea.getChildren().addAll(infoBox.getChildren());

    }

    private static void dessinerNoeuds(GraphicsContext gc, Noeud noeud, double x, double y, Scene scene, Canvas canvas, TextFlow infoArea) {

        // Dessiner le noeud parent plus grand
        gc.setFill(Color.web("#E5D2B8"));
        gc.fillOval(x - 3 * RAYON_NOEUD, y - 3 * RAYON_NOEUD, 6 * RAYON_NOEUD, 6 * RAYON_NOEUD);
        gc.setFill(Color.WHITE);
        Font font1 = Font.font("Georgia", 20);
        gc.setFont(font1);
        Text tempText1 = new Text(noeud.getName());
        tempText1.setFont(font1);
        double textWidth1 = tempText1.getLayoutBounds().getWidth();
        double textHeight1 = tempText1.getLayoutBounds().getHeight();
        double textX1 = x - textWidth1 / 2; // Centrer le texte en x
        double textY1 = y + textHeight1 / 4; // Centrer le texte en y
        gc.fillText(noeud.getName(), textX1, textY1);

            // Dessiner les enfants
        int nombreEnfants = noeud.getChildren().length;
        double angle = 2 * Math.PI / nombreEnfants;
        for (int i = 0; i < nombreEnfants; i++) {
            Noeud enfant = noeud.CreateChildren().get(i);
            Random rand = new Random();
            double enfantX = x + (20 + rand.nextInt(5)) * RAYON_NOEUD * Math.cos(i * angle);
            double enfantY = y + (20 + rand.nextInt(5)) * RAYON_NOEUD * Math.sin(i * angle);

            // Dessiner le noeud enfant
            if(enfant.isLeaf()){
                gc.setFill(Color.web("B3B792"));
            }else{
                gc.setFill(Color.web("725C3A"));
            }
            gc.fillOval(enfantX - 2 * RAYON_NOEUD, enfantY - 2 * RAYON_NOEUD, 4 * RAYON_NOEUD, 4 * RAYON_NOEUD);
            gc.setFill(Color.WHITE);
            gc.setFill(Color.WHITE);
            Font font = Font.font("Arial", 15);
            gc.setFont(font);
            Text tempText = new Text(enfant.getName());
            tempText.setFont(font);
            double textWidth = tempText.getLayoutBounds().getWidth();
            double textHeight = tempText.getLayoutBounds().getHeight();
            double textX = enfantX - textWidth / 2; // Centrer le texte en x
            double textY = enfantY + textHeight / 4; // Centrer le texte en y
            gc.fillText(enfant.getName(), textX, textY);

            final double finalX = enfantX;
            final double finalY = enfantY;

            // Ajouter un gestionnaire d'événements de clic à la scène
            gc.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                double mouseX = event.getX();
                double mouseY = event.getY();
                if (clicDansNoeud(mouseX, mouseY, finalX, finalY)) {
                    noeudHistorique.push(noeudActuel); // Ajouter le noeud actuel à l'historique
                    noeudActuel = enfant; // Mettre à jour le noeud actuel
                    dessinerArbre(canvas, enfant, scene, infoArea); // Dessiner le nouveau cluster
                    for(Noeud affiche : noeudHistorique){
                        System.out.print(affiche.getName() + ", ");
                    }
                    System.out.println();
                }
            });


                // Dessiner une ligne entre le parent et l'enfant
            gc.setStroke(Color.BLACK);
            double ligneStartX = x + 3 * RAYON_NOEUD * Math.cos(i * angle);
            double ligneStartY = y + 3 * RAYON_NOEUD * Math.sin(i * angle);
            double ligneEndX = enfantX - 2 * RAYON_NOEUD * Math.cos(i * angle);
            double ligneEndY = enfantY - 2 * RAYON_NOEUD * Math.sin(i * angle);
            gc.strokeLine(ligneStartX, ligneStartY, ligneEndX, ligneEndY);
        }

    }
    private static boolean clicDansNoeud(double clicX, double clicY, double noeudX, double noeudY) {
        return Math.pow(clicX - noeudX, 2) + Math.pow(clicY - noeudY, 2) <= Math.pow(RAYON_NOEUD, 2);
    }

    private static void afficherInfoNoeud(Noeud noeud) {
        Stage infoStage = new Stage();
        infoStage.setTitle("Node Information");

        VBox infoBox = noeud.afficheNoeud(); // Utilisation de la méthode afficheNoeud pour obtenir un VBox

        Scene infoScene = new Scene(infoBox, 300, 200); // Utilisation du VBox dans la scène
        infoStage.setScene(infoScene);

        // Fermer la fenêtre lorsque la souris quitte la scène
        infoScene.addEventFilter(MouseEvent.MOUSE_EXITED, exitEvent -> {
            infoStage.close();
        });

        infoStage.show();
    }

    public static Stack<Noeud> getNoeudHistorique() {
        return noeudHistorique;
    }


}