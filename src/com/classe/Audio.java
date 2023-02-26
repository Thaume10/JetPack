package com.classe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//Classe qui g�re le lancement d'audio dans le jeu
public class Audio {
	
	private Clip clip; // Attribut qui g�re les audios
	
	//***CONSTRUCTEUR****
	public Audio(String son) {
		
		try { // R�cup�ration du fichier audio sp�cifi� dans le constructeur -> fichier .wav n�cessaire
			AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(son));
			this.clip = AudioSystem.getClip();
			this.clip.open(audio);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//****METHODES****	
	public void play() { clip.start(); } //Lance un audio
	public void stop() { clip.stop();} //Arr�te un audio
	
	public static void playSound(String son) { // Lance un audio sp�cifi� en param�tre
		Audio s = new Audio(son);
		s.play();
	}

}
