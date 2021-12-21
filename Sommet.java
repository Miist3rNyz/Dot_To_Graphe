import java.util.ArrayList;
import java.util.Vector;

public class Sommet {
	int id;
	int couleur=-1;
	Vector<Sommet> voisins=new Vector<Sommet>();
	Vector<Integer> poids = new Vector<Integer>();
	boolean flag=false;
	
	/**
	 * Constructeur utilisant un identifiant pour le sommet.
	 * @param id La valeur de l'identifiant du sommet.
	 */
	Sommet(int id) {
		this.id=id;
	}
	
	/**
	 * Ajoute un sommet dans la lsite des voisins du sommet.
	 * @param s Le sommet $agrave; ajouter.
	 */
	void addVoisin(Sommet s) {
		voisins.add(s);
		poids.add(1);
	}
	
	void addVoisin(Sommet s, int p) {
		voisins.add(s);
		poids.add(p);
	}
	
	int getPoids(Sommet voisin) {
		return poids.get(voisins.indexOf(voisin));
	}
	
	void incrPoids(Sommet voisin) {
		for (int i=0;i<voisins.size();i++) {
			if (voisins.get(i).id==voisin.id)poids.set(i,poids.get(i)+1);
		}
	}
	
	void decrPoids(Sommet voisin) {
		for (int i=0;i<voisins.size();i++) {
			if (voisins.get(i).id==voisin.id)poids.set(i,poids.get(i)-1);
		}
	}
	
	public boolean equals(Sommet s) {
		if (s.id==id) return true;
		else return false;
	}
	
	int getCouleur() {
		return couleur;
	}
	
	void setCouleur(int couleur) {
		this.couleur=couleur;
	}
	
	int couleurAdmissible() {
		ArrayList<Integer> couleurVoisins=new ArrayList<Integer>();
		for (Sommet v : voisins) {
			if (!couleurVoisins.contains(v.getCouleur())) couleurVoisins.add(v.getCouleur());
		}
		int couleur=0;
		while(true) {
			if (!couleurVoisins.contains(couleur)) return couleur;
			couleur++;
		}
	}
	/**
	 * V&eacute;rifie si un sommet est bien voisin du sommet de l'instence courante.
	 * @param s Le voisin potentiel.
	 * @return true or false.
	 */
	boolean isVoisin(Sommet s) {
		return voisins.contains(s);
	}
	
	int degre() {
		return voisins.size();
	}
	
	@Override
	public String toString() {
		if (voisins.size()==0) return id+":()";
		String str=id+":(";
		for (int i=0;i<voisins.size()-1;i++)str+=voisins.get(i).id+",";
		return str+voisins.get(voisins.size()-1).id+")";
	}
	
	public String toDOT() {
		String str="";
		for (int i=0;i<voisins.size();i++) {
			if (id<voisins.get(i).id) str+="\""+id+"c"+couleur+"\""+" -- "+"\""+voisins.get(i).id+"c"+voisins.get(i).getCouleur()+"\""+" [label="+poids.get(i)+"];\n";
		}
		return str;
	}
	
	public String toDOTOriente() {
		String str="";
		for (int i=0;i<voisins.size();i++) {
			str+="\""+id+"c"+couleur+"\""+" -> "+"\""+voisins.get(i).id+"c"+voisins.get(i).getCouleur()+"\""+" [label="+poids.get(i)+"];\n";
		}
		return str;
	}
	
	public String toDOT(Graphe arbre) {
		String str="";
		for (int i=0;i<voisins.size();i++) {
			if (id<voisins.get(i).id) {
				if (arbre.isVoisin(id,voisins.get(i).id)) str+="\""+id+"c"+couleur+"\""+" -- "+"\""+voisins.get(i).id+"c"+voisins.get(i).getCouleur()+"\""+" [color=red,label="+poids.get(i)+"];\n";
				else str+="\""+id+"c"+couleur+"\""+" -- "+"\""+voisins.get(i).id+"c"+voisins.get(i).getCouleur()+"\""+" [label="+poids.get(i)+"];\n";
			}
		}
		return str;
	}
	
	public String toDOTOriente(Graphe arbre) {
		String str="";
		for (int i=0;i<voisins.size();i++) {
				if (arbre.isVoisin(id,voisins.get(i).id)) str+="\""+id+"c"+couleur+"\""+" -> "+"\""+voisins.get(i).id+"c"+voisins.get(i).getCouleur()+"\""+" [color=red,label="+poids.get(i)+"];\n";
				else str+="\""+id+"c"+couleur+"\""+" -> "+"\""+voisins.get(i).id+"c"+voisins.get(i).getCouleur()+"\""+" [label="+poids.get(i)+"];\n";
		}
		return str;
	}
}
