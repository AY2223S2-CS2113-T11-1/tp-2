package seedu.duck;


import seedu.duck.task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Deals with loading tasks from the file and saving tasks in the file
 */
public class Storage {
    static final String SAVEPATH = "data/savedata.txt";
    static final String SAVEFOLDER = "data";

    /**
     * Load tasks and the notes under the task from save data into the list
     *
     * @param line  The line of input from the save file
     * @param tasks The array list of tasks
     */
    static void loadTask(String line, ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes, String doneStatus) {
        if (line.contains("/by")) {
            if (line.contains("/day")) {
                loadRecurringDeadline(line, tasks);
            } else {
                loadDeadline(line, tasks);
            }
            loadTaskStatus(tasks, doneStatus);
            Task.incrementCount();
        } else if (line.contains("/class")) {
            loadSchoolClass(line, classes);
        } else if (line.contains("/from") || line.contains("/to")) {
            if (line.contains("/day")) {
                loadRecurringEvent(line, tasks);
            } else {
                loadEvent(line, tasks);
            }
            loadTaskStatus(tasks, doneStatus);
            Task.incrementCount();
        } else {
            loadTodo(line, tasks);
            loadTaskStatus(tasks, doneStatus);
            Task.incrementCount();
        }
    }

    /**
     * Deletes the existing savedata file then recreates a new blank slate.
     */
    static void clearTask() throws IOException {
        {
            try {
                FileWriter fw = new FileWriter(SAVEPATH, false);
                PrintWriter pw = new PrintWriter(fw, false);
                pw.flush();
                pw.close();
                fw.close();
            } catch (Exception exception) {
                System.out.println("Exception have been caught");
            }
        }
    }

    /**
     * Adds a _Todo_ into the list without generating messages,
     * to be used when loading from save data.
     *
     * @param line  The line of input from the save file
     * @param tasks The array list of tasks
     */
    static void loadTodo(String line, ArrayList<Task> tasks) {
        String description = line.substring(0, line.indexOf("<p>")).trim();
        Todo currTodo = new Todo(description);
        String priority = line.substring(line.indexOf("<p>") + 3,line.indexOf("<n>")).trim();
        currTodo.setPriority(priority);
        tasks.add(currTodo);
        String savedNotes = line.substring(line.indexOf("<n>") + 3);
        if (savedNotes.charAt(0) == '1'){
            savedNotes = savedNotes.substring(1);
            String[] notesToBeAdded;
            notesToBeAdded = savedNotes.split("@",0);
                for (String item : notesToBeAdded) {
                    tasks.get(tasks.size() - 1).addNotes(item);
                }
        }
    }


    /**
     * Adds an event into the list without generating messages,
     * to be used when loading from save data.
     *
     * @param line  The line of input from the save file
     * @param tasks The array list of tasks
     */
    static void loadEvent(String line, ArrayList<Task> tasks) {
        String description = line.substring(0, line.indexOf("/from")).trim();
        String start = line.substring(line.indexOf("/from") + 5, line.indexOf("/to")).trim();
        String end = line.substring(line.indexOf("/to") + 3, line.indexOf("<p>")).trim();
        String priority = line.substring(line.indexOf("<p>") + 3, line.indexOf("<p>") + 4).trim();
        Event currEvent = new Event(description, start, end);
        currEvent.setPriority(priority);
        tasks.add(currEvent);
        String savedNotes = line.substring(line.indexOf("<n>") + 3);
        if (savedNotes.charAt(0) == '1'){
            savedNotes = savedNotes.substring(1);
            String[] notesToBeAdded;
            notesToBeAdded = savedNotes.split("@",0);
            for (String item : notesToBeAdded) {
                tasks.get(tasks.size() - 1).addNotes(item);
            }
        }
    }

    static void loadRecurringEvent(String line, ArrayList<Task> tasks) {
        String description = line.substring(0, line.indexOf("/from")).trim();
        String start = line.substring(line.indexOf("/from") + 5, line.indexOf("/to")).trim();
        String end = line.substring(line.indexOf("/to") + 3, line.indexOf("<p>")).trim();
        String priority = line.substring(line.indexOf("<p>") + 3, line.indexOf("/day")).trim();
        DayOfWeek day = DayOfWeek.valueOf(line.substring(line.indexOf("/day") + 4).trim());
        RecurringEvent currEvent = new RecurringEvent(description, start, end, day);
        currEvent.setPriority(priority);
        tasks.add(currEvent);
    }

    /**
     * Adds a schoolClass to the list without generating messages,
     * to be used when loading from save data.
     *
     * @param line    The line of input from the user
     * @param classes The priority queue of school classes
     */
    static void loadSchoolClass(String line, PriorityQueue<SchoolClass> classes) {
        String description = line.substring(0, line.indexOf("/class")).trim();
        String className = line.substring(line.indexOf("/class") + 6, line.indexOf("/day")).trim();
        DayOfWeek day = DayOfWeek.valueOf(line.substring(line.indexOf("/day") + 4, line.indexOf("/from")).trim());
        String startString = line.substring(line.indexOf("/from") + 5, line.indexOf("/to")).trim();
        String endString = line.substring(line.indexOf("/to") + 3).trim();
        SchoolClass currSchoolClass = new SchoolClass(className, description, day, startString, endString);

        TaskList.checkClassOver(day, endString, currSchoolClass);
        classes.add(currSchoolClass);
    }

    /**
     * Adds a deadline into the list without generating messages,
     * to be used when loading from save data.
     *
     * @param line  The line of input from the save file
     * @param tasks The array list of tasks
     */
    static void loadDeadline(String line, ArrayList<Task> tasks) {
        String description = line.substring(0, line.indexOf("/by")).trim();
        String deadline = line.substring(line.indexOf("/by") + 3, line.indexOf("<p>")).trim();
        String priority = line.substring(line.indexOf("<p>") + 3, line.indexOf("<p>") + 4).trim();
        Deadline currDeadline = new Deadline(description, deadline);
        currDeadline.setPriority(priority);
        tasks.add(currDeadline);
        String savedNotes = line.substring(line.indexOf("<n>") + 3);
        if (savedNotes.charAt(0) == '1'){
            savedNotes = savedNotes.substring(1);
            String[] notesToBeAdded;
            notesToBeAdded = savedNotes.split("@",0);
            for (String item : notesToBeAdded) {
                tasks.get(tasks.size() - 1).addNotes(item);
            }
        }
    }

    /**
     * Adds a RecurringDeadline to the list when loading from save data
     *
     * @param line  The line of input from save file
     * @param tasks the array list of tasks
     */
    static void loadRecurringDeadline(String line, ArrayList<Task> tasks) {
        String description = line.substring(0, line.indexOf("/by")).trim();
        String deadline = line.substring(line.indexOf("/by") + 3, line.indexOf("<p>")).trim();
        String priority = line.substring(line.indexOf("<p>") + 3, line.indexOf("/day")).trim();
        DayOfWeek day = DayOfWeek.valueOf(line.substring(line.indexOf("/day") + 4).trim());
        RecurringDeadline currDeadline = new RecurringDeadline(description, deadline, day);
        currDeadline.setPriority(priority);
        tasks.add(currDeadline);
    }

    /**
     * Load the task status of a task from the save data
     *
     * @param tasks      The array list of tasks
     * @param doneStatus The done status of the current task
     */
    static void loadTaskStatus(ArrayList<Task> tasks, String doneStatus) {
        int taskNumber = Task.getTaskCount();
        if (doneStatus.equals("1")) {
            tasks.get(taskNumber).markAsDone();
        } else {
            tasks.get(taskNumber).markAsNotDone();
        }
    }

    /**
     * Saves the tasks in the list to the save file
     *
     * @param tasks The array list of tasks
     */
    static void save(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) throws IOException {
        File f = new File(SAVEPATH);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();

        // Saving the task list to the save file
        FileWriter fw = new FileWriter(SAVEPATH);
        for (Task currTask : tasks) {
            fw.write(currTask.toSaveString());
        }
        fw.close();

        // Saving the class schedule to the save file
        FileWriter fw2 = new FileWriter(SAVEPATH, true);
        PriorityQueue<SchoolClass> temp = new PriorityQueue<SchoolClass>(classes);
        while (!temp.isEmpty()) {
            fw2.write(temp.poll().toSaveString());
        }
        fw2.close();
    }

    /**
     * Try to save, shows error message if saving fails
     *
     * @param tasks The array list of tasks
     */
    static void trySave(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) {
        try {
            save(tasks, classes);
        } catch (IOException e) {
            System.out.println("Saving error.");
        }
    }

    /**
     * Load the save data
     *
     * @param tasks The array list of tasks
     */
    static void load(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) throws IOException {
        File folder = new File(SAVEFOLDER);
        if (!folder.exists()) {
            new File(SAVEFOLDER).mkdir();
        }

        File f = new File(SAVEPATH);
        if (!f.exists()) {
            f.createNewFile();
        }

        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String line = s.nextLine();
            String[] formattedInput = line.split(" ");
            String doneStatus = formattedInput[0];
            String command = "";
            for (int i = 1; i < formattedInput.length; i++) {
                command += formattedInput[i];
                command += " ";
            }
            loadTask(command, tasks, classes, doneStatus);
        }
    }

    /**
     * Try to load the save data, shows error message if loading fails
     *
     * @param tasks The array list of tasks
     */
    static void tryLoad(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) {
        try {
            load(tasks, classes);
        } catch (IOException e) {
            System.out.println("Error loading save file.");
        }
    }
}
