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

//Classe qui g�re la boutique du jeu
public class Boutique extends JFrame implements ActionListener, MouseListener {
	
	private JButton bt_1; // Bouton avec l'image du skin pour l'achat
	private int Piece; // Attributs qui r�cup�re les pi�ces d�ja collect�s dans le jeu
	
	 public Boutique(){
	    	
	    	//Initialisation fen�tre
	    	super("JetPack - Boutique");

	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        this.setSize(600,400);
	        this.setLocationRelativeTo(null); // au centre de l'�cran
	        this.setResizable(false); // impossible de changer sa taille
	        this.setAlwaysOnTop(true);
	        
	        this.Piece = litPiece(); // Renvoi le nombre de pi�ces collect�es en m�moire
	        
	        //Cr�ation du BoutonImage     
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
			
			// Visibilit� des �l�ments			
			panel.add(bt_1);
			this.add(panel); 
			this.setVisible(true);
	        	  
	    }


	 //****METHODES EVENT*****
	 
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == bt_1 ) { // Skin 1
			
			if (litPiece() > 10000) { // Prix du 1er skin = 10'000 Pi�ces
				
				int i = JOptionPane.showConfirmDialog(this, "Confirmer l'achat ?", "Confirmer", JOptionPane.YES_NO_OPTION); // Demande la confirmation avant l'achat
				
				if ( i == 0 ) { // Valide l'achat
					Audio.playSound("/audio/Cash.wav");
					changePiece(10000);  
					write(1); 
					this.dispose();	
				}
				
			} else { // Signifie au joueur qu'il n'a pas assez de pi�ces
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
		
    public int litPiece() { // Renvoi le nombre de pi�ces en m�moire
			
    	int retour = 0;
    	
	    try {
			Scanner lecture = new Scanner(new FileInputStream("files/Memoire.txt")); // Lecture des donn�es du fichier m�moire
		    String[] separer = new String[2]; 
			String data ="";
				
			while (lecture.hasNextLine()) {
				data = lecture.nextLine();
				separer = data.split(";"); // Permet de s�parer les donn�es par le point de virgule
			    retour = Integer.parseInt(separer[1]); // Recup�re le nombre de pi�ce ( 2�me attributs des donn�es )
			}
		} catch (Exception e) {
			    	e.printStackTrace();
	    }
	    
	    return retour;
	}
    
    public void changePiece(int s) { // Modifie la m�moire du nombre de pi�ce apr�s un achat
        
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
			
		try { // R�ecup�re toutes les donn�es du fichier avant d'ajouter le nouvelle utilisateur
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
    
    public int getScore_max() { // Lit le score maximal du fichier en m�moire
		
		int retour = 0;
		
        try {
			   Scanner lecture = new Scanner(new FileInputStream("files/Memoire.txt")); // Lecture des donn�es du fichier m�moire
			   String[] separer = new String[2]; 
			   String data ="";
			
			    while (lecture.hasNextLine()) {
				    data = lecture.nextLine();
				   separer = data.split(";"); // Permet de s�parer les donn�es par le point de virgule
				   retour = Integer.parseInt(separer[0]); // Recup�re le score max en m�moire (1er attribut en m�moire)
			    }
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
        return retour;
	}

}
