public class Main {
    public static void main(String[] args) {
        int nombreJoueur = 0;
        System.out.println("Sélectionner le nombre de joueurs supérieurs à 0 et inférieurs à 14");
        nombreJoueur =  Ut.saisirEntier();

        while(nombreJoueur < 1 || nombreJoueur > 14){
            System.out.println("Saisir un nombre de joueurs valables supérieurs à 0 et inférieurs à 14");
            nombreJoueur = Ut.saisirEntier();
        }
        String[] nomJoueur = new String[nombreJoueur];
        for(int i = 0 ; i < nombreJoueur ; i++){
            System.out.println("Saisir le nom du joueur " + i);
            nomJoueur[i] = Ut.saisirChaine();
        }
        Scrabble scrabble = new Scrabble(nomJoueur);
        scrabble.partie();
    }
}
