package seedu.duck.task;

import java.time.DayOfWeek;

public class RecurringEvent extends Event {
    private DayOfWeek day;

    public RecurringEvent(String description, String start, String end, DayOfWeek day) {
        super(description, start, end);
        this.day = day;
    }

    public DayOfWeek getDay() {
        return this.day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    @Override
    public String toSaveString() {
        return getDoneConditionString() + " " + getDescription() + " /from " + getStart() + " /to " + getEnd() +
                " <p>" + getPriorityIndex() + " /day " + day + System.lineSeparator();
    }

    @Override
    public String toString() {
        return "\t [E]" + "[" + getStatusIcon() + "] " + getDescription() + " (from: " + super.getStart()
                + " to: " + super.getEnd() + ")" + " (every " + day + ") (" + getPriority() + ")";
    }
}
