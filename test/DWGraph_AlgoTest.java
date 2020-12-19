package test;
import api.*;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;
import api.dw_graph_algorithms;
import api.directed_weighted_graph;


import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DWGraph_AlgoTest {
    static private Point3D p = Point3D.ORIGIN;
    private static dw_graph_algorithms alg =new DWGraph_Algo();



    @org.junit.jupiter.api.Test
    void copy() {
        dw_graph_algorithms copy =new DWGraph_Algo();
        directed_weighted_graph g = getRandomGraph();


        copy.init(g);
        directed_weighted_graph g1=copy.copy();


        assertEquals(g.edgeSize(), g1.edgeSize());
        assertEquals(g.nodeSize(), g1.nodeSize());
        assertEquals(g.getMC(), g1.getMC());
        assertEquals(g.getV().size(), g1.getV().size());
    }





    @Test
    public void isConnected() {
       directed_weighted_graph g = new DWGraph_DS();
        alg.init(g);
        assertTrue(alg.isConnected());

        g.addNode(new DWGraph_DS.NodeData(1,p));
        g.addNode(new DWGraph_DS.NodeData(2,p));
        g.addNode(new DWGraph_DS.NodeData(3,p));
        g.connect(1, 2, 1.1);
        g.connect(2, 3, 1.2);
        alg.init(g);
        assertFalse(alg.isConnected());
        g.connect(1, 3, 4);
        g.connect(3, 1, 2);
        g.connect(2, 1, 5);
        g.connect(3, 2, 3);
        alg.init(g);
        assertTrue(alg.isConnected());
    }

    @Test
    public void shortestPathDist() {
       directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new DWGraph_DS.NodeData(1,p));
        g.addNode(new DWGraph_DS.NodeData(2,p));
        g.addNode(new DWGraph_DS.NodeData(3,p));
        g.addNode(new DWGraph_DS.NodeData(4,p));
        g.addNode(new DWGraph_DS.NodeData(5,p));

        g.connect(1, 2, 1.1);
        g.connect(2, 3, 1.1);
        g.connect(3, 4, 1.1);
        g.connect(4, 5, 1.1);

        g.connect(1, 5, 10);

        alg.init(g);
        assertEquals(4.4, alg.shortestPathDist(1, 5));
        assertEquals(5, alg.shortestPath(1, 5).size());
    }



    @Test
    public void shortestPath() {
        Point3D p = new Point3D(1,3);
        Point3D p1 = new Point3D(1,3);
        Point3D p2 = new Point3D(1,3);
        directed_weighted_graph d = new DWGraph_DS();
        node_data n1 = new DWGraph_DS.NodeData(1,p);
        node_data n2 = new DWGraph_DS.NodeData(2,p1);
        node_data n3 = new DWGraph_DS.NodeData(3,p2);
        d.addNode(n1);
        d.addNode(n2);
        d.addNode(n3);
        d.connect(1,2,5);
        d.connect(2,3,4);
        d.connect(3,1,6);
        dw_graph_algorithms g = new DWGraph_Algo();
        g.init(d);
        List<node_data> list = g.shortestPath(1,3);
        assertEquals(list.get(0),n1);
        assertEquals(list.get(1),n2);
        assertEquals(list.get(2),n3);
    }

    @org.junit.jupiter.api.Test
    void saveAndLoad() {
        dw_graph_algorithms G2 = new DWGraph_Algo();
        directed_weighted_graph g2 = new DWGraph_DS();
        Point3D p2 [] = new Point3D[5];
        node_data n2 [] = new DWGraph_DS.NodeData[5];

        p2[0] = new Point3D(1, 6, 0);
        p2[1] = new Point3D(0, 2, 3);
        p2[2] = new Point3D(1, 4, 0);
        p2[3] = new Point3D(5, 2, 0);
        p2[4] = new Point3D(6,5, 0);

        n2[0] = new DWGraph_DS.NodeData(p2[0]);
        n2[1] = new DWGraph_DS.NodeData(p2[1]);
        n2[2] = new DWGraph_DS.NodeData(p2[2]);
        n2[3] = new DWGraph_DS.NodeData(p2[3]);
        n2[4] = new DWGraph_DS.NodeData(p2[4]);

        g2.addNode(n2[0]);
        g2.addNode(n2[1]);
        g2.addNode(n2[2]);
        g2.addNode(n2[3]);
        g2.addNode(n2[4]);

        g2.connect(n2[1].getKey(), n2[2].getKey(), 11);
        g2.connect(n2[2].getKey(), n2[3].getKey(), 11);
        g2.connect(n2[3].getKey(), n2[2].getKey(), 11);
        g2.connect(n2[3].getKey(), n2[4].getKey(), 11);
        g2.connect(n2[4].getKey(), n2[3].getKey(), 11);

        G2.init(g2);
        G2.save("TestForSave");

        dw_graph_algorithms G3 = new DWGraph_Algo();
        G3.load("TestForSave");

        Boolean flag = G2.isConnected() == G3.isConnected();
        assertEquals(true,flag);


        dw_graph_algorithms G4 = new DWGraph_Algo();
        directed_weighted_graph g4 = new DWGraph_DS();
        Point3D p4 [] = new Point3D[5];
        node_data n4 [] = new DWGraph_DS.NodeData[5];

        p4[0] = new Point3D(1, 6, 0);
        p4[1] = new Point3D(0, 2, 3);
        p4[2] = new Point3D(1, 4, 0);
        p4[3] = new Point3D(5, 2, 0);
        p4[4] = new Point3D(6,5, 0);

        n4[0] = new DWGraph_DS.NodeData (p4[0]);
        n4[1] = new DWGraph_DS.NodeData(p4[1]);
        n4[2] = new DWGraph_DS.NodeData(p4[2]);
        n4[3] = new DWGraph_DS.NodeData(p4[3]);
        n4[4] = new DWGraph_DS.NodeData(p4[4]);

        g4.addNode(n4[0]);
        g4.addNode(n4[1]);
        g4.addNode(n4[2]);
        g4.addNode(n4[3]);
        g4.addNode(n4[4]);

        g4.connect(n4[1].getKey(), n4[2].getKey(), 11);
        g4.connect(n4[2].getKey(), n4[3].getKey(), 11);
        g4.connect(n4[3].getKey(), n4[2].getKey(), 11);
        g4.connect(n4[3].getKey(), n4[4].getKey(), 11);
        g4.connect(n4[4].getKey(), n4[3].getKey(), 11);

        G4.init(g4);
        dw_graph_algorithms G5 = new DWGraph_Algo();
        G5.load("TestForSave");

        assertEquals(G4.isConnected(), G5.isConnected());
    }



    public static directed_weighted_graph getRandomGraph() {
        directed_weighted_graph g = new DWGraph_DS();
        int nodesSize = (int)(Math.random()*5)+5;
        for (int i = 0; i < nodesSize; i++) {
            g.addNode(new DWGraph_DS.NodeData(i, p));
        }
        for (int i = 0; i < nodesSize; i++) {
            int edgesSize = (int)(Math.random()*nodesSize);
            for (int j = 0; j < edgesSize; j++) {
                int dest = (int)(Math.random()*nodesSize);
                if(i != dest)
                    g.connect(i, dest, 1);
            }
        }
        return g;
    }

}
