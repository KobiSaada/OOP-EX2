# Welcome to OOP-EX2- The Challenge Pokemon Game 
![Webp net-compress-image (3)](https://user-images.githubusercontent.com/73976733/101813689-b9163080-3b25-11eb-9e93-5471b17b0e15.jpg)
![pokemon](https://user-images.githubusercontent.com/73976733/101813899-fe3a6280-3b25-11eb-9a16-72f29d4ae6c3.gif) 
# The Pokemon Challenge Game :
![output-onlinepngtools (6)](https://user-images.githubusercontent.com/73976733/101840405-b418a780-3b4c-11eb-8bdc-febf47295196.png)

# External Info :
* https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
* https://www.guru99.com/java-swing-gui.html
* https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
* https://en.wikipedia.org/wiki/Directed_graph





# Genral Info :

Task number 2 in the object-oriented course This task is divided into 2 parts, first part, similar to previous tasks. Implementing a graph.
Only this time a directed and weighted graph with the addition of departments and part two realizing a Pokemon game and managing agents for their target Pokemon similar to the Pacman game the game based on Grahs with nodes and edges taht represent the route. The player in the game represented by pokemonagent. There are pokemons scattered on the graph randomly from the srever and the goal of the game is to cath as much pokemons as possible and earn as many points as possible until the time is over.
The game draw by JFrme class.
The game will be managed by a server with which it will be possible to communicate via the following API:
<img width="1072" alt="Screen Shot 2020-12-16 at 13 02 24" src="https://user-images.githubusercontent.com/73976733/102340511-4da5e600-3f9f-11eb-8428-b79d1df7ded2.png">


# Getting Started :
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

# Prerequisites:

* JDK-15.0.1

* Eclipse

* IntelliJ

* or some other IDE (recommended)

# Installing :

Clone that project to your workspace directory

     git clone https://github.com/KobiSaada/OOP-EX2-Pokemon-Game                      
      
Open your IDE and make sure you see the project "OOP-EX2-Pokemon-Game"

# How to run it?
 
 If you are running from the terminal then go to the folder you downloaded from this Githav in the terminal run from the command line 
  java -jar EX2.jar {ID} {NumLevel} (NumLevel needs to be an Integer Between 0-23) 
  And the game will appear Enjoy!!
  
 else:
 
 There is a class called "Ex2" in the gameclient package, this is the only class that contains a main method – which means this is the class to "run".

Now, as you are running it – it will open GUI's (MyFrame_Gui class) window:
* Enter your Id:

<img width="525" alt="Screen Shot 2020-12-16 at 13 07 45" src="https://user-images.githubusercontent.com/73976733/102343296-06b9ef80-3fa3-11eb-96d4-4867e7b0438f.png">


* Choose the level between 0-23:

<img width="439" alt="Screen Shot 2020-12-16 at 13 08 18" src="https://user-images.githubusercontent.com/73976733/102343440-3b2dab80-3fa3-11eb-9a44-105d4f696941.png">


and after this window open and the game is starting up(in this window it's level 11) :

<img width="861" alt="Screen Shot 2020-12-16 at 13 34 40" src="https://user-images.githubusercontent.com/73976733/102343706-8647be80-3fa3-11eb-8d5c-549c51bd9969.png">

And when the game is over, the score and the number of steps taken will appear:

<img width="286" alt="Screen Shot 2020-12-16 at 13 28 37" src="https://user-images.githubusercontent.com/73976733/102344062-0706ba80-3fa4-11eb-87ef-48cddf933ffb.png">

 And :

<img width="359" alt="Screen Shot 2020-12-16 at 13 29 03" src="https://user-images.githubusercontent.com/73976733/102344251-4d5c1980-3fa4-11eb-8240-4b2094d3e481.png">




# Data Stracture:

# Edge class :

This class build single edge.

An object from class Edge contain the follow feature:

* Src - the key of tne node that represent the source.
* Dest - the key of tne node that represent the destination.
* Tag - its flag that change when pass on edge.
* Weight - how much cost to pass on this edge in the path.
* Info-String

# Node class:
This class build single node. An object from class Node contain the follow feature:

* Key - it the ID of this node in the graph.
* Tag - its flag that change when pass on node.
* Weight - it represent the cost of the path that take to get from Src to this node that represent the Dest.
* Location - represent the locatiobn of the node on the axis - X, Y, Z.
* Info-String

# DWGraph_DS class :
This class build a graph that defined by nodes and edges. It contain a collection of node with use in Node data Class and collection of edge with use in Edge Class In this class I used the hashamp for vertices and hasmap for Edges  I used this data structure because it actually performs the actions that the class needs most efficiently.

An object from class DGraph contain the follow feature:

* Nodes - is a collection of HashMap of nodes in the graph.
* Edges - is a collection of HashMap of edges in the graph.
* EdgeSize - is count of the edges in the graph
* MC - is a count of the changes that implement on the graph.


# DWGraph_Algo class:

In this class we solved a algorithmic problems as - What is the shortest path in the graph, does the graph is a connective graph, how to read graph from file and how save graph as file.

An object from class Graph_Algo contain the follow feature:

 directed_weighted_graph - this is the graph that we perform the algorithms on.
 * shortestpath -Finding the shortest route in a weighted and deliberate graph I realized this with the help of the Digestra algorithm
 
 * IsConected-Checking whether a weighted and directed graph is conected I implemented this with the help of a DFS algorithm on each vertex if it has a edge coming in and out of it so I used DFS twice in each direction of edge
 
 # Example For Shortest Path :
 ![multi-stage-graph](https://user-images.githubusercontent.com/73976733/102022330-c124d900-3d8e-11eb-84ae-4ad241999919.jpg)







# Game Client Stracture:



# CL_Pokemon class:
This class build single pokemon. An object from class CL_Pokemon contain the follow feature:

* Pos - represent the locatiobn of the fruit on the axis - X, Y, Z.
* Value - represent the point that the fruit is worth.
* Type - represent if the fruit on up edge or down edge -1(D) or 1(U).
* edge - represent the deatination of the robot


An object from class FruitsList contain the follow feature:

# CL_Agent class:

This class build single robot. An object from class Robots contain the follow feature:

* Pos - represent the locatiobn of the robot on the axis - X, Y, Z.

* Value - represents the points earned by the agent.

* Id - represent the ID of the agent

* _curr_edge - represent the destination node that the agent go to.

* Speed - represent the speed of the agent.


# Arena class:
An object from the Arena  class is a List of Agent and Pokemons. In order to realize a Arenat we used the LinkedList class where each node contains agent and pokemon object.


 
# Utils:
This package there are functions that are responsible for the operation of the game.





# MyFrame_Gui:
This class responsible for drawing all game data and the graphics to user with help class Jframe.

# HAVE FUN!
