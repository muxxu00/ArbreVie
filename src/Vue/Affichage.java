package Vue;

import Model.Noeud;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static java.awt.Font.BOLD;

public class Affichage {

    private static final int RAYON_NOEUD = 20;
    private static final int ESPACEMENT_HORIZONTAL = 80;
    private static final int ESPACEMENT_VERTICAL = 80;

    public static void dessinerArbre(Canvas canvas, Noeud racine) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        dessinerNoeuds(gc, racine, 400, 50);
    }

    private static void dessinerNoeuds(GraphicsContext gc, Noeud noeud, double x, double y) {
        // Dessiner le noeud
        gc.setFill(Color.BLUE);
        gc.fillOval(x - RAYON_NOEUD, y - RAYON_NOEUD, 2 * RAYON_NOEUD, 2 * RAYON_NOEUD);

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
            dessinerNoeuds(gc, enfant, currentX, startY);
            // Dessiner une ligne entre le parent et l'enfant
            gc.setStroke(Color.BLACK);
            gc.strokeLine(x, y + RAYON_NOEUD, currentX, startY - RAYON_NOEUD);
            currentX += ESPACEMENT_HORIZONTAL;
        }
    }
}