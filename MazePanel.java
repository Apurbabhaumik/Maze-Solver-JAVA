import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;

public class MazePanel extends JPanel {
    int[][] maze;
    List<Point> path;       // Final path
    Set<Point> visited;     // For animation

    int cellSize = 50;


    Point start, end;

    public void setMaze(int[][] maze, Point start, Point end) {
    this.maze = maze;
    this.start = start;
    this.end = end;
    repaint();
}

    public MazePanel(int[][] maze, Point start, Point end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        this.visited = new HashSet<>();
    }

    public void setVisited(Set<Point> visited) {
        this.visited = visited;
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(maze[0].length * cellSize, maze.length * cellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }

        // Visited nodes
        g.setColor(new Color(173, 216, 230)); // light blue
        for (Point p : visited) {
            g.fillRect(p.col * cellSize, p.row * cellSize, cellSize, cellSize);
        }

        // Final path
        if (path != null) {
            g.setColor(Color.YELLOW);
            for (Point p : path) {
                g.fillRect(p.col * cellSize, p.row * cellSize, cellSize, cellSize);
            }
        }

        // Start and end
        g.setColor(Color.GREEN);
        g.fillRect(start.col * cellSize, start.row * cellSize, cellSize, cellSize);

        g.setColor(Color.RED);
        g.fillRect(end.col * cellSize, end.row * cellSize, cellSize, cellSize);
    }
}
