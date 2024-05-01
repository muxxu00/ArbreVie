package Controler;

import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

import java.awt.*;

public class Mouse {

    private ScrollPane scrollPane;

    public Mouse(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public void onMouseScroll(ScrollEvent event) {
        double scaleFactor = (event.getDeltaY() > 0) ? 1.1 : 0.9; // Facteur de zoom

        // Sauvegardez les valeurs de défilement avant le zoom
        double preHvalue = scrollPane.getHvalue();
        double preVvalue = scrollPane.getVvalue();

        // Appliquez le zoom
        scrollPane.getContent().setScaleX(scrollPane.getContent().getScaleX() * scaleFactor);
        scrollPane.getContent().setScaleY(scrollPane.getContent().getScaleY() * scaleFactor);

        // Recalculez la position de la vue du ScrollPane pour rester centré sur la position de la souris avant le zoom
        double mouseX = event.getX();
        double mouseY = event.getY();
        double postHvalue = scrollPane.getHvalue();
        double postVvalue = scrollPane.getVvalue();
        double deltaX = (mouseX - scrollPane.getViewportBounds().getWidth() / 2) / scrollPane.getContent().getScaleX();
        double deltaY = (mouseY - scrollPane.getViewportBounds().getHeight() / 2) / scrollPane.getContent().getScaleY();
        double newHvalue = postHvalue + deltaX;
        double newVvalue = postVvalue + deltaY;

        // Ajustez la position de la vue du ScrollPane
        scrollPane.setHvalue(newHvalue);
        scrollPane.setVvalue(newVvalue);

        // Empêchez le défilement de la page par défaut
        event.consume();
    }
}
