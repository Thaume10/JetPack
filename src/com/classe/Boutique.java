package com.classe;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Classe qui gère la boutique du jeu
public class Boutique extends JFrame implements ActionListener, MouseListener {
	
	private JButton bt_1; // Bouton avec l'image du skin pour l'achat
	private int Piece; // Attributs qui récupère les pièces déja collectés dans le jeu
	
	 public Boutique(){
	    	
	    	//Initialisation fenêtre
	    	super("JetPack - Boutique");

	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        this.setSize(600,400);
	        this.setLocationRelativeTo(null); // au centre de l'écran
	        this.setResizable(false); // impossible de changer sa taille
	        this.setAlwaysOnTop(true);
	        
	        this.Piece = litPiece(); // Renvoi le nombre de pièces collectées en mémoire
	        
	        //Création du BoutonImage     
	        this.bt_1 = new JButton(new ImageIcon(getClass().getResource("/images/Skin1.png"))); // Recuppere l'addresse de l'image
	        this.bt_1.setBounds(185,25,210,310);
			this.bt_1.setBackground(Color.black);
			this.bt_1.setBorderPainted(false);
			this.bt_1.addActionListener(this);
			this.bt_1.addMouseListener(this);
			
	        
			// Panel			
			JPanel panel = new JPanel();  
			panel.setLayout(null);
			panel.setBounds(0,0,600,400);
			panel.setBackground(Color.lightGray);
			
			// Visibilité des éléments			
			panel.add(bt_1);
			this.add(panel); 
			this.setVisible(true);
	        	  
	    }


	 //****METHODES EVENT*****
	 
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == bt_1 ) { // Skin 1
			
			if (litPiece() > 10000) { // Prix du 1er skin = 10'000 Pièces
				
				int i = JOptionPane.showConfirmDialog(this, "Confirmer l'achat ?", "Confirmer", JOptionPane.YES_NO_OPTION); // Demande la confirmation avant l'achat
				
				if ( i == 0 ) { // Valide l'achat
					Audio.playSound("/audio/Cash.wav");
					changePiece(10000);  
					write(1); 
					this.dispose();	
				}
				
			} else { // Signifie au joueur qu'il n'a pas assez de pièces
				Audio.playSound("/audio/Error.wav");
				JOptionPane.showMessageDialog(this, "Montant insuffisant !");
			}		
		}		
	}
	
	public void mouseClicked(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) { // Intercation avec le bouton lors du survol
		this.bt_1.setBackground(Color.orange);
		Audio.playSound("/audio/Bip.wav");
		
	}

	public void mouseExited(MouseEvent arg0) { // Intercation avec le bouton lorsque l'on quitte le survol
		this.bt_1.setBackground(Color.black);
	}

	public void mousePressed(MouseEvent arg0) {}
	
	public void mouseReleased(MouseEvent arg0) {}
	
	//***METHODES*****
		
    public int litPiece() { // Renvoi le nombre de pièces en mémoire
			
    	int retour = 0;
    	
	    try {
			Scanner lecture = new Scanner(new FileInputStream("files/Memoire.txt")); // Lecture des données du fichier mémoire
		    String[] separer = new String[2]; 
			String data ="";
				
			while (lecture.hasNextLine()) {
				data = lecture.nextLine();
				separer = data.split(";"); // Permet de séparer les données par le point de virgule
			    retour = Integer.parseInt(separer[1]); // Recupère le nombre de pièce ( 2ème attributs des données )
			}
		} catch (Exception e) {
			    	e.printStackTrace();
	    }
	    
	    return retour;
	}
    
    public void changePiece(int s) { // Modifie la mémoire du nombre de pièce après un achat
        
    	int piece_memoire = Piece - s;
		
		try {
			
			PrintWriter writer = new PrintWriter("files/Memoire.txt", "UTF-8");
			writer.println(getScore_max() + ";" + piece_memoire);
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void write(int numero) { // Memorise les skins acheter
    	
		FileWriter saisie; 
		Scanner lecture; 
			
		try { // Réecupère toutes les données du fichier avant d'ajouter le nouvelle utilisateur
			lecture = new Scanner(new FileInputStream("files/Memoire_boutique"));
			String text =""; 
			
			while(lecture.hasNextLine()) {
				text += lecture.nextLine() + "\n";	
			}
			
			lecture.close();
			text += numero; 
			
			saisie = new FileWriter("files/Memoire_boutique");
			saisie.write(text);
			saisie.close();
 				
		} catch (IOException e) {
			e.printStackTrace();
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
				   retour = Integer.parseInt(separer[0]); // Recupère le score max en mémoire (1er attribut en mémoire)
			    }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
        return retour;
	}

}
