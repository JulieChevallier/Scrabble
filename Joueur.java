/**
 * Classe qui permet de gérer les joueurs
 * Un joueur a un nom, un chevalet et un score
 * Le score est un entier initialisé à 0
 * Le chevalet est un MEE initialisé à 27 lettres affichées en ASCII
 * Le nom est une chaine de caractère
 */
public class Joueur {
    private String nom;
    private MEE chevalet;
    private int score;

    /**
     * Constructeur de la classe Joueur
     * @param Nom : nom du joueur
     */
    public Joueur(String Nom){
        this.nom = Nom;
        this.score = 0;
        this.chevalet = new MEE(27);
    }

    public String toString(){
        String str = this.nom + ", son chevalet est " + this.chevalet.toString() + ", son score est " + this.score;
        return str;
    }

    public void PrintChevalet(){
        this.chevalet.PrintMEE();
    }

    /**
     *
     * @return le code ASCII de la première lettre du chevalet
     */
    public int GetAscii(){
        String str = this.chevalet.toString();
        char c = str.charAt(0);
        return c;
    }

    /**
     * Méthode qui permet reposer un jeton dans le sac
     * @param sac
     */
    public void ReposeJeton(MEE sac){
        this.chevalet.transfereAleat(sac, 1);
    }

    /**
     * Méthode qui retourne le nombre de points du chevalet
     * @param nbPointsJet : tableau qui contient le nombre de points de chaque jeton
     * @return le nombre de points du chevalet
     */
    public int nbPointsChevalet (int[] nbPointsJet){
        int Somme = this.chevalet.sommeValeurs(nbPointsJet);
        return Somme;
    }

    /**
     * Méthode qui permet d'ajouter des points au score du joueur
     * @param nb : nombre de points à ajouter
     */
    public void ajouteScore(int nb){
        this.score += nb;
    }

    public int getScore(){
        return this.score;
    }

    /**
     * Méthode qui permet de prendre des jetons dans le sac
     * @param s : sac
     * @param nbJetons : nombre de jetons à prendre
     */
    public void prendJetons(MEE s, int nbJetons){
        s.transfereAleat(this.chevalet, nbJetons);
    }

    /**
     * Méthode qui permet au Joueur de jouer son tour
     */
    public int joue(Plateau p,MEE s,int[] nbPointsJet){
        int Choix = 0;
        System.out.println("Choisir une action :\n1. Échanger un jeton\n2. Passer son tour\n3. Placer un mot\n");
        Choix = Ut.saisirEntier();
        while(Choix < 1 || Choix > 3){
            System.out.println("Entrée invalide");
            Choix = Ut.saisirEntier();
        }
        if(Choix == 1){
            this.echangeJetons(s);
        }
        else if(Choix == 2){
            return -1;
        }
        else{
            if(this.joueMot(p, s, nbPointsJet) == false){
                this.joue(p, s, nbPointsJet);
            }
        }

        if(this.chevalet.estVide() == true){
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui permet au joueur d'échanger des jetons en sa possession uniquement
     * @param sac
     */
    public void echangeJetons(MEE sac){
        System.out.println("Insérer les jetons que vous souhaitez échanger (sans espaces, en majuscules et que vous possédez)");
        String str = "";
        str = Ut.saisirChaine();
        while(this.estCorrectPourEchange(str) == false){
            System.out.println("Mauvaise saisie des jetons, réinsérer les jetons (sans espaces, en majuscules et que vous possédez)");
            str = Ut.saisirChaine();
        }
        this.echangeJetonsAux(sac, str);
    }

    /**
     * Méthode qui permet de vérifier que les jetons que le joueur souhaite échanger sont bien dans son chevalet
     * @param mot : jetons que le joueur souhaite échanger
     * @return true si les jetons sont bien dans le chevalet, false sinon
     */
    public boolean estCorrectPourEchange(String mot){
        MEE tab = new MEE(this.chevalet);
        for(int i = 0; i < mot.length() ; i++){
            char c = mot.charAt(i);
            if(c < 65 || c > 91){
                System.out.println("Erreur dans la saisie des jetons");
                return false;
            }
            if(tab.retire(c - 65) == false){
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui permet de transférer les jetons du chevalet vers le sac
     * @param sac
     * @param ensJetons : jetons à transférer
     */
    public void echangeJetonsAux(MEE sac, String ensJetons){
        char c = 0;
        sac.transfereAleat(this.chevalet, ensJetons.length());
        for(int i = 0 ; i < ensJetons.length() ; i++){
            c = ensJetons.charAt(i);
            this.chevalet.transfere(sac, c - 65);
        }
    }

    /**
     * Méthode qui permet au joueur de jouer un mot
     * @param p : plateau
     * @param s : sac
     * @param nbPointsJet : tableau qui contient le nombre de points de chaque jeton
     * @return true si le mot est valide, false sinon
     */
    public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet){
        Dico Dictionnaire = new Dico();
        Dictionnaire.Initialise();
        int CapeloDico = 0;
        char sens = 0;
        String str = "";
        String Coord = "";
        System.out.println("Saisir un mot valide");
        str = Ut.saisirChaine();

        if(Dictionnaire.InDictionnary(str) == false){
            return false;
        }

        System.out.println("Saisir la position ligne(A,B,C,..) et la colonne(1,2,3,..) sens h.Horizontale ou v.Verticale");
        Coord = Ut.saisirChaine();
        sens = Ut.saisirCaractere();

        if(p.placementValide(str, Coord, sens, this.chevalet) == false){
            return false;
        }
        this.joueMotAux(p, s, nbPointsJet, str, Coord, sens);
        return true;
    }

    /**
     * Méthode qui permet de jouer un mot
     * @param p : plateau
     * @param s : sac
     * @param nbPointsJet : tableau qui contient le nombre de points de chaque jeton
     * @param mot : mot à jouer
     * @param Coord : coordonnées de la case de départ
     * @param sens : sens du mot
     */
    public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, String Coord, char sens){
        int numLig = 0;
        int numCol = 0;

        if(Coord.length() == 2){
            numLig = Coord.charAt(0) - 65;
            numCol = Coord.charAt(1) - 49;
        }
        else{
            numLig = Coord.charAt(0) - 65;
            numCol = 10;
            numCol += Coord.charAt(2) - 49;
        }

        int Piocher = 0;
        int somme = 0;
        somme = p.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet);
        Piocher = p.Place(mot, numLig, numCol, sens, this.chevalet);

        if(Piocher == 7){
            somme = somme + 50;
        }
        this.prendJetons(s, Piocher);

        this.score = this.score + somme;
    }
}