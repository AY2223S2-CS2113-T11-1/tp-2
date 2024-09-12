package seedu.duck;

import seedu.duck.task.SchoolClass;
import seedu.duck.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Deals with making sense of the user command
 */
public class Parser {

    /**
     * Returns boolean value of true if input String is an integer,
     * else returns boolean value of false
     *
     * @param word String input to check if it is an integer
     * @return true if input String is an integer, otherwise false
     */
    public static boolean isNumeric(String word) {
        int valueToConvert;
        try {
            valueToConvert = Integer.parseInt(word);
            return true;
        } catch (NumberFormatException e) {
            // Empty catch block, since the only purpose is to return false
            // if try block fails.
        }
        return false;
    }

    /**
     * Processes the user input and executes the appropriate command
     *
     * @param tasks The array list of tasks
     * @param line The line of user input
     * @param in The input from scanner
     */
    static void processCommand(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes, String line, Scanner in) throws IOException {
        while (!line.trim().equals("bye")) {
            String[] words = line.split(" ");
            line = line.trim();
            if (line.isBlank()) {
                Ui.emptyCommandMessage();
            } else {
                switch (words[0]) {
                case "list":
                    if (words.length == 1) {
                        Ui.list(tasks);
                    } else if (words.length == 2 && isNumeric(words[1])) {
                        Ui.printUpcomingTasks(tasks, words[1]);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "list_today":
                    Ui.listToday(tasks, classes);
                    break;
                case "priority_list":
                    Ui.printPriorityList(tasks);
                    break;
                case "low_priority":
                    Ui.printLowPriority(tasks);
                    break;
                case "medium_priority":
                    Ui.printMediumPriority(tasks);
                    break;
                case "high_priority":
                    Ui.printHighPriority(tasks);
                    break;
                case "list_classes":
                    Ui.listClasses(classes, tasks);
                    break;
                case "help":
                    Ui.help();
                    break;
                case "upcoming_class":
                    Ui.displayNextUpcomingClass(classes);
                    break;
                case "unmark":
                    if (words.length == 2 && isNumeric(words[1])) {
                        TaskList.unmarkTask(tasks, words);
                        Storage.trySave(tasks, classes);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "mark":
                    if (words.length == 2 && isNumeric(words[1])) {
                        TaskList.markTask(tasks, words);
                        Storage.trySave(tasks, classes);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "delete":
                    if (words.length == 2 && isNumeric(words[1])) {
                        TaskList.deleteTask(tasks, words);
                        Storage.trySave(tasks, classes);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "remove":
                    if (words.length > 1 && words[1].equals("/class")) {
                        TaskList.tryDeleteClass(classes, line);
                        Storage.trySave(tasks, classes);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "edit":
                    if (words.length == 2 && isNumeric(words[1])) {
                        TaskList.tryEditTask(tasks, words);
                        Storage.trySave(tasks, classes);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "find":
                    if (words.length > 1) {
                        Ui.find(tasks, words);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "purge":
                    TaskList.purge(tasks, classes);
                    break;
                case "priority":
                    if (words.length == 3) {
                        TaskList.setPriority(tasks, words);
                        Storage.trySave(tasks, classes);
                    } else {
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "add_note":
                    if(words.length == 2 && isNumeric(words[1])){
                        TaskList.addNote(tasks, words);
                        Storage.trySave(tasks, classes);
                    }else{
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "delete_note":
                    if(words.length == 3){
                        TaskList.deleteNotes(tasks,words);
                        Storage.trySave(tasks, classes);
                    } else{
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "view_notes":
                    if(words.length == 2 && isNumeric(words[1])) {
                        Ui.printNotes(tasks, words);
                    }else{
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "edit_note":
                    if(words.length == 3){
                        TaskList.editNote(tasks, words);
                        Storage.trySave(tasks, classes);
                    }else{
                        Ui.unknownCommandMessage();
                    }
                    break;
                case "clear":
                    if (Ui.doubleCheck()) {
                        // Find tasks that contain a keyword
                        tasks.clear();
                        Task.clearCount();
                        classes.clear();
                        Ui.borderLine();
                        System.out.println("\t Got it, all tasks have been cleared.");
                        Ui.borderLine();
                        Storage.clearTask();
                        break;
                    } else {
                        Ui.borderLine();
                        System.out.println("\t Quack! Process cancelled.");
                        Ui.borderLine();
                        break;
                    }
                default:
                    TaskList.addTask(line, tasks, classes);
                    Storage.trySave(tasks, classes);

                }
                line = in.nextLine();
            }
        }
    }

    /**
     * Process the array of words from the user input and extracts the
     * keywords into a single string to use for the find function
     *
     * @param words The array of words generated from the user input
     * @return The keywords string to use for the find function
     */
    static String processKeywords(String[] words,int index) {
        String rawKeyword = "";
        for (int i = index; i < words.length; i++) {
            rawKeyword += (" " + words[i]);
        }
        String keyword = rawKeyword.trim();
        return keyword;
    }
}
