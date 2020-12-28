package Tests;


import api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


class DWGraph_AlgoTest {

    DWGraph_DS g = new DWGraph_DS();
    DWGraph_Algo w=new DWGraph_Algo(g);

    Node a = new Node(0);
    Node b = new Node(1);
    Node c = new Node(2);
    Node d=  new Node(3);
    Node e=  new Node(4);
    Node f=  new Node(5);
    Node h=  new Node(6);

    @Test
    void isConnected() {

        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(c);//3

        g.connect(0,1,2);
		g.connect(0,2,2);
		g.connect(1,2,2);
		g.connect(2,0,2);
		g.connect(2,3,2);
        Assertions.assertEquals(true,w.isConnected());
    }
    @Test
    void shortestPathDist() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3
        g.addNode(e);//4
        g.addNode(f);//5
        g.addNode(h);//6

        g.connect(0,1,3);
        g.connect(0,3,10);
        g.connect(0,2,2);
        g.connect(1,6,2);
        g.connect(6,3,1);
        g.connect(2,4,12);
        g.connect(2,5,4);
        g.connect(5,4,4);
        g.connect(5,3,8);
        Assertions.assertEquals(6.0,w.shortestPathDist(0,3));

    }
    @Test
    void shortestPath() {
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3
        g.addNode(e);//4
        g.addNode(f);//5
        g.addNode(h);//6

		g.connect(1,2,2);
		g.connect(1,3,4);
		g.connect(2,3,1);
		g.connect(2,4,7);
		g.connect(3,5,3);
		g.connect(5,4,2);
		g.connect(4,6,1);
		g.connect(5,6,5);

        String String="";
        for(node_data curr:w.shortestPath(1,6) ){
            String+=curr.getKey() +"->";
        }
        assertEquals("1->2->3->5->4->6->",String);
    }

    }
