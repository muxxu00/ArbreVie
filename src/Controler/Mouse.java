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
        double mouseX = event.getX();
        double mouseY = event.getY();

        // Calculez les coordonnées de la souris par rapport au ScrollPane
        Point2D pos = scrollPane.getContent().sceneToLocal(mouseX, mouseY);

        // Sauvegardez la position avant le zoom
        double preScaleX = scrollPane.getHvalue();
        double preScaleY = scrollPane.getVvalue();

        // Appliquez le zoom
        scrollPane.setScaleX(scrollPane.getScaleX() * scaleFactor);
        scrollPane.setScaleY(scrollPane.getScaleY() * scaleFactor);

        // Calculez les nouvelles valeurs de défilement
        double dx = pos.getX() - (mouseX - preScaleX * scrollPane.getWidth());
        double dy = pos.getY() - (mouseY - preScaleY * scrollPane.getHeight());

        // Ajustez la position du ScrollPane pour conserver la position de la souris
        scrollPane.setHvalue(dx / (scrollPane.getContent().getLayoutBounds().getWidth() - scrollPane.getViewportBounds().getWidth()));
        scrollPane.setVvalue(dy / (scrollPane.getContent().getLayoutBounds().getHeight() - scrollPane.getViewportBounds().getHeight()));

        // Empêchez le défilement de la page par défaut
        event.consume();
    }
}
