package api;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import gameClient.util.Point3D;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms, Serializable {

    private directed_weighted_graph DwAlgo;

    @Override
    public String toString() {
        return "DWGraph_Algo{" +
                "DwAlgo=" + DwAlgo +
                '}';
    }

    @Override
    public void init(directed_weighted_graph g) {
       this.DwAlgo = new DWGraph_DS();
        this.DwAlgo = g;

    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.DwAlgo;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph toCopy = new DWGraph_DS();


        for (node_data currV : this.DwAlgo.getV()) {
            node_data n = new DWGraph_DS.NodeData(currV.getKey(), currV.getLocation(), currV.getInfo());
            toCopy.addNode(n);
        }

        for (node_data currV : this.DwAlgo.getV()) {
            if (this.DwAlgo.getE(currV.getKey()) != null) {
                for (edge_data currE : this.DwAlgo.getE(currV.getKey())) {
                    edge_data e = new DWGraph_DS.NodeData.Edge(this.DwAlgo.getNode(currE.getSrc()), this.DwAlgo.getNode(currE.getDest()), currE.getWeight());
                    toCopy.connect(e.getSrc(), e.getDest(), e.getWeight());
                }
            }
        }
        return toCopy;
    }

    @Override
    public boolean isConnected() {
        if (this.DwAlgo.nodeSize() == 0 || this.DwAlgo.nodeSize() == 1 || (this.DwAlgo.getV().isEmpty()))
            return true;
        //   directed_weighted_graph copied = copy();
        Set_No_Visited(this.DwAlgo);
        int KeyIterate = this.DwAlgo.getV().iterator().next().getKey();
        DfsMarked(this.DwAlgo, KeyIterate);
        for (node_data curr1 : this.DwAlgo.getV()) {
            if (curr1.getTag() == 0) return false;
        }
        Change_Direction(this.DwAlgo);
        Set_No_Visited(this.DwAlgo);
        DfsMarked(this.DwAlgo, KeyIterate);
        for (node_data curr2 : this.DwAlgo.getV()) {
            if (curr2.getTag() == 0) return false;
        }
        return true;
    }

    public void Set_No_Visited(directed_weighted_graph graph) {
        for (node_data noVisit : graph.getV()) {
            noVisit.setTag(0);
        }
    }


    public node_data getN(directed_weighted_graph k) {
        for (node_data s : k.getV()) {
            if (s == null)
                continue;
            return s;
        }
        return null;
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        if ((this.DwAlgo.getNode(src) == null) || this.DwAlgo.getNode(dest) == null) {
            System.out.print("One or two of the inputs do not exist");
            return -1;
        }
        MyDijkstra(src, dest);
        if (this.DwAlgo.getNode(dest).getWeight() == Integer.MAX_VALUE) {
            System.out.print("There is not a path between the nodes : ");
            return -1;
        }
        return this.DwAlgo.getNode(dest).getWeight();
    }

    private node_data find_Min_Node(Collection<node_data> collectN) {
        Point3D p = new Point3D(0, 0);
        node_data toReturn = new DWGraph_DS.NodeData(0, p);
        toReturn.setWeight(Integer.MAX_VALUE);
        toReturn.setInfo("empty");
        toReturn.setTag(1);
        for (node_data curr : collectN) {
            if (curr.getTag() == 0 && curr.getWeight() < toReturn.getWeight()) {
                toReturn = curr;
            }
        }
        return toReturn;
    }

    public void MyDijkstra(int src, int dest) {
        for (node_data curr : this.DwAlgo.getV()) {
            curr.setTag(0);
            curr.setInfo("");
            curr.setWeight(Integer.MAX_VALUE);
        }
        this.DwAlgo.getNode(src).setWeight(0);
        node_data min = this.DwAlgo.getNode(src);
        node_data prev = this.DwAlgo.getNode(src);
        while (prev != this.DwAlgo.getNode(dest) && min.getInfo() != "empty") {
            min.setTag(1);
            if (this.DwAlgo.getE(min.getKey()) != null) {
                for (edge_data currE : this.DwAlgo.getE(min.getKey())) {
                    if ((this.DwAlgo.getNode(currE.getDest()).getTag() == 0) && (min.getWeight() + currE.getWeight() < this.DwAlgo.getNode(currE.getDest()).getWeight())) {
                        this.DwAlgo.getNode(currE.getDest()).setWeight(min.getWeight() + currE.getWeight());
                        this.DwAlgo.getNode(currE.getDest()).setInfo("" + min.getKey());
                        prev = min;
                    }
                }

            }
            min = find_Min_Node(this.DwAlgo.getV());

        }
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        Double val = shortestPathDist(src, dest);
        if (val == -1)
            return null;
        LinkedList<node_data> ShortestPath = new LinkedList<node_data>();
        node_data currV = this.DwAlgo.getNode(dest);
        ShortestPath.add(currV);
        while (currV != this.DwAlgo.getNode(src)) {
            node_data toAdd = this.DwAlgo.getNode(Integer.parseInt(currV.getInfo()));
            ShortestPath.addFirst(toAdd);
            currV = toAdd;
        }
        return ShortestPath;
    }


    @Override
    public boolean save(String file) {
        // this.DwAlgo = new DWGraph_DS("Project Ariel");
        directed_weighted_graph g = copy();


        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonElements = new JsonArray();

        for (node_data i : g.getV()) {
            JsonObject n = new JsonObject();
            if (i.getLocation() != null) {
                n.addProperty("pos", i.getLocation().toString());
            } else {
                n.addProperty("pos", "null");
            }
            n.addProperty("id", i.getKey());
            jsonElements.add(n);
        }
        JsonArray Edge = new JsonArray();
        for (node_data i : g.getV()) {
            for (edge_data j : g.getE(i.getKey())) {
                JsonObject Edge1 = new JsonObject();
                Edge1.addProperty("src", j.getSrc());
                Edge1.addProperty("w", j.getWeight());
                Edge1.addProperty("dest", j.getDest());
                Edge.add(Edge1);
            }
        }
        jsonObject.add("Nodes", jsonElements);
        jsonObject.add("Edges", Edge);


        try {


            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(gson.toJson(jsonObject));
            String json = gson.toJson(g);
            String js = gson.toJson(jsonObject);

            System.out.println(jsonObject);
            System.out.println(js);
            pw.flush();
            pw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean load(String file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
            FileInputStream fileInputStream = new FileInputStream(file);
            JsonReader reader = new JsonReader(new InputStreamReader(fileInputStream));
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            directed_weighted_graph g = new DWGraph_DS();
            for (JsonElement node : jsonObject.getAsJsonArray("Nodes")) {
                DWGraph_DS.NodeData n = new DWGraph_DS.NodeData(node.getAsJsonObject().get("id").getAsInt());
                String[] gPos = node.getAsJsonObject().get("pos").getAsString().split(",");
                n.setLocation(new DWGraph_DS.NodeData.geoLocation(Double.parseDouble(gPos[0]), Double.parseDouble(gPos[1]), Double.parseDouble(gPos[2])));
                g.addNode(n);
            }
            for (JsonElement _node : jsonObject.getAsJsonArray("Edges")) {
                g.connect(_node.getAsJsonObject().get("src").getAsInt(), _node.getAsJsonObject().get("dest").getAsInt(), _node.getAsJsonObject().get("w").getAsDouble());
            }
            this.init(g);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void stag(node_data n) {
        for (edge_data i : this.DwAlgo.getE(n.getKey())) {
            this.DwAlgo.getNode(i.getSrc()).setTag(1);
            this.DwAlgo.getNode(i.getDest()).setTag(1);
            i.setTag(1);
        }
    }

    public void DfsMarked(directed_weighted_graph DwG, int key) {
        DwG.getNode(key).setTag(1);
        if (DwG.getE(key) != null) {
            for (edge_data currNi : DwG.getE(key)) {
                if (DwG.getNode(currNi.getDest()).getTag() == 0)
                    DfsMarked(DwG, currNi.getDest());
            }
        }
    }

    public void Change_Direction(directed_weighted_graph changeD) {
        for (node_data curr : changeD.getV()) {
            if (changeD.getE(curr.getKey()) != null) {
                Iterator changeIterate = changeD.getE(curr.getKey()).iterator();
                edge_data curr_change;
                while (changeIterate.hasNext()) {
                    curr_change = (edge_data) changeIterate.next();
                    if (curr_change != null && curr_change.getTag() == 0) {

                        if (changeD.getEdge(curr_change.getDest(), curr_change.getSrc()) != null) {
                            changeD.getEdge(curr_change.getDest(), curr_change.getSrc()).setTag(1);
                            curr_change.setTag(1);
                        } else {
                            changeD.connect(curr_change.getDest(), curr_change.getSrc(), curr_change.getWeight());
                            changeD.getEdge(curr_change.getDest(), curr_change.getSrc()).setTag(1);
                            changeD.removeEdge(curr_change.getSrc(), curr_change.getDest());
                            changeIterate = changeD.getE(curr.getKey()).iterator();
                        }
                    }
                }
            }
        }
    }

}

