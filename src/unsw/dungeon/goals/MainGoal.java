package unsw.dungeon.goals;

import unsw.dungeon.Dungeon;

import java.util.*;

public class MainGoal implements Goal {
    protected Dungeon dungeon;
    private List<Goal> goals;

    public MainGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.goals = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int indentation = 0;
        Stack<Goal> stack = new Stack<>();
        Stack<Integer> sizes = new Stack<>();

        for (Goal goal : goals)
            stack.push(goal);

        while (!stack.isEmpty()) {
            Goal curr = stack.pop();
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

        return sb.toString();
    }

    @Override
    public boolean checkSelf() {
        return checkSubGoals();
    }

    @Override
    public boolean checkSubGoals() {
        for (Goal goal : goals) {
            if (!goal.checkSelf() || !goal.checkSubGoals())
                return false;
        }
        return true;
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
}
