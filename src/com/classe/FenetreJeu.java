package com.classe;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe qui crée l'IHM du jeu
public class FenetreJeu extends JFrame implements ActionListener {
	
	private Environnement scene;
	private Thread flux; 
	private Timer temps; // Timer indépendant qui permet de gérer les évenements indépendamment
	private Chrono chrono; 

	//***CONSTRUCTEURS***
    public FenetreJeu(Environnement s){
    	
    	//Initialisation fenêtre
    	super("JetPack - Game");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600,640);
        this.setLocationRelativeTo(null); // au centre de l'écran
        this.setResizable(false); // impossible de changer sa taille
        this.setAlwaysOnTop(true);
     
        this.scene = s;
        
       
        
        // Permet de lire les evenements du jeu
        this.scene.setFocusable(true);
        this.scene.requestFocus();
        this.scene.addKeyListener(new Clavier(scene));
        
        //Initialisation timer
        this.temps = new Timer(3, this);
        this.temps.start();
        
        //Création du chrono
        this.chrono = new Chrono(scene);
        this.flux = new Thread(chrono); 
        this.flux.start();
        
        //Création zone de contenu
        this.setContentPane(scene);
        this.setVisible(true);
    }


    //***METHODES EVENT****
	public void actionPerformed(ActionEvent e) {
		
		if (!this.chrono.jeu_ok) { // si le jeu c'est terminé
						
			try {
				this.flux.sleep(600); // permet de limiter la validation de la fenêtre de fin avec la touche espace
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//Ferme toute les variable et ouvre la fenetre de fin
			this.flux.stop();
			this.temps.stop();			
			
			Fenetre_Fin f2 = new Fenetre_Fin(scene);
			f2.setScorefin(this.scene.getJetPack().getScore()); // Permet de mémoriser le score
			
			this.dispose();
		}
		
	}



	
	

}