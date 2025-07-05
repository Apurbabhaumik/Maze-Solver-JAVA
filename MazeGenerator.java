import java.util.*;

public class MazeGenerator {
    private int rows, cols;
    private int[][] maze;

    private static final int WALL = 1;
    private static final int PATH = 0;

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new int[rows][cols];
        for (int[] row : maze) Arrays.fill(row, WALL); // start with all walls
    }

    public int[][] generateMaze() {
        generate(1, 1);
        return maze;
    }

    private void generate(int r, int c) {
        maze[r][c] = PATH;

        int[][] directions = { {0, -2}, {0, 2}, {-2, 0}, {2, 0} };
        Collections.shuffle(Arrays.asList(directions));

        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            if (withinBounds(nr, nc) && maze[nr][nc] == WALL) {
                maze[r + dir[0] / 2][c + dir[1] / 2] = PATH; // knock down wall
                generate(nr, nc);
            }
        }
    }

    private boolean withinBounds(int r, int c) {
        return r > 0 && r < rows - 1 && c > 0 && c < cols - 1;
    }
}
