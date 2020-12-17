package api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import gameClient.util.Point3D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
//import com.google.gson.*


public class DWGraph_DS implements directed_weighted_graph, Serializable {
    public HashMap<Integer, NodeData> Nodes = new HashMap<>();
    public HashMap<Integer, HashMap<Integer, NodeData.Edge>> Edges = new HashMap<>();
    private int EdgeSize;
    private int MC;
    String name = "";

    public DWGraph_DS(String graphNAme) {
        this.name = graphNAme;

    }

    public DWGraph_DS() {

    }
    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (Nodes.containsKey(key)) {
            return Nodes.get(key);
        }
        return null;
    }
    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
         if(src==dest)
            return;
        if (!Nodes.containsKey(src) || !Nodes.containsKey(dest)) {
            return null;
        }
        if (!Edges.get(src).containsKey(dest)) {
            return null;
        }
        return Edges.get(src).get(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(node_data n) {

        if (!this.Nodes.containsKey(n.getKey()) && this.Nodes != null && this.Edges != null && n != null) {
            this.Nodes.put(n.getKey(), (NodeData) n);
            HashMap<Integer, NodeData.Edge> edge = new HashMap<Integer, NodeData.Edge>();
            this.Edges.put(n.getKey(), edge);
            this.MC++;
        }
        return;
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
         if(src==dest)
            return;
        if (!Nodes.containsKey(src) || !Nodes.containsKey(dest)) {
            return;
        }
        if (w < 0) {
            System.out.println("The weight must be positive");
            return;
        }
        if (Nodes.get(src) == Nodes.get(dest)) {
            return;
        }
        if (!Edges.containsKey(src)) {
            NodeData.Edge value = new NodeData.Edge(Nodes.get(src), Nodes.get(dest), w);
            this.Edges.put(src, new HashMap<Integer, NodeData.Edge>());
            this.Edges.get(dest).put(src, value);
            this.Nodes.get(dest).addNeighbor((NodeData) this.getNode(src));


        } else if (Edges.containsKey(src)) {
            if (Edges.get(src).containsKey(dest)) {
                return;
            }
            NodeData.Edge value = new NodeData.Edge(Nodes.get(src), Nodes.get(dest), w);
            this.Edges.get(src).put(dest, value);
            this.Nodes.get(src).addNeighbor((NodeData) this.getNode(dest));

        }
        EdgeSize++;
        MC++;
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        Collection<node_data> Pointer_ShallCopy = new ArrayList<node_data>();
        for (int n : this.Nodes.keySet()) {
            Pointer_ShallCopy.add(this.Nodes.get(n));
        }
        return Pointer_ShallCopy;
    }
    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        Collection<edge_data> Pointer_ShallCopy = new ArrayList<edge_data>();
        if (!this.Nodes.containsKey(node_id)) {
            return Pointer_ShallCopy;
        }
        //  Collection<edge_data> Pointer_ShallCopy= new ArrayList<edge_data>();
        for (int n : this.Edges.get(node_id).keySet()) {
            Pointer_ShallCopy.add(this.Edges.get(node_id).get(n));
        }
        return Pointer_ShallCopy;
    }
    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_data removeNode(int key) {
        if (!Nodes.containsKey(key)) {
            return null;
        }
        for (int i = 0; i < Nodes.get(key).getNeighbors().size(); i++) {
            removeEdge(key, Nodes.get(key).getNeighbors().get(i).getKey());
            Nodes.get(key).getNeighbors().remove(i);
        }
        for (int n : this.Nodes.keySet()) {
            if (this.Nodes.get(n).getNeighbors().contains(Nodes.get(key))) {
                this.Nodes.get(n).getNeighbors().remove(Nodes.get(key));
                this.Edges.get(n).remove(key);
            }
        }
        node_data DeleteNode = Nodes.get(key);
        Nodes.remove(key);
        MC++;
        return DeleteNode;
    }
    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if (!this.Edges.get(src).containsKey(dest)) {
            return null;
        }
        edge_data DeleteEdge = this.Edges.get(src).get(dest);
        this.Edges.get(src).remove(dest);
        this.Nodes.get(src).getNeighbors().remove(this.Nodes.get(dest));
        EdgeSize--;
        MC++;
        return DeleteEdge;

    }
    /** Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return this.Nodes.size();
    }
    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return this.EdgeSize;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    public static class NodeData implements node_data, Serializable {
        private double Weight = 0;
@Expose
@SerializedName(value = "id")
        private int key;
        private String keyData;
        private int tag;
        private static int vertex = 0;
@Expose
@SerializedName(value = "pos")
        private geo_location location;
        private ArrayList<NodeData> Neighbors = new ArrayList<NodeData>();

        /*
         * constructor
         */
        public NodeData(int key, double weight, geo_location location, String info) {

            this.key = key;
            this.Weight = 0;
            this.location = location;
            this.keyData = info;
        }

        /*
         * constructor
         */
        public NodeData(int key, geo_location location, String info) {

            this.key = key;
            this.Weight = 0;
            this.location = location;
            this.keyData = info;
        }

        /*
         * constructor
         */
        public NodeData(int key, geo_location location) {
            this.key = key;
            this.Weight = 0;
            this.location = location;
        }

        public NodeData() {
            this.key = vertex++;
            this.Weight = 0;
            this.location = location;
            this.keyData = "";
            this.tag = 0;

        }

        public NodeData(int id) {
            this.key=id;
        }

        public NodeData(Point3D tmp, int key, double v, String s, int i) {
            this.location = tmp;
            this.key=key;
            this.Weight=v;
            this.keyData=s;
            this.tag=i;
        }

        public NodeData(Point3D point3D) {
            this.location=point3D;
        }

        /**  this function return this neighbors
         * @return
         */
        public ArrayList<NodeData> getNeighbors() {
            return this.Neighbors;
        }


        /**  add nieghbor n to this nieghbors
         * @param n
         */
        public void addNeighbor(NodeData n) {
            this.Neighbors.add(n);
        }


        public void clearNeighbors() {
            this.Neighbors.clear();
        }

        /**
         * Returns the key (id) associated with this node.
         * @return
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /** Returns the location of this node, if
         * none return null.
         *
         * @return
         */
        @Override
        public geo_location getLocation() {
            return this.location;
        }


        /** Allows changing this node's location.
         * @param p - new new location  (position) of this node.
         */
        @Override
        public void setLocation(geo_location p) {
            this.location = p;
        }


        /**
         * Returns the weight associated with this node.
         * @return
         */
        @Override
        public double getWeight() {
            return this.Weight;
        }


        /**
         * Allows changing this node's weight.
         * @param w - the new weight
         */
        @Override
        public void setWeight(double w) {
            if (w < 0) {
                throw new RuntimeException("the Weight cannot be negative");

            }
            this.Weight = w;
        }

        /**
         * Returns the remark (meta data) associated with this node.
         * @return
         */
        @Override
        public String getInfo() {
            return this.keyData;
        }


        @Override
        public String toString() {
            return " NodeInfo {" + " key = " + key + " }";

        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.keyData = s;

        }

        /**
         * Temporal data (aka color: e,g, white, gray, black)
         * which can be used be algorithms
         * @return
         */
        @Override
        public int getTag() {
            return this.tag;
        }

        /**
         * Allows setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(int t) {
            this.tag = t;

        }

        public static class Edge implements edge_data, Serializable {
            private node_data Src;
            private node_data Dest;
            private int Tag;
            private double weight;
            private String Info;

            public Edge(node_data src, node_data dest, double weight) {
                this.Src = src;
                this.Dest = dest;
                this.weight = weight;
            }

            public Edge() {
                this.Src=new NodeData();
                this.Dest=new NodeData();
                this.weight=0;
                this.Tag=0;

            }
            public Edge(edge_data edge_data) {
              this.Src=new NodeData(edge_data.getSrc());
                this.Dest=new NodeData(edge_data.getDest());
            //    this.weight=edge_data.getWeight();
                this.Tag=edge_data.getTag();
                this.Info=edge_data.getInfo();

            }

            public Edge(int i, int i1, int weight) {


            }

            /**
             * The id of the source node of this edge.
             * @return
             */
            @Override
            public int getSrc() {
                return this.Src.getKey();
            }
            /**
             * The id of the destination node of this edge
             * @return
             */
            @Override
            public int getDest() {
                return this.Dest.getKey();
            }
            /**
             * @return the weight of this edge (positive value).
             */
            @Override
            public double getWeight() {
                return this.weight;
            }

            /**
             * Returns the remark (meta data) associated with this edge.
             * @return
             */
            @Override
            public String getInfo() {
                return this.Info;
            }

            /**
             * Allows changing the remark (meta data) associated with this edge.
             * @param s
             */
            @Override
            public void setInfo(String s) {
                this.Info = s;
            }

            /**
             * Temporal data (aka color: e,g, white, gray, black)
             * which can be used be algorithms
             * @return
             */
            @Override
            public int getTag() {
                return this.Tag;
            }

            /**
             * This method allows setting the "tag" value for temporal marking an edge - common
             * practice for marking by algorithms.
             * @param t - the new value of the tag
             */
            @Override
            public void setTag(int t) {
                this.Tag = t;
            }

            @Override
            public String toString() {
                return "Edge{" +
                        "Src=" + Src +
                        ", Dest=" + Dest +
                        ", Tag=" + Tag +
                        ", weight=" + weight +
                        ", Info='" + Info + '\'' +
                        '}';
            }
        }

        public static class geoLocation implements geo_location {
            private double x;
            private double y;
            private double z;

            public geoLocation(double px, double py, double pz) {
                this.x=px;
                this.y=py;
                this.z=pz;
            }

            @Override
            public double x() {
                return this.x;
            }

            @Override
            public double y() {
                return this.y;
            }

            @Override
            public double z() {
                return this.z;
            }

            @Override
            public double distance(geo_location g) {
                return Math.sqrt((x - g.x()) * (x - g.x()) +
                        (y - g.y()) * (y - g.y()));
            }
        }

    }
}
