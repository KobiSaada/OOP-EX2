package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Ex2 implements Runnable, ActionListener {

    private static MyFrame_Gui gui;
    private static Arena arena;
    public static game_service game;
    public static int numGame;

 
    public static void main(String[] a) {
        if (a.length == 2) {
            numGame = Integer.parseInt(a[1]);
            id = Integer.parseInt(a[0]);
            game = Game_Server_Ex2.getServer(numGame);
            ImageIcon imageIcon = new ImageIcon("doc/pikachu-oh-yeah.gif");
            JOptionPane.showMessageDialog(null,"Enjoy And Cath them all !!","Welcome to Pokemon Challenge Game",JOptionPane.OK_CANCEL_OPTION,imageIcon);

        }
        else {
            ImageIcon imageIcon = new ImageIcon("doc/pikachu-oh-yeah.gif");
            String s = (String) JOptionPane.showInputDialog(null, " Please Enter Your Id Number : ", "ID", JOptionPane.QUESTION_MESSAGE, imageIcon, null, 0);

            id = Integer.parseInt(s);

            ImageIcon imageIcon1 = new ImageIcon("doc/tenor (1).gif");
            String[] chooseNumOfGame = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
            Object selectedNumOfGame = JOptionPane.showInputDialog(null, "Choose a num of game", "Message", JOptionPane.INFORMATION_MESSAGE, imageIcon1, chooseNumOfGame, chooseNumOfGame[0]);

            int num = Integer.parseInt((String) selectedNumOfGame);
            game = Game_Server_Ex2.getServer(num);
            numGame = num;
        }


        Thread client = new Thread(new Ex2());
        client.start();

    }

    @Override
    public void run() {


 
        game.login(id);
     

        String g = game.getGraph();
        String pks = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        init(game);

        game.startGame();
File file = new File("doc/musicGame.wav");
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    clip.start();

        

        gui.setTitle("Ex2 - OOP: The Challenge Pokemon Game");
        int ind = 0;
        //  long dt=100;

        long time = game.timeToEnd();
        while (game.isRunning()) {

            moveAgants(game, arena.getGraph());
            try {
                if (ind % 1 == 0) {
                    arena.setTime("Time Left :" + (double)game.timeToEnd()/1000);
                    gui.repaint();
                }
                Thread.sleep(numOfSleep());
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String res = game.toString();

        System.out.println(res);
      //  JFrame jj =new JFrame();
       ImageIcon imageIcon2= new ImageIcon("doc/tenor 2.gif");
     //   JOptionPane.showMessageDialog(jj,imageIcon2);
     //   jj.createImage(50,50);

        JFrame jf = new JFrame();
        jf.setIconImage(imageIcon2.getImage());
        jf.createImage(50,50);
        jf.setBounds(0,100,80,80);
       JOptionPane.showMessageDialog(jf,"THE GAME IS OVER" + "\n" + "YOUR GRADE IS : " + myGrade(game) + "\n" + "YOUR MOVES IS :" + " " + numOfMoves(game));
        JOptionPane.showMessageDialog(jf,imageIcon2);
        System.exit(0);
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     *
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        arena.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        arena.setGraph(gg);
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        arena.setPokemons(ffs);
        List<edge_data> CL1 = getListOfEdgeF();
        for (int i = 0; i < log.size(); i++) {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            //  int src = ag.getSrcNode();
            double v = ag.getValue();
            if (dest == -1) {
                nextNode(ag, gg, CL1);
                dest = ag.getNextNode();


                System.out.println("Agent: " + id + ", val: " + v + "  turned to node: " + dest);
            }
        }
    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);

        arena = new Arena();
        arena.setGraph(gg);
        arena.setPokemons(Arena.json2Pokemons(fs));
        gui = new MyFrame_Gui("test Ex2");
        gui.setSize(1050, 750);
        gui.update(arena);


        gui.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            //  int src_node = arena.getPokemons().indexOf(0);  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), arena.getGraph());
            }
            List<edge_data> CL = getListOfEdgeF();

            double min = Integer.MAX_VALUE;

            for (int a = 0; a < rs; a++) {
                edge_data ans = new DWGraph_DS.NodeData.Edge();

                for (edge_data c : CL) {
                    if (c.getWeight() < min)
                        min = c.getWeight();
                    ans = c;


                }

                int nn = ans.getSrc();
                game.addAgent(nn);

                CL.remove(ans);


            }





        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * This function finds the edge on which the pokemon is located.
     *
     * @param pokemon is the pokemon we are looking for.
     * @return the edge we found.
     */
    public static edge_data getEdge(CL_Pokemon pokemon) {
        edge_data ans;

        for (node_data n : arena.getGraph().getV()) {
            if (arena.getGraph().getE(n.getKey()) != null) {
                Iterator it = arena.getGraph().getE(n.getKey()).iterator();
                while (it.hasNext()) {
                    ans = (edge_data) it.next();
                    node_data src = arena.getGraph().getNode(ans.getSrc());
                    node_data dest = arena.getGraph().getNode(ans.getDest());
                    double dis = distance((Point3D) src.getLocation(), (Point3D) dest.getLocation());
                    double sTf = distance((Point3D) src.getLocation(), pokemon.getLocation());
                    double fTd = distance(pokemon.getLocation(), (Point3D) dest.getLocation());
                    if (sTf + fTd <= dis + 0.0001 * 0.0001) {
                        if (pokemon.getType() == -1 && src.getKey() > dest.getKey()) {
                            return ans;
                        } else if (pokemon.getType() == 1 && src.getKey() < dest.getKey()) {
                            return ans;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * This is an auxiliary function for calculating distance between two points.
     *
     * @param p1 is the first point.
     * @param p2 is the second point.
     * @return the distance.
     */
    public static double distance(Point3D p1, Point3D p2) {
        double x = Math.pow(p1.x() - p2.x(), 2);
        double y = Math.pow(p1.y() - p2.y(), 2);
        return Math.sqrt(x + y);
    }

    /**
     * This function collects all the edges that have Pokemon in a list.
     *
     * @return the list the function has built.
     */
    public static List<edge_data> getListOfEdgeF() {
        List<edge_data> edgeOfFruit = new LinkedList<>();

        for (CL_Pokemon f : arena.getPokemons()) {
            boolean b = true;
            edge_data edgeFruit = getEdge(f);

            for (edge_data e : edgeOfFruit) { //This for prevents duplication between edge
                if (edgeOfFruit.size() > 0) {
                    if (e.equals(edgeFruit)) b = false;
                }
            }
            if (b) edgeOfFruit.add(getEdge(f));
        }
        return edgeOfFruit;
    }


    /**
     * This function checks which vertex should send the Agent.
     *
     * @param agent     is the AgentPokemon.
     * @param graphGame is the graph of this game.
     */
    public static void nextNode(CL_Agent agent, directed_weighted_graph graphGame, List<edge_data> edgeOfFruit) {
        boolean ifCatchFruit = false;
        edge_data destRobots[] = new edge_data[arena.getAgents().size()];

        for (CL_Agent rd : arena.getAgents()) {
            destRobots[rd.getID()] = rd.get_curr_edge();
            System.out.println(rd.toString());
        }
        dw_graph_algorithms g = new DWGraph_Algo();
        g.init(graphGame);
        edge_data minDest = new DWGraph_DS.NodeData.Edge();
        double min = Integer.MAX_VALUE;
        for (edge_data e : edgeOfFruit) {
            for (edge_data ed : destRobots) {
                if (e.equals(ed)) ifCatchFruit = true;
            }
            if (!ifCatchFruit && arena.getGraph().getNode(e.getSrc()) != null || arena.getGraph().getNode(agent.getSrcNode()) != null) {
                double temp = g.shortestPathDist(agent.getSrcNode(), e.getSrc());
                if (temp < min) {
                    min = temp;
                    minDest = e;
                }
            }
        }
        agent.set_curr_edge(minDest);
        if (arena.getGraph().getNode(minDest.getDest()) != null || arena.getGraph().getNode(agent.getSrcNode()) != null) {
            List<node_data> shortestPath = g.shortestPath(agent.getSrcNode(), minDest.getSrc());
            shortestPath.add(arena.getGraph().getNode(minDest.getDest()));
            Run_To_Pokemon_Agent(agent, shortestPath, minDest);
            edgeOfFruit.remove(minDest);

        }
    }

    /**
     * this is an auxiliary function to move the Agent from the method "next node" to the closest Pokemon.
     *
     * @param ag           - the Agent to move
     * @param shortestPath - the route that the robot should to pass.
     * @param catchPokemon - the pokemon that the agent need to cath.
     */
    public static void Run_To_Pokemon_Agent(CL_Agent ag, List<node_data> shortestPath, edge_data catchPokemon) {
        for (node_data n : shortestPath) {

            game.chooseNextEdge(ag.getID(), n.getKey());


        }

    }

    /**
     * This function check the num of sleep for the run function.
     *
     * @return the num of sleep.
     */
    public int numOfSleep() {
        int ans = 100;
        if (this.numGame == 3) return 103;

        if (this.numGame == 10) return 278;
        if (this.numGame == 8) return 145;
        if (this.numGame == 7) return 104;
        if (this.numGame == 4) return 111;
        if (this.numGame == 1) return 108;
        if (this.numGame == 2) return 119;
        if (this.numGame == 0) return 147;
        if (this.numGame == 6) return 400;
        if (this.numGame == 5) return 113;
        if (this.numGame == 9) return 127;
        //  if (this.numGame == 10) return 80;
        if (this.numGame == 11) return 101;
        if (this.numGame == 12) return 155;
        if (this.numGame == 13) return 101;
        if (this.numGame == 14) return 180;
        if (this.numGame == 15) return 110;
        if (this.numGame == 16) return 113;
        if (this.numGame == 17) return 98;
        if (this.numGame == 18) return 560;
        if (this.numGame == 19) return 500;
        if (this.numGame == 20) return 115;
        if (this.numGame == 21) return 81;
        if (this.numGame == 22) return 203;
        for (CL_Agent r : arena.getAgents()) {
            List<edge_data> edgeFruit = getListOfEdgeF();
            for (edge_data e : edgeFruit) {
                if (r.getSrcNode() == e.getSrc()) {
                    return 80;
                }
            }
        }
        return ans;
    }

    /**
     * This function takes from the server as points earned in the game.
     *
     * @param server is the type of game.
     * @return the grade of game.
     */
    public double myGrade(game_service server) {
        double myGrade = 0;
        try {
            String json = server.toString();
            JSONObject gameJson = new JSONObject(json);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            myGrade = gameServer.getDouble("grade");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myGrade;
    }

    /**
     * This function calculates the amount of moves during the game.
     *
     * @param server is the type of the game.
     * @return the amount of moves.
     */
    public double numOfMoves(game_service server) {
        double myMoves = 0;
        try {
            String json = server.toString();
            JSONObject gameJson = new JSONObject(json);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            myMoves = gameServer.getDouble("moves");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myMoves;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


