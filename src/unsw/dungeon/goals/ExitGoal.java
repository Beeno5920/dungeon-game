package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.fieldobjects.Exit;

import java.util.ArrayList;
import java.util.List;

public class ExitGoal implements Goal {
    private Dungeon dungeon;
    private List<Goal> subGoals;
    private BooleanProperty achieved;

    public ExitGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.subGoals = new ArrayList<>();
        this.achieved = new SimpleBooleanProperty(false);
    }

    public BooleanProperty getAchievedProperty() {
        return achieved;
    }

    @Override
    public boolean checkSelf() {
        int[] position = dungeon.getPlayer().getPosition();
        for (Entity entity : dungeon.getEntities(position[0], position[1])) {
            if (entity instanceof Exit) {
                achieved.set(dungeon.getPlayer().isSamePosition(entity.getX(), entity.getY()));
                return achieved.get();
            }
        }
        return false;
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
        return "Enter the exit\n";
    }
}
