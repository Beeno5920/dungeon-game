package unsw.dungeon.Goal;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Item.Treasure;

import java.util.ArrayList;
import java.util.List;

public class TreasureGoal implements Goal {
    private Dungeon dungeon;
    private List<Goal> subGoals;

    public TreasureGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.subGoals = new ArrayList<>();
    }

    @Override
    public boolean checkSelf() {
        for (int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                for (Entity entity : dungeon.getEntities(j, i)) {
                    if (entity instanceof Treasure)
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkSubGoals() {
        for (Goal goal : subGoals) {
            if (!goal.checkSubGoals() || !goal.checkSubGoals())
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
