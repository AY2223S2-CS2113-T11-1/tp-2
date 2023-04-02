package seedu.duck;

import seedu.duck.exception.*;
import seedu.duck.task.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Contains operations to manipulate the task list
 */
public class TaskList {
    static void addTask(String line, ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) {
        if (line.contains("/by")) {
            // Adding a Deadline
            if (line.contains("/re")) {
                try {
                    addRecurringDeadline(line, tasks);
                    Task.incrementCount();
                } catch(IllegalDeadlineException | StringIndexOutOfBoundsException e) {
                    Ui.deadlineErrorMessage();
                } catch (IllegalArgumentException e) {
                    Ui.invalidDayMessage();
                } catch (DateTimeParseException e) {
                    Ui.invalidDateTimeMessage();
                }
            }else {
                try {
                    addDeadline(line, tasks);
                    Task.incrementCount();
                } catch (IllegalDeadlineException e) {
                    Ui.deadlineErrorMessage();
                } catch (expiredDateException e) {
                    Ui.expiredErrorMessage();
                } catch (DateTimeException e) {
                    Ui.invalidDateTimeMessage();
                }
            }
        } else if (line.contains("/class")) {
            // Adding a SchoolClass
            try {
                addSchoolClass(line, classes);
            } catch (IllegalSchoolClassException | IndexOutOfBoundsException e) {
                Ui.eventErrorMessage();
            } catch (expiredDateException e) {
                Ui.expiredErrorMessage();
            } catch (startAfterEndException e) {
                Ui.startAfterEndErrorMessage();
            } catch (DateTimeException e) {
                Ui.invalidDateTimeMessage();
            }
        } else if (line.contains("/from") || line.contains("/to")) {
            // Adding an Event
            if (line.contains("/re")) {
                try {
                    addRecurringEvent(line, tasks);
                    Task.incrementCount();
                } catch (IllegalEventException | StringIndexOutOfBoundsException e) {
                    Ui.eventErrorMessage();
                } catch (IllegalArgumentException e) {
                    Ui.invalidDayMessage();
                } catch (DateTimeParseException e) {
                    Ui.invalidDateTimeMessage();
                }
            } else{
                try {
                    addEvent(line, tasks);
                    Task.incrementCount();
                } catch (IllegalEventException | IndexOutOfBoundsException e) {
                    Ui.eventErrorMessage();
                } catch (expiredDateException e) {
                    Ui.expiredErrorMessage();
                } catch (startAfterEndException e) {
                    Ui.startAfterEndErrorMessage();
                } catch (DateTimeException e) {
                    Ui.invalidDateTimeMessage();
                }
            }
        } else {
            // Adding a _Todo_
            try {
                addTodo(line, tasks);
                Task.incrementCount();
            } catch (IllegalTodoException e) {
                Ui.todoErrorMessage();
            }
        }
    }

    /**
     * Adds a _Todo_ to the list
     *
     * @param line  The line of input from the user
     * @param tasks The array list of tasks
     */
    static void addTodo(String line, ArrayList<Task> tasks) throws IllegalTodoException {
        if (line.isBlank()) {
            throw new IllegalTodoException();
        } else {
            Todo currTodo = new Todo(line);
            tasks.add(currTodo);
            Ui.addedTaskMessage(currTodo);
        }
    }
    /**
     * Sets a priority to the specified task
     *
     * @param words  The input variable from the user, consisting of an index and a priority (from 1 to 3)
     * @param tasks The array list of tasks
     */
    static void setPriority(ArrayList<Task> tasks, String[] words) {
        int taskNumber = Integer.parseInt(words[1]);
        int taskCount = Task.getTaskCount();
        if (taskNumber > taskCount || taskNumber < 0) {
            // Input task number exceeds the number of tasks in the list
            Ui.exceedTaskNumberMessage(taskNumber);
        } else {
            tasks.get(taskNumber - 1).setPriority(words[2]);
            // Printing out marked as done message
            Ui.borderLine();
            System.out.println("\t Understood. The task's new priority is:");
            System.out.println("\t " + tasks.get(taskNumber - 1).getPriority());
            Ui.borderLine();
        }
    }

    /**
     * Adds an event to the list
     *
     * @param line  The line of input from the user
     * @param tasks The array list of tasks
     */
    static void addEvent(String line, ArrayList<Task> tasks) throws IllegalEventException, startAfterEndException,
            expiredDateException {
        String description = line.substring(0, line.indexOf("/from")).trim();
        String startString = line.substring(line.indexOf("/from") + 5, line.indexOf("/to")).trim();
        String endString = line.substring(line.indexOf("/to") + 3).trim();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime start = LocalDateTime.parse(startString, dateFormat);
        LocalDateTime end = LocalDateTime.parse(endString, dateFormat);
        if (start.isAfter(end)) {
            throw new startAfterEndException();
        } else if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new expiredDateException();
        } else if (description.isBlank() || startString.isBlank() || endString.isBlank()) {
            throw new IllegalEventException();
        } else {
            Event currEvent = new Event(description, startString, endString);
            tasks.add(currEvent);
            Ui.addedTaskMessage(currEvent);
        }
    }

    static void addRecurringEvent(String line, ArrayList<Task> tasks) throws IllegalEventException{
        String description = line.substring(4, line.indexOf("/from")).trim();
        String start = line.substring(line.indexOf("/from") + 5, line.indexOf("/to")).trim();
        String end = line.substring(line.indexOf("/to") + 3, line.indexOf("/day")).trim();
        DayOfWeek day = DayOfWeek.valueOf(line.substring(line.indexOf("/day") + 4).trim());
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
        //check whether start and end are in the correct format
        LocalTime.parse(start, timeFormat);
        LocalTime.parse(end, timeFormat);
        if (description.isBlank() || start.isBlank() || end.isBlank()) {
            throw new IllegalEventException();
        } else {
            RecurringEvent currEvent = new RecurringEvent(description, start, end, day);
            tasks.add(currEvent);
            Ui.addedTaskMessage(currEvent);
        }
    }

    /**
     * Adds a schoolClass to the list
     *
     * @param line  The line of input from the user
     * @param classes The priority queue of school classes
     */
    static void addSchoolClass(String line, PriorityQueue<SchoolClass> classes) throws IllegalSchoolClassException,
            startAfterEndException, expiredDateException, IllegalArgumentException, NullPointerException {
        String description = line.substring(0, line.indexOf("/class")).trim();
        String className = line.substring(line.indexOf("/class") + 6, line.indexOf("/day")).trim();
        try {
            DayOfWeek day = DayOfWeek.valueOf(line.substring(line.indexOf("/day") + 4, line.indexOf("/from")).trim());
            String startString = line.substring(line.indexOf("/from") + 5, line.indexOf("/to")).trim();
            String endString = line.substring(line.indexOf("/to") + 3).trim();
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
            LocalTime start = LocalTime.parse(startString, timeFormat);
            LocalTime end = LocalTime.parse(endString, timeFormat);
            if (start.isAfter(end)) {
                throw new startAfterEndException();
            } else if (className.isBlank() || startString.isBlank() || endString.isBlank()) {
                throw new IllegalSchoolClassException();
            } else {
                SchoolClass currSchoolClass = new SchoolClass(className, description, day, startString, endString);
                classes.add(currSchoolClass);
                Ui.addedSchoolClassMessage(currSchoolClass, classes);
            }
        } catch (IllegalArgumentException e) {
            Ui.invalidDayMessage();
        } catch (NullPointerException e) {
            Ui.emptyDayErrorMessage();
        }
    }

    /**
     * Adds a deadline to the list
     *
     * @param line  The line of input from the user
     * @param tasks The array list of tasks
     */


    static void addDeadline(String line, ArrayList<Task> tasks) throws IllegalDeadlineException, expiredDateException {
        String description = line.substring(0, line.indexOf("/by")).trim();
        String deadlineString = line.substring(line.indexOf("/by") + 3).trim();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime deadline = LocalDateTime.parse(deadlineString, dateFormat);
        //System.out.println(description.isBlank());
        if (description.isBlank() || deadlineString.isBlank()) {
            throw new IllegalDeadlineException();
        } else if (deadline.isBefore(LocalDateTime.now())) {
            throw new expiredDateException();
        } else {
            Deadline currDeadline = new Deadline(description, deadlineString);
            tasks.add(currDeadline);
            Ui.addedTaskMessage(currDeadline);
        }
    }


    /**
     * adds a recurringDeadline to the list
     *
     * @param line the line of input from the user
     * @param tasks the array list of tasks
     */
    static void addRecurringDeadline(String line, ArrayList<Task> tasks) throws IllegalDeadlineException,
            IllegalArgumentException {
        String description = line.substring(4, line.indexOf("/by")).trim();
        String deadline = line.substring(line.indexOf("/by") + 3, line.indexOf("/day")).trim();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
        LocalTime.parse(deadline, timeFormat);
        DayOfWeek day = DayOfWeek.valueOf(line.substring(line.indexOf("/day") + 4).trim());
        if (description.isBlank() || deadline.isBlank()) {
            throw new IllegalDeadlineException();
        } else {
            RecurringDeadline currDeadline = new RecurringDeadline(description, deadline, day);
            tasks.add(currDeadline);
            Ui.addedTaskMessage(currDeadline);
        }
    }

    /**
     * Marks a task as done
     *
     * @param tasks The array list of tasks
     * @param words The array of words generated from the user input
     */
    static void markTask(ArrayList<Task> tasks, String[] words) {
        int taskNumber = Integer.parseInt(words[1]);
        int taskCount = Task.getTaskCount();
        if (taskNumber > taskCount || taskNumber < 0) {
            // Input task number exceeds the number of tasks in the list
            Ui.exceedTaskNumberMessage(taskNumber);
        } else {
            tasks.get(taskNumber - 1).markAsDone();
            // Printing out marked as done message
            Ui.borderLine();
            System.out.println("\t Understood. I've marked this task as done:");
            System.out.println("\t " + tasks.get(taskNumber - 1));
            Ui.borderLine();
        }
    }

    /**
     * Marks a task as not done
     *
     * @param tasks The array list of tasks
     * @param words The array of words generated from the user input
     */
    static void unmarkTask(ArrayList<Task> tasks, String[] words) {
        int taskNumber = Integer.parseInt(words[1]);
        int taskCount = Task.getTaskCount();
        if (taskNumber > taskCount || taskNumber < 0) {
            // Input task number exceeds the number of tasks in the list
            Ui.exceedTaskNumberMessage(taskNumber);
        } else {
            tasks.get(taskNumber - 1).markAsNotDone();
            // Printing out marked as not done message
            Ui.borderLine();
            System.out.println("\t Understood. I've marked this task as not done yet:");
            System.out.println("\t " + tasks.get(taskNumber - 1));
            Ui.borderLine();
        }
    }

    /**
     * edits the attributes of a specific task
     * @param tasks The array list of tasks
     * @param words The array of words generated from the user input
     */
    static void editTask(ArrayList<Task> tasks, String[] words) throws expiredDateException,
            startAfterEndException, EmptyDescriptionException {
        int taskNumber = Integer.parseInt(words[1]);
        int taskCount = Task.getTaskCount();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        if (taskNumber > taskCount) {
            Ui.exceedTaskNumberMessage(taskNumber);
            return;
        }
        Task taskToEdit = tasks.get(taskNumber - 1);
        if (taskToEdit instanceof Todo) {
            Ui.editTodoMessage();
            String editLine = Ui.askForEditMessage().trim();
            if (editLine.isBlank()) {
                throw new EmptyDescriptionException();
            }
            taskToEdit.setDescription(editLine);
        } else if (taskToEdit instanceof Deadline) {
            Ui.editDeadlineMessage();
            String editLine = Ui.askForEditMessage().trim();
            String[] editWords = editLine.split(" ");
            if (taskToEdit instanceof RecurringDeadline) {
                if (editWords.length > 1 && editWords[0].equals("/description")) {
                    String newDescription = editLine.substring(12).trim();
                    if (newDescription.isBlank()) {
                        throw new EmptyDescriptionException();
                    }
                    taskToEdit.setDescription(newDescription);
                } else if (editWords.length > 1 && editWords[0].equals("/deadline")){
                    String deadline = editLine.substring(9).trim();
                    LocalTime.parse(deadline, timeFormat);
                    ((RecurringDeadline) taskToEdit).setDeadline(deadline);
                } else if (editWords.length > 1 && editWords[0].equals("/day")) {
                    DayOfWeek day = DayOfWeek.valueOf(editLine.substring(4).trim());
                    ((RecurringDeadline) taskToEdit).setDay(day);
                } else {
                    Ui.unknownCommandMessage();
                }
            } else {
                if (editWords.length > 1 && editWords[0].equals("/description")) {
                    String newDescription = editLine.substring(editLine.indexOf(words[1]));
                    if (newDescription.isBlank()) {
                        throw new EmptyDescriptionException();
                    }
                    taskToEdit.setDescription(newDescription);
                } else if (editWords.length > 1 && editWords[0].equals("/deadline")) {
                    String deadlineString = editLine.substring(9).trim();
                    System.out.println(deadlineString);
                    LocalDateTime deadline = LocalDateTime.parse(deadlineString, dateFormat);
                    if (deadline.isBefore(LocalDateTime.now())){
                        throw new expiredDateException();
                    } else {
                        ((Deadline) taskToEdit).setDeadline(deadlineString);
                    }
                } else {
                    Ui.unknownCommandMessage();
                }
            }
        } else if (taskToEdit instanceof Event) {
            Ui.editEventMessage();
            String editLine = Ui.askForEditMessage().trim();
            String[] editWords = editLine.split(" ");
            if (taskToEdit instanceof RecurringEvent) {
                if (editWords.length > 1 && editWords[0].equals("/description")) {
                    String newDescription = editLine.substring(12).trim();
                    if (newDescription.isBlank()) {
                        throw new EmptyDescriptionException();
                    }
                    taskToEdit.setDescription(newDescription);
                } else if (editWords.length > 1 && editWords[0].equals("/from")){
                    String start = editLine.substring(5).trim();
                    LocalTime.parse(start, timeFormat);
                    ((RecurringEvent) taskToEdit).setStart(start);
                } else if (editWords.length > 1 && editWords[0].equals("/to")) {
                    String end = editLine.substring(3).trim();
                    LocalTime.parse(end, timeFormat);
                    ((RecurringEvent) taskToEdit).setEnd(end);
                } else if(editWords.length > 1 && editWords[0].equals("/day")) {
                    DayOfWeek day = DayOfWeek.valueOf(editLine.substring(4).trim());
                    ((RecurringEvent) taskToEdit).setDay(day);
                } else {
                    Ui.unknownCommandMessage();
                }
            } else {
                LocalDateTime start = LocalDateTime.parse(((Event) taskToEdit).getStart(), dateFormat);
                LocalDateTime end = LocalDateTime.parse(((Event) taskToEdit).getEnd(), dateFormat);
                String newStartString = null;
                String newEndString = null;
                LocalDateTime newStart = null;
                LocalDateTime newEnd = null;
                if (editWords.length > 1 && editWords[0].equals("/description")) {
                    String newDescription = editLine.substring(12).trim();
                    if (newDescription.isBlank()) {
                        throw new EmptyDescriptionException();
                    }
                    taskToEdit.setDescription(newDescription);
                } else if (editWords.length > 1 && editWords[0].equals("/from")){
                    newStartString = editLine.substring(5).trim();
                    newStart = LocalDateTime.parse(newStartString, dateFormat);
                    //((Event) taskToEdit).setStart(newStart);
                } else if (editWords.length > 1 && editWords[0].equals("/to")) {
                    newEndString = editLine.substring(3).trim();
                    newEnd= LocalDateTime.parse(newEndString, dateFormat);
                    //((Event) taskToEdit).setEnd(newEnd);
                } else {
                    Ui.unknownCommandMessage();
                }
                if (newStart != null) {
                    if (newStart.isAfter(end)) {
                        throw new startAfterEndException();
                    } else if (newStart.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
                        throw new expiredDateException();
                    } else {
                        ((Event) taskToEdit).setStart(newStartString);
                    }
                } else if (newEnd != null) {
                    if (start.isAfter(newEnd)) {
                        throw new startAfterEndException();
                    } else if (start.isBefore(LocalDateTime.now()) || newEnd.isBefore(LocalDateTime.now())) {
                        throw new expiredDateException();
                    } else {
                        ((Event) taskToEdit).setEnd(newEndString);
                    }
                }
            }
        }
        Ui.printEditedTask(taskToEdit);
    }

    /**
     * tries editTask and handles exceptions
     * @param tasks The array list of tasks
     * @param words The array of words generated from the user input
     */
    static void tryEditTask(ArrayList<Task> tasks, String[] words) {
        try {
            editTask(tasks, words);
        } catch (DateTimeParseException e) {
            Ui.invalidDateTimeMessage();
        } catch (IllegalArgumentException e) {
            Ui.invalidDayMessage();
        } catch (expiredDateException e) {
            Ui.expiredErrorMessage();
        } catch (startAfterEndException e) {
            Ui.startAfterEndErrorMessage();
        } catch (EmptyDescriptionException e) {
            Ui.emptyDescriptionErrorMessage();
        }
    }

    /**
     * Deletes a task from the list
     *
     * @param tasks The array list of tasks
     * @param words The array of words generated from the user input
     */
    static void deleteTask(ArrayList<Task> tasks, String[] words) {
        int taskNumber = Integer.parseInt(words[1]);
        int taskCount = Task.getTaskCount();
        if (taskNumber > taskCount || taskNumber < 0) {
            // Input task number exceeds the number of tasks in the list
            Ui.exceedTaskNumberMessage(taskNumber);
        } else {
            Task taskToDelete = tasks.get(taskNumber - 1);
            tasks.remove(taskNumber - 1);
            Task.decrementCount();
            Ui.deleteTaskMessage(taskToDelete);
        }
    }

    /**
     * Deletes a SchoolClass from the priority queue
     *
     * @param classes The priority queue of SchoolClasses
     * @param line The line of user input
     * @throws IllegalArgumentException handle IllegalArgumentException
     * @throws NullPointerException handle NullPointerException
     * @throws StringIndexOutOfBoundsException handle StringIndexOutOfBoundsException
     */
    static void deleteClass(PriorityQueue<SchoolClass> classes, String line) throws
            IllegalArgumentException, NullPointerException, StringIndexOutOfBoundsException{
        try {
            // Buffer holds the string "remove class" and is redundant
            String buffer = line.substring(0, line.indexOf("/class")).trim();
            String className = line.substring(line.indexOf("/class") + 6, line.indexOf("/description")).trim();
            String description = line.substring(line.indexOf("/description") + 12, line.indexOf("/day")).trim();
            DayOfWeek day = DayOfWeek.valueOf(line.substring(line.indexOf("/day") + 4, line.indexOf("/from")).trim());
            String startString = line.substring(line.indexOf("/from") + 5, line.indexOf("/to")).trim();
            String endString = line.substring(line.indexOf("/to") + 3).trim();
            SchoolClass toDelete = new SchoolClass(className, description, day, startString, endString);
            if (classes.remove(toDelete)) {
                Ui.deleteClassMessage();
            } else {
                Ui.unsuccessfulDeleteClassMessage();
            }
        } catch (IllegalArgumentException e) {
            Ui.invalidDayMessage();
        } catch (NullPointerException e) {
            Ui.emptyDayErrorMessage();
        } catch (StringIndexOutOfBoundsException e) {
            Ui.invalidRemoveClassMessage();
        }
    }

    /**
     * Tries to delete a SchoolClass from the priority queue, and throws an error message
     * if unsuccessful. This method should be used instead of directly invoking
     * the deleteClass() method.
     *
     * @param classes The priority queue of SchoolClasses
     * @param line The line of user input
     */
    static void tryDeleteClass(PriorityQueue<SchoolClass> classes, String line) {
        if (!line.contains("/class") || !line.contains("/description") || !line.contains("/day") ||
                !line.contains("/from") || !line.contains("/to")) {
            Ui.invalidRemoveClassMessage();
        } else {
            deleteClass(classes, line);
        }
    }

    /**
     * Checks if the current day and time is past the day and end time of the SchoolClass.
     * If SchoolClass is over, mark as done, otherwise mark as not done.
     *
     * @param day The enum for the day of week registered in the current SchoolClass to check
     * @param endString The end timing for the current SchoolClass to check
     * @param currSchoolClass The current SchoolClass
     */
    static void checkClassOver(DayOfWeek day, String endString, SchoolClass currSchoolClass) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayToday = today.getDayOfWeek();
        if (dayToday.getValue() > day.getValue()) { // day of week passed
            currSchoolClass.markAsDone();
        } else if (dayToday.getValue() < day.getValue()) { // day of week not passed
            currSchoolClass.markAsNotDone();
        } else { // same day of week
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
            LocalTime classEndTime = LocalTime.parse(endString, timeFormat);
            LocalTime currTime = LocalTime.now();
            if (currTime.isAfter(classEndTime)) {
                currSchoolClass.markAsDone();
            } else {
                currSchoolClass.markAsNotDone();
            }
        }
    }

    /**
     * Clears the task list and SchoolClass priority queue, and reloads from save file.
     * This function is mainly used to update the done status of SchoolClasses in the schedule.
     *
     * @param tasks The ArrayList of tasks
     * @param classes The priority queue of SchoolClasses
     */
    static void refresh(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) {
        tasks.clear();
        Task.clearCount();
        classes.clear();
        Storage.tryLoad(tasks, classes);
    }

    static void purge(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) {
        Ui.borderLine();
        System.out.println("\t Displaying all expired tasks below...");
        System.out.println();
        if (tasks.size() > 0) {
            int expiredCount = 0;
            ArrayList<Task> expiredTasks = new ArrayList<>();
            for (Task task : tasks) {
                if (task instanceof Deadline && !(task instanceof RecurringDeadline)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmm");
                    String deadline = ((Deadline) task).getDeadline();
                    Date d = null;
                    Date n = new Date();
                    try {
                        d = format.parse(deadline);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    assert d != null;
                    long diff = d.getTime() - n.getTime();
                    if (diff < 0) {
                        expiredCount++;
                        System.out.println(task);
                        expiredTasks.add(task);
                    }
                } else if (task instanceof Event && !(task instanceof RecurringEvent)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmm");
                    String end = ((Event) task).getEnd();
                    Date d = null;
                    Date n = new Date();
                    try {
                        d = format.parse(end);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    assert d != null;
                    long diff = d.getTime() - n.getTime();
                    if (diff < 0) {
                        expiredCount++;
                        System.out.println(task);
                        expiredTasks.add(task);
                    }
                } else if (task instanceof SchoolClass) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmm");
                    String end = ((SchoolClass) task).getEnd();
                    Date d = null;
                    Date n = new Date();
                    try {
                        d = format.parse(end);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    assert d != null;
                    long diff = d.getTime() - n.getTime();
                    if (diff < 0) {
                        expiredCount++;
                        System.out.println(task);
                        expiredTasks.add(task);
                    }
                }
            }
            if (expiredCount > 0) {
                Ui.borderLine();
                System.out.println("\t Quack! A total of " + expiredCount + " tasks have expired!");
                System.out.println("\t Should I remove these tasks from the pending list human?");
                Ui.borderLine();
                if (Ui.doubleCheck()) {
                    for (Task expiredTask : expiredTasks) {
                        tasks.removeIf(task -> task == expiredTask);
                        Task.decrementCount();
                    }
                    Storage.trySave(tasks, classes);
                    Ui.borderLine();
                    System.out.println("\t Expired Tasks have been purged from the list!");
                    System.out.println("\t I love purging things, human...");
                    Ui.borderLine();
                } else {
                    Ui.borderLine();
                    System.out.println("\t Quack! Expired tasks have not been purged.");
                    Ui.borderLine();
                }
            } else {
                System.out.println("\t Quack! No tasks have expired!");
                Ui.borderLine();
            }
        } else {
            System.out.println("\t Quack! No tasks currently pending!");
            Ui.borderLine();
        }
    }

    /**
     * Takes in the task number and adds a note to the list of notes under that task
     *
     * @param tasks The arraylist of tasks
     * @param words The array of strings from user input
     */
    static void addNote(ArrayList<Task> tasks, String[] words){
        int index = Integer.parseInt(words[1]);
        if (index-1 < tasks.size() && index >= 1) {
            System.out.println("\t What note would you like to add to the following task?");
            System.out.println(tasks.get(index - 1).toString());
            Ui.borderLine();
            Scanner userInput = new Scanner(System.in);
            String noteToAdd = userInput.nextLine();
            tasks.get(index - 1).addNotes(noteToAdd);
            System.out.println("\t The note has been added!");
            Ui.borderLine();
        } else {
            Ui.exceedTaskNumberMessage(index);
        }
    }

    /**
     * Takes in the task number and index of the note to be deleted and then deletes it
     *
     * @param tasks The arraylist of tasks
     * @param words The array of strings from user input
     */
    static void deleteNotes(ArrayList<Task> tasks, String[] words) {
        int index = Integer.parseInt(words[1]);
        int indexOfNoteToBeDeleted = Integer.parseInt(words[2]);
        if (index-1 < tasks.size() && index >= 1) {
            if (indexOfNoteToBeDeleted-1 < tasks.get(index-1).numberOfNotes() && indexOfNoteToBeDeleted >= 1) {
                Ui.borderLine();
                System.out.println("\t Deleting note: ");
                ArrayList<String> noteToBeDeleted = tasks.get(index - 1).getAdditionalNotes();
                System.out.println("\t \t" + noteToBeDeleted.get(indexOfNoteToBeDeleted - 1));
                tasks.get(index - 1).deleteNote(indexOfNoteToBeDeleted);
                Ui.borderLine();
            } else {
                Ui.exceedNoteNumberMessage(indexOfNoteToBeDeleted);
            }
        } else {
            Ui.exceedTaskNumberMessage(index);
        }
    }

    /**
     * Takes in the task number and index of the note to be edited and then changes it
     *
     * @param tasks The arraylist of tasks
     * @param words The array of strings from user input
     */
    static void editNote(ArrayList<Task> tasks, String[] words) {
        int index = Integer.parseInt(words[1]);
        int indexOfNoteToBeEdited = Integer.parseInt(words[2]);
        Scanner userInput = new Scanner(System.in);
        if (index-1 < tasks.size() && index >= 1) {
            if (indexOfNoteToBeEdited-1 < tasks.get(index-1).numberOfNotes() && indexOfNoteToBeEdited >= 1) {
                Ui.borderLine();
                System.out.println("\t What would you like to change the note to? ");
                System.out.println("\t" + "\t" + tasks.get(index - 1).getNote(indexOfNoteToBeEdited - 1));
                String editedNote = userInput.nextLine();
                tasks.get(index - 1).editNote(indexOfNoteToBeEdited - 1, editedNote);
                System.out.println("\t" + "The specified note has been edited!");
                Ui.borderLine();
            } else {
                Ui.exceedNoteNumberMessage(indexOfNoteToBeEdited);
            }
        } else {
            Ui.exceedTaskNumberMessage(index);
        }
    }
}
