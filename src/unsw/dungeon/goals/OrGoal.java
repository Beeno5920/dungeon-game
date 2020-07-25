package unsw.dungeon.goals;

import java.util.ArrayList;
import java.util.List;

public class OrGoal implements Goal {
    private List<Goal> subGoals;

    public OrGoal() {
        this.subGoals = new ArrayList<>();
    }

    @Override
    public boolean checkSelf() {
        return checkSubGoals();
    }

    @Override
    public boolean checkSubGoals() {
        if (subGoals.isEmpty())
            return true;
        for (Goal goal : subGoals) {
            if (goal.checkSelf() && goal.checkSubGoals())
                return true;
        }
        return false;
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
