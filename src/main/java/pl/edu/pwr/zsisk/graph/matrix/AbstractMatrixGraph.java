package pl.edu.pwr.zsisk.graph.matrix;

import pl.edu.pwr.zsisk.graph.Edge;
import pl.edu.pwr.zsisk.graph.GraphException;
import pl.edu.pwr.zsisk.graph.IGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractMatrixGraph implements IGraph {
    protected int[][] matrix;
    private int graphDimension;

    public AbstractMatrixGraph(String fileName) {
        try (BufferedReader inputStream = new BufferedReader(new FileReader(fileName))) {
            graphDimension = -1;
            // read graph dimension
            do {
                var readiedLine = inputStream.readLine().split(" ");
                if (readiedLine[0].equals("DIMENSION:"))
                    graphDimension = Integer.parseInt(readiedLine[2]);
            } while (graphDimension == -1);
            // initialize graphMatrix
            matrix = new int[graphDimension][graphDimension];
            for (int i = 0; i < graphDimension; i++) {
                for (int j = 0; j < graphDimension; j++) {
                    matrix[i][j] = -1;
                }
            }
            // jump to matrix
            //noinspection StatementWithEmptyBody
            while (!Objects.equals(inputStream.readLine().split(" ")[0], "EDGE_WEIGHT_SECTION")) ;
            int rowCounter = 0, columnCounter = 0;
            var readiedLine = "";
            while (true) {
                readiedLine = inputStream.readLine();
                if (readiedLine.equals("EOF"))
                    break;
                var readiedValues = Arrays.stream(readiedLine.split(" "))
                        .filter(s -> !s.equals(""))
                        .map(Integer::parseInt).toList();
                for (Integer readiedValue : readiedValues) {
                    if (columnCounter == graphDimension) {
                        rowCounter++;
                        columnCounter = 0;
                    }
                    try {
                        addEdge(new Edge(rowCounter, columnCounter, readiedValue == 9999 ? -1 : readiedValue));
                    } catch (GraphException e) {
                        System.out.println("error:" + e);
                    }
                    columnCounter++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printInTerminal() {
        for (int i = 0; i < graphDimension; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    public int getGraphDimension() {
        return graphDimension;
    }

    @Override
    public int getEdgeWeight(int beginNode, int endNode) {
        return matrix[beginNode][endNode];
    }
}
