package com.classe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Classe de gestion de l'environnement de jeu
public class Environnement extends JPanel {
	
	// ****ATTRIBUTS****** // 
	private ImageIcon icoFond; // Gère le fichier image du fond
	private Image imgFond1; // Deux images réelle pour créer un défilement
	private Image imgFond2;
	
	private JLabel img_piece; // image de la pièce
	private JLabel nb_piece; // Etiquette des pièces récoltées
	private JLabel etiquette; // Etiquette de score
	
	private int xFond1; // repère la position de l'image de fond 1 en abscisse
	private int xFond2; // repère la position de l'image de fond 2 en abscisse
	
	private int dx; // permet de faire bouger les élements du jeu
	
	private long temps = 0; // timer (voir chrono)
	
	private Personnage JetPack; // Personnage dans le jeu
	
	private ArrayList<Bloc> liste_missile = new ArrayList<Bloc>();  // listes des missiles a l'ecran
    private PackPiece pak;  // liste de pièce avec une forme random
    private ArrayList<Obstacle> tab_obstacle; // liste des obstacles
    
    private Color couleur_ecriture;

	// ****CONSTRUCTEUR****** //
	public Environnement(String Image, Color c, int d) {
		
		super();
		
		this.setLayout(null);
		this.couleur_ecriture = c;// def couleur texte
		
		this.xFond1 = -50; 
		this.xFond2 = 1550;
		this.dx = d; // Défini un pas de translation horizontale au début du jeu
		
		// Création des adresses des images 
		this.icoFond = new ImageIcon(getClass().getResource(Image)); // image du fond suivant le monde
		this.imgFond1 = icoFond.getImage();
		this.imgFond2 = icoFond.getImage();
		
		this.img_piece = new JLabel(new ImageIcon(getClass().getResource("/images/piece.png")));
		this.img_piece.setBounds(20,10,45,45);
		
		// Création personnage 
		this.JetPack = new Personnage(60,104,100,500);
		
		// Ajout du bloc ( 4 obstacles et 1 pouvoir avec un y random et un x décalé)
		
		tab_obstacle = new ArrayList<Obstacle>();
		
		tab_obstacle.add(new Bloc(198,50,1600,(int)(Math.random()*500),new ImageIcon(getClass().getResource("/images/nyan_cat.png")),true));
		tab_obstacle.add(new Bloc(322,85,1900,(int)(Math.random()*500),new ImageIcon(getClass().getResource("/images/obstacle_horizontal.png")),false));
		tab_obstacle.add(new Bloc(64,250,2200,(int)(Math.random()*200),new ImageIcon(getClass().getResource("/images/obstacle_vertical.png")),false));
		tab_obstacle.add(new Bloc(198,50,2500,(int)(Math.random()*500),new ImageIcon(getClass().getResource("/images/nyan_cat.png")),true));
		tab_obstacle.add(new Pouvoir(50,50,1600,(int)(Math.random()*500)));
		
		this.pak = new PackPiece(JetPack,1800);
			
		// Ajout de l'étiquette de score 
		
		this.etiquette = new JLabel("Score : " + JetPack.getScore());
		this.etiquette.setBounds(1450,15, 200, 30);
		this.etiquette.setForeground(couleur_ecriture);
		this.etiquette.setFont(new Font("Bauhaus 93", Font.BOLD, 20));

		// Ajout de l'étiquette des pièces
		
		this.nb_piece = new JLabel( "" + JetPack.getNbPieces());
		this.nb_piece.setBounds(80,15, 200, 40);
		this.nb_piece.setForeground(couleur_ecriture);
		this.nb_piece.setFont(new Font("Bauhaus 93", Font.BOLD, 30));
		
		this.add(img_piece);
		this.add(nb_piece);
		this.add(etiquette);	
		
		
	}	
	
	
	// ****METHODES****** //
	private void deplacementFond() {
		
		this.xFond1 -= this.dx; // Fais varier le fond d'un pas constant
		this.xFond2 -= this.dx;
		
		// Permet un switch entre les deux images de fond pour un défilement en continu
		if ( this.xFond1 <= -1600 ) { this.xFond1 = 1600 ; }
		else if (this.xFond2 <= -1600 ) { this.xFond2 = 1600 ; }

		// recréer des nouvelles pièces quand les dernières pièces sont passées
		else if (pak.getPackChoisi().get(pak.getPackChoisi().size()-1).x <= -300 ) { pak = new PackPiece(JetPack,1800); }
		
	}
	

	// comme la methode paint
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics g2 = (Graphics2D) g; // Permet un meilleur rendu des composants graphiques

		// repaint les scores et pièces accumulées
		this.etiquette.setText("Score : " + JetPack.getScore());
		this.nb_piece.setText(""+JetPack.getNbPieces());

		// deplace pièce et fond
		deplacementFond();

		// gere les obstacles et le tir de missile
	    gestionTir();
		gestionObstacle();
		
		// deplace le personnage
		this.JetPack.mouvement();		

		// pièces bougent
		pak.AnimPack(dx);

		
		// Dessine le jeu 
		g2.drawImage(this.imgFond1,this.xFond1,0,null);
		g2.drawImage(this.imgFond2,this.xFond2,0,null);
		g2.drawImage(this.JetPack.anim(),this.JetPack.getDx(),this.JetPack.getDy(),null);
		
		
		for (Obstacle o : tab_obstacle) {
			g2.drawImage(o.img_bloc, o.x, o.y, null);
		}
		
		for(Piece p : pak.getPackChoisi()) {
			g2.drawImage(p.img_bloc, p.x, p.y, null);
		}
		
		
		if (!liste_missile.isEmpty()) {
		    for (Bloc missile : liste_missile) {
		        g2.drawImage(missile.img_bloc, missile.x, missile.y, null);
		    }
		}
		
	}
	

	// crée les obstacles fixes de manière aléatoire ( vertical ou horizontal)
	private Bloc creeObstacle() {
		
		Bloc retour = new Bloc();
		int random = (int)(Math.random()*2);
		
		do switch (random) {
			case 0 : retour = new Bloc(322, 85, 1600, (int) (Math.random() * 500), new ImageIcon(getClass().getResource("/images/obstacle_horizontal.png")), false);
			break;
			case 1 : retour = new Bloc(64, 250, 1600, (int) (Math.random() * 500), new ImageIcon(getClass().getResource("/images/obstacle_vertical.png")), false);
			break;
		} while ( (retour.y + retour.hauteur) >= 600);
		
		return retour;
	
	}

	// gère un missile lors du tir (création,déplacement, disparition)
	public void gestionTir() {
		if ( JetPack.getTir() == true ) {
			liste_missile.add(new Bloc(84,31,JetPack.getDx()+10,JetPack.getDy()+20, new ImageIcon(getClass().getResource("/images/missile.png")),true)); 
			JetPack.setTir(false);
		}
		
		if (liste_missile.size() != 0){ 
			  try { for ( Bloc m : liste_missile ) {
				   
				   if ( m.isVisible() && m.x >= 1600 ) { // si missile sort de l'écran sort de la liste
					   m.disparition();
					   liste_missile.remove(m);
				   }
			   } 
			  
			} catch ( Exception e ) {}
		}
		
		if (! liste_missile.isEmpty()) {
		    for ( Bloc missile : liste_missile) {
		        if (missile.isVisible()) {
			      missile.x += dx +1;  // deplacement
			      
			      
			      for (Obstacle o : tab_obstacle) {
			    	  if ( o instanceof Bloc ) {
			               ((Bloc) o).collisionMissile(missile); // collision
			          }
		          }
		       }
		    }
		}	
	}

	// gère la création des obstacles mouvent et le renouvellement/déplacement de tout les obstacles
	public void gestionObstacle() {
		
		try {
			for(Obstacle o : tab_obstacle) {
		
				// gestion obstacles( renouvellment, deplacement)
			   if ( o instanceof Bloc) {
				   
				   if ( o.mouvant && o.x < -700 ) {
					   
					   tab_obstacle.add(new Bloc(198,50,1600,(int)(Math.random()*500),new ImageIcon(getClass().getResource("/images/nyan_cat.png")),true));
					   tab_obstacle.remove(o);
					   
				   } else if ( o.x < -350 && !o.mouvant  ) { // les fixes deux fois moins loin car deux fois moins vite que les mouvant
				       tab_obstacle.add(creeObstacle());
				       tab_obstacle.remove(o);
				   } else if ( o.mouvant) {
					   o.x -= (dx+1);
				   } else {
					   o.x -= dx;
				   }

				   // gestion pouvoirs ( renouvellement, animation, déplacement, collision)
			   } else if ( o instanceof Pouvoir ) {
				   
				   if(o.x <= -5000) {
				       tab_obstacle.add(new Pouvoir(50,50,1600,(int)(Math.random()*500)));
				       tab_obstacle.remove(o);
				   } else {
					   o.x -= dx;
					   o.anim();
					   o.collision(JetPack);
				   }
			  }
		   }
		} catch (Exception e) {} // debug les exceptions
		
		
	}


	//****GETTERS****
	public Personnage getJetPack() {return JetPack;}
	
	public ArrayList<Obstacle> getTab_obstacle(){ return tab_obstacle;}
	
	//***SETTERS***

	public void setTemps(int t) {this.temps += t;}


}
