package Model;

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
                this.isLeaf = Boolean.parseBoolean(finis[i][2]);
                this.toLorgLink = Boolean.parseBoolean(finis[i][3]);
                this.extinct = Boolean.parseBoolean(finis[i][4]);
                this.confidence = Integer.parseInt(finis[i][5]);
                this.phylesis = Integer.parseInt(finis[i][6]);
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


    public String afficheNoeud(){
        String res = "id : " + this.id + "\n";
        res += "name : " + this.name + "\n";
        res += "children : ";
        for(int i = 0; i < this.children.length; i++){
            res += this.children[i] + " ";
        }
        res += "\n";
        res += "isLeaf : " + this.isLeaf + "\n";
        res += "toLorgLink : " + this.toLorgLink + "\n";
        res += "extinct : " + this.extinct + "\n";
        res += "confidence : " + this.confidence + "\n";
        res += "phylesis : " + this.phylesis + "\n";
        return res;
    }

    public String getName(){
        return this.name;
    }

    public int[] getChildren(){
        return this.children;
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
