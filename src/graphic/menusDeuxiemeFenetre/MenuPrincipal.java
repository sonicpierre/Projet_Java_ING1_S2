package graphic.menusDeuxiemeFenetre;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import control.elementSauv.personnesDejaInscrite;
import control.personne.Acteur;
import control.personne.Artiste;
import graphic.fenetre.FenetreFond;
import graphic.menusParametreFenetre.MenuProfilDescription;

@SuppressWarnings("serial")
public class MenuPrincipal extends JTabbedPane{

	private static MenuPrincipal instance;
	
	private String login;
	private JPanel constructionPanel;
	private JScrollPane musique;
	private JScrollPane artiste;
	private Artiste artisteSelectionne;
	
	private MenuPrincipal(String login) {
		this.login = login;
		
		musique = new JScrollPane();
		JPanel menuMusique = MenuMusique.getInstance(login);
		musique.setPreferredSize(new Dimension(100, MenuMusique.getInstance(login).getNombreDeMusique() * 160));
		musique.setViewportView(menuMusique);
		
		artiste = new JScrollPane();
		artiste.setPreferredSize(new Dimension(100,100));
		artiste.setViewportView(constructionDuMenuPrincipal());
		
		
		this.add(artiste, "Artiste");
		this.add(musique, "Musique");
		
	}
	
	
	private JPanel constructionDuMenuPrincipal() {
		int nombreDeLigne = personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).getMaListeArtiste().size();
		if(personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).getMaListeArtiste().isEmpty())
			constructionPanel = new JPanel();
		else {
			constructionPanel = new JPanel(new GridLayout(nombreDeLigne%4 + 1, 4, 10, 20));
			for(Artiste monArtiste : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).getMaListeArtiste())
				constructionPanel.add(constructionCase(monArtiste));
		}
		return constructionPanel;
	}
	
	private JPanel constructionCase(Artiste artiste) {
		JPanel constructionCase = new JPanel(new FlowLayout());
		ImageIcon monImage = new ImageIcon(new ImageIcon(artiste.getCheminVersImage()).getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));//REDIMENSIUON IMG 150 PAR 150
		JLabel maPhoto = new JLabel(monImage);
		maPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(artiste.getType().equals("Chanteur")) {
					setArtisteSelectionne(artiste);
					MenuAjoutAlbum.getInstance(login).setArtiste(artiste);
					MenuProfilDescription.getInstance(login).setArtiste(artiste);
					MenuAjoutMusique.getInstance(login).update();
					MenuMusique.getInstance(login).update();
					MenuPrincipal.getInstance(login).removeAll();
					JScrollPane temporaireMusique = new JScrollPane();
					temporaireMusique.setViewportView(MenuMusique.getInstance(login));
					MenuPrincipal.getInstance(login).add(temporaireMusique,"Musique de " + artiste.getNom());
					MenuPrincipal.getInstance(login).validate();
					TopMenuDescriptif.getInstance(login).setArtiste(artiste);
					TopMenuDescriptif.getInstance(login).updateVersChanteur();
				}
			}
		});
		JLabel nomArtiste = new JLabel(artiste.getNom());
		JLabel prenomArtiste = new JLabel(artiste.getPrenom());
		constructionCase.add(maPhoto);
		constructionCase.add(nomArtiste);
		constructionCase.add(prenomArtiste);
		return constructionCase;
	}
	
	public void update() {
		this.removeAll();
		musique = new JScrollPane();
		JPanel menuMusique = MenuMusique.getInstance(login);
		musique.setPreferredSize(new Dimension(100, MenuMusique.getInstance(login).getNombreDeMusique() * 160));
		musique.setViewportView(menuMusique);
		
		artiste = new JScrollPane();
		artiste.setPreferredSize(new Dimension(100,100));
		artiste.setViewportView(constructionDuMenuPrincipal());
		
		
		this.add(artiste, "Artiste");
		this.add(musique, "Musique");
	}
	
	
	public static MenuPrincipal getInstance(String login) {
		if (instance == null)
			instance = new MenuPrincipal(login);
		return instance;
	}


	public Artiste getArtisteSelectionne() {
		return artisteSelectionne;
	}


	public void setArtisteSelectionne(Artiste artisteSelectionne) {
		this.artisteSelectionne = artisteSelectionne;
	}

	
	
}
