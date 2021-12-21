import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class GrapheOriente extends Graphe {
	public GrapheOriente(GrapheOriente g) {
		super(g);
	}
	
	public GrapheOriente(int size) {
		super(size);
	}
	
	@Override
	public void addArete(int i,int j, int p) {
		sommets.get(i).addVoisin(sommets.get(j),p);
	}
	
	@Override
	public void addArete(int i,int j) {
		sommets.get(i).addVoisin(sommets.get(j));
	}
	
	public String toDOT() {
		String str="digraph G{\n";
		for (int i=0;i<sommets.size();i++) {
			str+=sommets.get(i).toDOTOriente();
		}
		return str+"}";
	}
	
	public String toDOT(Graphe arbre) {
		String str="digraph G{\n";
		for (int i=0;i<sommets.size();i++) {
			str+=sommets.get(i).toDOTOriente(arbre);
		}
		return str+"}";
	}
	
	public String toDOT(Chemin c) {
		return toDOT(c.toGraphe(size));
	}
	
	public Arbre BFS(int racine) {
		Arbre arbre = new Arbre(size,racine);
		LinkedList<Sommet> Q = new LinkedList<Sommet>();
		Q.add(this.sommets.get(racine));
		this.sommets.get(racine).flag=true;
		while(!Q.isEmpty()) {
			Sommet sommetCourant = Q.getFirst();
			for (Sommet voisinCourant : sommetCourant.voisins) {
				if (sommetCourant.getPoids(voisinCourant)!=0 && voisinCourant.flag==false) {
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
	
	public GrapheOriente FF(int source, int destination) {
		GrapheOriente flot=new GrapheOriente(size);
		GrapheOriente grapheCourant=new GrapheOriente(this);
		Arbre bfs=grapheCourant.BFS(source);
		Chemin cheminAugmentant=bfs.cheminFromR(destination);
		while (cheminAugmentant!=null) {
			flot.ajouterCheminFlot(cheminAugmentant);
			grapheCourant.retirerChemin(cheminAugmentant);
			bfs=grapheCourant.BFS(source);
			cheminAugmentant=bfs.cheminFromR(destination);
		}
		return flot;
	}
	
	private void ajouterCheminFlot(Chemin c) {
		for (int i=0;i<c.getSize()-1;i++) {
			if (isVoisin(c.get(i),c.get(i+1))) sommets.get(c.get(i)).incrPoids(sommets.get(c.get(i+1)));
			else addArete(c.get(i),c.get(i+1));
		}
	}
	
	private void retirerChemin(Chemin c) {
		for (int i=0;i<c.getSize()-1;i++) {
			sommets.get(c.get(i)).decrPoids(sommets.get(c.get(i+1)));
		}
		ajouterCheminFlot(c.reverse());
	}
	private void traitement(GrapheOriente arbre,String line) {
        String[] parts = line.split(" -> ");				// split au niveau de la flèche
        process(arbre,line,parts);					// appelle la méthode process sur arbre et ligne avec le tableau split autour de la fleche
        
        
    }

    public GrapheOriente ToGraphe(String file) throws IOException  {
            // ---------------------------------------
            BufferedReader reader1;    
            //creation graphe
            try {
                reader1 = new BufferedReader(new FileReader(file));				// lis le fichier avec le chemin pris en attribut
                String line = reader1.readLine();							// lis la première ligne
               // System.out.println(line);						
                String[] lines= line.split("//");						// split la première ligne afin de récuperer le nombre de ligne contenu dans le fichier
                
                line = reader1.readLine();
                GrapheOriente arbre = new GrapheOriente(Integer.parseInt(lines[1]));			// creer une instance de graphe orienté avec le chiffre de la première ligne 
                while (line != null) {									// tant qu'il y a des lignes 
                    traitement(arbre,line);								// on appelle la méthode traitement sur le graphe créer et sur les lignes 
                    //System.out.println(line);
                    line = reader1.readLine(); // read next line				// change de ligne 
                }
                reader1.close();				// ferme le lecteur
                return arbre;				// renvoie l'arbre
                	
            }catch (IOException e) {				// si il y a une erreur renvoie un message dans la console 
                System.err.println("Caught IOException: " + e.getMessage());}
    return null;			// permet de fermer la méthode
    }
    
}
