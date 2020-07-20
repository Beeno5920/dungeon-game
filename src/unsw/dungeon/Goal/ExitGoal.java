package unsw.dungeon.Goal;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.FieldObject.Exit;

import java.util.ArrayList;
import java.util.List;

public class ExitGoal implements Goal {
    private Dungeon dungeon;
    private List<Goal> subGoals;

    public ExitGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.subGoals = new ArrayList<>();
    }

    @Override
    public boolean checkSelf() {
        int[] position = dungeon.getPlayer().getPosition();
        for (Entity entity : dungeon.getEntities(position[0], position[1])) {
            if (entity instanceof Exit)
                return dungeon.getPlayer().isSamePosition(entity.getX(), entity.getY());
        }
        return false;
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
