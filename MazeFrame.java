import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashSet;

public class MazeFrame extends JFrame {
    MazePanel panel;
    int[][] maze;
    Point start, end;

    // UI controls
    JButton bfsButton, dfsButton, resetButton, generateButton;
    JSlider speedSlider;
    JLabel algoLabel, pathLengthLabel, visitedLabel, timeLabel;

    public MazeFrame() {
        // Start with a simple default maze
        maze = new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 1},
                {0, 0, 0, 1},
                {1, 1, 0, 0}
        };
        start = new Point(0, 0);
        end = new Point(3, 3);

        // Main panel
        panel = new MazePanel(maze, start, end);
        add(panel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttons = new JPanel();
        bfsButton = new JButton("Solve BFS");
        dfsButton = new JButton("Solve DFS");
        resetButton = new JButton("Reset");
        generateButton = new JButton("Generate Maze");

        buttons.add(bfsButton);
        buttons.add(dfsButton);
        buttons.add(resetButton);
        buttons.add(generateButton);

        // Speed Slider
        speedSlider = new JSlider(1, 200, 50);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        buttons.add(new JLabel("Speed:"));
        buttons.add(speedSlider);

        add(buttons, BorderLayout.SOUTH);

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(0, 1));
        algoLabel = new JLabel("Algorithm: -");
        pathLengthLabel = new JLabel("Path Length: -");
        visitedLabel = new JLabel("Nodes Visited: -");
        timeLabel = new JLabel("Execution Time: -");

        statsPanel.add(algoLabel);
        statsPanel.add(pathLengthLabel);
        statsPanel.add(visitedLabel);
        statsPanel.add(timeLabel);

        add(statsPanel, BorderLayout.EAST);

        // Button Actions
        bfsButton.addActionListener(e -> solveBFSAnimated());
        dfsButton.addActionListener(e -> solveDFSAnimated());
        resetButton.addActionListener(e -> reset());
        generateButton.addActionListener(e -> generateMaze());

        pack();
        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void solveBFSAnimated() {
        new Thread(() -> {
            java.util.Set<Point> visited = new HashSet<>();
            long startTime = System.currentTimeMillis();

            java.util.List<Point> path = MazeSolver.solveBFSWithAnimation(
                maze, start, end, visited, panel, speedSlider.getValue()
            );

            long endTime = System.currentTimeMillis();

            panel.setVisited(visited);
            panel.setPath(path);
            panel.repaint();

            algoLabel.setText("Algorithm: BFS");
            pathLengthLabel.setText("Path Length: " + (path != null ? path.size() : 0));
            visitedLabel.setText("Nodes Visited: " + visited.size());
            timeLabel.setText("Execution Time: " + (endTime - startTime) + " ms");
        }).start();
    }

    void solveDFSAnimated() {
        new Thread(() -> {
            java.util.Set<Point> visited = new HashSet<>();
            java.util.List<Point> path = new java.util.ArrayList<>();

            long startTime = System.currentTimeMillis();

            MazeSolver.solveDFSWithAnimation(
                maze, start, end, visited, path, panel, speedSlider.getValue()
            );

            long endTime = System.currentTimeMillis();

            panel.setVisited(visited);
            panel.setPath(path);
            panel.repaint();

            algoLabel.setText("Algorithm: DFS");
            pathLengthLabel.setText("Path Length: " + path.size());
            visitedLabel.setText("Nodes Visited: " + visited.size());
            timeLabel.setText("Execution Time: " + (endTime - startTime) + " ms");
        }).start();
    }

    void generateMaze() {
        MazeGenerator generator = new MazeGenerator(21, 21); // choose maze size
        maze = generator.generateMaze();
        start = new Point(1, 1);
        end = new Point(maze.length - 2, maze[0].length - 2);
        panel.setMaze(maze, start, end);
        reset();
        pack();
    }

    void reset() {
        panel.setVisited(new HashSet<>());
        panel.setPath(null);
        panel.repaint();

        algoLabel.setText("Algorithm: -");
        pathLengthLabel.setText("Path Length: -");
        visitedLabel.setText("Nodes Visited: -");
        timeLabel.setText("Execution Time: -");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MazeFrame().setVisible(true);
        });
    }
}
