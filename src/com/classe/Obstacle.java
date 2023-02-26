package com.classe;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Obstacle {
	
    protected int hauteur; // hauteur de l'image d'un obstacle
    protected int largeur; // largeur de l'image d'un obstacle
    protected int x; // position x de l'obstacle
    protected int y; // position y de l'obstacle
    
	protected ImageIcon ico_bloc; // Attributs qui gère le fichier image
	protected Image img_bloc; // Attributs qui récuppère l'image réelle

	protected boolean mouvant = false; // indique si l'object est mouvant
    protected boolean visible; // boolean qui indique si l'obstacle est visible
    protected int frequence; // Permet d'animer les pièces à une certaines fréquence

    //***CONSTRUCTEURS*****
    public Obstacle (int largeur , int hauteur, int x , int y) {

        this.hauteur = hauteur;
        this.largeur = largeur;
        this.x = x;
        this.y = y;
        this.frequence =0;
        this.visible = true;
    }

    public Obstacle() {
    	this.visible = false;
    }

    //***METHODES***
    public boolean collision(Personnage p) {
    	
    	if ( this.visible ) {
            // Collision frontale (corps)
            if((this.y > p.getDy()) && ( this.x > p.getDx()) && (this.y+40<(p.getDy()+ p.getHauteur()))&&(this.x<(p.getDx()+ p.getLargeur()))) {

                return true;
            
                // Collision depuis le dessous vers le haut (tete)
            }else //Aucune collision
                if((p.getDx()<this.x+this.largeur) && ((p.getDx()>this.x)) && (p.getDy()<this.y+this.hauteur-10)&&(p.getDy()>this.y)){
                return true;
            
                // Collision depuis le dessus vers le bas (pied)
            }else return (p.getDx() < this.x + this.largeur) && ((p.getDx() > this.x)) && (p.getDy() + p.getHauteur() > this.y + 10) && (p.getDy() + p.getHauteur() < this.y + this.hauteur);
    	} else {
    		return false;
    	}

    }
    
    public void anim() {} // Méthode mère d'animation

}