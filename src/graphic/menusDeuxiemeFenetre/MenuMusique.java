package graphic.menusDeuxiemeFenetre;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileSystemView;

import control.elementSauv.personnesDejaInscrite;
import control.musique.Album;
import control.musique.Titre;
import control.personne.Artiste;

//
@SuppressWarnings("serial")
public class MenuMusique extends JPanel{

	private static MenuMusique instance;
	private JPanel menuFinal;
	private String login;
	private Artiste artiste;
	private int nombreDeMusique;
	Map<JCheckBox, Titre> mesAssociationsCheckTitre;
	private Titre titreEnCoursDeLecture;
	//A chaque musique sont associé des bouttons :) 
	
	private MenuMusique(String login) {
		this.login = login;
		this.artiste = null;
		this.titreEnCoursDeLecture = null;
		if(artiste == null)
			constructionDuPanelGlobal();
			
		//On utilise ça pour les JScrollPan pour ajouter à la place de add un JScrollPane ne peut gérer qu'un seul élément à la foi
		this.add(menuFinal);
	}
	

	private void constructionDuPanelGlobal() {
		mesAssociationsCheckTitre = new HashMap<JCheckBox, Titre>();
		nombreDeMusique = 0;
		boolean premierPassage = false;
		if(artiste == null) {
			nombreDeMusique = 0;
			for(Artiste monArtiste : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).getMaListeArtiste()) {//POUR TOUS LES STYLES DE ZIK, ON PASSE TOUS LES ALBUMS YANT UN CERTAIN STYLE, ON COMPTE LEUR NOMBRE DE TITRE DANS L'LABUM => NBR DE LIGNE DU TABLEAU QUON VEUR AFFICHER
				for(Album monAlbum : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(monArtiste).getMaListeDeAlbums())	//POUR TOUS LES ALBUM DAN SLA LISTE D'ALBUM,SAUVEGARDER
					nombreDeMusique+= monAlbum.getChansonsDelAlbum().size() + 1;//COMPTE DU NBR DE TITRE + 1 CAR RAJOUT D'UNE LIGNE NOM + PHOTO
			}
		} else {
			nombreDeMusique = 0;
			for(Album monAlbum : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(artiste).getMaListeDeAlbums())	//POUR TOUS LES ALBUM DAN SLA LISTE D'ALBUM,SAUVEGARDER
				nombreDeMusique+= monAlbum.getChansonsDelAlbum().size() + 1;//COMPTE DU NBR DE TITRE + 1 CAR RAJOUT D'UNE LIGNE NOM + PHOTO
		}
		
			//CREATION COLONNES: NOM DE TITRE + GESTION LECTURE
			menuFinal = new JPanel(new GridLayout(nombreDeMusique, 2, 20, 10));
			
			menuFinal.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 10));//BORDURE
			
			if(artiste == null) {
				for(Artiste monArtiste : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).getMaListeArtiste()) {
					for(Album monAlbum : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(monArtiste).getMaListeDeAlbums()) {//POUR TOUS LES ALBUM INSCRIS
						premierPassage = true;
						for(Titre monTitre : monAlbum.getChansonsDelAlbum()) {//ON PASSSE TOUTES LES MUSIQUES
							if(premierPassage) {
								JLabel album = new JLabel("<html><FONT color=\"#ff0000\" size = \"6\" face=\"Times New Roman\">"+ monAlbum.getTitre() +"</FONT></html>");//ECRITURE EN HTML PERMETTAT DES PREZ FOLIFOLI
								menuFinal.add(album);//AJOUT TITRE 
								ImageIcon monImage = new ImageIcon(new ImageIcon(monAlbum.getCheminVersImageAssocie()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));//REDIMENSIUON IMG 150 PAR 150
								JLabel imageCorespondante = new JLabel(monImage);
								
								imageCorespondante.addMouseListener(new MouseAdapter() {
	
									@Override
									public void mousePressed(MouseEvent event) {
										monAlbum.setSelected(true);
										if(event.isPopupTrigger()) {
											createPopupMenu(monAlbum).show(event.getComponent(), event.getX(), event.getY());
										}
									}
	
								});
								
								menuFinal.add(imageCorespondante);//BOUTON CORRESPONDANT AU CONTROL DE LA ZIK
								premierPassage = false;
							}
								
							JPanel temponConstruction = constructionEtiquette(monTitre);//TITRE ZIK
							menuFinal.add(temponConstruction);
							JPanel bouttons = lesBouttonsDeControle(monTitre);//CONTROLE LECTURE ZIK
							menuFinal.add(bouttons);
						}
					}
				} 
			}else {
					
					for(Album monAlbum : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(artiste).getMaListeDeAlbums()) {//POUR TOUS LES ALBUM INSCRIS
						premierPassage = true;
						for(Titre monTitre : monAlbum.getChansonsDelAlbum()) {//ON PASSSE TOUTES LES MUSIQUES
							if(premierPassage) {
								JLabel album = new JLabel("<html><FONT color=\"#ff0000\" size = \"6\" face=\"Times New Roman\">"+ monAlbum.getTitre() +"</FONT></html>");//ECRITURE EN HTML PERMETTAT DES PREZ FOLIFOLI
								menuFinal.add(album);//AJOUT TITRE 
								ImageIcon monImage = new ImageIcon(new ImageIcon(monAlbum.getCheminVersImageAssocie()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));//REDIMENSIUON IMG 150 PAR 150
								JLabel imageCorespondante = new JLabel(monImage);
								
								imageCorespondante.addMouseListener(new MouseAdapter() {
	
									@Override
									public void mousePressed(MouseEvent event) {
										monAlbum.setSelected(true);
										if(event.isPopupTrigger()) {
											createPopupMenu(monAlbum).show(event.getComponent(), event.getX(), event.getY());
										}
									}
	
								});
								
								menuFinal.add(imageCorespondante);//BOUTON CORRESPONDANT AU CONTROL DE LA ZIK
								premierPassage = false;
							}
								
							JPanel temponConstruction = constructionEtiquette(monTitre);//TITRE ZIK
							menuFinal.add(temponConstruction);
							JPanel bouttons = lesBouttonsDeControle(monTitre);//CONTROLE LECTURE ZIK
							menuFinal.add(bouttons);
						}
					}
						
				}
		}
	
	
	private JPanel constructionEtiquette(Titre titre) {//CONSTRUCTION PANEL ETIQUETTE (TITRE) DE LA CHANSON
		JPanel description = new JPanel(new FlowLayout());
		JCheckBox maCheckBox = new JCheckBox(titre.getTitre());
		mesAssociationsCheckTitre.put(maCheckBox, titre);
		description.add(maCheckBox);
		return description;
	}
	
	
	private JPanel lesBouttonsDeControle(Titre titreAssocie) {//BOUTON DE COTROLE => CONTROLE LECTEUR DOU LE TRIRE ASSOCIÉ
		
		JPanel menuInter = new JPanel(new FlowLayout());
		JButton play = new JButton("Play");
		JButton stop = new JButton("Stop");
		JButton reset = new JButton("Reset");
		play.addActionListener((event)-> play(titreAssocie));//DEFINITION DE L'ACTION DU BOUTON CORRESPONDANT AU BON TITRE @SEE TITRE
		stop.addActionListener((event)-> stop());
		reset.addActionListener((event)-> reset());
		menuInter.add(play);
		menuInter.add(stop);
		menuInter.add(reset);
		return menuInter;
	}
	
	public void update() {//
		this.removeAll();
		this.artiste = MenuPrincipal.getInstance(login).getArtisteSelectionne();
		constructionDuPanelGlobal();//RECLACULE INTERIEUR PANEL
		this.add(menuFinal);//REAJOUTE AUX ONGLETS
		
		this.validate();//RECALCUL DE L'EMPLACMENT DES LAYOUT 
	}
	
	public static MenuMusique getInstance(String login) {
		if (instance == null)
			instance = new MenuMusique(login);
		return instance;
	}
	
	public void checkOperation(String operation) {
	      Set<Entry<JCheckBox, Titre>> set = mesAssociationsCheckTitre.entrySet();
	      Iterator<Entry<JCheckBox, Titre>> monIterateur = set.iterator();
	      boolean passage = false;
	      boolean passagePlayer = false;
	      while(monIterateur.hasNext()){
	         Entry<JCheckBox, Titre> e = monIterateur.next();
	         if(e.getKey().isSelected() && operation.equals("Supprimer")) {
	        	 e.getValue().supprimerMusique();
	        	 passage = true;
	        	 //On supprime un album qui est vide
	        	 if(e.getValue().getAlbumAssocie().getChansonsDelAlbum().isEmpty()) {
	        		 personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(artiste).getMaListeDeAlbums().remove(e.getValue().getAlbumAssocie());
	        		 personnesDejaInscrite.getInstance().sauvegarder();
	        	 }
	         } else if(e.getKey().isSelected() && operation.equals("Play")) {
	        	 this.play(e.getValue());
	        	 passagePlayer = true;
	         } else if(e.getKey().isSelected() && operation.equals("Stop")) {
	        	 this.stop();
	        	 passagePlayer = true;
	         } else if(e.getKey().isSelected() && operation.equals("Reset")) {
	        	 this.reset();
	        	 passagePlayer = true;
	         }
	      }
	      if(passage && !passagePlayer)
	    	  this.update();
	      else if(!passage && !passagePlayer){
	    	  JMenuItem indication = new JMenuItem();
	    	  JOptionPane.showMessageDialog(indication,"Aucune musique sélectionnée");
	      }
	}
	
	
	
	
	private JPopupMenu createPopupMenu(Album albumAssocie) {
		JPopupMenu monPopUp = new JPopupMenu();
		monPopUp.add(MenuRaccourcis.getInstance(login, artiste).actSuppressionAlbum);
		monPopUp.add(MenuRaccourcis.getInstance(login, artiste).actRenommer);
		monPopUp.add(MenuRaccourcis.getInstance(login, artiste).actChangerImage);
		return monPopUp;
	}

	public void supprimerAlbum() {
		for(Album monAlbum : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(artiste).getMaListeDeAlbums())
			if(monAlbum.isSelected()) {
				personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(artiste).getMaListeDeAlbums().remove(monAlbum);
				personnesDejaInscrite.getInstance().sauvegarder();
				break;
			}
				
	}
	
	public void renommerAlbum() {
		for(Album monAlbum : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(artiste).getMaListeDeAlbums())
			if(monAlbum.isSelected()) {
				monAlbum.setTitre("Momo va aux moules");
				personnesDejaInscrite.getInstance().sauvegarder();
				break;
			}
				
	}
	
	public void changerImage() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = jfc.getSelectedFile();
			for(Album monAlbum : personnesDejaInscrite.getInstance().getMaListDePersonneInscrite().get(login).rechercher(artiste).getMaListeDeAlbums())
				if(monAlbum.isSelected()) {
					monAlbum.setCheminVersImageAssocie(selectedFile.getAbsolutePath());
					personnesDejaInscrite.getInstance().sauvegarder();
					break;
				}
		}
		
	}
	
	
	private void play(Titre titreAssocie) {
		if(titreEnCoursDeLecture==null) {
			this.titreEnCoursDeLecture = titreAssocie;
			this.titreEnCoursDeLecture.play();
		} else if(titreEnCoursDeLecture.isPlaying() && !titreEnCoursDeLecture.isEnPause()) {
			this.titreEnCoursDeLecture.stop();
			this.titreEnCoursDeLecture = titreAssocie;
			this.titreEnCoursDeLecture.play();
		} else {
			this.titreEnCoursDeLecture = titreAssocie;
			this.titreEnCoursDeLecture.play();
		}
			
	}
	
	
	private void stop() {
		if(titreEnCoursDeLecture!=null)
			titreEnCoursDeLecture.stop();
		else {
    	  JMenuItem indication = new JMenuItem();
    	  JOptionPane.showMessageDialog(indication,"Aucun Titre est joué");
		}
	}
	
	private void reset() {
		if(titreEnCoursDeLecture!=null)
			titreEnCoursDeLecture.reset();
		else {
	    	  JMenuItem indication = new JMenuItem();
	    	  JOptionPane.showMessageDialog(indication,"Aucun Titre est joué");
			}
	}


	public Titre getTitreEnCoursDeLecture() {
		return titreEnCoursDeLecture;
	}


	public void setTitreEnCoursDeLecture(Titre titreEnCoursDeLecture) {
		this.titreEnCoursDeLecture = titreEnCoursDeLecture;
	}


	public Artiste getArtiste() {
		return artiste;
	}


	public void setArtiste(Artiste artiste) {
		this.artiste = artiste;
	}


	public int getNombreDeMusique() {
		return nombreDeMusique;
	}


	public void setNombreDeMusique(int nombreDeMusique) {
		this.nombreDeMusique = nombreDeMusique;
	}
	
	
	
}