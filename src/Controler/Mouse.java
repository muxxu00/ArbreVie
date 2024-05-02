package Controler;

import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.awt.*;

public class Mouse {

    private Pane layout;

    public Mouse(Pane layout) {
        this.layout = layout;
    }

    public void onMouseScroll(ScrollEvent event) {
        double scaleFactor = (event.getDeltaY() > 0) ? 1.1 : 0.9; // Facteur de zoom

        // Coordonnées de la souris relatives au contenu du Pane
        double mouseX = event.getX() - (layout.getWidth() - layout.getLayoutBounds().getWidth()) / 2;
        double mouseY = event.getY() - (layout.getHeight() - layout.getLayoutBounds().getHeight()) / 2;

        // Appliquez le zoom
        layout.setScaleX(layout.getScaleX() * scaleFactor);
        layout.setScaleY(layout.getScaleY() * scaleFactor);

        // Recalculez la position de la vue du Pane pour rester centré sur la position de la souris avant le zoom
        double newTranslateX = layout.getTranslateX() - mouseX * (scaleFactor - 1);
        double newTranslateY = layout.getTranslateY() - mouseY * (scaleFactor - 1);

        // Ajustez la position de la vue du Pane
        layout.setTranslateX(newTranslateX);
        layout.setTranslateY(newTranslateY);

        // Empêchez le défilement de la page par défaut
        event.consume();
    }
}
