/**
 * Classe principale du jeu Scrabble
 */
public class Scrabble {
    private int[] nbPointsJet = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10 };
    private Joueur[] joueurs;
    private int numJoueur;
    private Plateau plateau;
    private MEE sac;

    public Scrabble(String[] nomJoueurs){
        int[] sacN = { 9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1 ,2};
        this.plateau = new Plateau();
        this.sac = new MEE(sacN);
        this.joueurs = new Joueur[nomJoueurs.length];

        for (int i = 0; i < nomJoueurs.length; i++) {
            this.joueurs[i] = new Joueur(nomJoueurs[i]);
        }

        this.numJoueur = Ut.randomMinMax(0, joueurs.length - 1);
        boolean Verif = false;
        int oldPlayer = 0;
        int ancienneValue = 1000;
        int[] tab = new int[nomJoueurs.length];
        int numberPlayer = nomJoueurs.length;
        int JoueurPluspetit = 0;

        for(int i = 0 ; i < tab.length ; i++){
            tab[i] = i;
        }

        while(!Verif){
            int FirstnumberPlayer = numberPlayer;
            for(int i = 0; i < numberPlayer ; i++){
                this.joueurs[tab[i]].prendJetons(this.sac, 1);
                while(this.joueurs[tab[i]].GetAscii() == 63){
                    this.joueurs[tab[i]].ReposeJeton(this.sac);
                    this.joueurs[tab[i]].prendJetons(this.sac, 1);
                }
                System.out.print("le joueur " + tab[i] + " a pioché ");
                this.joueurs[tab[i]].PrintChevalet();;

            }
            for(int i = 0 ; i < numberPlayer ; i++){
                if(this.joueurs[tab[i]].GetAscii() < ancienneValue){
                    ancienneValue = this.joueurs[tab[i]].GetAscii();
                    oldPlayer = tab[i];
                }
            }
            for(int i = 0 ; i < numberPlayer ; i++){
                if(this.joueurs[tab[i]].GetAscii() == ancienneValue){
                    oldPlayer = tab[i];
                    JoueurPluspetit++;
                }
                else{
                    for(int j = i ; j < numberPlayer - 1 ; j++){
                        tab[j] = tab[j+1];
                    }
                    tab[numberPlayer - 1] = -1;
                    numberPlayer--;
                }
            }

            for(int i = 0 ; i < FirstnumberPlayer ; i++){
                this.joueurs[i].ReposeJeton(this.sac);
            }
            Verif = true;

            if(numberPlayer != 1){
                Verif = false;
            }
            if(JoueurPluspetit == 1){
                Verif = true;
            }
            JoueurPluspetit = 0;
        }
        this.numJoueur = oldPlayer;
        System.out.println("le joueur qui commence est le joueur " + oldPlayer);
    }

    public String toString(){
        /*résultat : affiche le plateau et le joueurs à l'emplacement this.numJoueur de la liste des joueurs*/
        return ("l'etat du plateau est \n" + this.plateau.toString() + "\nC'est au joueur " + this.joueurs[this.numJoueur].toString());
    }

    public void partie(){
        boolean inPartie = false;
        int SommeTotal = 0;
        int somme = 0;
        int Passer = 0;
        int joueurVidée = -1;
        int resultOfPlay = 0;
        String str = "";

        for(int i = 0 ; i < this.joueurs.length ; i++){
            this.joueurs[i].prendJetons(this.sac, 7);
        }

        while (inPartie == false) {
            System.out.println(this.toString());

            resultOfPlay = this.joueurs[this.numJoueur].joue(this.plateau, this.sac, this.nbPointsJet);
            if(resultOfPlay == -1){
                Passer++;
            }
            else{
                Passer = 0;
            }
            if(Passer == this.joueurs.length){
                inPartie = true;
            }
            if(this.sac.estVide() && this.joueurs[this.numJoueur].nbPointsChevalet(this.nbPointsJet) == 0){
                inPartie = true;
                joueurVidée = this.numJoueur;
            }
            numJoueur++;
            if(this.numJoueur == this.joueurs.length ){
                this.numJoueur = 0;
            }
        }
        for(int i = 0 ; i < this.joueurs.length ; i++){
            somme = this.joueurs[i].nbPointsChevalet(this.nbPointsJet);
            SommeTotal = SommeTotal + somme;
            this.joueurs[i].ajouteScore(-somme);
        }
        if(joueurVidée != -1){
            this.joueurs[joueurVidée].ajouteScore(SommeTotal);
        }
        int ancienneSomme = 0;
        for(int i = 0 ; i < this.joueurs.length ; i++){
            if(this.joueurs[i].getScore() >= ancienneSomme){
                ancienneSomme = this.joueurs[i].getScore();
            }
        }
        System.out.println("Le ou Les gagnants sont :");
        for(int i = 0 ; i < this.joueurs.length ; i++){
            if(this.joueurs[i].getScore() == ancienneSomme){
                str = this.joueurs[i].toString();
                System.out.println(str);
            }
        }
        if(ancienneSomme == 0){
            for(int i = 0 ; i < this.joueurs.length ; i++){
                str = this.joueurs[i].toString();
                System.out.println(str);
            }
        }
    }
}