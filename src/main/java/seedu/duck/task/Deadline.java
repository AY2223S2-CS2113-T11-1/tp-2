package seedu.duck.task;

public class Deadline extends Task {
    private String by;

    public Deadline(String description, String deadline) {
        super(description);
        by = deadline;
    }

    public void setDeadline(String deadline) {
        by = deadline;
    }

    public String getDeadline() {
        return by;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + " /by " + getDeadline() + " <p>" + getPriorityIndex() + " <n>" + getSavedNotes() + System.lineSeparator();
    }

    @Override
    public String toString() {
        return "\t [D]" + super.toString() + " (by: " + by + ")" + " (" + getPriority() + ")";
    }
}
