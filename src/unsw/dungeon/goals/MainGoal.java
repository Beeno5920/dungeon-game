package unsw.dungeon.goals;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import unsw.dungeon.Dungeon;

import java.util.*;

public class MainGoal implements Goal {
    protected Dungeon dungeon;
    private List<Goal> goals;

    public MainGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.goals = new ArrayList<>();
    }

    public Pair<String, List<CheckBox>> constructDisplayComponent() {
        StringBuilder sb = new StringBuilder();
        int indentation = 0;
        Stack<Goal> stack = new Stack<>();
        Stack<Integer> sizes = new Stack<>();
        List<CheckBox> checkBoxes = new ArrayList<>();

        for (Goal goal : goals)
            stack.push(goal);


        while (!stack.isEmpty()) {
            Goal curr = stack.pop();

            CheckBox checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().bindBidirectional(curr.getAchievedProperty());
            checkBoxes.add(checkBox);

            sb.append("  ".repeat(Math.max(0, indentation)));
            if (!sizes.isEmpty()) {
                sizes.push(sizes.pop() - 1);
                if (sizes.peek() == 0) {
                    indentation--;
                    sizes.pop();
                }
            }
            if (curr instanceof AndGoal) {
                sizes.push(curr.getGoals().size());
                sb.append("AND:\n");
                indentation++;
            } else if (curr instanceof OrGoal) {
                sizes.push(curr.getGoals().size());
                sb.append("OR:\n");
                indentation++;
            } else {
                sb.append(curr.toString());
            }

            for (Goal goal : curr.getGoals())
                stack.push(goal);
        }

        return new Pair<>(sb.toString(), checkBoxes);
    }

    @Override
    public boolean checkSelf() {
        return checkSubGoals();
    }

    @Override
    public boolean checkSubGoals() {
        boolean result = true;
        for (Goal goal : goals) {
            boolean self = goal.checkSelf(), subGoals = goal.checkSubGoals();
            if (!self || !subGoals)
                result = false;
        }
        return result;
    }

    @Override
    public void addSubGoal(Goal goal) {
        goals.add(goal);
    }

    @Override
    public void removeSubGoal(Goal goal) {
        goals.remove(goal);
    }

    @Override
    public List<Goal> getGoals() {
        return goals;
    }

    @Override
    public BooleanProperty getAchievedProperty() {
        return null;
    }
}
