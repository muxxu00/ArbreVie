import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Noeud a = new Noeud("C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\treeoflife_nodes_simplified.csv", "C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\treeoflife_links_simplified.csv", 1);
        String b = a.afficheNoeud();
        TreeView<String> treeView = createTreeView(a);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(treeView);

        // Création de la scène
        Scene scene = new Scene(scrollPane, 800, 600);

        // Configuration de la scène pour la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Arbre de Vie");
        primaryStage.show();
    }

    private TreeView<String> createTreeView(Noeud rootNode) {
        // Création de l'arbre de vue à partir du nœud racine
        TreeItem<String> rootItem = new TreeItem<>(rootNode.getName());
        addChildrenToTreeView(rootNode, rootItem);
        TreeView<String> treeView = new TreeView<>(rootItem);
        return treeView;
    }

    private void addChildrenToTreeView(Noeud parentNode, TreeItem<String> parentItem) {
        // Ajout des enfants du nœud parent à l'arbre de vue
        List<Noeud> children = parentNode.CreateChildren();
        for (Noeud child : children) {
            TreeItem<String> childItem = new TreeItem<>(child.getName());
            parentItem.getChildren().add(childItem);
            addChildrenToTreeView(child, childItem);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
