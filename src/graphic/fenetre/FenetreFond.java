package graphic.fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphic.menusDeuxiemeFenetre.MenuMusique;
import graphic.menusDeuxiemeFenetre.MenuPrincipal;
import graphic.menusDeuxiemeFenetre.MenuRepresentation;
import graphic.menusDeuxiemeFenetre.TopMenuDescriptif;
import graphic.menusParametreFenetre.MenuAjoutAlbum;
import graphic.menusParametreFenetre.MenuAjoutMusique;
import graphic.menusParametreFenetre.MenuAjoutRepresentation;
import graphic.menusParametreFenetre.MenuProfilDescription;


@SuppressWarnings("serial")
public class FenetreFond extends JFrame {
	
	private static FenetreFond instance;
	
	private static final Dimension dimFenetre = new Dimension(1200, 800);
	
	//On déclare une image de fond qui peut être un gif pour que ça soit un peu plus dinamique.
	private JLabel ImageFond;
	
	//Permet de savoir si la fenêtre de fond est active en d'autre terme si on s'est login ou pas
	private boolean FenetreFondDepartActive;
	
	/**
	 * Ici on le construit la fenêtre de fond qui sera la fenêtre principale.
	 * A la base elle ne contient qu'une image mais par la suite, elle contiendra le menu avec les différents onglets.
	 */
	
	private FenetreFond() {
		//On donne la bonne dimension à la fenêtre 
		setSize(dimFenetre);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		//On utilise un JLabel qui permet d'ajouter une image à l'intérieur
		ImageFond = new JLabel(new ImageIcon("ImageDeFond/ImageAnime.gif"));
		//On ajouter le Label à la fenêtre
		this.add(ImageFond);
		//Permet d'enlever le contour de la fenètre, il est donc impossible de redimentionner ou de fermer la fenêtre
		setUndecorated(true);
		setVisible(true);
		//Rend impossible le redimentionnement de la fenêtre
		setResizable(false);
		//Donne la possibilité à la sourie d'être utilisée.
		setFocusable(true);
		//On ne s'est pas encore login
		this.FenetreFondDepartActive = true;
	}
	
	/**
	 * Va permettre de se déconnecter et de se reconnecter avec un autre utilisateur SQL
	 * On ajoute dans la fenêtre les onglets
	 * 
	 * @param login
	 */

	public void changerFenetre(String login) {
		if(FenetreFondDepartActive) {
			//On rend la fenêtre invisible
			dispose();
			//On enlève l'image de fond
			this.remove(ImageFond);
			//On remet le menu permettant de fermer et redimensionner la fenêtre
			setUndecorated(false);
			//On donne une couleur au fond de la fenêtre
			getContentPane().setBackground(new Color(100,100,100));
			//On définit le BorderLayout pour organiser les différents panels
			JPanel intermediaire = new JPanel(new BorderLayout());
			intermediaire.add(TopMenuDescriptif.getInstance(login));
			//On met ici la barre de menu en haut pour naviger dans l'applie
			this.add(intermediaire, BorderLayout.NORTH);
			this.add(MenuPrincipal.getInstance(login), BorderLayout.CENTER);
			setResizable(true);
			setVisible(true);
			setFocusable(true);
			//On est login donc la fenêtre de fond n'est plus active.
			FenetreFondDepartActive = false;
		}
		else {
			dispose();
			this.getContentPane().removeAll();
			this.add(ImageFond);
			setUndecorated(true);
			setResizable(false);
			setFocusable(true);
			FenetreFondDepartActive = true;
			setVisible(true);
			FenetreLogin.getInstance().setVisible(true);
		}
	}
	
	public void retourEtatInitial(String login) {
		dispose();
		MenuPrincipal.getInstance(login).setArtisteSelectionne(null);
		MenuMusique.getInstance(login).setArtiste(null);
		MenuAjoutAlbum.getInstance(login).setArtiste(null);
		MenuAjoutMusique.getInstance(login).setArtiste(null);
		MenuAjoutRepresentation.getInstance(login).setArtiste(null);
		MenuRepresentation.getInstance(login).setArtiste(null);
		MenuProfilDescription.getInstance(login).setArtiste(null);
		TopMenuDescriptif.getInstance(login).setArtiste(null);
		TopMenuDescriptif.getInstance(login).updateVersInitial();
		
		this.getContentPane().removeAll();
		JPanel intermediaire = new JPanel(new BorderLayout());
		
		intermediaire.add(TopMenuDescriptif.getInstance(login));
		this.add(intermediaire, BorderLayout.NORTH);
		MenuMusique.getInstance(login).update();
		MenuPrincipal.getInstance(login).update();
		MenuRepresentation.getInstance(login).update();
		this.add(MenuPrincipal.getInstance(login), BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static FenetreFond getInstance() {
		if (instance == null)
			instance = new FenetreFond();
		return instance;
	}

}