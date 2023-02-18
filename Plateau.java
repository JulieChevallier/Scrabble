import java.util.zip.ZipEntry;

/**
 * Classe qui permet de gérer le plateau de jeu
 * Le plateau est un tableau de Case
 * Le plateau est initialisé à partir d'un tableau de 15 cases sur 15 cases, il est donc symétrique
 * Chaque case est initialisée avec une couleur
 */
public class Plateau {
    private Case [][] g;
    Dico VerifMot;

    public Plateau(){
        int[][] plateau = {
                { 0 , 0 , 5},
                { 1 , 1 , 4},
                { 2 , 2 , 4},
                { 3 , 3 , 4},
                { 4 , 4 , 4},
                { 5 , 5 , 3},
                { 6 , 6 , 2},
                { 7 , 7 , 4},
                { 0 , 3 , 2},
                { 0 , 7 , 5},
                { 3 , 7 , 2},
                { 2 , 6 , 2},
                { 1 , 5 , 3},
        };

        this.VerifMot = new Dico();
        this.g = new Case[15][15];

        for(int i = 0; i < 13 ; i++){
            int x = plateau[i][0];
            int y = plateau[i][1];
            int couleur = plateau[i][2];
            this.g[x][y] = new Case(couleur);
            this.g[x][14-y] = new Case(couleur);
            this.g[14-x][y] = new Case(couleur);
            this.g[14-x][14-y] = new Case(couleur);

            this.g[y][x] = new Case(couleur);
            this.g[y][14-x] = new Case(couleur);
            this.g[14-y][x] = new Case(couleur);
            this.g[14-y][14-x] = new Case(couleur);
        }

        for(int i = 0 ; i < 15 ; i++){
            for(int j = 0 ; j < 15 ; j++){
                if(this.g[i][j] == null){
                    this.g[i][j] = new Case(1);
                }
            }
        }
        this.VerifMot.Initialise();
    }

    public String toString(){
        String str = "";
        char A = 'A'; // coordonnée ligne
        str = str + "   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15\n"; // affichage coordonnée colonne

        for(int i = 0 ; i < 15 ; i++){
            str = str + " "; // espace coordonnée colonne

            for(int j = 0 ; j < 61 ; j++){
                str = str + "-"; // ligne séparation

            }

            str = str + '\n';
            str = str + A; // affichage coordonnée ligne

            for(int j = 0 ; j < 15 ; j++){
                str = str + "| ";

                if(this.g[i][j].estRecouverte()){ //vérifie si la case est recouverte
                    if(this.g[i][j].getJoker() == true){ // cas joker
                        str = str + "?" + " ";
                    }
                    else{
                        str = str + this.g[i][j].getLetter() + " "; // cas lettre
                    }
                }
                else if(this.g[i][j].getColor() > 1 && j < 15){
                    str = str + this.g[i][j].getColor() + " "; // cas couleur != grise
                }
                else{
                    str = str + "  "; // cas couleur est grise
                }
            }
            str = str + "|"; // fin de ligne
            str = str + "\n";
            A++; // incrémentation coordonnée ligne

        }

        str = str + " ";
        for(int j = 0 ; j < 61 ; j++){ // dernière ligne
            str = str + "-";

        }
        return str; // retourne le plateau
    }

    private boolean isVide(Case[][] g){
        for(int i = 0 ; i < 15 ; i++){
            for(int j = 0 ; j < 15 ; j++){
                if(g[i][j].estRecouverte() == true){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean placementValide(String mot, String Coord, char sens, MEE e){
        if(Coord.length() > 3 || Coord.length() < 2){
            return false;
        }
        int numCol = 0;
        int numLig = 0;
        if(Coord.length() == 2){
            numLig = Coord.charAt(0) - 65;
            numCol = Coord.charAt(1) - 49;
        }
        else{
            numLig = Coord.charAt(0) - 65;
            numCol = 10;
            numCol += Coord.charAt(2) - 49;
        }
        String str = "";
        int Joker = 0;
        boolean Verif = false;
        boolean Verif2 = false; // les deux boolean serve a verifier durant tout le programme
        MEE tab = new MEE(e);
        if(sens != 'h' && sens != 'v'){
            return false;
        }
        if(isVide(this.g) == true){
            if(mot.length() < 2){
                return false;
            }
            if(sens == 'h'){
                for(int i = numCol ; i < numCol + mot.length() ; i++){
                    if(i == 7 && numLig == 7){
                        Verif = true;
                    }
                }

                if(Verif == false){
                    return false;
                }


            }
            else{

                for(int i = numLig ; i < numLig + mot.length() ; i++){
                    if(i == 7 && numCol == 7){
                        Verif = true;
                    }
                }

                if(Verif == false){
                    return false;
                }

            }

            Verif = false;
            for(int i = 0 ; i < mot.length() ; i++){

                char C = mot.charAt(i);

                if(tab.retire(C - 65) == false){ //la lettre a etant 65 dans la table ascii on la ramene a 0
                    if(tab.retire(26) == true){
                        Joker++;
                    }
                    else{
                        return false;
                    }
                }

            }


        }
        else{
            if(sens == 'h'){

                if(numCol + mot.length() > 15){
                    return false;
                }

                int ToucheAvant = 1;
                while(this.g[numLig][numCol - ToucheAvant].estRecouverte() == true && this.g[numLig][numCol - ToucheAvant] != null){
                    ToucheAvant++;
                }
                ToucheAvant--;
                if(ToucheAvant > 0 ){
                    Verif = true;
                }
                for(int i = numCol - ToucheAvant ; i < numCol ; i++){
                    str = str + this.g[numLig][i].getLetter();
                }
                str = str + mot;
                int ToucheApres = mot.length();
                while(this.g[numLig][numCol+ToucheApres].estRecouverte() == true && this.g[numLig][numCol+ToucheApres] != null){
                    str = str + this.g[numLig][numCol + ToucheApres].getLetter();
                    ToucheApres++;
                }
                if(ToucheApres > mot.length()){
                    Verif = true;
                }
                if(str.length() > mot.length()){
                    if(this.VerifMot.InDictionnary(str) == false){
                        return false;
                    }
                }


                int l = 0;

                for(int i = numCol ; i < numCol + mot.length() ; i++){
                    char c = mot.charAt(l);
                    ToucheAvant = 1;
                    ToucheApres = 1;
                    str = "";
                    if(this.g[numLig][i].estRecouverte() == true){
                        Verif = true;
                        if(this.g[numLig][i].getLetter() != c){
                            return false;
                        }


                    }
                    else if(tab.retire(c - 65) != true){
                        if(tab.retire(26) == true){
                            Joker++;
                        }
                        else{
                            return false;
                        }
                    }
                    if( (this.g[numLig - 1][i] != null && this.g[numLig - 1][i].estRecouverte()) || (this.g[numLig + 1][i] != null && this.g[numLig + 1][i].estRecouverte())){
                        while(this.g[numLig - ToucheAvant][i].estRecouverte() == true && this.g[numLig - ToucheAvant][i] != null){
                            ToucheAvant++;
                        }
                        if(ToucheAvant != 0){
                            ToucheAvant--;
                        }
                        while(this.g[numLig + ToucheApres][i].estRecouverte() == true && this.g[numLig + ToucheApres][i] != null ){
                            ToucheApres++;
                        }
                        for(int j = numLig - ToucheAvant ; j < numLig + ToucheApres ; j++){
                            if(j == numLig){
                                str = str + mot.charAt(i-numCol);
                            }
                            else{
                                str = str + this.g[j][i].getLetter();
                            }
                        }
                        if(str.length() > 0){
                            if(this.VerifMot.InDictionnary(str) == false){
                                return false;
                            }
                        }

                    }
                    if(this.g[numLig][i].estRecouverte() == false){
                        Verif2 = true;
                    }
                    l++;
                }

                if(Verif == false || Verif2 == false){
                    return false;
                }
            }
            else{
                if(numLig + mot.length() > 15){
                    return false;
                }
                int ToucheAvant = 1;
                while(this.g[numLig - ToucheAvant][numCol].estRecouverte() == true && this.g[numLig - ToucheAvant][numCol] != null){
                    ToucheAvant++;
                    Verif = true;
                }
                ToucheAvant--;
                for(int i = numLig - ToucheAvant ; i < numLig ; i++){
                    str = str + this.g[i][numCol].getLetter();
                }
                str = str + mot;
                int ToucheApres = mot.length();
                while(this.g[numLig + ToucheApres][numCol].estRecouverte() == true && this.g[numLig + ToucheApres][numCol] != null){
                    Verif = true;
                    str = str + this.g[numLig + ToucheApres][numCol].getLetter();
                    ToucheApres++;
                }
                if(this.VerifMot.InDictionnary(str) == false){
                    return false;
                }
                int l = 0;
                str = "";
                ToucheApres = 1;
                ToucheAvant = 1;
                for(int i = numLig ; i < numLig + mot.length() ; i++){
                    char c2 = mot.charAt(l);
                    ToucheAvant = 1;
                    ToucheApres = 1;
                    if(this.g[i][numCol].estRecouverte() == true){
                        Verif = true;
                        if(this.g[i][numCol].getLetter() != c2){
                            return false;
                        }

                    }
                    else if(tab.retire(c2 - 65) != true){
                        if(tab.retire(26) == true){
                            Joker++;
                        }
                        else{
                            return false;
                        }
                    }
                    if( (this.g[i][numCol - 1] != null && this.g[i][numCol - 1 ].estRecouverte()) || (this.g[i][numCol - 1] != null && this.g[i][numCol - 1].estRecouverte())){
                        while(this.g[i][numCol - ToucheAvant].estRecouverte() == true && this.g[i][numCol - ToucheAvant] != null){
                            ToucheAvant++;
                        }
                        if(ToucheAvant != 0){
                            ToucheAvant--;
                        }
                        while(this.g[i][numCol + ToucheApres].estRecouverte() == true && this.g[i][numCol + ToucheApres] != null ){
                            ToucheApres++;
                        }
                        for(int j = numCol - ToucheAvant ; j < numCol + ToucheApres ; j++){
                            if(j == numCol){
                                str = str + mot.charAt(i-numLig);
                            }
                            else{
                                str = str + this.g[j][i].getLetter();
                            }
                        }
                        if(str.length() > 0){
                            if(this.VerifMot.InDictionnary(str) == false){
                                return false;
                            }
                        }

                    }
                    if(this.g[i][numCol].estRecouverte() == false){
                        Verif2 = true;
                    }
                    l++;

                }
                if(Verif == false || Verif2 == false){
                    return false;
                }


            }
        }
        if(Joker > e.NbJetons(26)){
            return false;
        }
        return true;
    }

    public int nbPointsPlacement(String mot, int numLig, int numCol, char sens, int[] nbPointJet)
    {
        int multiplicateur = 1;
        int sommeMot = 0;
        if(sens == 'h'){
            int l = 0;
            for(int i = numCol ; i < numCol + mot.length() ; i++){
                char c = mot.charAt(l);
                int ToucheAvant = 1;
                int ToucheApres = 1;
                if(this.g[numLig][i].estRecouverte() == false){
                    while(this.g[numLig - ToucheAvant][i].estRecouverte() == true && this.g[numLig - ToucheAvant][i] != null){
                        if(this.g[numLig - ToucheAvant][i].getJoker() == false){
                            sommeMot += nbPointJet[this.g[numLig - ToucheAvant][i].getLetter() - 65];
                        }
                        ToucheAvant++;
                    }
                    while(this.g[numLig + ToucheApres][i].estRecouverte() == true && this.g[numLig + ToucheApres][i] != null ){
                        if(this.g[numLig + ToucheApres][i].getJoker() == false){
                            sommeMot += this.g[i][numCol + ToucheApres].getLetter();
                        }
                        ToucheApres++;
                    }
                }
                if(this.g[numLig][i].estRecouverte() == true){
                    if(this.g[numLig][i].getJoker() == true){
                    }
                    else{
                        sommeMot += nbPointJet[c-65];
                    }
                }
                else{
                    if(this.g[numLig][i].getJoker() == true){
                        if(this.g[numLig][i].getColor() == 4){
                            multiplicateur *= 2;
                        }
                        else if(this.g[numLig][i].getColor() == 5){
                            multiplicateur *= 3;
                        }
                    }
                    else if(this.g[numLig][i].getColor() == 2){
                        sommeMot += nbPointJet[c-65] * 2;
                    }
                    else if(this.g[numLig][i].getColor() == 3){
                        sommeMot += nbPointJet[c-65] * 3;
                    }
                    else if(this.g[numLig][i].getColor() == 4){
                        multiplicateur *= 2;
                    }
                    else if(this.g[numLig][i].getColor() == 5){
                        multiplicateur  *= 3;
                    }
                    else{
                        sommeMot += nbPointJet[c-65];
                    }
                }

                l++;
            }
            sommeMot = sommeMot * multiplicateur;
        }
        else{
            int l = 0;
            for(int i = numLig ; i < numLig + mot.length() ; i++){
                char c = mot.charAt(l);
                int ToucheAvant = 1;
                int ToucheApres = 1;
                if(this.g[i][numCol].estRecouverte() == false){
                    while(this.g[i][numCol - ToucheAvant].estRecouverte() == true && this.g[i][numCol - ToucheAvant] != null){
                        if(this.g[i][numCol - ToucheAvant].getJoker() == false){
                            sommeMot += nbPointJet[this.g[i][numCol - ToucheAvant].getLetter() - 65];
                        }
                        ToucheAvant++;
                    }
                    while(this.g[i][numCol + ToucheApres].estRecouverte() == true && this.g[i][numCol + ToucheApres] != null ){
                        if(this.g[i][numCol+ToucheApres].getJoker() == false){
                            sommeMot += this.g[i][numCol + ToucheApres].getLetter();
                        }
                        ToucheApres++;
                    }
                }
                if(this.g[i][numCol].estRecouverte() == true){
                    if(this.g[i][numCol].getJoker() == true){
                    }
                    else{
                        sommeMot += nbPointJet[c-65];
                    }
                }
                else{
                    if(this.g[i][numCol].getJoker() == true){
                        if(this.g[i][numCol].getColor() == 4){
                            multiplicateur *= 2;
                        }
                        else if(this.g[i][numCol].getColor() == 5){
                            multiplicateur *= 3;
                        }
                    }
                    else if(this.g[i][numCol].getColor() == 2){
                        sommeMot += nbPointJet[c-65] * 2;
                    }
                    else if(this.g[i][numCol].getColor() == 3){
                        sommeMot += nbPointJet[c-65] * 3;
                    }
                    else if(this.g[i][numCol].getColor() == 4){
                        sommeMot += nbPointJet[c - 65];
                        multiplicateur *= 2;
                    }
                    else if(this.g[i][numCol].getColor() == 5){
                        sommeMot += nbPointJet[c - 65];
                        multiplicateur  *= 3;
                    }
                    else{
                        sommeMot += nbPointJet[c-65];
                    }
                }

                l++;
            }
        }

        return sommeMot;
    }

    public int Place(String mot , int numLig, int numCol, char sens, MEE e)
    {
        MEE tab = new MEE(e);
        int Retire = 0;
        if(sens == 'h'){
            int l = 0;
            for(int i = numCol ; i < numCol + mot.length() ; i++){
                char c = mot.charAt(l);
                if(this.g[numLig][i].estRecouverte() == false){
                    if(tab.retire(c - 65) == false){
                        this.g[numLig][i].setLetter(c);
                        this.g[numLig][i].setjoker();
                        e.retire(26);
                    }
                    e.retire(c - 65);
                    this.g[numLig][i].setLetter(c);
                    Retire++;
                }
                l++;

            }
        }
        else{
            int l = 0;
            for(int i = numLig ; i < numLig + mot.length() ; i++){
                char c = mot.charAt(l);
                if(this.g[i][numCol].estRecouverte() == false){
                    if(tab.retire(c - 65) == false){
                        this.g[i][numCol].setLetter(c);
                        this.g[i][numCol].setjoker();
                        e.retire(26);
                    }
                    e.retire(c - 65);
                    this.g[i][numCol].setLetter(c);
                    Retire++;
                }
                l++;

            }
        }
        return Retire;
    }
}
