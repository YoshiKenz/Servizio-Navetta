package model.geoUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import model.RegistroAttivitaNavette;
import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.jdbcDao.FermataDaoJDBC;
import persistence.daoManage.jdbcDao.TrattoLineaDaoJDBC;
import persistence.persistentModel.Fermata;
import persistence.persistentModel.Linea;
import persistence.persistentModel.Navetta;
import persistence.persistentModel.TrattoLinea;

public class GeoUtil {

	private GeoUtil() {}

	public static double distance(double p1LAT,double p1LNG, double p2LAT, double p2LNG) {

		try {

			double earthRadius = 6371;//Km
			double latDifferenceDegrees = (p2LAT-p1LAT) * (Math.PI/180);
			double lngDifferenceDegrees = (p2LNG-p2LNG) * (Math.PI/180);

			double a =
					Math.sin(latDifferenceDegrees/2) * Math.sin(latDifferenceDegrees/2) +
					Math.cos(p1LAT * (Math.PI/180)) * Math.cos(p2LAT * (Math.PI/180)) *
					Math.sin(lngDifferenceDegrees/2) * Math.sin(lngDifferenceDegrees/2);

			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			double distanceInKm = earthRadius * c;

			return distanceInKm;
		}catch(Exception e) {return -1;}

	}

	public static ArrayList<ArrayList<TrattoLinea> > computeRoutes(Fermata partenza, Fermata arrivo){
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		TrattoLineaDaoJDBC trattoDao = (TrattoLineaDaoJDBC) df.getTrattoLineaDAO();
		ArrayList<TrattoLinea> tuttiTratti = (ArrayList<TrattoLinea>) trattoDao.findAll();

		Graph<String, DefaultWeightedEdge> grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class); 

		/*If there are not instances of "TrattoLinea" connecting instances of "Fermata" 
		 * the instances "partenza" and "fermata" wouldn't be in the graph and this 
		 * would throw exception computing paths */
		grafo.addVertex(partenza.getNome());
		System.out.println("V : "+arrivo.getNome());
		System.out.println("V : "+partenza.getNome());
		grafo.addVertex(arrivo.getNome());

		for(TrattoLinea t : tuttiTratti) {
			grafo.addVertex(t.getArrivo().getNome());
			System.out.println("V : "+t.getArrivo().getNome());
			grafo.addVertex(t.getPartenza().getNome());
			System.out.println("V : "+t.getPartenza().getNome());
			DefaultWeightedEdge edge = grafo.addEdge(t.getPartenza().getNome(), t.getArrivo().getNome());
			System.out.print("E : "+t.getPartenza().getNome()+" -> "+t.getArrivo().getNome());
			grafo.setEdgeWeight(edge, t.getDistanzaKM());
			System.out.println(" W : "+t.getDistanzaKM());
		}

		GraphPath<String, DefaultWeightedEdge> shortestPath = null;
		KShortestSimplePaths<String, DefaultWeightedEdge> paths = new KShortestSimplePaths<>(grafo);
		List<GraphPath<String, DefaultWeightedEdge>> alternatives = new ArrayList<>();

		try {

			shortestPath = DijkstraShortestPath.findPathBetween(grafo, partenza.getNome(), arrivo.getNome());
			/**/
			int maxHops = 5;
			int kNumPaths = 5;
			paths = new KShortestSimplePaths<>(grafo,maxHops);
			alternatives = paths.getPaths(partenza.getNome(), arrivo.getNome(), kNumPaths);
		}catch(IllegalArgumentException e) {
			System.out.println("Impossible to compute paths");
		}
		/**/


		if(shortestPath!=null)
			alternatives.add(shortestPath);
		ArrayList<ArrayList<TrattoLinea> > ret = new ArrayList<ArrayList<TrattoLinea>>(alternatives.size());
		int indexPath = 0;
		FermataDaoJDBC fdao = (FermataDaoJDBC) df.getFermataDAO();
		if(shortestPath!=null)
			for(GraphPath<String, DefaultWeightedEdge> g : alternatives) {
				ret.add(new ArrayList<TrattoLinea>());
				List<String> fermate = g.getVertexList();
				List<DefaultWeightedEdge> archi =  g.getEdgeList();

				for(int j=0;j<fermate.size()-1;j++) {
					ret.get(indexPath).add(new TrattoLinea((Fermata)fdao.findByPrimaryKey(fermate.get(j)), (Fermata)fdao.findByPrimaryKey(fermate.get(j+1)), 0.0, grafo.getEdgeWeight(archi.get(j))));
				}
				indexPath++;
			}

		return ret;
	}

	public static ArrayList<ArrayList<Navetta> > computeBus(ArrayList<TrattoLinea> route,RegistroAttivitaNavette registro){
		System.out.println(registro);
		ArrayList<ArrayList<Navetta> > ret = new ArrayList<ArrayList<Navetta> >();
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		TrattoLineaDaoJDBC tDao = (TrattoLineaDaoJDBC) df.getTrattoLineaDAO();
		for(TrattoLinea t : route) {
			ArrayList<Navetta> navInTratto = new ArrayList<Navetta>();
			HashSet<Linea> lineeAttive = registro.getLineeAttive();
			/*DEBUG*/
			for(Linea ln : lineeAttive)
				System.out.println("Linea attiva : "+ln.getNome());
			/*DEBUG*/
			//Now the collection contains all the active lines that contain "TrattoLinea" "t"
			ArrayList<Linea> lineeConTratto = tDao.getLinee(t);
			lineeAttive.retainAll(lineeConTratto);
			for(Linea ln : lineeAttive)
				System.out.println("Dopo linea attiva : "+ln.getNome());
			for(Linea ln : lineeAttive) {
				System.out.println("enter");
				HashSet<Navetta> navetteAttive = registro.getNavetteAttive(ln);
				/*DEBUG*/
				for(Navetta nav : navetteAttive)
					System.out.println("Navetta attiva : "+nav.getID());
				/*DEBUG*/
				navInTratto.addAll(navetteAttive);
			}
			if(navInTratto.isEmpty())
				return null;
			ret.add(navInTratto);

		}
		return ret;
	}

}
