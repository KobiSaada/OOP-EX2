package test;

import api.*;
import gameClient.util.Point3D;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    static private directed_weighted_graph randGraph;
   static directed_weighted_graph g0 = new DWGraph_DS();
    static directed_weighted_graph g1 = new DWGraph_DS();
    static directed_weighted_graph g2 = new DWGraph_DS();
    static directed_weighted_graph g3 = new DWGraph_DS();

    //creat points to the node's
    static Point3D x = new Point3D(1,8);
    static Point3D y = new Point3D(2,6);
    static Point3D z = new Point3D(6,9);
    static Point3D t = new Point3D(4,13);
    static Point3D r = new Point3D(5,17);

    //creat arrays for the node
    static node_data[] arrN0=new DWGraph_DS.NodeData[2];
    static node_data[] arrN1=new DWGraph_DS.NodeData[2];
    static node_data[] arrN2=new DWGraph_DS.NodeData[3];
    static node_data[] arrN3=new DWGraph_DS.NodeData[5];

    //creat arrays for the edges
    static DWGraph_DS.NodeData.Edge[] arrE0=new DWGraph_DS.NodeData.Edge[1];
    static DWGraph_DS.NodeData.Edge[] arrE2 =new DWGraph_DS.NodeData.Edge[4];
    static DWGraph_DS.NodeData.Edge[] arrE3=new DWGraph_DS.NodeData.Edge[7];

    static node_data notEqual = new DWGraph_DS.NodeData(100,new Point3D(100,100));
    static private Point3D p = Point3D.ORIGIN;

    @BeforeAll
    static void setRandomGraph() {
        randGraph = getRandomGraph();
    }
    @Before
    public void BeforeEach() {
        g0 = new DWGraph_DS();
        g1 = new DWGraph_DS();
        g2 = new DWGraph_DS();
        g3 = new DWGraph_DS();

        //Create Dgraph: a0 -> b0
        node_data a0 = new DWGraph_DS.NodeData(1, x);
       node_data b0 = new DWGraph_DS.NodeData(2, y);

        g0.addNode(a0);
        g0.addNode(b0);
        g0.connect(a0.getKey(),b0.getKey(),4);

        //Initialize the array of Node's
        arrN0[0]=a0;
        arrN0[1]=b0;

        //Initialize the array of Edge's
        arrE0[0]=new DWGraph_DS.NodeData.Edge(a0.getKey(),b0.getKey(), (int) 4.0);

        //---------------------------------------------------------//
        //Create Dgraph: a1,b1 (standalone)
       node_data a1 = new DWGraph_DS.NodeData(1, x);
        node_data b1 = new DWGraph_DS.NodeData(2,y);

        g1.addNode(a1);
        g1.addNode(b1);

        //Initialize the array of Node's
        arrN1[0]=a1;
        arrN1[1]=b1;

        //---------------------------------------------------------//
        /*Create Dgraph: a2 -> b2
                         b2 -> c2
                         c2 -> a2
                         a2 -> c2
         */
        node_data a2 = new DWGraph_DS.NodeData(1, x);
      node_data b2 = new DWGraph_DS.NodeData(2,y);
       node_data c2 = new DWGraph_DS.NodeData(3,z);

        g2.addNode(a2);
        g2.addNode(b2);
        g2.addNode(c2);
        g2.connect(a2.getKey(),b2.getKey(),4);
        g2.connect(b2.getKey(),c2.getKey(),5);
        g2.connect(c2.getKey(),a2.getKey(),8);
        g2.connect(a2.getKey(),c2.getKey(),11);

        //Initialize the array of Node's
        arrN2[0]=a2;
        arrN2[1]=b2;
        arrN2[2]=c2;

        //Initialize the array of Edge's
        arrE2[0]=new DWGraph_DS.NodeData.Edge(a2.getKey(),b2.getKey(), (int) 4.0);
        arrE2[1]=new DWGraph_DS.NodeData.Edge(b2.getKey(),c2.getKey(),5);
        arrE2[2]=new DWGraph_DS.NodeData.Edge(c2.getKey(),a2.getKey(),8);
        arrE2[3]=new DWGraph_DS.NodeData.Edge(a2.getKey(),c2.getKey(),11);


        //---------------------------------------------------------//
        /*Create Dgraph: a3 -> b3
                         b3 -> c3
                         c3 -> a3
                         a3 -> c3
                         a3 -> d3
                         d3 -> e3
                         e3 -> b3
         */
       node_data a3 = new DWGraph_DS.NodeData(1, x);
        node_data b3 = new DWGraph_DS.NodeData(2, y);
       node_data c3 = new DWGraph_DS.NodeData(3, z);
        node_data d3 = new DWGraph_DS.NodeData(4, t);
        node_data e3 = new DWGraph_DS.NodeData(5, r);

        g3.addNode(a3);
        g3.addNode(b3);
        g3.addNode(c3);
        g3.addNode(d3);
        g3.addNode(e3);
        g3.connect(a3.getKey(),b3.getKey(),4);
        g3.connect(b3.getKey(),c3.getKey(),5);
        g3.connect(c3.getKey(),a3.getKey(),8);
        g3.connect(a3.getKey(),c3.getKey(),11);
        g3.connect(a3.getKey(),d3.getKey(),6);
        g3.connect(d3.getKey(),e3.getKey(),7);
        g3.connect(e3.getKey(),b3.getKey(),7);

        //Initialize the array of Node's
        arrN3[0]=a3;
        arrN3[1]=b3;
        arrN3[2]=c3;
        arrN3[3]=d3;
        arrN3[4]=e3;

//        Initialize the array of Edge's
        arrE3[0] =new DWGraph_DS.NodeData.Edge(a3.getKey(),b3.getKey(),4);
        arrE3[1] =new DWGraph_DS.NodeData.Edge(b3.getKey(),c3.getKey(),5);
        arrE3[2] =new DWGraph_DS.NodeData.Edge(c3.getKey(),a3.getKey(),8);
        arrE3[3] =new DWGraph_DS.NodeData.Edge(a3.getKey(),c3.getKey(),11);
        arrE3[4] =new DWGraph_DS.NodeData.Edge(a3.getKey(),d3.getKey(),6);
        arrE3[5] =new DWGraph_DS.NodeData.Edge(d3.getKey(),e3.getKey(),7);
        arrE3[6] =new DWGraph_DS.NodeData.Edge(e3.getKey(),b3.getKey(),7);



    }

    /**
     * Checks whether the Default constructor is working properly
     */


    @Test
    void getNode() {
       directed_weighted_graph Graph = new DWGraph_DS();
       node_data N = new DWGraph_DS.NodeData(10,new Point3D(0,0,0),"");
        Graph.addNode(N);
        assertEquals(Graph.getNode(10), N);

    }





    @Test
    void getEdge() {
        directed_weighted_graph Graph = new DWGraph_DS();
       node_data N = new DWGraph_DS.NodeData(10,new Point3D(0,0,0),"");
        Graph.addNode(N);
        node_data N2 = new DWGraph_DS.NodeData(11,new Point3D(0,0,0),"");
        Graph.addNode(N2);
        Graph.connect(N.getKey(),N2.getKey(),10);
        DWGraph_DS.NodeData.Edge edge = (DWGraph_DS.NodeData.Edge) Graph.getEdge(N.getKey(), N2.getKey());
        //assertEquals(edge, Graph.Edges.get(N.getKey()).get(N2.getKey()));
    }





    @Test
    void addNode() {
        directed_weighted_graph Graph = new DWGraph_DS();
        node_data N = new DWGraph_DS.NodeData(10,new Point3D(0,0,0),"");
        Graph.addNode(N);
        assertTrue(Graph.getV().contains(N));

    }


    @Test
    void connect() {
        directed_weighted_graph Graph = new DWGraph_DS();
       node_data N = new DWGraph_DS.NodeData(10,new Point3D(0,0,0),"");
        Graph.addNode(N);
      node_data N2 = new DWGraph_DS.NodeData(11,new Point3D(0,0,0),"");
        Graph.addNode(N2);
        Graph.connect(N.getKey(),N2.getKey(),10);
       assertTrue(Graph.getE(N.getKey()).size()>=1);
    }

        @Test
    void getV() {
           directed_weighted_graph Graph = new DWGraph_DS();
           node_data N = new DWGraph_DS.NodeData(10,new Point3D(0,0,0),"");
            Graph.addNode(N);
          node_data N2 = new DWGraph_DS.NodeData(11,new Point3D(0,0,0),"");
            Graph.addNode(N2);
            ArrayList<node_data> nodes = (ArrayList<node_data>) Graph.getV();
            for(node_data u : Graph.getV()) {
                if(!nodes.contains(Graph.getNode(u.getKey()))) {
                    fail("This Node should be in the Collection.");
                }
            }
            nodes.get(0).setWeight(10);
            if(!(nodes.get(0).getWeight() == Graph.getNode(N.getKey()).getWeight())) {
                fail("This Nodes should have the same weight.");
            }

    }


    @Test
    void removeNode() {

        directed_weighted_graph Graph = new DWGraph_DS();
       node_data N = new DWGraph_DS.NodeData(10,new Point3D(0,0,0),"");
        Graph.addNode(N);
        Graph.removeNode(N.getKey());
        assertTrue(!Graph.getV().contains(N.getKey()));


    }

    @Test
    void removeEdge() {
       directed_weighted_graph Graph = new DWGraph_DS();
      node_data N = new DWGraph_DS.NodeData(10,new Point3D(0,0,0),"");
        Graph.addNode(N);
       node_data N2 = new DWGraph_DS.NodeData(11,new Point3D(0,0,0),"");
        Graph.addNode(N2);
        Graph.connect(N.getKey(),N2.getKey(),10);
        Graph.removeEdge(N.getKey(),N2.getKey());
        assertTrue(!Graph.getE(N.getKey()).contains(N2.getKey()));

    }

    @Test
    void nodeSize() {
      //  assertEquals(arrN0.length,g0.nodeSize());
        node_data a2 = new DWGraph_DS.NodeData(1, x);
        node_data b2 = new DWGraph_DS.NodeData(2,y);
        node_data c2 = new DWGraph_DS.NodeData(3,z);

        g2.addNode(a2);
        g2.addNode(b2);
        g2.addNode(c2);
        g2.connect(a2.getKey(),b2.getKey(),4);
        g2.connect(b2.getKey(),c2.getKey(),5);
        g2.connect(c2.getKey(),a2.getKey(),8);
        g2.connect(a2.getKey(),c2.getKey(),11);

        assertEquals(3,g2.nodeSize());

    }



    @Test
    void edgeSize() {
        node_data a2 = new DWGraph_DS.NodeData(1, x);
        node_data b2 = new DWGraph_DS.NodeData(2,y);
        node_data c2 = new DWGraph_DS.NodeData(3,z);

        g2.addNode(a2);
        g2.addNode(b2);
        g2.addNode(c2);
        g2.connect(a2.getKey(),b2.getKey(),4);
        g2.connect(b2.getKey(),c2.getKey(),5);
        g2.connect(c2.getKey(),a2.getKey(),8);
        g2.connect(a2.getKey(),c2.getKey(),11);


        assertEquals(4,g2.edgeSize());


    }



    @Test
    void getMC() {
       node_data toAdd1=new DWGraph_DS.NodeData(6, new Point3D(55,9));
        node_data toAdd2=new DWGraph_DS.NodeData(7, new Point3D(6,0));

        int beforeChange0=g0.getMC();
        g0.addNode(toAdd1);
        g0.addNode(toAdd2);
        g0.connect(toAdd1.getKey(),toAdd2.getKey(),100);
        g0.removeEdge(toAdd1.getKey(),toAdd2.getKey());
        g0.removeNode(toAdd1.getKey());
        g0.removeNode(toAdd2.getKey());

        int beforeChange1=g1.getMC();
        g1.addNode(toAdd1);
        g1.addNode(toAdd2);
        g1.connect(toAdd1.getKey(),toAdd2.getKey(),100);
        g1.removeEdge(toAdd1.getKey(),toAdd2.getKey());
        g1.removeNode(toAdd1.getKey());
        g1.removeNode(toAdd2.getKey());

        int beforeChange2=g2.getMC();
        g2.addNode(toAdd1);
        g2.addNode(toAdd2);
        g2.connect(toAdd1.getKey(),toAdd2.getKey(),100);
        g2.removeEdge(toAdd1.getKey(),toAdd2.getKey());
        g2.removeNode(toAdd1.getKey());
        g2.removeNode(toAdd2.getKey());

        int beforeChange3=g3.getMC();
        g3.addNode(toAdd1);
        g3.addNode(toAdd2);
        g3.connect(toAdd1.getKey(),toAdd2.getKey(),100);
        g3.removeEdge(toAdd1.getKey(),toAdd2.getKey());
        g3.removeNode(toAdd1.getKey());
        g3.removeNode(toAdd2.getKey());


        assertEquals(beforeChange0+beforeChange1+beforeChange2+beforeChange3+24,g0.getMC()+g1.getMC()+g2.getMC()+g3.getMC());

    }




    @Test
    public void MiloneNode()
    {
        directed_weighted_graph g = new DWGraph_DS();

        Point3D p1 = new Point3D(0,0);

        for (int i = 1; i <= 1000000 ; i++) {
            node_data node = new DWGraph_DS.NodeData( i, new Point3D(p1.x()+i , p1.y()+i ,p1.z()+i ));
            g.addNode(node);
        }

        for (int i = 1; i <= 1000000-10 ; i++) {
            g.connect(i, i+1, i*0.5);
            g.connect(i, i+2, i*0.3);
            g.connect(i, i+3, 1);
            g.connect(i, i+4, i*10);
            g.connect(i, i+5, i*3);
            g.connect(i, i+6, i*0.8);
            g.connect(i, i+7, i*0.5);
            g.connect(i, i+8, i*7);
            g.connect(i, i+9, i*3);
            g.connect(i, i+10, i*2.5);
        }

        dw_graph_algorithms test = new DWGraph_Algo();
        test.init(g);

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