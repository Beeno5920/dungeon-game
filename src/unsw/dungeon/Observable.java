package unsw.dungeon;

public interface Observable {
    public void notify(Observer object);

    public void notifyAllObservers();

    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);
}
