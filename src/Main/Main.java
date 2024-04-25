package Main;

import Model.Noeud;
import Vue.Affichage;
import Controler.Mouse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private Mouse mouse ;
    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(3000, 2500);

        // Créer un ScrollPane pour contenir le Canvas
        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setPannable(true); // Permettre le déplacement par glissement

        mouse = new Mouse(scrollPane);

        // Gestionnaire d'événements de la souris pour le ScrollPane
        scrollPane.setOnScroll(event -> mouse.onMouseScroll(event));

        Noeud racine = new Noeud("ressources/treeoflife_nodes.csv", "ressources/treeoflife_links.csv", 1);

        Affichage.dessinerArbre(canvas, racine);

        StackPane root = new StackPane(scrollPane);
        root.setFocusTraversable(true);

        Scene scene = new Scene(root, 1500, 1000);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Arbre de Vie");
        primaryStage.show();
    }



    /*private TreeView<String> createTreeView(Model.Noeud rootNode) {
        // Création de l'arbre de vue à partir du nœud racine
        TreeItem<String> rootItem = new TreeItem<>(rootNode.getName());
        addChildrenToTreeView(rootNode, rootItem);
        TreeView<String> treeView = new TreeView<>(rootItem);
        return treeView;
    }

    private void addChildrenToTreeView(Model.Noeud parentNode, TreeItem<String> parentItem) {
        // Ajout des enfants du nœud parent à l'arbre de vue
        List<Model.Noeud> children = parentNode.CreateChildren();
        for (Model.Noeud child : children) {
            TreeItem<String> childItem = new TreeItem<>(child.getName());
            parentItem.getChildren().add(childItem);
            addChildrenToTreeView(child, childItem);
        }
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
