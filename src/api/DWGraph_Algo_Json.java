package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class DWGraph_Algo_Json implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String graphNAme = jsonObject.get("graphName").getAsString();
        directed_weighted_graph graph = new DWGraph_DS(graphNAme);

        JsonObject jsonGraph=jsonObject.get("Nodes").getAsJsonObject();

        for (Map.Entry<String,JsonElement>set :jsonGraph.entrySet()){
           String hashKey=set.getKey();
           JsonElement jsonValue=set.getValue();
           int nodeNum = jsonValue.getAsJsonObject().get("nodeNum").getAsInt();
           node_data node_data =new DWGraph_DS.NodeData();
           graph.addNode(node_data);

        }
        return graph;
    }
}
