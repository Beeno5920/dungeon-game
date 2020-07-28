package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.characters.Enemy;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;

import java.util.ArrayList;
import java.util.List;

public class KillAllEnemiesGoal implements Goal {
    private Dungeon dungeon;
    private List<Goal> subGoals;
    private BooleanProperty achieved;

    public KillAllEnemiesGoal(Dungeon dungeon) {
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
                    if (entity instanceof Enemy) {
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
        return "Kill all enemies\n";
    }
}
