package com.classe;

import java.awt.Image;
import java.io.FileInputStream;
import java.util.Scanner;

import javax.swing.ImageIcon;

//Classe qui gère le personnage principal du jeu
public class Personnage {
	
	private int largeur; // largeur de l'image du perso
	private int hauteur; // longueur de l'image du perso
	private int x; // position x du personnage
	private int y; // position y du personnage
	
	private ImageIcon icoPerso; // Gère le fichier image
	private Image imgPerso; // Recupère l'image réelle du perso
	
	private int numPouvoir; // Permet de connaître si le perso à un pouvoir 
	private long tpsPouvoir; // Permet de gérer le temps du pouvoir
	
	private boolean tir; // signifie que le perso a tirer un missile
	private int nbMissile ; // donne le nombre de missile à tirer
	
	private boolean collision; // permet de gerer la collision lorsque le perso est invincible
	private boolean vol; // indique si le personnage vol ( monte )
	
	private int nbPieces; // Nombres de pièces collectées
	
	private double score; // Score du personnage
	
	private int skin; // Permet de connaître si le personnage de base à un skin différent ( achat en boutique )
	
	
	//****CONSTRUCTEURS******
	Personnage(int largeur, int longueur, int x, int y){
		
		this.largeur = largeur;
		this.hauteur = longueur;
		this.x = x;
		this.y = y;
		this.vol = false; // Le personnage est au sol au début
		this.numPouvoir = 0;
		this.tpsPouvoir = 0;
		this.nbPieces =0; 
		this.score = 0;
		this.tir = false;
		this.nbMissile = 0; 
		this.collision = false;
		
		readSkin(); // Affecte le numero de skin au personnage
		
		if (this.skin == 0) {
		    this.icoPerso = new ImageIcon(getClass().getResource("/images/jetpack.png"));
		    
		} else if ( this.skin == 1 ) { // Skin "Royal" après l'achat en boutique
			this.icoPerso = new ImageIcon(getClass().getResource("/images/jetpack_gold.png"));
		}
		
		this.imgPerso = icoPerso.getImage();
	
	}
	
	
	// ****GETTERS****** //
	public int getDy() {return this.y;}		
		
	public int getDx() {return this.x;}
		
	public Image getImage() {return this.imgPerso;}
		
	public int getHauteur() {return this.hauteur;}
		
	public int getLargeur() {return this.largeur;}
		
	public int getNumPouvoir() {return this.numPouvoir;}
		
	public int getNbPieces() {return this.nbPieces;}
	
	public long getTpsPouvoir() {return this.tpsPouvoir;}
	
	public int getScore() {return (int)this.score;}
	
	public int getnbMissile() {return this.nbMissile;}
	
	public boolean getTir() {return this.tir; }
	
	public boolean getCollision() {return this.collision;}
		
	// ****SETTERS****** //
	public void setDy(int y) {this.y = y; }
		
	public void setVol(boolean v) {this.vol = v;}
		
	public void setNumPouvoir (int x) {this.numPouvoir = x;}
		
	public void addPiece() {this.nbPieces += 1;}
	
	public void setTpsPouvoir(long t) {this.tpsPouvoir =t;}
	
	public void setScore(double x) { this.score += x; }
	
	public void setMissile(int x) {this.nbMissile = x; }
	
	public void setTir(boolean t) { this.tir = t; }
	
	public void setCollision(boolean c) { this.collision = c; }
		
	
	// ****METHODES****** //
	public Image anim() { // Permet de créer des animations en changeant l'image représentant le personnage
		
		String str ="";
		ImageIcon ico;
		Image imgretour;
		
		this.tpsPouvoir -= 3; // Réduit de 3ms le temps du pouvoir à chaque animation 
		
		if (this.tpsPouvoir < 0) {
			this.tpsPouvoir = 0; 
			this.numPouvoir =0;
		}
		
		if (this.vol == true ) { // Si le personnage vol -> image du perso avec jetpack en marche
						
			if (numPouvoir == 0 ) { // Pouvoir par défaut
				
				if ( skin == 0 ) {
			       str = "/images/jetpack_feu.png";
				} else if ( skin == 1 ) { // Skin "Royal"
					str = "/images/jetpack_gold_feu.png";
				}
			} else if ( numPouvoir == 1 ) { // Si personnage invincible -> perso avec bulle autour
				str ="/images/jetpack_feu_invincible.png"; 
				
			} else if ( numPouvoir == 2) { // Pouvoir "Aimant" -> personnage gold
				str ="/images/jetpack_feu_aimant.png";
				
			} else if ( numPouvoir == 3) { // Pouvoir "Tir Missile" -> personnage avec lance roquette
			    str ="/images/jetpack_feu_missile.png";
		    }
		} else  { // Si le personnage ne vol pas -> image sans le feu du jetpack	
			if (numPouvoir == 0) {	// Personnage par défaut
				
				if ( skin == 0 ) {
				       str = "/images/jetpack.png";
					} else if ( skin == 1 ) { // Skin "Royal"
						str = "/images/jetpack_gold.png";
					}
				
			} else if ( numPouvoir == 1    ) { // Pouvoir invicible 
				str ="/images/jetpack_invincible.png"; 	
			} else if ( numPouvoir == 2) { // Pouvoir Aimant
				
				str ="/images/jetpack_aimant.png";
				
			} else if ( numPouvoir == 3) { // Pouvoir Tir Missile
			    str ="/images/jetpack_missile.png";
		    }
		}
		
		ico = new ImageIcon(getClass().getResource(str));
		imgretour = ico.getImage();
		
		return imgretour; // Renvoi l'image correspondant bien à l'état du personnage
	}
	
	public void mouvement() { // Modifie la position y du JetPack selon si il vol ou non 
		
		if (this.vol == true ) {
			
			// On test si le jetpack a atteint le plafond
			if (y >= 2) { // Axe des Y vers le bas
				 this.y += -2;  
			}
			
		} else {
			
			if ( y <= 500 ) { //On test si le jetpack n'est pas encore au sol
				this.y += 2;
			}			
		}	
	}
	
	public void readSkin() { // Lit le numéro du skin en mémoire pour que le perso ait la bonne image
		
		String data = "0";
		try {
			
			   Scanner lecture = new Scanner(new FileInputStream("files/Memoire_boutique")); // Lecture des données du fichier mémoire
			   
			    while (lecture.hasNextLine()) {
				    data = lecture.nextLine();
			    }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		
        this.skin = Integer.parseInt(data); // Conversion String-> Int
	}

}