package pl.edu.pwr.zsisk;

import pl.edu.pwr.zsisk.graph.IGraph;
import pl.edu.pwr.zsisk.graph.matrix.DirectedAdjacencyMatrix;

public class Engine {
    public static void main(String[] args) {
        IGraph graph = new DirectedAdjacencyMatrix("br17.atsp");
        graph.printInTerminal();
    }
}