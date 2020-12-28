package gameClient;

import api.node_data;

import java.util.List;

public class Temp_POK {
    private double dist;
    private CL_Agent agent;
    private CL_Pokemon pokemon;
    private List<node_data> path_pok;


    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public CL_Agent getAgent() {
        return agent;
    }

    public void setAgent(CL_Agent agent) {
        this.agent = agent;
    }

    public CL_Pokemon getPokemon() { return pokemon; }

    public void setPokemon(CL_Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public List<node_data> getPath_pok() {
        return path_pok;
    }

    public void setPath_pok(List path_pok) {
        this.path_pok = path_pok;
    }
}