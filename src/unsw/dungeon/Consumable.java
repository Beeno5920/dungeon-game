package unsw.dungeon;

public interface Consumable {
    public int getAvailability();

    public void setAvailability(int i);

    public int increaseAvailability(int i);

    public int decreaseAvailability(int i);
}
