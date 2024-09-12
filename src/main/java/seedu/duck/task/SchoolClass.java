package seedu.duck.task;

import java.time.DayOfWeek;

public class SchoolClass extends Task implements Comparable<SchoolClass> {
    private String className; // Name of class
    private DayOfWeek day; // Day of the week
    private String start; // Start date/time
    private String end;   // End date/time

    public SchoolClass(String className, String description, DayOfWeek day, String start, String end) {
        super(description);
        this.className = className;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public DayOfWeek getDay() {
        return this.day;
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
    public int compareTo(SchoolClass lesson) {
        if (lesson.day.compareTo(this.day) == 0) {
            return this.start.compareTo(lesson.start);
        } else {
            return this.day.compareTo(lesson.day);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        SchoolClass lesson = (SchoolClass) obj;
        return className.equals(lesson.getClassName()) && this.getDescription().equals(lesson.getDescription())
                && day.equals(lesson.getDay()) && start.equals(lesson.getStart()) && end.equals(lesson.getEnd());
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + " /class " + getClassName() + " /day " + getDay() + " /from "
                + getStart() + " /to "+ getEnd() + System.lineSeparator();
    }

    @Override
    public String toString() {
        if (getDescription().isBlank()) {
            return "\t [" + day + "]" + "[" + getStatusIcon() + "] " + className + " (from: "
                    + start + " to: " + end + ")";
        }
        return "\t [" + day + "]" + "[" + getStatusIcon() + "] " + className + ": "
                + getDescription() + " (from: " + start + " to: " + end + ")";
    }
}
