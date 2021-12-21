import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

public class Graphe {
	Vector<Sommet> sommets=new Vector<Sommet>();
	int size;
	
	public Graphe(int size){
		this.size=size;
		for(int id=0;id<size;id++) {
			sommets.add(new Sommet(id));
		}
	}
	
	public Graphe() {
	}
	
	public Graphe(Graphe G) {
		this.size=G.getSize();
		for(int id=0;id<size;id++) {
			sommets.add(new Sommet(id));
		}
		for (int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				if (G.isVoisin(i,j)) addArete(i,j,G.sommets.get(i).getPoids(G.sommets.get(j)));
			}
		}
	}
	
	public int getSize() {
		return size;
	}

	public void addArete(int i, int j) {
		sommets.get(i).addVoisin(sommets.get(j));
		sommets.get(j).addVoisin(sommets.get(i));
	}
	
	public void addArete(int i, int j,int p) {
		sommets.get(i).addVoisin(sommets.get(j),p);
		sommets.get(j).addVoisin(sommets.get(i),p);
	}
		
	public boolean isVoisin(int i, int j) {
		return sommets.get(i).isVoisin(sommets.get(j));
	}

	@Override
	public String toString() {
		String str="";
		for (int i=0;i<size;i++)str+=sommets.get(i)+" ";
		return str;
	}
	
	public String toDOT() {
		String str="graph G{\n";
		for (int i=0;i<sommets.size();i++) {
			str+=sommets.get(i).toDOT();
		}
		return str+"}";
	}
	
	public int degreMax() {
		int dMax=0;
		for (Sommet s : sommets) {
			if (s.degre()>dMax) dMax=s.degre();
		}
		return dMax;
	}
	
	public String toDOT(Graphe arbre) {
		String str="graph G{\n";
		for (int i=0;i<sommets.size();i++) {
			str+=sommets.get(i).toDOT(arbre);
		}
		return str+"}";
	}
	
	public Arbre BFS(int racine) {
	Arbre arbre = new Arbre(size,racine);
	LinkedList<Sommet> Q = new LinkedList<Sommet>();
	Q.add(sommets.get(racine));
	sommets.get(racine).flag=true;
	while(!Q.isEmpty()) {
		Sommet sommetCourant = Q.getFirst();
		for (Sommet voisinCourant : sommetCourant.voisins) {
			if (voisinCourant.flag==false) {
				Q.add(voisinCourant);
				voisinCourant.flag=true;
				arbre.addArete(sommetCourant.id,voisinCourant.id);
			}
		}
		Q.remove();
	}
	for (Sommet s : sommets) s.flag=false;
	return arbre;
	}
	
	public Graphe Dijkstra(int racine) {
		Graphe arbre = new Graphe(size);
		ArrayList<Sommet> S = new ArrayList<Sommet>(); // S pour sommet
		ArrayList<Integer> P = new ArrayList<Integer>(); // P pour poids
		ArrayList<Sommet> O = new ArrayList<Sommet>(); // O pour origine
		// Initialisation des listes
		for (Sommet voisinCourant : sommets.get(racine).voisins) {
			S.add(voisinCourant);
			O.add(sommets.get(racine));
			P.add(sommets.get(racine).getPoids(voisinCourant));
		}
		sommets.get(racine).flag=true;
		
		while(!S.isEmpty()) {
			int index=P.indexOf(Collections.min(P));
			Sommet sommetCourant = S.get(index);
			int poidsCourant = P.get(index);
			arbre.addArete(O.get(index).id,sommetCourant.id,poidsCourant);
			
			for (Sommet voisinCourant : sommetCourant.voisins) {
				if (!S.contains(voisinCourant) && !voisinCourant.flag) {
					S.add(voisinCourant);
					P.add(poidsCourant+sommetCourant.getPoids(voisinCourant));
					O.add(sommetCourant);
				}
				else if(!voisinCourant.flag && poidsCourant+sommetCourant.getPoids(voisinCourant)<P.get(S.indexOf(voisinCourant))) {
					int indexVoisin=S.indexOf(voisinCourant);
					P.set(indexVoisin,poidsCourant+sommetCourant.getPoids(voisinCourant));
					O.set(indexVoisin,sommetCourant);
				}
			}
			
			sommetCourant.flag=true;
			S.remove(index);
			P.remove(index);
			O.remove(index);
		}
		for (Sommet s : sommets) s.flag=false;
		return arbre;
		}
	
	public void coloration() {
		for (Sommet sommetCourant : sommets) {
			//On cherche une couleur qui n'est pas prÃ©sente chez les voisins.
			int couleur=-1;
			boolean fini=false;
			while(!fini) {
				couleur++;
				fini=true;
				for (Sommet voisinCourant: sommetCourant.voisins) {
					if (voisinCourant.getCouleur()==couleur) fini=false;
				}
			}
			//On donne la couleur qui convient Ã  sommetCourant.
			sommetCourant.setCouleur(couleur);
		}
	}
	
	public void coloration2() {
		for (Sommet sommetCourant : sommets) {
			sommetCourant.setCouleur(sommetCourant.couleurAdmissible());
		}
	}
	public Graphe ToGraphe(String file) throws IOException  {
        // ---------------------------------------
        BufferedReader reader1;    
        //lecture du dot avec ajout des arretes 
        try {
            reader1 = new BufferedReader(new FileReader(file));
            String line = reader1.readLine();
           // System.out.println(line);
            String[] lines= line.split("//");
            line = reader1.readLine();
            Graphe arbre = new Graphe(Integer.parseInt(lines[1]));
            while (line != null) {
            	String[] deb= line.split("color=\"");		// permet d'assigner une couleur a un sommet si c'est présent dans le fichier dot 
            	
                if (deb.length==2) coloregraphe(arbre,line,deb);	// dans ce cas on appelle la méthode coloregraphe
                traitement(arbre,line);				// puis traitement de l'arbre
                //System.out.println(line);	
                line = reader1.readLine(); // read next line
                }
            reader1.close();
            return arbre;
            
                
                
            }catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());}
        return null;
    }
    private void traitement(Graphe arbre,String line) {
        String[] parts = line.split(" -- ");				// on split a l'element " -- " ce qui permet de recuperer un tableau avec le premier chiffre et le reste de la ligne 
        process(arbre,line,parts);   		// on lance la méthode process sur l'arbre concerné avec le nombre de ligne et avec le tableau du premier split 
    }
    protected void process(Graphe arbre,String line,String[] parts) {		
        if (parts.length == 2) { 								// si le premier split à 2 partie
            String[] parts2 = parts[1].split(" ");							// on split a nouveau a l'espace la deuxième élement du premier split
            if (parts2.length == 1)											// si ce nouveau split comporte seulement 1 element
                arbre.addArete(Integer.parseInt(parts[0]), Integer.parseInt(parts2[0]));		// on ajoute le premier element contenu dans le premier tableau du split et on ajoute le premier 
																								//element du deuxième split ce qui correspond aux chiffres de chaque coté de la fleche
            else {														// sinon cela veut dire qu'il y a des options concernant les lignes
                String[] parts3 = parts2[1].split(",");					// on split a la virgule
                String[] parts4= parts3[0].split("=");						// on split au égale
                if (parts4[0].equals("[label")) {						// si le premier element du 4 eme split est [label alors
                    String[] parts5 = parts4[1].split(" ");				// on resplit le deuxieme element du 4 eme split au egal
                    arbre.addArete(Integer.parseInt(parts[0]), Integer.parseInt(parts2[0]),Integer.parseInt(parts5[0]));		// on ajoute l'arrete avec les deux chiffres et le poids
                }else
                    arbre.addArete(Integer.parseInt(parts[0]), Integer.parseInt(parts2[0]),1);	// sinon on ajoute les deux chiffres avec juste un poids de 1 

            }
        }
    }
    protected void coloregraphe(Graphe arbre,String line,String[] deb) {
            //dictionnaire des couleurs
            Dictionary<String, Integer> color= new Hashtable<String, Integer>();			// creation d'un dictionnaire
            color.put("black\"]",-1);			// la couleur noir correspond a un poids de -1
            color.put("red\"]",2);				// la couleur rouge correspond a un poids de 2 
            color.put("blue\"]",3);				// poids de 3 
            color.put("yellow\"]",4);			// poids de 4 
            color.put("purple\"]",5);			// poids de 5 
            color.put("green\"]",6);			// poids de 6 
            String[] deb2=deb[0].split(" ");	// on split le premier element de deb a l'espace
            
            arbre.sommets.get(Integer.parseInt(deb2[0])).setCouleur((int) color.get(deb[1]));	// on assigne au sommet concerner par la couleur le poids correspondant a la couleur
            
        }
}
