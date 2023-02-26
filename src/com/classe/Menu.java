package com.classe;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Classe qui gère le menu de lancement de jeu
public class Menu extends JFrame implements ActionListener, MouseListener {
	
	private JButton monde_1; // bouton lancement du jeu dans le monde 1
	private JButton monde_2; // bouton lancement du jeu dans le monde 2
	private JButton bt_boutique; // bouton de la boutique

	 
	private JFrame menu; 
	
	//***CONSTRUCTEURS****
	public Menu(){
		 
		//paramètres fenetre
		this.menu = new JFrame("Jetpack - Menu ");
		this.menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.menu.setSize(600,400);
		this.menu.setLocationRelativeTo(null); // au centre de l'écran
		this.menu.setResizable(false); // impossible de changer sa taille
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0,0,600,400);
		panel.setBackground(null);

		// bouton monde 1
		this.monde_1 = new JButton(new ImageIcon(getClass().getResource("/images/Vignette1.png")));
		this.monde_1.setBounds(100,200,160,100);
		this.monde_1.setBackground(Color.black);
		this.monde_1.setBorderPainted(false);
		this.monde_1.addActionListener(this);
		this.monde_1.addMouseListener(this);
		this.monde_1.setFocusable(false);

		// bouton monde 2
		this.monde_2 = new JButton(new ImageIcon(getClass().getResource("/images/Vignette2.png")));
		this.monde_2.setBounds(300,200,160,100);
		this.monde_2.setBackground(Color.black);
		this.monde_2.setBorderPainted(false);
		this.monde_2.addActionListener(this);
		this.monde_2.setFocusable(false);
		this.monde_2.addMouseListener(this);

		// bouton boutique
		this.bt_boutique = new JButton(new ImageIcon(getClass().getResource("/images/shop.png")));
		this.bt_boutique.setBounds(460,70,120,40);
		this.bt_boutique.setBackground(Color.black);
		this.bt_boutique.setBorderPainted(false);
		this.bt_boutique.addActionListener(this);
		this.bt_boutique.setFocusable(false);
		this.bt_boutique.addMouseListener(this);
		
		// Création d'image

		// image de fond
		JLabel img_fond = new JLabel(new ImageIcon(getClass().getResource("/images/Image_Fond_1.jpg")));
		img_fond.setBounds(0,0,600,400);

		// image du perso
		JLabel img_perso = new JLabel(new ImageIcon(getClass().getResource("/images/jetpack_feu.png")));
		img_perso.setBounds(40,100,60,104);

		// image de la pice
		JLabel img_piece = new JLabel(new ImageIcon(getClass().getResource("/images/piece.png")));
		img_piece.setBounds(450,15,45,45);
		
		
		// Création étiquette 
		
		String str = "Crédits : Martin Boisseau | Clément Bouvet | Tom Lavigne | Tristan Paget | Mathis Von Stebut" ;
		JLabel texte = new JLabel(str);
		texte.setBounds(40,330,550,50);
		texte.setForeground(Color.WHITE);
		texte.setFont(new Font("Arial", Font.ITALIC, 12));
		
		JLabel titre = new JLabel("-JetPack-");
		titre.setBounds(180,70,300,100);
		titre.setForeground(Color.orange);
		titre.setFont(new Font("Bauhaus 93", Font.BOLD, 50));
		
		JLabel score = new JLabel("Best Score : " + getScore_max());
		score.setBounds(220,135,300,70);
		score.setForeground(Color.BLACK);
		score.setFont(new Font("Arial", Font.BOLD, 20));
		
		JLabel pieces = new JLabel( "" + litPiece());
		pieces.setBounds(505,0,300,70);
		pieces.setForeground(Color.YELLOW);
		pieces.setFont(new Font("Bauhaus 93", Font.BOLD, 30));
		
		// Ajout Elements 
		
		panel.add(titre);
		panel.add(texte);
		panel.add(score);
		panel.add(pieces);
		
		panel.add(this.monde_1);
		panel.add(this.monde_2);
		panel.add(this.bt_boutique);
		
		panel.add(img_perso);
		panel.add(img_piece);
		panel.add(img_fond);
		
				
		
		
	
		this.menu.add(panel);
		this.menu.setVisible(true);
		
				
	}
	
	//***METHODES EVENT***
	public void actionPerformed(ActionEvent e) {
				
		if ( e.getSource() ==this.monde_1 ) { // Lancement du jeu lors du clic avec fond différent en fonction du monde
			
			FenetreJeu fenetre = new FenetreJeu(new Environnement("/images/Image_Fond_1.jpg", Color.YELLOW, 2));    
			this.menu.dispose();
			
		} else if ( e.getSource() == this.monde_2 ) {
			   FenetreJeu fenetre = new FenetreJeu( new Environnement("/images/img_fond_blanc.png", Color.black, 3));
			   this.menu.dispose();

		} else if ( e.getSource() == this.bt_boutique) {
			Boutique fenetre = new Boutique();
			this.repaint();

		}
	}
	
	//***METHODES****
	public void setScorefin(int s) {// Actualise ou non le score maximal selon la dernière partie
        if ( s > getScore_max()) {      
            setScore_max(s);         
        }
    }
	
	public int getScore_max() { // Récupère le score maximal du fichier en mémoire
		
		int retour = 0;
		
        try {
			
			   Scanner lecture = new Scanner(new FileInputStream("files/Memoire.txt")); // Lecture des données du fichier mémoire
			   String[] separer = new String[2]; 
			   String data ="";
			
			    while (lecture.hasNextLine()) {
				    data = lecture.nextLine();
				   separer = data.split(";"); // Permet de séparer les données par le point de virgule
				   retour = Integer.parseInt(separer[0]); // Recupère le score max
			    }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
        return retour;
	}
	
	public void setScore_max(int s) { // Ecrit dans la mémoire le nouveau score max
		
		try {
			
			PrintWriter writer = new PrintWriter("files/Memoire.txt", "UTF-8");
			writer.println(s);
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int litPiece() {  // recupère le nombre de pièce récoltée
		int retour = 0;
		
           try {
			
			   Scanner lecture = new Scanner(new FileInputStream("files/Memoire.txt")); // Lecture des données du fichier mémoire
			   String[] separer = new String[2]; 
			   String data ="";
			
			    while (lecture.hasNextLine()) {
				    data = lecture.nextLine();
				   separer = data.split(";"); // Permet de séparer les données par le point de virgule
				   retour = Integer.parseInt(separer[1]); // Recupère le nombre de pièce
			    }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
           return retour;
	}

	
	public void mouseClicked(MouseEvent arg0) {}

	
	public void mouseEntered(MouseEvent e) { //  si Bouton survolé son + change couleur du contour
		
		Audio.playSound("/audio/Bip.wav");
		
	
		if (e.getSource() == this.monde_1) {
			this.monde_1.setBackground(Color.orange);
		} else if ( e.getSource() == this.monde_2){
			this.monde_2.setBackground(Color.orange);
		} else if ( e.getSource() == this.bt_boutique){
			this.bt_boutique.setBackground(Color.white);
		}
		
		
	}

	public void mouseExited(MouseEvent arg0) { // reviens noir si souris survole plus
		
		this.monde_1.setBackground(Color.black);
		this.monde_2.setBackground(Color.black);
		this.bt_boutique.setBackground(Color.black);
		
	}

	
	public void mousePressed(MouseEvent arg0) {}

	
	public void mouseReleased(MouseEvent arg0) {}
}