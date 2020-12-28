package Tests;

import api.DWGraph_DS;
import api.Node;
import api.edge_data;
import api.node_data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DWGraph_DSTest {

    DWGraph_DS g = new DWGraph_DS();
    Node a = new Node(0);
    Node b = new Node(1);
    Node c = new Node(2);
    Node d=  new Node(3);
    Node e=  new Node(4);

    @Test
    void getNode() {

        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
       // System.out.println(g.nodeSize());
       Assertions.assertEquals(3,g.nodeSize());
        Assertions.assertEquals(null, g.getNode(5));

    }
    @Test
    void getEdge(){
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.connect(0,1, 2.0);
        assertEquals( 2.0,g.getEdge(0,1).getWeight());
        g.removeEdge(0,1);
        g.getEdge(0,1);
      //  assertEquals(null,g.getEdge(0,1));
    }
    @Test
    void addNode() {

        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        Assertions.assertEquals(3,g.nodeSize());
         g.removeNode(0);
         g.removeNode(1);
         g.removeNode(2);
        Assertions.assertEquals(0,g.nodeSize());
    }
    @Test
    void connect() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3

        g.connect(0,1,2);
        g.connect(0,2,3);
        g.connect(0,3,0);
        g.connect(1,4,0);
        g.connect(1,4,0);

        Assertions.assertEquals(null,g.getEdge(0,5));
        Assertions.assertEquals(2,g.getEdge(0,1).getWeight());
        Assertions.assertEquals(5,g.edgeSize());
    }
    @Test
    void getV() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3
        Collection<node_data> Nodes = g.getV();
        Assertions.assertEquals(4,g.getV().size());
    }
    @Test
    void getE() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3
        g.connect(0,1,0);
        g.connect(0,2,0);
        g.connect(0,3,0);
        g.connect(0,1,0);
        Collection<edge_data> edges = g.getE(0);
        Assertions.assertEquals(3,edges.size());
    }
    @Test
    void removeNode() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3
        Assertions.assertEquals(4,g.nodeSize());
        g.removeNode(7);
        Assertions.assertEquals(4,g.nodeSize());
       g.removeNode(3);
        Assertions.assertEquals(3,g.nodeSize());
    }
    @Test
    void removeEdge() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3

        g.connect(0,1,3);

        g.connect(1,2,2);

        g.connect(0,3,0);

        Assertions.assertEquals(3,g.edgeSize());

        g.removeEdge(0,1);

        Assertions.assertEquals(2,g.edgeSize());

    }
    @Test
    void nodeSize() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3

        Assertions.assertEquals(4,g.nodeSize());
        g.removeNode(0);
        g.removeNode(1);
        g.removeNode(2);
        Assertions.assertEquals(1,g.nodeSize());
        g.addNode(a);
        g.addNode(c);

        Assertions.assertEquals(3,g.nodeSize());
    }
    @Test
    void edgeSize() {
        g.addNode(a);//0
        g.addNode(b);//1
        g.addNode(c);//2
        g.addNode(d);//3
        g.connect(0,1,1);
        Assertions.assertEquals(1,g.edgeSize());
        g.connect(0,3,1);
        Assertions.assertEquals(2,g.edgeSize());

    }


}








