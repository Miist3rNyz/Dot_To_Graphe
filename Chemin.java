import java.util.ArrayList;
import java.util.Iterator;

public class Chemin implements Iterable<Integer> {
private ArrayList<Integer> chemin;
	
	public Chemin() {
		chemin = new ArrayList<Integer>();
	}
	
	public void add(int i) {
		chemin.add(i);
	}
	
	public void addFirst(int i) {
		chemin.add(0,i);
	}
	
	public int get(int i) {
		return chemin.get(i);
	}
	
	public int getSize() {
		return chemin.size();
	}
	
	public String toString() {
		String str="Chemin: ";
		for (int i : chemin) {
			str+=i+" ";
		}
		return str;
	}
	
	public Graphe toGraphe(int size) {
		Graphe G=new Graphe(size);
		for (int i=0;i<chemin.size()-1;i++) {
			G.addArete(chemin.get(i),chemin.get(i+1));
		}
		return G;
	}
	
	public Chemin reverse() {
		Chemin c = new Chemin();
		for(int i : chemin) {
			c.addFirst(i);
		}
		return c;
	}

	@Override
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return chemin.iterator();
	}
}
