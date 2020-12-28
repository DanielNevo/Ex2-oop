package gameClient;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import api.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server_Ex2;

public class Ex2 implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	private static List<CL_Pokemon> pokemons;

	static  int time=0;
	//HashMap<String,edge_data>edge=new HashMap<String,edge_data>();
	public static void main(String[] a) {
		Thread client = new Thread(new Ex2());
		client.start();
	}

	@Override
	public void run() {
		int scenario_num =11;
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
		//	int id = 999;
		//	game.login(id);
		String g = game.getGraph();
		String pks = game.getPokemons();
		DWGraph_Algo algo =new DWGraph_Algo();
		algo.load(g);
		init(game);


		game.startGame();
		_win.setName("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind=0;
		long dt=100;

		while(game.isRunning()) {
			time++;
			moveAgants(game,algo.getGraph());
			try {
				if(ind%1==0) {_win.repaint();}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		System.exit(0);
	}
	/**
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg) {
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(gg);


		String fs = game.getPokemons();
		List<CL_Pokemon> pok = Arena.json2Pokemons(fs);
		pokemons = Arena.json2Pokemons(fs);
		_ar.setPokemons(pok);


		Queue<Double> mindist1 = new PriorityQueue<>(new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				if (o1 < o2) {
					return -1;
				} else if (o1 > o2) {
					return 1;
				}
				return 0;
			}
		});
		Queue<CL_Pokemon> bestvaluepok = new PriorityQueue<>(new Comparator<CL_Pokemon>() {
			@Override
			public int compare(CL_Pokemon o1, CL_Pokemon o2) {
				if (o1.getValue() < o2.getValue()) {
					return 1;
				} else if (o1.getValue() > o2.getValue()) {
					return -1;
				}
				return 0;
			}
		});
		for (CL_Pokemon list : pokemons) {
			bestvaluepok.add(list);
		}
		List<Temp_POK> listofvalue = new LinkedList<>();


		for (int i = 0; i < log.size(); i++) {
			CL_Agent ag = log.get(i);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();

			if (dest == -1) {

				List<node_data> pathToPokemon = pathToPok(gg, src);

				Temp_POK pok1 = new Temp_POK();
				pok1.setPath_pok(pathToPokemon);
				int key = 0;
				for (node_data path : pathToPokemon) {
					key = path.getKey();
				}
				double tempmin = algo.shortestPathDist(src, key);
				mindist1.add(tempmin);//this for min path queue
				pok1.setDist(tempmin);//update the distance
				pok1.setAgent(ag);
				listofvalue.add(pok1);//מחסן

			}
		}

         //find min dis
		Temp_POK finaly=null;
		double bestpath= mindist1.poll();
		for (Temp_POK find:listofvalue){
			if(bestpath==find.getDist()){
				finaly=find;
				break;
			}
		}
		//find the pok and set the pokemon to true
		CL_Pokemon pokemonb=bestvaluepok.poll();
		for (int i=0;i<pokemons.size();i++){
			if(pokemonb.getValue()==pokemons.get(i).getValue()){
				pokemons.get(i).setflag(true);
				break;
			}
		}
		mindist1.clear();
		listofvalue.clear();
		CL_Agent agent= finaly.getAgent();
		//Arena.updateEdge(pokemons.get(j),algo.getGraph());

				for (node_data p : finaly.getPath_pok()) {
					game.chooseNextEdge(finaly.getAgent().getID(), p.getKey());
					System.out.println("Agent: " + finaly.getAgent().getID() + ", val: " + finaly.getAgent().getValue() + "turned to node: " + p.getKey() + ", " + "speed:" + finaly.getAgent().getSpeed());
				}
			}







	public static List<node_data> pathToPok(directed_weighted_graph g, int src) {
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		int dest = 0;
		int index = 0;
		double val1;
		double val2 = 0;
		boolean flag = false;
		CL_Pokemon maxVaule = null;
		for (int i = 0; i < pokemons.size(); i++) {
			Arena.updateEdge(pokemons.get(i), g);
			val1 = pokemons.get(i).getValue();
			if (pokemons.get(i).getflag() == false) {
				if (val1 > val2) {
					val2 = val1;
					index = i;
					flag = true;
				}
			}
		}
		if (flag == true) {


			if (pokemons.get(index).getType() == -1) {
				if (pokemons.get(index).get_edge().getDest() < pokemons.get(index).get_edge().getSrc()) {
					dest = pokemons.get(index).get_edge().getDest();
					return algo.shortestPath(src, dest);
				} else {
					dest = pokemons.get(index).get_edge().getSrc();
					return algo.shortestPath(src, dest);
				}
			}
			if (pokemons.get(index).getType() == 1) {
				if (pokemons.get(index).get_edge().getSrc() > pokemons.get(index).get_edge().getDest()) {
					dest = pokemons.get(index).get_edge().getDest();
					return algo.shortestPath(src, dest);
				} else {
					dest = pokemons.get(index).get_edge().getDest();
					return algo.shortestPath(src, dest);
				}
			}

		}
		return algo.shortestPath(src, dest);
	}






	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(directed_weighted_graph g, int src,int dest) {
//	private static int nextNode(directed_weighted_graph g, int src) {//, List<node_data> pathToPokemon) {
		int ans = -1;
		//	g.getNode(src);
		int dest1=0;
//		for (node_data curr : pathToPokemon) {
//			dest=curr.getKey();
//			break;
//		}
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		//	int s = ee.size();
		while(itr.hasNext()) {

			ans=itr.next().getDest();
			if(ans!=dest)
				itr.next();
		}
//		int r = (int)(Math.random()*s);
//		int i=0;
//		while(i<r) {itr.next();i++;}
//
//		ans = itr.next().getDest();

		return ans;
	}
	private void init(@NotNull game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		dw_graph_algorithms algo =new DWGraph_Algo();
		algo.load(g);
		algo.init(algo.getGraph());
		_ar = new Arena();
		_ar.setGraph(algo.getGraph());
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new MyFrame("Ex2_Pokimon");
		_win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_win.setSize(500,500);
		_win.getContentPane().setBackground(Color.black);
		_win.update(_ar);
		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			pokemons=Arena.json2Pokemons(game.getPokemons());
			//	List<CL_Agent> log ;
			//	List<CL_Agent> agents= Arena.getAgents(g,algo.getGraph());
//			List<CL_Agent> log = Arena.getAgents(g, algo.getGraph());
//			_ar.setAgents(log);
			int dest = 0;
//			Queue<Double> q = new PriorityQueue<Double>(new Comparator<Double>() {
//				@Override
//				public Double compare(Object a,Object b) {
//					// compare if the tag is smaller he will get much more priority
//					double A=  a;
//					double B=  b;
//					if (a.getValue()>b.getValue()) {
//						return a.getValue();
//					}
//
//					else if (a.getValue()<b.getValue()) {
//						return b.getValue();
//					}
//					return 0.0;
//				}
//			});

			for (int i = 0; i < cl_fs.size(); i++) {
				{
					Arena.updateEdge(cl_fs.get(i), algo.getGraph());
					Arena.updateEdge(pokemons.get(i),algo.getGraph());
				}
			}
			//	CL_Pokemon maxValue = null;
//			int index=0;
			for (int i = 0; i <rs; i++)//agent
			{
				dest = 0;

				double val1;
				double val2 = 0;
				CL_Pokemon maxValue = null;
				for (int j = 0; j < _ar.getPokemons().size(); j++) {
					Arena.updateEdge(_ar.getPokemons().get(j), algo.getGraph());
					val1 = _ar.getPokemons().get(j).getValue();
					if (_ar.getPokemons().get(j).getflag() == false) {
						if (val1 > val2) {
							val2 = val1;
							maxValue = _ar.getPokemons().get(j);
							dest = j;
						}
					}
				}
				if (maxValue != null) {
					if (maxValue.getType() == 1&&maxValue.getflag()==false) {
//							agents = Arena.getAgents(game.getAgents(), algo.getGraph());
//							_ar.setAgents(agents);
						if(maxValue.get_edge().getSrc()>maxValue.get_edge().getDest()){
							game.addAgent(maxValue.get_edge().getDest());

						}else {
							game.addAgent(maxValue.get_edge().getSrc());
							//game.chooseNextEdge(maxValue.get_edge().getSrc(), maxValue.get_edge().getDest());
						}
//							cl_fs.remove(dest);
						maxValue.setflag(true);
						maxValue = null;
						//	_ar.setAgents(log);

					} else if (maxValue.getType() == -1&&maxValue.getflag()==false) {
//							agents=Arena.getAgents(game.getAgents(), algo.getGraph());
//							_ar.setAgents(agents);
						if(maxValue.get_edge().getSrc()>maxValue.get_edge().getDest()){
							game.addAgent(maxValue.get_edge().getSrc());

						}else{
							game.addAgent(maxValue.get_edge().getDest());
							game.chooseNextEdge(maxValue.get_edge().getDest(), maxValue.get_edge().getSrc());
						}
//							cl_fs.remove(dest);
						maxValue.setflag(true);
						maxValue = null;
						//	_ar.setAgents(log);
					}
				}
				cl_fs.remove(dest);
				_ar.setPokemons(cl_fs);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}

}