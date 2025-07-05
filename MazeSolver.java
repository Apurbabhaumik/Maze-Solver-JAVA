import java.util.*;

public class MazeSolver {

    // -----------------------------------------
    // ✅ BFS with animation
    // -----------------------------------------
    public static java.util.List<Point> solveBFSWithAnimation(
        int[][] maze,
        Point start,
        Point end,
        java.util.Set<Point> visited,
        MazePanel panel,
        int delay
    ) {
        java.util.Queue<Point> queue = new LinkedList<>();
        java.util.Map<Point, Point> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        int rows = maze.length;
        int cols = maze[0].length;

        int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            // Animate visit
            panel.setVisited(visited);
            panel.repaint();
            sleep(delay);

            if (current.equals(end)) {
                // Reconstruct path
                java.util.List<Point> path = new ArrayList<>();
                Point temp = end;
                while (temp != null) {
                    path.add(0, temp);
                    temp = parent.get(temp);
                }
                return path;
            }

            for (int[] dir : directions) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                Point neighbor = new Point(newRow, newCol);

                if (isValid(newRow, newCol, maze, rows, cols) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }

        return null; // no path found
    }

    // -----------------------------------------
    // ✅ DFS with animation
    // -----------------------------------------
    public static void solveDFSWithAnimation(
        int[][] maze,
        Point start,
        Point end,
        java.util.Set<Point> visited,
        java.util.List<Point> path,
        MazePanel panel,
        int delay
    ) {
        dfsHelper(maze, start, end, visited, path, panel, delay);
    }

    private static boolean dfsHelper(
        int[][] maze,
        Point current,
        Point end,
        java.util.Set<Point> visited,
        java.util.List<Point> path,
        MazePanel panel,
        int delay
    ) {
        visited.add(current);
        path.add(current);

        // Animate visit
        panel.setVisited(visited);
        panel.setPath(new ArrayList<>(path));
        panel.repaint();
        sleep(delay);

        if (current.equals(end)) {
            return true; // path found
        }

        int rows = maze.length;
        int cols = maze[0].length;
        int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

        for (int[] dir : directions) {
            int newRow = current.row + dir[0];
            int newCol = current.col + dir[1];
            Point neighbor = new Point(newRow, newCol);

            if (isValid(newRow, newCol, maze, rows, cols) && !visited.contains(neighbor)) {
                if (dfsHelper(maze, neighbor, end, visited, path, panel, delay)) {
                    return true; // stop on first path found
                }
            }
        }

        path.remove(path.size() - 1); // backtrack
        return false;
    }

    // -----------------------------------------
    // ✅ Helper: check cell validity
    // -----------------------------------------
    private static boolean isValid(int row, int col, int[][] maze, int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] == 0;
    }

    // -----------------------------------------
    // ✅ Helper: sleep for animation delay
    // -----------------------------------------
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
