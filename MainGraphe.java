import java.io.IOException;

public class MainGraphe {
	public static void main(String[] args) throws IOException {
	Graphe G = new GrapheOriente(5);		// creation d'un graphe oriente
	Graphe T= new Graphe();	// creation d'un graphe
	/*G.addArete(0,3,3);
	G.addArete(2,4,2);
	G.addArete(1,4,1);
	G.addArete(3,1,5);
	G.addArete(3,2,1);
	G.addArete(1,2,1);
	G.coloration();
	System.out.println(G.toDOT());
	//System.out.println(G.toDOT(G.Dijkstra(0)));
	//System.out.println(G.toDOT(G.BFS(0)));
	//Chemin c = new Chemin();
	//c.add(0);
	//c.add(3);
	//c.add(2);
	/*System.out.println(G.toDOT(G.BFS(0).cheminFromR(4)));
	GrapheOriente Gclone = new GrapheOriente(G);
	System.out.println(Gclone.toDOT());
	System.out.println(G.FF(0,4).toDOT());*/
	G=G.ToGraphe("C:\\Users\\kevin\\Desktop\\test\\test.dot");		// stocke le graphe oriente contenu dans le fichier
	T=T.ToGraphe("C:\\Users\\kevin\\Desktop\\test\\test2.dot");		// stocke le graphe contenu dans le fichier
	System.out.println(G.toDOT());			// affiche le graphe oriente G
	System.out.println(T.toDOT());// affiche le graphe T

	
}
}
