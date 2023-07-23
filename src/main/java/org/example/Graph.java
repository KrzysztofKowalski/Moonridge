/*
 *
 * This file is part of arche-quickstart.
 *  Copyright (c) 2023.
 * arche-quickstart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [Project Name] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with arche-quickstart. If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
