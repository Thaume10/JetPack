package com.classe;


import java.util.ArrayList;

// Classe qui g�re des paquets de pi�ces ( permet de cr�er des designs )
public class PackPiece {

    private Personnage Perso;
    private int x0; // position en haut � gauche du paquet de pi�ce
    private int y0;
    private ArrayList<Piece> PackChoisi; // Liste de pi�ces d'une certaine forme

    //***CONSTRUCTEURS****
    public PackPiece(Personnage p,int X){
    	 this.x0=X;
    	 this.Perso=p;
         
    	 do {
    	    this.y0= (int)(Math.random()*600); // apparition verticale al�atoire
         
         
            int a = (int) (1+ Math.random()*5);

             switch (a) {  // choix al�atoire de la forme des pi�ces
                 case 1 : 
                	 lignepiece();
                     break;
                 case 2 : 
                	 coeurpiece();
                	 break;
                 case 3 :
                	 Tpiece();  // Initiales des auteurs du code
                	 break;
                 case 4 : 
                	 Cpiece();
                	 break;
                 case 5 : 
                	 Mpiece();
                	 break;
             }
    	 } while (Ymax() > 560);
    }

    //***METHODES****
    public void AnimPack(int dx){ // Permet d'animer chaque pi�ce du pack
        for(Piece p : this.PackChoisi){
            p.AnimPiece(dx);
        }
    }

    public void lignepiece(){ // Permet de cr�er une ligne de pi�ce
        ArrayList<Piece> ligne = new ArrayList<>();
        for(int i=0;i<10;i++){
            ligne.add(new Piece(45,45,x0+50*i,y0,Perso));
        }
        this.PackChoisi=ligne;
    }

    public void coeurpiece(){ // Permet de cr�er un paquet de pi�ces en forme de coeur
        ArrayList<Piece> coeur = new ArrayList<Piece>();
        
        coeur.add(new Piece(45, 45, x0, y0,Perso));
        coeur.add(new Piece(45, 45, x0+50, y0,Perso));
        coeur.add(new Piece(45, 45, x0+100, y0+50,Perso)); // milieu
        coeur.add(new Piece(45, 45, x0+150, y0,Perso));
        coeur.add(new Piece(45, 45, x0+200, y0,Perso));
        coeur.add(new Piece(45, 45, x0+250, y0+50,Perso));
        coeur.add(new Piece(45, 45, x0+250, y0+100,Perso));  //max
        coeur.add(new Piece(45, 45, x0+200, y0+150,Perso));
        coeur.add(new Piece(45, 45, x0+150, y0+200,Perso));
        coeur.add(new Piece(45, 45, x0+100, y0+250,Perso));
        coeur.add(new Piece(45, 45, x0+50, y0+200,Perso));
        coeur.add(new Piece(45, 45, x0, y0+150,Perso));
        coeur.add(new Piece(45, 45, x0-50, y0+100,Perso));
        coeur.add(new Piece(45, 45, x0-50, y0+50,Perso));
        
        this.PackChoisi=coeur;
    }
    
    public void Tpiece() { // Permet de cr�er un paquet de pi�ce en forme de T
    	ArrayList<Piece> tab = new ArrayList<Piece>();
    	
    	tab.add(new Piece(45,45,x0, y0, Perso));
    	tab.add(new Piece(45,45,x0+50, y0, Perso));
    	tab.add(new Piece(45,45,x0+100, y0, Perso));
    	tab.add(new Piece(45,45,x0+150, y0, Perso));
    	tab.add(new Piece(45,45,x0+200, y0, Perso));
    	tab.add(new Piece(45,45,x0+100, y0+50, Perso));
    	tab.add(new Piece(45,45,x0+100, y0+100, Perso));
    	tab.add(new Piece(45,45,x0+100, y0+150, Perso));
    	
    	this.PackChoisi = tab; 
    }
    public void Cpiece() { // Permet de cr�er un paquet de pi�ce en forme de C
        ArrayList<Piece> tab = new ArrayList<Piece>();
    	
    	tab.add(new Piece(45,45,x0, y0, Perso));
    	tab.add(new Piece(45,45,x0+50, y0, Perso));
    	tab.add(new Piece(45,45,x0+100, y0, Perso));
    	tab.add(new Piece(45,45,x0+150, y0, Perso));
    	tab.add(new Piece(45,45,x0, y0+50, Perso));
    	tab.add(new Piece(45,45,x0, y0+100, Perso));
    	tab.add(new Piece(45,45,x0, y0+150, Perso));
    	tab.add(new Piece(45,45,x0+50, y0+150, Perso));
    	tab.add(new Piece(45,45,x0+100, y0+150, Perso));
    	tab.add(new Piece(45,45,x0+150, y0+150, Perso));
    	
    	this.PackChoisi = tab; 
    }
    public void Mpiece() { // Permet de cr�er un paquet de pi�ce en forme de M
        ArrayList<Piece> tab = new ArrayList<Piece>();
    	
    	tab.add(new Piece(45,45,x0, y0, Perso));
    	tab.add(new Piece(45,45,x0, y0+50, Perso));
    	tab.add(new Piece(45,45,x0, y0+100, Perso));
    	tab.add(new Piece(45,45,x0, y0+150, Perso));
    	tab.add(new Piece(45,45,x0+50, y0+25, Perso));
    	tab.add(new Piece(45,45,x0+100, y0+75, Perso));
    	tab.add(new Piece(45,45,x0+150, y0+25, Perso));
    	tab.add(new Piece(45,45,x0+200, y0, Perso));
    	tab.add(new Piece(45,45,x0+200, y0+50, Perso));
    	tab.add(new Piece(45,45,x0+200, y0+100, Perso));
    	tab.add(new Piece(45,45,x0+200, y0+150, Perso));
    	
    	this.PackChoisi = tab;
	
    }
    
    public int Ymax() { // Cherche le y max de la liste ( pi�ce la plus haute )
    
    	int ymax = y0; 
    	for (Piece p : PackChoisi) {
    		if ( p.y > ymax ) {
    			ymax = p.y;
    		}
    	}
    	return ymax; 
    
    }
    
    public ArrayList<Piece> getPackChoisi() {return this.PackChoisi;}
}