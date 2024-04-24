package Main;
import Model.Noeud;
import Vue.Affichage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(1500, 1000);

        Noeud racine = new Noeud("ressources/treeoflife_nodes_simplified.csv", "ressources/treeoflife_links_simplified.csv", 1);

        Affichage.dessinerArbre(canvas, racine);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, 800, 600);
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
