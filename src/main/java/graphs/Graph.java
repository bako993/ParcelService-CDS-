package graphs;

import java.util.*;

public class Graph {
    ArrayList<Node> nodes;
    public int[][] matrix;
    int nrOfNode;
    private static final int PARENT = -1;
    public Graph(int nrOfNode) {
        this.nrOfNode = nrOfNode;
        matrix = new int[nrOfNode][nrOfNode];
        nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(int src, int dst, int weight) {
        matrix[src][dst] = weight;

        // Removing the below line will make the graph directed.
        matrix[dst][src] = weight;
    }

    public boolean checkEdge(int src, int dst) {
        if (matrix[src][dst] == 0) {
            return false;
        } else {
            return true;
        }
    }
    public void print() {
        System.out.println("Adjacency Matrix Graph:  ");
        System.out.println();
        System.out.print("          ");
        for (Node node :
                nodes) {
            System.out.print(node.client+ "  ");
        }
        System.out.println();
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            System.out.print( count++ +"::" +nodes.get(i).client + "   ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "       ");
            }
            System.out.println();
        }
    }
    public void bfs(int src) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[nrOfNode];
        queue.offer(src);
        visited[src] = true;
        System.out.println("Breadth first search: ");
        while (queue.size() != 0) {
            src = queue.poll();
            System.out.println(nodes.get(src).client + " = visited");
            for (int i = 0; i < matrix[src].length; i++) {
                if (matrix[i][src] ==nodes.get(src).cost &&matrix[src][i] ==nodes.get(src).cost && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }
    }
    public void dfs(int src) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[nrOfNode];
        stack.push(src);
        visited[src] = true;
        System.out.println("Depth fist search: ");
        while (stack.size() != 0) {
            src = stack.pop();
            System.out.println(nodes.get(src).client + " = visited");
            for (int i = 0; i < matrix[src].length; i++) {
                if (matrix[i][src] ==nodes.get(src).cost && matrix[src][i] ==nodes.get(src).cost && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                }
            }
        }
    }

    public void primAlgorithm(int[][] adjMatrix) {
        int V = adjMatrix.length;
        int parent[] = new int[V];
        int weight[] = new int[V];
        Boolean visited[] = new Boolean[V];
        for (int i = 0; i < V; i++) {
            weight[i] = Integer.MAX_VALUE;
            visited[i] = false;
            parent[i] = 0;
        }
        weight[0] = 0;
        for (int i = 0; i < V - 1; i++) {
            int u = minWeight(weight,visited);
            visited[u] = true;
            for (int v = 0; v < V; v++)
                if (adjMatrix[u][v] != 0 && visited[v] ==false && adjMatrix[u][v] < weight[v]) {
                    parent[v] = u;
                    weight[v] = adjMatrix[u][v];
                }
        }
        int sum = 0;
        System.out.println("Edge  -->  Weight");
        for (int i = 0; i < V ; i++) {
            System.out.println(parent[i] + " - " + i + " --> " + adjMatrix[i][parent[i]]);
            sum += adjMatrix[i][parent[i]];
        }
        System.out.println("Total cost is : " + sum);
    }

    public int minWeight(int weight[], Boolean visited[]) {
        int V = weight.length;
        int min = Integer.MAX_VALUE;
        //   int minIndex = -1;
        int minIndex = 0;
        for (int i = 0; i < V; i++)
            if (visited[i]==false && weight[i] < min) {
                min = weight[i];
                minIndex = i;
            }
        return minIndex;
    }

    public void dijkstraAlgo(int[][] adjacencyMatrix, int start) {

        int[] shortestDistances = new int[matrix.length];

        boolean[] visitedNode = new boolean[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            shortestDistances[i] = Integer.MAX_VALUE;
            visitedNode[i] = false;
        }

        shortestDistances[start] = 0;

        int[] parents = new int[matrix.length];

        parents[start] = PARENT;

        for (int i = 1; i < matrix.length; i++) {

            int nearestNode = -1;

            int shortestDistance = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                if (!visitedNode[j] && shortestDistances[j] < shortestDistance) {
                    nearestNode = j;
                    shortestDistance = shortestDistances[j];
                }
            }

            visitedNode[nearestNode] = true;

            for (int k = 0; k < matrix.length; k++) {
                int edgeDistance = adjacencyMatrix[nearestNode][k];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[k])) {
                    parents[k] = nearestNode;
                    shortestDistances[k] = shortestDistance + edgeDistance;
                }
            }
        }
        printDijkstra(start, shortestDistances, parents);
    }

    private void printDijkstra(int startVertex, int[] distances, int[] parents) {
        int nNodes = distances.length;
        System.out.print("Vertex\t\t Distance\t\tPath");
        for (int i = 0; i < nNodes; i++) {
            if (i != startVertex) {
                System.out.print("\n" + startVertex + " -> ");
                if (i < 10) {
                    System.out.print(i + " \t\t\t");
                } else {
                    System.out.print(i + " \t\t");
                }
                System.out.print(distances[i] + "\t\t\t");
                printDijkstraPath(i, parents);
            }
        }
    }

    private void printDijkstraPath(int currentVertex, int[] parents) {

        if (currentVertex == PARENT) {
            return;
        }
        printDijkstraPath(parents[currentVertex], parents);
        System.out.print(currentVertex + " ");
    }
}