import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int u, int v, int w) {
        src = u;
        dest = v;
        weight = w;
    }

    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class UnionFind {
    int[] parent;

    UnionFind(int n) {
        parent = new int[n];
        Arrays.fill(parent, -1);
    }

    int find(int u) {
        if (parent[u] < 0) return u;
        return parent[u] = find(parent[u]);
    }

    boolean union(int u, int v) {
        int pu = find(u), pv = find(v);
        if (pu == pv) return false;
        parent[pu] = pv;
        return true;
    }
}

public class primsandkruskals {

    static void kruskalMST(List<Edge> edges, int V) {
        Collections.sort(edges);
        UnionFind uf = new UnionFind(V);

        int mstCost = 0;
        System.out.println("Kruskal's MST edges:");
        for (Edge e : edges) {
            if (uf.union(e.src, e.dest)) {
                System.out.println(e.src + " - " + e.dest + " : " + e.weight);
                mstCost += e.weight;
            }
        }
        System.out.println("Total Cost (Kruskal): " + mstCost);
    }

    static void primMST(int[][] graph, int V) {
        boolean[] visited = new boolean[V];
        int[] key = new int[V];
        int[] parent = new int[V];

        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = -1, min = Integer.MAX_VALUE;
            for (int v = 0; v < V; v++) {
                if (!visited[v] && key[v] < min) {
                    min = key[v];
                    u = v;
                }
            }

            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !visited[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        int cost = 0;
        System.out.println("\nPrim's MST edges:");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + " : " + graph[i][parent[i]]);
            cost += graph[i][parent[i]];
        }
        System.out.println("Total Cost (Prim): " + cost);
    }

    public static void main(String[] args) {
        int V = 5;
        int[][] graph = {
            {0, 2, 0, 6, 0},
            {2, 0, 3, 8, 5},
            {0, 3, 0, 0, 7},
            {6, 8, 0, 0, 9},
            {0, 5, 7, 9, 0}
        };

        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (graph[i][j] != 0) {
                    edgeList.add(new Edge(i, j, graph[i][j]));
                }
            }
        }

        kruskalMST(edgeList, V);
        primMST(graph, V);
    }
}
