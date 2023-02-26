package com.classe;


import javax.swing.ImageIcon;

// Classe fille d'obstacle -> le pouvoir est un bonus pour le personnage
public class Pouvoir extends Obstacle {
	
	// Deux fichiers image l'animation
	private ImageIcon ico_pouvoir1; // Gère le fichier n°1 image du pouvoir 
	private ImageIcon ico_pouvoir2; // Gère le fichier n°2 image du pouvoir 
	
	//***CONSTRUCTEURS*****
	public Pouvoir(int largeur , int hauteur, int x , int y) {
		
		super(largeur, hauteur, x ,y);
		this.ico_pouvoir1 = new ImageIcon(getClass().getResource("/images/pouvoir1.png")); 
		this.ico_pouvoir2 = new ImageIcon(getClass().getResource("/images/pouvoir2.png"));
		this.img_bloc = ico_pouvoir1.getImage();
		this.visible = true;
			
	}
	
	//****METHODES****
	public void anim () { // Permet d'animer l'image représentant le pouvoir
		
		if (this.visible) {
		    this.frequence += 1 ; // Augmente le compteur de fréquence 
		
		    if ( this.frequence >= 300 ) { // Permet une animation moins régulière (300*3ms) 
			   this.frequence = 0;	
			
		       if (this.img_bloc == ico_pouvoir1.getImage()) {
			       this.img_bloc = ico_pouvoir2.getImage();
		       } else {
			       this.img_bloc = ico_pouvoir1.getImage();
		       }
		    }
		    
		} else  { // Fais disparaître l'image du pouvoir après récupération par le personnage	
			 this.img_bloc = null; 
		}		
	}
	
	public boolean collision(Personnage p) { // Gère la collision avec un personnage qui est différente pour un pouvoir
		
		boolean retour = super.collision(p);
		
		if (retour == true ) { // Test si il y a eu collision avec un obstacle
			int n = (int)(1+ Math.random()*3); // 3 pouvoir pour le moment -> 1: invincibilité | 2 : aimant | 3 : Tireur ; que l'on affecte au personnage aléatoirement
			p.setNumPouvoir(n); 
			
			if (n == 3) { // Si c'est le pouvoir de "Tir Missile"
				p.setMissile(100); // Nombre de roquette disponible
			}
			
			p.setTpsPouvoir(6000); //Pouvoir disponible pendant un certain temps
			this.ico_pouvoir1 = null;
			this.ico_pouvoir2 = null;	
			this.visible = false;
		}
		return retour;
    }
}