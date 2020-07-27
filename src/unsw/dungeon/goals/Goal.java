package unsw.dungeon.goals;

import java.util.List;

public interface Goal {
    public boolean checkSelf();

    public boolean checkSubGoals();

    public void addSubGoal(Goal goal);

    public void removeSubGoal(Goal goal);

    public List<Goal> getGoals();
}
