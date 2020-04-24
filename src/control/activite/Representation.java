package control.activite;

import java.io.Serializable;

public class Representation implements Serializable{

	private static final long serialVersionUID = 1875901704643110886L;
	
	private String titre, cheminVersImage, duree;

	
	public Representation(String titre, String duree, String cheminVersImage) {
		this.setTitre(titre);
		this.duree = duree;
		this.cheminVersImage = cheminVersImage;

	}
	
	@Override
	public boolean equals(Object represAComparer) {
		Representation representationAComparer = (Representation) represAComparer;
		if(this.titre.equals(representationAComparer.getTitre()) && (this.duree == representationAComparer.duree))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int compteurFinal = 0;
		
		char[] monTitre = this.getTitre().toCharArray();

		for(char titre : monTitre)
			compteurFinal+= (int) titre;
		
		return compteurFinal;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getCheminVersImage() {
		return cheminVersImage;
	}

	public void setCheminVersImage(String cheminVersImage) {
		this.cheminVersImage = cheminVersImage;
	}
}
