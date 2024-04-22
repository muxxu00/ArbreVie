import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Noeud a = new Noeud("C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\treeoflife_nodes_simplified.csv", "C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\treeoflife_links_simplified.csv", 2);
        String b = a.afficheNoeud();
        Button button = new Button(b);

        // Ajout d'un gestionnaire d'événements au bouton
        button.setOnAction(e -> System.out.println("Oui Oui je suis le roi du monde"));

        // Création d'un conteneur de mise en page
        StackPane root = new StackPane();
        root.getChildren().add(button);

        // Création de la scène
        Scene scene = new Scene(root, 300, 250);

        // Configuration de la scène pour la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exemple JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
