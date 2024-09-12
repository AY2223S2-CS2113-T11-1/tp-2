package seedu.duck.task;

public class Event extends Task {
    private String start; // Start date/time
    private String end;   // End date/time

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + " /from " + getStart() + " /to " + getEnd() +
                " <p>" + getPriorityIndex() + " <n>" + getSavedNotes()  + System.lineSeparator();
    }

    @Override
    public String toString() {
        return "\t [E]" + super.toString() + " (from: " + start
                + " to: " + end + ")" + " (" + getPriority() + ")";
    }
}
