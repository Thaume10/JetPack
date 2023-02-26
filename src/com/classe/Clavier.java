package com.classe;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Classe gestion des touches -------------
public class Clavier implements KeyListener {
	
	private boolean isSpacePressed = false; // Gère si la touche espace est appuyé 
	private boolean isEnterPressed = false; // Gère si la touche entrer est appuyé 
	
	private Environnement scene; //Attribut qui permet d'accéder aux attributs du jeu
	
	//***CONSTRUCTEURS*****
	public Clavier(Environnement s) {
		this.scene = s;
	}

	//***METHODES EVENT*****	
	public void keyPressed(KeyEvent e) {
			
		if (e.getKeyCode() == KeyEvent.VK_SPACE ) { // Si touche espace appuyée
			
			isSpacePressed = true;
			
		} else if ( e.getKeyCode() == KeyEvent.VK_ENTER && scene.getJetPack().getNumPouvoir() == 3 && scene.getJetPack().getnbMissile() > 0 ) { // Si le personnage à le pouvoir de "Tir de Missile" et a encore des missiles
			isEnterPressed = true;
		}
		doAction(); // Permet la gestion de plusieurs touches en même temps
	}

	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		
        case KeyEvent.VK_SPACE : 
             isSpacePressed = false;
            break;
        
         case KeyEvent.VK_ENTER : 
              isEnterPressed = false;
             break;
         
        }
		
	    doAction();	
	}

	public void keyTyped(KeyEvent e) {}
	
	public void doAction() { // Methodes de gestion des actions selon les touches
		
       if (isSpacePressed  & isEnterPressed) { // Si les deux touches sont appuyés
			
			this.scene.getJetPack().setVol(true); // Indique que le personnage est en train de voler
			this.scene.getJetPack().setTir(true); // Indique que le personnage est en train de tirer
			this.scene.getJetPack().setMissile(scene.getJetPack().getnbMissile()-1); // Enleve 1 au nombre de missile disponible
			
		} else if ( isEnterPressed ) { // Si le personnage tir sans monter (voler)
			
			this.scene.getJetPack().setTir(true);
			this.scene.getJetPack().setMissile(scene.getJetPack().getnbMissile()-1);
			
		} else if ( isSpacePressed) { // Si le personnage veut monter ( voler )
			
			this.scene.getJetPack().setVol(true);
		}            	
		
		if ( !isSpacePressed ) {
			
			this.scene.getJetPack().setVol(false);
		}
	}

}
