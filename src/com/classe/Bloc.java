package com.classe;

import javax.swing.ImageIcon;

//Classe fille d'un obstacle -> représente un bloc qui empêche le personnage de passer
public class Bloc extends Obstacle{
	
	//***CONSTRUCTEURS*******
	public Bloc(int largeur , int hauteur, int x , int y, ImageIcon i, boolean m) {
		super(largeur, hauteur, x ,y);
		this.ico_bloc = i; // Permet de retrouver l'adresse de l'image, peut importe si le projet change d'adresse
		this.img_bloc = i.getImage(); 
		this.mouvant = m; // determine si bloc mouvant
	}
	
	public Bloc() {
		super();
	}

	//***METHODES***
	public void disparition() { // fait disparaitre l'image du bloc
		this.visible = false;
		this.img_bloc = null;
	}
	
	public boolean isVisible() {
		return this.visible; 
	} //renvoi si l'image est visible
	
	public void collisionMissile(Bloc o) {  // determine si le bloc rentre en colision avec un missile o
		if((this.y-10 <= o.y) &&( (this.y+this.hauteur+20)>=(o.y+ o.hauteur)) && ((this.x <= o.x+o.largeur)) && this.visible) {
			disparition();
			o.disparition();
		}
	}
	
}