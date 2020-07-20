package unsw.dungeon.Goal;

import unsw.dungeon.Dungeon;

import java.util.ArrayList;
import java.util.List;

public class AndGoal implements Goal {
    private List<Goal> subGoals;

    public AndGoal() {
        this.subGoals = new ArrayList<>();
    }

    @Override
    public boolean checkSelf() {
        return checkSubGoals();
    }

    @Override
    public boolean checkSubGoals() {
        for (Goal goal : subGoals) {
            if (!goal.checkSelf() || !goal.checkSubGoals())
                return false;
        }
        return true;
    }

    @Override
    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }

    @Override
    public void removeSubGoal(Goal goal) {
        subGoals.remove(goal);
    }
}
