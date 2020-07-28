package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

public class AndGoal implements Goal {
    private List<Goal> subGoals;
    private BooleanProperty achieved;

    public AndGoal() {
        this.subGoals = new ArrayList<>();
        this.achieved = new SimpleBooleanProperty(false);
    }

    public BooleanProperty getAchievedProperty() {
        return achieved;
    }

    @Override
    public boolean checkSelf() {
        return checkSubGoals();
    }

    @Override
    public boolean checkSubGoals() {
        boolean result = true;
        for (Goal goal : subGoals) {
            boolean self = goal.checkSelf(), subGoals = goal.checkSubGoals(); // in order to update all the boolean properties of subgoals
            if (!self || !subGoals)
                result = false;
        }
        achieved.set(result);
        return result;
    }

    @Override
    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }

    @Override
    public void removeSubGoal(Goal goal) {
        subGoals.remove(goal);
    }

    @Override
    public List<Goal> getGoals() {
        return subGoals;
    }
}
