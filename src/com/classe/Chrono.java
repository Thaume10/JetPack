package com.classe;

// Classe de gestion du temps pour la cr�ation d'animation 
public class Chrono  implements Runnable {
	
	private final int REFRESH = 3; // Dur�e du temps d'attente entre deux rafraichissement de la scene
	public boolean jeu_ok = true; // Permet une boucle infini tant que le personnage est en "vie"
	private Environnement scene; // Attribut qui permet d'acc�der aux attributs du jeu
	
	//***CONSTRUCTEUR****
	public Chrono(Environnement s) {
		this.scene = s;
	}
	

	//***METHODE RUNNABLE****
	public void run() {

        // Boucle rafraichissement tant que le jeu le permet
        while(jeu_ok) {

            this.scene.getJetPack().setScore(0.1); // Augmente le score � chaque rafraichissement
            this.scene.repaint(); // R�actualise la sc�ne
            this.scene.setTemps(3); // Ajout � la sc�ne du temps �coul� 
            
            
            try { for(Obstacle o : this.scene.getTab_obstacle()) {

                if ( o.collision(this.scene.getJetPack()) && o instanceof Bloc ) { // Test si le personnage est entrer en collision avec un obstacle de type Bloc (malus)
            	
            	    if (this.scene.getJetPack().getTpsPouvoir() == 0) { // Permet la gestion de la collision lors de la fin du pouvoir invicible
            		    
            	    	this.scene.getJetPack().setCollision(false);
            	    }
            	
            	    if (  (this.scene.getJetPack().getTpsPouvoir() == 0 || (this.scene.getJetPack().getNumPouvoir() != 1) ) && this.scene.getJetPack().getCollision() == false  ) { // Si personnage non invincible -> fin du jeu
            		   
            	       jeu_ok=false;
            		   Audio.playSound("/audio/explosion.wav");
            		   
            	    } else if (this.scene.getJetPack().getNumPouvoir() == 1 && this.scene.getJetPack().getCollision() == false ) { // Si le personnage �tait invicible et n'�tait encore pas rentrer en collision avec un obstacle avant
            		   
            	       Audio.playSound("/audio/explosion.wav");
            		   this.scene.getJetPack().setCollision(true); // Signale que le personnage a eu une collision avec son pouvoir invicible
            		   this.scene.getJetPack().setNumPouvoir(0); // Reinitialise le pouvoir du personnage
            		   this.scene.getJetPack().setTpsPouvoir(300); // Permet d'�viter la collision � l'instant d'apr�s -> invicible = l'obstacle en question est neutralis�
           		
            	   }          		        	          	
               }  
            }
            } catch (Exception e) {}
            
             // Pause du flux entre chaques boucles
            try {				
         	   Thread.sleep(REFRESH); 
            } catch(InterruptedException e) {}
       }
					
	}	
}