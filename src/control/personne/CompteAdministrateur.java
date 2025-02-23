package control.personne;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;
import java.util.HashSet;
import java.util.Set;

/**
 *<b>CompteAdministrateur</b> est la classe qui gère le compte administrateur
 *@author VIRGAUX Pierre
 *@version 2.0
 **/


public class CompteAdministrateur implements Serializable{

	private static final long serialVersionUID = 1395120222597597878L;
	
	/**
	 *Mot de passe crypté
	 **/
	
	private SignedObject passewordCrypte;
	
	/**
	 *Déclaration des couleurs fenêtre
	 **/
	
	private Color couleurDuFond, couleurEcriture;
	
	/**
	 *Set artiste
	 *@see Artiste
	 **/
	
	private Set<Artiste> maListeArtiste;
	

	/**
	 *Définition des préférences du compte administrateur
	 *@param passeword
	 *	Mot de passe
	 *@param couleurDeFond
	 *	Couleur de fond fenêtre
	 *@param couleurEcriture
	 *	Coulur d'écriture
	 *@see Artiste
	 **/
	
	public CompteAdministrateur(String passeword, Color couleurDeFond, Color couleurEcriture) {
		this.maListeArtiste = new HashSet<Artiste>();
		this.couleurDuFond = couleurDeFond;
		this.couleurEcriture = couleurEcriture;
		securiser(passeword);
	}
	
	/**
	 *Définition des couleurs et cryptage mot de passe
	 *@param passeword
	 *	Mot de passe utilisateur
	 *@see Artiste
	 **/
	
	public CompteAdministrateur(String passeword) {
		this.maListeArtiste = new HashSet<Artiste>();
		securiser(passeword);
		this.couleurDuFond = Color.BLACK;
		this.couleurEcriture = Color.WHITE;
	}
	
	/**Récupère le mot de passe à encoder
	 *@return Mot de passe crypté
	 **/
	
	public SignedObject getPasseword() {
		return passewordCrypte;
	}
	
	/**Met en place la sécurité du mot de passe
	 *@param passeword 
	 * 	Mot de passe utilisateur
	 **/
	
	public void setPasseword(String passeword) {
		securiser(passeword);
	}
	
	/**Récupère la couleur de fond
	 *@return La couleur de fond
	 **/
	
	public Color getCouleurDuFond() {
		return couleurDuFond;
	}
	
	/**Initialise la couleur de fond
	 *@param couleurDuFond
	 *	Couleur de fond
	 **/
	
	public void setCouleurDuFond(Color couleurDuFond) {
		this.couleurDuFond = couleurDuFond;
	}
	
	/**Récupère la couleur d'écriture
	 *@return La couleur d'écriture
	 **/
	
	public Color getCouleurEcriture() {
		return couleurEcriture;
	}
	
	/**Initialise la couleur d'écriture
	 *@param couleurEcriture
	 *	couleur d'écriture
	 **/
	
	public void setCouleurEcriture(Color couleurEcriture) {
		this.couleurEcriture = couleurEcriture;
	}
		
	
	/**
	 *<p>Cette méthode est un point important de la classe Compte.
	 *En effet elle permet de sécuriser le champ du mot de passe. De plus, puisque le Serialize serait trop simplement lu
	 *via un éditeur de texte, nous avons sécurisé le fichier d'informations en utilisant l'objet SignedObject qui utilise
	 *l'algorithme de cryptage DSA.
	 *</p>
	 *<p>Le choix de cet objet s'est fait car :
	 *<ul>
	 *<li>Serializable</li>
	 *<li>Compatible avec notre méthode de sauvegarde fichier</li>
	 *</ul>
	 * </p>
	 **/
	
	private void securiser(String passeword) {//PRIVATISATION DU FICHIER DE MDP
		/**
		 *Création d'une clé
		 **/
		
		KeyPairGenerator keyGen;
		try {
			
			/**
			 *Signature d'un objet
			 **/
			
			keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
			
			/**Définit la source pour la génération de la clé
			 **/
			
			SecureRandom random;
			random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			
			/**Ici 1024 est la taille de la clé générée
			 **/
			
			keyGen.initialize(1024, random);
			
			/**
			 *Création d'une clé privée
			 **/
			
			PrivateKey signingKey = keyGen.generateKeyPair().getPrivate();
			
			/**Création d'une signature
			 **/
			
			Signature signingEngine = Signature.getInstance("DSA");
			signingEngine.initSign(signingKey);
			
			/**Signature de l'objet
			 **/
			
			this.passewordCrypte = new SignedObject(passeword, signingKey, signingEngine);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 *Permet de recherhcher un artiste, suite à la l'impossibilité de la rechercher via la fonction get()
	 *@param artisteRecherche
	 *	Artiste recherché
	 *@see Artiste
	 *@return Artiste recherché
	 **/
	
	public Artiste rechercher(Artiste artisteRecherche) {
		for(Artiste monArtiste : this.maListeArtiste)
			if(artisteRecherche == monArtiste) {
				return monArtiste;
			}
		return null;
		
	}
	
	/**
	 *Récupère la liste d'artiste
	 *@return Liste d'artiste
	 **/
	
	public Set<Artiste> getMaListeArtiste() {
		return maListeArtiste;
	}

	/**
	 *Définition de la liste d'album
	 *@param maListeArtiste
	 *	Liste d'artiste
	 *@see Artiste
	 **/
	
	public void setMaListeArtiste(Set<Artiste> maListeArtiste) {
		this.maListeArtiste = maListeArtiste;
	}


}
