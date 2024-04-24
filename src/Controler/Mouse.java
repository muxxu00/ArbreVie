package controller;

import javafx.scene.input.ScrollEvent;

public class Mouse {

    private double mouseX, mouseY; // Coordonnées de la souris

    public void onMouseScroll(ScrollEvent event) {
        double delta = event.getDeltaY(); // Variation de la molette de la souris
        double scaleFactor = (delta > 0) ? 1.1 : 0.9; // Facteur de zoom

        // Mettre à jour les coordonnées de la souris
        mouseX = event.getX();
        mouseY = event.getY();

        // Appliquer le zoom
        event.consume(); // Empêcher le défilement de la page par défaut
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}
