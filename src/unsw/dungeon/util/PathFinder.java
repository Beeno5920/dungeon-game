package unsw.dungeon.util;

import unsw.dungeon.util.algorithms.SearchingAlgorithm;

import java.util.List;

public class PathFinder {
    private SearchingAlgorithm searchingAlgorithm;

    public PathFinder(SearchingAlgorithm searchingAlgorithm) {
        this.searchingAlgorithm = searchingAlgorithm;
    }

    public List<Node> findPath() {
        return (List<Node>) searchingAlgorithm.run();
    }

    public void setStartPoint(int[] startPoint) {
        searchingAlgorithm.setStartPoint(startPoint);
    }

    public void setEndPoint(int[] endPoint) {
        searchingAlgorithm.setEndPoint(endPoint);
    }

    public void setNegation(boolean negation) {
        searchingAlgorithm.setNegation(negation);
    }

    public static class Node {
        public int x, y;
        public long dist;
        public Node prev;

        public Node(int x, int y, long dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.prev = null;
        }
    }
}
