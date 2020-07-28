package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.fieldobjects.FloorSwitch;

import java.util.ArrayList;
import java.util.List;

public class BouldersGoal implements Goal {
    private Dungeon dungeon;
    private List<Goal> subGoals;
    private BooleanProperty achieved;

    public BouldersGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.subGoals = new ArrayList<>();
        this.achieved = new SimpleBooleanProperty(false);
    }

    public BooleanProperty getAchievedProperty() {
        return achieved;
    }

    @Override
    public boolean checkSelf() {
        for (int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                for (Entity entity : dungeon.getEntities(j, i)) {
                    if (entity instanceof FloorSwitch && !((FloorSwitch) entity).isTriggered()) {
                        achieved.set(false);
                        return false;
                    }
                }
            }
        }
        achieved.set(true);
        return true;
    }

    @Override
    public boolean checkSubGoals() {
        boolean result = true;
        for (Goal goal : subGoals) {
            boolean self = goal.checkSelf(), subGoals = goal.checkSubGoals();   // in order to update all the boolean properties of subgoals
            if (!self || !subGoals)
                result = false;
        }
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

    public String toString() {
        return "Place boulders on all the floor switches\n";
    }
}
