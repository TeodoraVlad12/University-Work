import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Main {
    private static final int V = 20; // Number of vertices in the graph
    private static final int MAX_PARALLEL_DEPTH = 2; // Control parallel exploration depth
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Object lock = new Object();

    // Check if a vertex can be added to the path
    private static boolean isSafe(boolean[][] graph, List<Integer> path, int pos, int v) {
        // Check if the vertex connects to the previous vertex
        if (!graph[path.get(pos - 1)][v])
            return false;

        // Check if the vertex is already in the path
        return !path.subList(0, pos).contains(v);
    }

    // Utility method to find Hamiltonian Cycle with multi-level parallel exploration
    private static List<Integer> hamCycleUtil(boolean[][] graph, List<Integer> path, int pos, int depth) throws Exception {
        // If all vertices are included in the path
        if (pos == V) {
            // Check if last vertex connects to the first vertex to complete the cycle
            if (graph[path.get(pos - 1)][path.get(0)])
                return path;
            else
                return new ArrayList<>();
        }

        // If we're at a depth where we want to parallelize
        if (depth <= MAX_PARALLEL_DEPTH) {
            // Collect safe neighbors
            List<Integer> safeNeighbors = new ArrayList<>();
            for (int v = 0; v < V; v++) {
                if (isSafe(graph, path, pos, v)) {
                    safeNeighbors.add(v);
                }
            }

            // Parallel exploration of neighbors
            List<Future<List<Integer>>> futures = safeNeighbors.stream()
                    .map(v -> executorService.submit(() -> {
                        // Create a new path with the current neighbor
                        List<Integer> newPath = new ArrayList<>(path);
                        newPath.set(pos, v);

                        // Recursively explore with increased depth
                        return hamCycleUtil(graph, newPath, pos + 1, depth + 1);
                    }))
                    .collect(Collectors.toList());

            // Check results of parallel tasks
            for (Future<List<Integer>> future : futures) {
                List<Integer> result = future.get();
                if (!result.isEmpty()) {
                    return result;
                }
            }
        } else {
            // Sequential exploration for deeper levels
            for (int v = 0; v < V; v++) {
                if (isSafe(graph, path, pos, v)) {
                    // Create a new path with the current vertex
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.set(pos, v);

                    // Recursively explore
                    List<Integer> result = hamCycleUtil(graph, newPath, pos + 1, depth + 1);
                    if (!result.isEmpty()) {
                        return result;
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    // Find Hamiltonian Cycle starting from a fixed vertex
    private static void findHamiltonianCycle(boolean[][] graph, int fixedStartVertex) {
        try {
            // Initialize path with the fixed start vertex
            List<Integer> path = new ArrayList<>(Collections.nCopies(V, -1));
            path.set(0, fixedStartVertex);

            // Find Hamiltonian Cycle
            List<Integer> resultPath = hamCycleUtil(graph, path, 1, 1);

            // Synchronize output
            synchronized (lock) {
                if (resultPath.isEmpty()) {
                    System.out.println("No Hamiltonian Cycle found.");
                } else {
                    System.out.print("Hamiltonian Cycle found starting from vertex " + fixedStartVertex + ": ");
                    for (int vertex : resultPath) {
                        System.out.print(vertex + " ");
                    }
                    System.out.println(resultPath.get(0)); // Complete the cycle
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate a random directed graph
    private static boolean[][] generateRandomGraph(int v, double edgeProbability) {
        boolean[][] graph = new boolean[v][v];
        Random random = new Random();

        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (i != j && random.nextDouble() < edgeProbability) {
                    graph[i][j] = true;
                }
            }
        }
        return graph;
    }

    // Print the graph
    private static void printGraph(boolean[][] graph) {
        for (boolean[] row : graph) {
            for (boolean edge : row) {
                System.out.print(edge ? "1 " : "0 ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            // Example graph
            boolean[][] graph1 = {
                    {false, true, false, true, false},
                    {true, false, true, true, true},
                    {false, true, false, false, true},
                    {true, true, false, false, true},
                    {false, true, true, true, false}
            };

            int v = 20;
            double edgeProbability = 0.3; //0.1 for generating a graph without a cycle

            // Generate and print random graph
            boolean[][] graph = generateRandomGraph(v, edgeProbability);
            System.out.println("Generated Graph:");
            printGraph(graph);

            long startTime = System.nanoTime();

            // Find Hamiltonian Cycle starting from a FIXED vertex (4)
            findHamiltonianCycle(graph, 4);

            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Execution Time: " + duration / 1_000_000.0 + " ms");

            // Shutdown executor service
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
