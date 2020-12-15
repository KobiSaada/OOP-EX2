# OOP-EX2- The Challenge Pokemon Game 
![Webp net-compress-image (3)](https://user-images.githubusercontent.com/73976733/101813689-b9163080-3b25-11eb-9e93-5471b17b0e15.jpg)
![pokemon](https://user-images.githubusercontent.com/73976733/101813899-fe3a6280-3b25-11eb-9a16-72f29d4ae6c3.gif) 
# The Pokemon Challenge Game :
![output-onlinepngtools (6)](https://user-images.githubusercontent.com/73976733/101840405-b418a780-3b4c-11eb-8bdc-febf47295196.png)

# Genral Info :

Task number 2 in the object-oriented course This task is divided into 2 parts, first part, similar to previous tasks. Implementing a graph.
Only this time a deliberate and weighted graph with the addition of departments and part two realizing a Pokemon game and managing agents for their target Pokemon similar to the Pacman game the game based on Grahs with nodes and edges taht represent the route. The player in the game represented by pokemonagent. There are fruits scattered on the graph randomly from the srever and the goal of the game is to eat as much fruit as possible and earn as many points as possible until the time is over.
The game draw by JFrme class.

# Getting Started :
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

# Prerequisites:

* JDK-11.0.4

* Eclipse

* IntelliJ

* or some other IDE (recommended)

# Installing :

Clone that project to your workspace directory

     git clone https://github.com/KobiSaada/OOP-EX2-Pokemon-Game                      
      
Open your IDE and make sure you see the project "OOPEX2-Challenge Pokemon"

# How to run it?

There is a class called "Ex2" in the gameclient package, this is the only class that contains a main method – which means this is the class to "run".

Now, as you are running it – it will open GUI's (MyFrame class) window:
* Enter your Id:

<img width="320" alt="Screen Shot 2020-12-11 at 0 49 02" src="https://user-images.githubusercontent.com/73976733/101839282-c42f8780-3b4a-11eb-973d-c538c7b8f349.png">


* Choose the level between 0-23:

<img width="234" alt="Screen Shot 2020-12-11 at 0 50 19" src="https://user-images.githubusercontent.com/73976733/101839374-f17c3580-3b4a-11eb-9b75-dd88c46867a0.png">


and after this window open and the game is starting up(in this window it's level 11) :

<img width="859" alt="Screen Shot 2020-12-13 at 21 05 27" src="https://user-images.githubusercontent.com/73976733/102022217-04327c80-3d8e-11eb-8cd6-c335ec408a76.png">




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
This class build a graph that defined by nodes and edges. It contain a collection of node with use in Node Class and collection of edge with use in Edge Clas.

An object from class DGraph contain the follow feature:

* Nodes - is a collection of HashMap of nodes in the graph.
* Edges - is a collection of HashMap of edges in the graph.
* EdgeSize - is count of the edges in the graph
* MC - is a count of the changes that implement on the graph.


# DWGraph_Algo class:

In this class we solved a algorithmic problems as - What is the shortest path in the graph, does the graph is a connective graph, how to read graph from file and how save graph as file.

An object from class Graph_Algo contain the follow feature:

 directed_weighted_graph - this is the graph that we perform the algorithms on.
 
 # Example For Shortest Path :
 ![multi-stage-graph](https://user-images.githubusercontent.com/73976733/102022330-c124d900-3d8e-11eb-84ae-4ad241999919.jpg)







# Game Client Stracture:



# CL_Pokemon class:
This class build single pokemon. An object from class CL_Pokemon contain the follow feature:

* Pos - represent the locatiobn of the fruit on the axis - X, Y, Z.
* Value - represent the point that the fruit is worth.
* Type - represent if the fruit on up edge or down edge -1(D) or 1(U).
* Pic - represent the picture of the fruit (pokemon).
* edge - represent the deatination of the robot


An object from class FruitsList contain the follow feature:

# CL_Agent class:

This class build single robot. An object from class Robots contain the follow feature:

* Pos - represent the locatiobn of the robot on the axis - X, Y, Z.

* Value - represents the points earned by the robot.

* Id - represent the ID of the robot

* _curr_edge - represent the destination node that the robot go to.

* Speed - represent the speed of the robot.


# Arena class:
An object from the Arena  class is a List of Agent and Pokemons. In order to realize a Arenat we used the LinkedList class where each node contains agent and pokemon object.


 
# Utils:
This package there are functions that are responsible for the operation of the game.





# MyFrame_Gui:
This class responsible for drawing all game data and the graphics to user with help class Jframe.

# HAVE FUN!
