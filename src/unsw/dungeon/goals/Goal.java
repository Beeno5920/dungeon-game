package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;

import java.util.List;

public interface Goal {
    public boolean checkSelf();

    public boolean checkSubGoals();

    public void addSubGoal(Goal goal);

    public void removeSubGoal(Goal goal);

    public List<Goal> getGoals();

    public BooleanProperty getAchievedProperty();
}
