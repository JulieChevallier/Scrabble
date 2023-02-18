/**
 * Classe qui stocke les lettres du sac
 * Un sac est un MEE
 */
public class MEE {
    private int [] tabFreq;//tab nb exemplaire
    private int nbTotEx;//nb total exemplaire

    /**
     * Constructeur de la classe MEE à partir d'un nombre de lettres
     * @param max : nombre de lettres
     */
    public MEE (int max){
        this.tabFreq = new int[max];
        this.nbTotEx = 0;
    }

    /**
     * Constructeur de la classe MEE à partir d'un tableau de fréquence
     * @param tab : tableau de fréquence
     */
    public MEE (int[] tab){
        int somme = 0;
        this.tabFreq = new int[tab.length];
        for(int i = 0 ; i < tab.length ; i++){
            somme = somme + tab[i];
        }
        this.nbTotEx = somme;

        for (int i = 0 ; i < tab.length ; i++) {
            this.tabFreq[i] = tab[i];
        }
    }

    /**
     * Constructeur de la classe MEE à partir d'un autre MEE
     * @param e : MEE
     */
    public MEE (MEE e){
        this.tabFreq = new int[e.tabFreq.length];
        this.nbTotEx = e.nbTotEx;

        for (int i = 0 ; i < e.tabFreq.length ; i++) {
            this.tabFreq[i] = e.tabFreq[i];
        }
    }

    /**
     * Méthode qui permet de paramétrer l'affichage du MEE
     */
    public String toString(){
        String str = "";
        for (int i = 0 ; i < this.tabFreq.length ; i++) {
            if (this.tabFreq[i] > 0) {
                if(i == 26){
                    for(int j = 0; j < this.tabFreq[i] ; j++){
                        str = str + "?";
                    }
                }
                else{
                    for(int j = 0; j < this.tabFreq[i]; j++) {
                        str = str + (char) (i + 65);
                    }
                }
            }
        }
        str = str + "\n";
        return str;
    }

    /**
     * Méthode qui permet d'afficher le MEE
     */
    public void PrintMEE(){
        String str = "";
        for (int i = 0 ; i < this.tabFreq.length ; i++) {
            if (this.tabFreq[i] > 0) {
                if(i == 26){
                    for(int j = 0; j < this.tabFreq[i] ; j++){
                        str = str + "?";
                    }
                }
                else{
                    for(int j = 0; j < this.tabFreq[i]; j++) {
                        str = str + (char) (i + 65);
                    }
                }
            }
        }
        System.out.println(str);
    }

    /**
     * Méthode qui permet de savoir si le MEE est vide
     * @return true si le MEE est vide, false sinon
     */
    public boolean estVide (){
        if (this.nbTotEx == 0) {

            return true;
        }
        else {

            return false;
        }
    }

    /**
     * Méthode qui permet d'ajouter un exemplaire d'une lettre
     * @param i : lettre à ajouter
     */
    public void ajoute(int i){
        this.tabFreq[i]++;
        this.nbTotEx += 1;
    }

    /**
     * Méthode qui permet de retirer un exemplaire d'une lettre, si elle existe
     * @param i : lettre à retirer
     * @return true si la lettre a été retirée, false sinon
     */
    public boolean retire(int i){
        if(this.tabFreq[i] == 0){
            return false;
        }
        else{
            this.tabFreq[i] -= 1;
            this.nbTotEx = this.nbTotEx - 1;
            return true;
        }
    }

    /**
     * Méthode qui permet de retirer un exemplaire d'une lettre aléatoire, si elle existe
     * @return la lettre retirée
     */
    public int retireAleat(){
        int a = Ut.randomMinMax(0,26);

        if (retire(a)){
            return this.tabFreq[a];
        }
        return this.tabFreq[a];
    }

    /**
     * Méthode qui permet de transférer un exemplaire d'une lettre d'un MEE à un autre, si elle existe
     * @param e : MEE de destination
     * @param i : lettre à transférer
     * @return true si la lettre a été transférée, false sinon
     */
    public boolean transfere(MEE e, int i){
        if(this.tabFreq[i] > 0){
            this.retire(i);
            e.ajoute(i);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Méthode qui permet de transférer un exemplaire d'une lettre aléatoire d'un MEE à un autre, si elle existe
     * @param e : MEE de destination
     * @return la lettre transférée
     */
    public int transfereAleat(MEE e, int k){
        int nbE = 0;

        while(!this.estVide() && nbE < k ){
            int random = Ut.randomMinMax(0, 26);

            if(this.transfere(e, random)){
                nbE++;
            }
        }
        return nbE;
    }

    /**
     * Méthode qui permet de renvoyer la somme des valeurs d'un tableau de fréquence
     * @param v : tableau de fréquence
     * @return la somme des valeurs du tableau
     */
    public int sommeValeurs (int[] v){
        int re = 0;

        for (int i = 0; i < this.tabFreq.length-1 ; i++){
            re += this.tabFreq[i] * v[i] ;
        }
        return re;
    }

    /**
     * Méthode qui permet de renvoyer le nombre d'exemplaires d'une lettre
     * @param i : lettre
     * @return le nombre d'exemplaires de la lettre
     */
    public int NbJetons(int i){
        return this.tabFreq[i];
    }
}