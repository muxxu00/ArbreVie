package Vue;

import Model.Noeud;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static java.awt.Font.BOLD;

public class Affichage {

    private static final int RAYON_NOEUD = 20;
    private static final int ESPACEMENT_HORIZONTAL = 80;
    private static final int ESPACEMENT_VERTICAL = 80;

    public static void dessinerArbre(Canvas canvas, Noeud racine, Scene scene) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        dessinerNoeuds(gc, racine, scene.getWidth()/2, scene.getHeight()/2 , scene);
    }

    private static void dessinerNoeuds(GraphicsContext gc, Noeud noeud, double x, double y, Scene scene) {
        // Dessiner le noeud
        
        if (noeud.isLeaf()) {
            //dessiner le noeud feuille
            gc.setFill(Color.GREEN);

            //youyou start here
            gc.fillOval(x - RAYON_NOEUD, y - RAYON_NOEUD, 2 * RAYON_NOEUD, 2 * RAYON_NOEUD);
            //youyou end here

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", 7));
            gc.fillText(noeud.getName(), x - RAYON_NOEUD + 5, y + 5);

            // Ajouter un gestionnaire d'événements de clic à la scène
            gc.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                double mouseX = event.getX();
                double mouseY = event.getY();
                if (clicDansNoeud(mouseX, mouseY, x, y)) {
                    afficherInfoNoeud(noeud);
                }
            });
        } else {
            //dessiner les noeuds non feuilles
            gc.setFill(Color.BROWN);


            //youyou start here
            gc.fillOval(x - RAYON_NOEUD, y - RAYON_NOEUD, 2 * RAYON_NOEUD, 2 * RAYON_NOEUD);
            //youyou end here

            // Dessiner le nom du noeud
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", 7));
            gc.fillText(noeud.getName(), x - RAYON_NOEUD + 5, y + 5);

            // Dessiner les enfants
            int nombreEnfants = noeud.getChildren().length;
            double espacementTotal = (nombreEnfants - 1) * ESPACEMENT_HORIZONTAL;
            double startX = x - espacementTotal / 2;
            double startY = y + 2 * RAYON_NOEUD + ESPACEMENT_VERTICAL;
            double currentX = startX;
            for (Noeud enfant : noeud.CreateChildren()) {
                dessinerNoeuds(gc, enfant, currentX, startY, scene);
                // Dessiner une ligne entre le parent et l'enfant
                gc.setStroke(Color.BLACK);

                //youyou start here tu devras surement changer des truc ici pour soit changer les lignes
                //soir carrement les supprimer pour faire autre chose a la place
                gc.strokeLine(x, y + RAYON_NOEUD, currentX, startY - RAYON_NOEUD);
                currentX += ESPACEMENT_HORIZONTAL;
            }
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
}