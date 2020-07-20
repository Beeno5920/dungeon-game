package unsw.dungeon.Goal;

import unsw.dungeon.Dungeon;

import java.util.ArrayList;
import java.util.List;

public class MainGoal implements Goal {
    protected Dungeon dungeon;
    private List<Goal> goals;

    public MainGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.goals = new ArrayList<>();
    }

    @Override
    public boolean checkSelf() {
        return checkSubGoals();
    }

    @Override
    public boolean checkSubGoals() {
        for (Goal goal : goals) {
            if (!goal.checkSelf() || !goal.checkSubGoals())
                return false;
        }
        return true;
    }

    @Override
    public void addSubGoal(Goal goal) {
        goals.add(goal);
    }

    @Override
    public void removeSubGoal(Goal goal) {
        goals.remove(goal);
    }
}
