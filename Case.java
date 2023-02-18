/**
 * Classe qui contient les informations d'une case
 * Une case a une couleur, une lettre et un booléen qui indique si la case est recouverte
 * La couleur est un entier entre 0 et 5
 * La lettre est un caractère entre A et Z initialisé à ' '
 */
public class Case {
    private int color;
    private boolean covered;
    private char Letter;
    private boolean joker;

    /**
     * Constructeur de la classe Case
     * @param color : couleur de la case
     */
    public Case(int color){
        this.color = color;
        this.covered = false;
        this.joker = false;
    }


    public void setjoker(){
        this.joker = true;
    }

    public boolean getJoker(){
        return this.joker;
    }

    public int getColor(){
        return this.color;
    }

    public char getLetter(){
        return this.Letter;
    }

    public void setLetter(char let){
        this.Letter = let;
        this.covered = true;
    }

    /**
     * Méthode qui permet de savoir si la case est recouverte
     * @return true si la case est recouverte, false sinon
     */
    public boolean estRecouverte(){
        if(this.covered == true){
            return true;
        }
        return false;
    }

    public String toString(){
        String str = "";
        str = str + this.color;
        str = str + this.covered;
        str = str + this.Letter;
        return str;
    }
}
