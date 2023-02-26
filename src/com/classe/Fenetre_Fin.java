package com.classe;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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


//Menu de fin de jeux qui récapitule les données de la partie
public class Fenetre_Fin extends JFrame implements ActionListener, KeyListener, MouseListener {
	
	private JButton bouton_Ok; // bouton OK pour continuer -> direction le menu de départ
	private JPanel panel;
	private Environnement scene; // Permet de connaître les attributs de jeu
	private JFrame fenetre; 
	
	//***CONSTRUCTEUR****
	public Fenetre_Fin(Environnement scene){
		
		//Initialisation Fenetre
		this.scene = scene;
	
		this.fenetre = new JFrame("Jetpack - Game Over ");
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setSize(600,400);
		this.fenetre.setLocationRelativeTo(null); // au centre de l'écran
		this.fenetre.setResizable(false); // impossible de changer sa taille
		
		//Panel
		this.panel = new JPanel(); 
		this.panel.setLayout(null);
		this.panel.setBounds(0,0,600,400);
		
		//Bouton
		this.bouton_Ok = new JButton("OK");
		this.bouton_Ok.setBounds(225,270,150,70);
		this.bouton_Ok.setBackground(Color.YELLOW);
		this.bouton_Ok.setBorderPainted(false);
		this.bouton_Ok.addActionListener(this);
		this.bouton_Ok.addKeyListener(this);
		this.bouton_Ok.addMouseListener(this);
		
		// Création des etiquettes/image
		JLabel img_fond = new JLabel(new ImageIcon(getClass().getResource("/images/Image_Fond_1.jpg")));
		img_fond.setBounds(0,0,600,400);
		img_fond.setOpaque(false);
		
		JLabel img_piece = new JLabel(new ImageIcon(getClass().getResource("/images/piece.png")));
		img_piece.setBounds(450,10,45,45); 
		
		JLabel fin = new JLabel("-Game Over-");
		fin.setBounds(180,40,400,50);
		fin.setForeground(Color.orange);
		fin.setFont(new Font("Bauhaus 93", Font.BOLD, 40));
		
		int s = scene.getJetPack().getScore(); // Recupère le score de la partie actuel
		
		JLabel score_fin = new JLabel("Score : " + s);
		score_fin.setBounds(140,110,400,50);
		score_fin.setForeground(Color.BLACK);
		score_fin.setFont(new Font("Bauhaus 93", Font.BOLD, 30));
		
		int s2 = getScore_max(); // Recupère le score max en mémoire
		JLabel score_max;
		
		if ( s >= s2 ) { // Affiche le bon score maximal
			score_max = new JLabel("Score maximal : " + s );
		} else {
			score_max = new JLabel("Score maximal : " + s2 );
		}		
		score_max.setBounds(140,170,400,50);
		score_max.setForeground(Color.BLACK);
		score_max.setFont(new Font("Bauhaus 93", Font.BOLD, 30));
		
		JLabel pieces = new JLabel("" + scene.getJetPack().getNbPieces()); // Etiquettes avec le nombre de pieces
		pieces.setBounds(510,5,400,50);
		pieces.setForeground(Color.yellow);
		pieces.setFont(new Font("Bauhaus 93", Font.BOLD, 30));		
				
		// Ajout Elements 
			
		this.panel.add(fin);
		this.panel.add(score_fin);
		this.panel.add(score_max);
		this.panel.add(pieces);	
		this.panel.add(bouton_Ok);
		this.panel.add(img_piece);
		this.panel.add(img_fond);	
		this.fenetre.add(panel);
		this.fenetre.setVisible(true);
					
	}
	

	//**METHODES EVENT**
	public void actionPerformed(ActionEvent e) {
					
		if ( e.getSource() == this.bouton_Ok ) { // Lancement du menu lors du click sur bouton
			
			this.fenetre.dispose();
			Menu menu1 = new Menu();
				
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == this.bouton_Ok && e.getKeyChar()== KeyEvent.VK_ENTER) { // Lancement du menu avec touche ENTER
			this.fenetre.dispose();
		    Menu menu1 = new Menu();
			
		}
	}
	
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) { // Survol du Bouton
		
		this.bouton_Ok.setBackground(Color.white);
		Audio.playSound("/audio/Bip.wav");
		
	}

	public void mouseExited(MouseEvent e) { // Lorsque l'on quitte le survol du bouton
		this.bouton_Ok.setBackground(Color.yellow);	
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	//**METHODES***
	public void setScorefin(int s) { // Actualise ou non le score maximal selon la dernière partie
        if ( s > getScore_max()) {    
            setScore_max(s);         
        } else {
        	setScore_max(getScore_max());
        }
    }
	
	public int getScore_max() { // Lit le score maximal du fichier en mémoire
		
		int retour = 0;
		
        try {
			
			   Scanner lecture = new Scanner(new FileInputStream("files/Memoire.txt")); // Lecture des données du fichier mémoire
			   String[] separer = new String[2]; 
			   String data ="";
			
			    while (lecture.hasNextLine()) {
				    data = lecture.nextLine();
				   separer = data.split(";"); // Permet de séparer les données par le point de virgule
				   retour = Integer.parseInt(separer[0]); // Recupère le score max en mémoire ( 1er attribut)
			    }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
        return retour;
	}
	
	public void setScore_max(int s) { // Ecrit dans la mémoire le nouveau score max
		
		int piece_memoire = litPiece();
		
		try {
			
			PrintWriter writer = new PrintWriter("files/Memoire.txt", "UTF-8");
			writer.println(s+";"+(piece_memoire+this.scene.getJetPack().getNbPieces()));
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int litPiece() { // Permet la lecture du nombre de pièces déjà collectées en mémoire
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

	
	
}
