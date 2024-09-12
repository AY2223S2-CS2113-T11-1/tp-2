package seedu.duck.task;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + " <p>" + getPriorityIndex() + " <n>" + getSavedNotes() + System.lineSeparator();
    }

    @Override
    public String toString() {
        return "\t [T]" + super.toString() + " (" + getPriority() + ")";
    }
}
