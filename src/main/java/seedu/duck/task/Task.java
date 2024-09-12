package seedu.duck.task;
import java.util.ArrayList;

public class Task {
    private static int taskCount;
    private String description;
    private int priority;
    private boolean isDone;
    private final ArrayList<String> additionalNotes = new ArrayList<String>();

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.priority = 1; //automatically set to low priority
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setPriority(String priority) {
        this.priority = Integer.parseInt(priority);
    }

    public String getDescription() {
        return this.description;
    }

    public static void clearCount() {
        taskCount = 0;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        if(isDone) {
            // Mark done task with X
            return "X";
        }
        return " ";
    }
    public String hasNotes(){
        if (additionalNotes.isEmpty()){
            return "0";
        } else {
            return "1";
        }
    }
    public String getSavedNotes(){
        String save = "";
        save += hasNotes();
        for (int i = 0; i < additionalNotes.size(); i++){
            save += additionalNotes.get(i);
            if (!(i==additionalNotes.size()-1)) {
                save += "@";
            }
        }
        return save;
    }
    public void addNotes(String noteToAdd){
            this.additionalNotes.add(noteToAdd);
    }

    public ArrayList<String> getAdditionalNotes(){
        return this.additionalNotes;
    }
    public String getNote(int index){
        return this.additionalNotes.get(index);
    }

    public void deleteNote(int indexToBeDeleted){
        this.additionalNotes.remove(indexToBeDeleted-1);
    }

    public void editNote(int indexToBeEdited, String editedNote){
        this.additionalNotes.set(indexToBeEdited,editedNote);
    }

    public int numberOfNotes(){
        return this.additionalNotes.size();
    }

    public String getPriority() {
        if (priority == 1) {
            // Mark done task with X
            return "Low priority.";
        } else if(priority == 2) {
            // Mark done task with X
            return "Medium priority.";
        } else if(priority == 3) {
            // Mark done task with X
            return "High priority.";
        }
        return "No priority established.";
    }

    public int returnPriority(){
        return this.priority;
    }

    public int getPriorityIndex() {
        return this.priority;
    }

    public static void incrementCount() {
        taskCount++;
    }

    public static void decrementCount() {
        taskCount--;
    }

    public static int getTaskCount() {
        return taskCount;
    }

    public String getDoneConditionString() {
        if (isDone) {
            return "1";
        }
        return "0";
    }

    public String toSaveString() {
        return getDoneConditionString() + " " + getDescription();
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }
}
