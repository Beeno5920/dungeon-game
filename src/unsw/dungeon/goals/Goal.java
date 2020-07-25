package unsw.dungeon.goals;

public interface Goal {
    public boolean checkSelf();

    public boolean checkSubGoals();

    public void addSubGoal(Goal goal);

    public void removeSubGoal(Goal goal);
}
