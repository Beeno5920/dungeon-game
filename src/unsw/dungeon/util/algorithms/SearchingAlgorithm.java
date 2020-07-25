package unsw.dungeon.util.algorithms;

public interface SearchingAlgorithm {
    public Object run();

    public void setStartPoint(int[] startPoint);

    public void setEndPoint(int[] endPoint);

    public void setNegation(boolean negation);
}
