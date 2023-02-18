import java.io.*;
import java.util.HashSet;

/**
 * Classe qui permet de gérer le dictionnaire
 * Le dictionnaire est un HashSet de String
 * Le dictionnaire est initialisé à partir d'un fichier texte 'dicoReference.txt'
 */
public class Dico {
    public HashSet<String> dico;

    /**
     * Constructeur de la classe Dico
     */
    public Dico(){
        this.dico = new HashSet<String>();
    }

    /**
     * Méthode qui permet d'initialiser le dictionnaire
     */
    public void Initialise(){
        try{
            File file = new File("dicoReference.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            for(int i = 0 ; i < 411431 ; i++){
                line = br.readLine();

                this.dico.add(line);
            }
            fr.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui permet de savoir si un mot est dans le dictionnaire
     * @param str : mot à tester
     * @return true si le mot est dans le dictionnaire, false sinon
     */
    public boolean InDictionnary(String str){
        System.out.println(str);
        if(this.dico.contains(str) == true){
            System.out.println("Est un mot valide");
            return true;
        }
        else{
            System.out.println("N'est pas un mot valide");
            return false;
        }
    }
}