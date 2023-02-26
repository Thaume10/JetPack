package com.classe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//Classe qui gère le lancement d'audio dans le jeu
public class Audio {
	
	private Clip clip; // Attribut qui gère les audios
	
	//***CONSTRUCTEUR****
	public Audio(String son) {
		
		try { // Récupération du fichier audio spécifié dans le constructeur -> fichier .wav nécessaire
			AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(son));
			this.clip = AudioSystem.getClip();
			this.clip.open(audio);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//****METHODES****	
	public void play() { clip.start(); } //Lance un audio
	public void stop() { clip.stop();} //Arrête un audio
	
	public static void playSound(String son) { // Lance un audio spécifié en paramètre
		Audio s = new Audio(son);
		s.play();
	}

}
