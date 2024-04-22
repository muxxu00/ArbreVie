import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderExample {
    //ici faire une vrai fonctino qui prend en parametre le path du fichier
    //et qui retourne un tableau de string
    public static String[][] readCSV(String path) {
        String csvFile = path;
        String line = "";
        String csvSeparator = ",";
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // Divisez la ligne en valeurs individuelles en utilisant la virgule comme séparateur
                String[] data = line.split(csvSeparator);
                dataList.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] dataArray = dataList.toArray(new String[dataList.size()][]);
        return dataArray;
    }
    /*public static void main(String[] args) {
        // Chemin du fichier CSV à lire
        String path = "C:\\Users\\Mathias\\Desktop\\PCII\\ArbreVie\\ressources\\treeoflife_nodes_simplified.csv";
        String[][] finis = readCSV(path);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        for(int i = 0; i < finis.length; i++){
            for(int j = 0; j < finis[i].length; j++){
                System.out.print(finis[i][j] + " ");
            }
            System.out.println();
        }
    }*/
}
