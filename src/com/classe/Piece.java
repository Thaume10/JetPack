package com.classe;

import javax.swing.ImageIcon;

// Classe fille d'obstacle -> permet de créer des pièces
public class Piece extends Obstacle{

    private ImageIcon ico_piece; // Gère le fichier n°1 de l'image
    private ImageIcon ico_piece2; // Gère le fichier n°2 de l'image
    
    private Personnage Perso; // Récupère le personnage pour la gestion des collisions
    
    //***CONSTRUCTEURS****
    public Piece(int largeur , int hauteur, int x , int y, Personnage perso) {

        super(largeur, hauteur, x ,y);
        this.visible=true;
        this.Perso=perso;
        this.ico_piece = new ImageIcon(getClass().getResource("/images/piece.png"));
        this.ico_piece2 = new ImageIcon(getClass().getResource("/images/piece2.png"));
        this.img_bloc = ico_piece.getImage();
      
    }
    
    //****METHODES****
    public void anim() { // Gère l'animation d'une pièce
    	
        if(this.visible) {
        	
        	this.frequence +=1;
        	
        	if ( this.frequence >= 35 ) { // Permet d'animer une pièce à une certaine fréquence en changeant son image
        		
        		if (this.img_bloc == this.ico_piece.getImage()) {
                     this.img_bloc = this.ico_piece2.getImage();
                } else {
                     this.img_bloc = this.ico_piece.getImage();
                }
        		this.frequence = 0;
        	}
        }
    }

    
    public void disparition(){ // Fais disparaître une pièce après récupération de celle-ci
    	
    	if (Perso.getNumPouvoir() != 2) { // si le personnage n'a pas le pouvoir 'aimant'
    		if(this.collision(Perso) && visible) {
            	
                this.img_bloc = null;
                this.visible = false;
                this.Perso.addPiece();  // Ajoute 1 au nombre de pièce collecté par le personnage
                this.Perso.setScore(10); // Ajoute 10 au score du perso par piece collecté
            }
    	} else if ( Perso.getNumPouvoir() == 2 && visible) { // Si le personnage à le pouvoir de l'aimant
    		
    		// On va tester si le personnage est dans une zone de 150px autour de la piece
    		if ((Perso.getDx() > this.x-150 && Perso.getDx() < this.x+150) && (Perso.getDy() > this.y-150 && Perso.getDy() < this.y+150)) { // attire les pieces sans avoir à entrer en collision avec 		
    			this.img_bloc = null;
                this.visible = false;
                this.Perso.addPiece();  // Ajoute 1 au nombre de pièce collecté par le personnage
                this.Perso.setScore(10); // Ajoute 10 au score du perso par piece collecté
    		}	
    	}
        
    }

    // Permet l'animation/déplacements des pièces et de tester si elles sont visibles ou non
    public void AnimPiece(int dx){
        this.x-=dx; // permet de faire varier la position x de la pièce
        this.anim();
        this.disparition();
    }

}