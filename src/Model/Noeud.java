package Model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Noeud {
    private int id;
    private String name;
    private int[] children;
    private boolean isLeaf;
    private boolean toLorgLink;
    private boolean extinct;
    private int confidence;
    private int phylesis;

    public Noeud(String PathfichierNode, String PathfichierLink, int id) {
        this.id = id;
        String[][] finis = CSVReaderExample.readCSV(PathfichierNode);
        String[][] finis2 = CSVReaderExample.readCSV(PathfichierLink);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        for (int i = 0; i < finis.length; i++) {
            if (finis[i][0].equals(Integer.toString(id))) {
                this.name = finis[i][1];
                this.isLeaf = !Boolean.parseBoolean(finis[i][3]);
                if(finis[i][4]=="0"){
                    this.toLorgLink = false;
                }else{
                    this.toLorgLink = true;
                }
                this.extinct = Boolean.parseBoolean(finis[i][5]);
                this.confidence = Integer.parseInt(finis[i][6]);
                this.phylesis = Integer.parseInt(finis[i][7]);
                break;
            }
        }
        int enfants = 0;
        for (int i = 0; i < finis2.length; i++) {
            if (finis2[i][0].equals(Integer.toString(id))) {
                //ajoute l'enfant dans le tableau puis reviens sur la boucle
                //pour voir si il y a un autre enfant
                enfants++;
            }
        }
        this.children = new int[enfants];
        for (int j = 0; j < finis2.length; j++) {
                if (finis2[j][0].equals(Integer.toString(id))) {
                    this.children[enfants-1] = Integer.parseInt(finis2[j][1]);
                    enfants--;
                }
        }
    }


    public VBox afficheNoeud() {
        VBox vbox = new VBox();

        // Affichage de l'id
        Text idText = new Text("  id : " + this.id);
        vbox.getChildren().add(idText);

        // Affichage du nom
        Text nameText = new Text("  name : " + this.name);
        vbox.getChildren().add(nameText);

        // Affichage des enfants
        StringBuilder childrenString = new StringBuilder("  children : ");
        for (int i = 0; i < this.children.length; i++) {
            childrenString.append(this.children[i]).append(" ");
        }
        Text childrenText = new Text(childrenString.toString());
        vbox.getChildren().add(childrenText);

        // Affichage de l'état de feuille
        Text isLeafText = new Text("  isLeaf : " + this.isLeaf);
        vbox.getChildren().add(isLeafText);

        Text istoLorgLinkText = new Text("  toLorgLink : " + this.toLorgLink);
        vbox.getChildren().add(istoLorgLinkText);

        // Création du lien
        String toLorgLink = String.format("http://tolweb.org/%s/%d", this.name, this.id);
        Hyperlink hyperlink = new Hyperlink(toLorgLink);

        // Ajout d'un EventHandler pour ouvrir le lien dans un navigateur externe lorsque le lien est cliqué
        hyperlink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI(toLorgLink));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        // Affichage du lien
        Text toLorgLinkText = new Text("  toLorgLink : ");
        HBox linkBox = new HBox(new Text("  toLorgLink : "), hyperlink);
        vbox.getChildren().add(linkBox);

        // Affichage de l'état extinct
        Text extinctText = new Text("  extinct : " + this.extinct);
        vbox.getChildren().add(extinctText);

        // Affichage du niveau de confiance
        Text confidenceText = new Text("  confidence : " + this.confidence);
        vbox.getChildren().add(confidenceText);

        // Affichage du niveau de phylésie
        Text phylesisText = new Text("  phylesis : " + this.phylesis);
        vbox.getChildren().add(phylesisText);

        return vbox;
    }

    public String getName(){
        return this.name;
    }

    public int[] getChildren(){
        return this.children;
    }

    public boolean isLeaf(){
        return children.length == 0;
    }

    public boolean isToLorgLink(){
        return this.toLorgLink;
    }
    public boolean isEmpty(){
        return this.children.length == 0;
    }

    public List<Noeud> CreateChildren(){
        List<Noeud> enfants = new ArrayList<>();
        for(int i = 0; i < this.children.length; i++){
            enfants.add(new Noeud("C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\treeoflife_nodes_simplified.csv", "C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\treeoflife_links_simplified.csv", this.children[i]));
        }
        return enfants;
    }
}
