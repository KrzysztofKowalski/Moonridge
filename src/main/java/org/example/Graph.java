package org.example;


import java.util.List;

public class Graph {
    public static class Edge {
        public int from;
        public int to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    /*
        n - number of nodes in the graph
        edges - contains edges of the graph of form {from: 0, to: 4}
        note that node indices in this case are zero-based
     */
    public Graph(int n, List<Edge> edges) {
        // Initialize your graph here
    }

    public boolean hasEdgeInBetween(int from, int to) {
        // answer the query here
        return false;
    }

}
