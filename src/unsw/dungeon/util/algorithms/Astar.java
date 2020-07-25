package unsw.dungeon.util.algorithms;

import unsw.dungeon.characters.Character;
import unsw.dungeon.util.PathFinder.Node;

import java.util.*;

public class Astar implements SearchingAlgorithm {
    private Character subject;
    private int[] start;
    private int[] end;
    private boolean negation = false;
    private final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private List<Node> path;

    public Astar(Character subject, int[] start, int[] end) {
        this.subject = subject;
        this.start = start;
        this.end = end;
        this.path = new ArrayList<>();
    }

    private long getHeuristic(int x, int y) {
        return (int) (Math.pow(x - end[0], 2) + Math.pow(y - end[1], 2));
    }

    private void constructPath(Node end) {
        while (end.prev != null) {
            path.add(0, end);
            end = end.prev;
        }
    }

    @Override
    public List<Node> run() {
        setStartPoint(subject.getPosition());
        path.clear();

        Set<String> visited = new HashSet<>();
        PriorityQueue<Node> toExplore = new PriorityQueue<>((a, b) -> (int) (a.dist - b.dist));
        if (negation)
            toExplore = new PriorityQueue<>((a, b) -> (int) (b.dist - a.dist));
        toExplore.add(new Node(start[0], start[1], getHeuristic(start[0], start[1])));

        while (!toExplore.isEmpty()) {
            Node curr = toExplore.poll();
            if (curr.x == end[0] && curr.y == end[1] || (negation && toExplore.size() > 20)) {
                constructPath(curr);
                return path;
            }
            visited.add(curr.x + " " + curr.y);
            for (int[] dir : dirs) {
                int x = curr.x + dir[0], y = curr.y + dir[1];
                if (subject.canMoveTo(x, y) && !visited.contains(x + " " + y)) {
                    Node next = new Node(x, y, curr.dist + 1 + getHeuristic(x, y));
                    next.prev = curr;
                    toExplore.add(next);
                }
            }
        }

        return path;
    }

    @Override
    public void setStartPoint(int[] startPoint) {
        start = startPoint;
    }

    @Override
    public void setEndPoint(int[] endPoint) {
        end = endPoint;
    }

    public void setSubject(Character subject) {
        this.subject = subject;
    }

    @Override
    public void setNegation(boolean negation) {
        this.negation = negation;
    }
}
