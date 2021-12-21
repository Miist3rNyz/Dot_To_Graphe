import java.util.ArrayList;

public class Arbre extends GrapheOriente {
	int racine;
	ArrayList<Integer> parents;
	
	public Arbre(int size, int racine) {
		super(size);
		this.racine=racine;
		parents = new ArrayList<Integer>();
		for (int i=0;i<size;i++) parents.add(-1);
	}

	@Override
	public void addArete(int i,int j) {
		sommets.get(i).addVoisin(sommets.get(j));
		parents.set(j,i);
	}
	
	@Override
	public void addArete(int i,int j,int p) {
		sommets.get(i).addVoisin(sommets.get(j),p);
		parents.set(j,i);
	}
	
	public Chemin cheminFromR(int i) {
		if (parents.get(i)==-1) return null;
		Chemin c = new Chemin();
		c.add(i);
		int sommetCourant = i;
		while (sommetCourant!=racine) {
			sommetCourant=parents.get(sommetCourant);
			c.addFirst(sommetCourant);
		}
		return c;
	}
}
