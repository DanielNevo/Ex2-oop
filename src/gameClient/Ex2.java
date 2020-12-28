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
	static  int time=0;
	public static void main(String[] a) {
		Thread client = new Thread(new Ex2());
		client.start();
	}

	@Override
	public void run() {
		int scenario_num =11 ;
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//			int id = 999;
//			game.login(id);
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

		String fs =  game.getPokemons();
		List<CL_Pokemon> pok = Arena.json2Pokemons(fs);
		_ar.setPokemons(pok);
		int size_pok=-1;
		List<CL_Pokemon>help=new LinkedList<>() ;
		for (CL_Pokemon pokemon:pok){
			help.add(pokemon);
		}

		for(int i=0;i<log.size();i++) {
			CL_Agent ag = log.get(i);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();

			if(dest==-1) {


				List<node_data> pathToPokemon = pathToPok(gg,src,help,size_pok);


				for (node_data curr : pathToPokemon) {
					dest=curr.getKey();
					game.chooseNextEdge(ag.getID(),dest);
				}
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest+ ", "+ "speed:"+ag.getSpeed());

			}
		}
	}


	public static List<node_data> pathToPok(directed_weighted_graph g, int src,List help,int index_pok){
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		int dest = 0;
		List<CL_Pokemon>help2=new LinkedList<>();
		for (Object temp:help){
			help2.add((CL_Pokemon) temp);
		}
		int index=0;
		double val1;
		double val2= 0;
		boolean flag=false;
		CL_Pokemon maxVaule=null;
		for (int i=0;i<help2.size();i++) {
			Arena.updateEdge((CL_Pokemon) help2.get(i), g);
			val1 = help2.get(i).getValue();
			if (help2.get(i).getflag() == false) {
				if (val1 > val2) {
					val2 = val1;
					index = i;
					flag = true;

				}
			}
		}

		if(flag==true) {
			help2.get(index).setflag(true);
			if (help2.get(index).getType()== -1 && src < help2.get(index).get_edge().getSrc() ) {
				dest=help2.get(index).get_edge().getSrc();
				return algo.shortestPath(src, dest);
			}
			else {

				dest =help2.get(index).get_edge().getDest();

				if (src == dest) {
					dest=help2.get(index).get_edge().getSrc();
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

		int ans = -1;

		int dest1=0;


		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		//	int s = ee.size();
		while(itr.hasNext()) {

			ans=itr.next().getDest();
			if(ans!=dest)
				itr.next();
		}


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
			int dest = 0;

			for (int i = 0; i < cl_fs.size(); i++) {
				{
					Arena.updateEdge(cl_fs.get(i), algo.getGraph());
				}
			}
	;

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

						if(maxValue.get_edge().getSrc()>maxValue.get_edge().getDest()){
							game.addAgent(maxValue.get_edge().getDest());

						}else {
							game.addAgent(maxValue.get_edge().getSrc());
							game.chooseNextEdge(maxValue.get_edge().getSrc(), maxValue.get_edge().getDest());
						}

						maxValue.setflag(true);
						maxValue = null;


					} else if (maxValue.getType() == -1&&maxValue.getflag()==false) {

						if(maxValue.get_edge().getSrc()>maxValue.get_edge().getDest()){
							game.addAgent(maxValue.get_edge().getSrc());

						}else{
							game.addAgent(maxValue.get_edge().getDest());
							game.chooseNextEdge(maxValue.get_edge().getDest(), maxValue.get_edge().getSrc());
						}

						maxValue.setflag(true);
						maxValue = null;

					}
				}
				cl_fs.remove(dest);
				_ar.setPokemons(cl_fs);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}
