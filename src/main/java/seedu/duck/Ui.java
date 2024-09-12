package seedu.duck;

import seedu.duck.task.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Random;


/**
 * Deals with interactions with the user
 */
public class Ui {
    static void printDuck() {
        System.out.println("           ,-.\n" +
                "       ,--' ~.).\n" +
                "     ,'         `.\n" +
                "    ; (((__   __)))\n" +
                "    ;  ( (#) ( (#)\n" +
                "    |   \\_/___\\_/\n" +
                "   ,\"  ,-'    `__\".\n" +
                "  (   ( ._   ____`.)--._        _\n" +
                "   `._ `-.`-' \\(`-'  _  `-. _,-' `-/`.\n" +
                "    ,')   `.`._))  ,' `.   `.  ,','  ;\n" +
                "  .'  .     `--'  /     ).   `.      ;\n" +
                " ;     `-        /     '  )         ;\n" +
                " \\                       ')       ,'\n" +
                "  \\                     ,'       ;\n" +
                "   \\               `~~~'       ,'\n" +
                "    `.                      _,'\n" +
                "      `-._________,--'");
    }
    static void printMotivationalQuote(){
        String[] quotes = {
                "Believe you can and you're halfway there. -Theodore Roosevelt",
                "Start where you are. Use what you have. Do what you can. -Arthur Ashe",
                "You miss 100% of the shots you don't take. -Wayne Gretzky",
                "Success is not final, failure is not fatal: it is the courage to continue that counts. -Winston Churchill",
                "You are never too old to set another goal or to dream a new dream. -C.S. Lewis",
                "Believe in yourself and all that you are. Know that there is something inside you that is greater than any obstacle. -Christian D. Larson",
                "Success is not how high you have climbed, but how you make a positive difference to the world. -Roy T. Bennett",
                "A journey of a thousand miles begins with a single step. -Lao Tzu",
                "The only way to do great work is to love what you do. -Steve Jobs",
                "You have brains in your head. You have feet in your shoes. You can steer yourself any direction you choose. -Dr. Seuss",
                "Believe you can and you're halfway there. -Theodore Roosevelt",
                "You never know how strong you are until being strong is your only choice. -Bob Marley",
                "Do not wait for opportunities, create them. -Roy T. Bennett",
                "Believe in yourself, take on your challenges, dig deep within yourself to conquer fears. Never let anyone bring you down. You got this. -Chantal Sutherland",
                "The greatest glory in living lies not in never falling, but in rising every time we fall. -Nelson Mandela",
                "Believe in your infinite potential. Your only limitations are those you set upon yourself. -Roy T. Bennett",
                "What you get by achieving your goals is not as important as what you become by achieving your goals. -Zig Ziglar",
                "Don't watch the clock; do what it does. Keep going. -Sam Levenson",
                "Believe that you will succeed, and you will. -Dale Carnegie",
                "Success is not the key to happiness. Happiness is the key to success. If you love what you are doing, you will be successful. -Albert Schweitzer"
        };
        Random rand = new Random();
        int index = rand.nextInt(quotes.length);
        System.out.println("\t Have some motivation! Quack!" + "\n");
        System.out.println("\t " + quotes[index]);
    }

    /**
     * Prints out all currently stored tasks in the list
     *
     * @param tasks The array list of tasks
     */
    static void list(ArrayList<Task> tasks) {
        int taskCount = Task.getTaskCount();
        borderLine();
        System.out.println("\t Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t " + (i + 1) + "." + tasks.get(i));
            if (!tasks.get(i).getAdditionalNotes().isEmpty()) {
                printList(tasks, i);
            }
        }
        borderLine();
    }

    static void listClasses(PriorityQueue<SchoolClass> classes, ArrayList<Task> tasks) {
        TaskList.refresh(tasks, classes);
        Iterator<SchoolClass> iterator = classes.iterator();
        borderLine();
        System.out.println("\t Here is your class schedule:\n");
        while (iterator.hasNext()) {
            System.out.println("\t"+iterator.next());
        }
        borderLine();
    }

    /**
     * prints out all classes, deadlines and events happening today
     *
     * @param tasks   array list of all tasks
     * @param classes pq of all classes
     */
    static void listToday(ArrayList<Task> tasks, PriorityQueue<SchoolClass> classes) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayToday = today.getDayOfWeek();
        borderLine();
        System.out.println("\t Here is your class schedule for today");
        for (SchoolClass c : classes) {
            if (c.getDay() == dayToday) {
                System.out.println(c);
            }
        }
        System.out.println();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateToday = dateFormat.format(today);
        System.out.println("\t Here are your tasks today");
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                if (task instanceof RecurringDeadline) {
                    if (((RecurringDeadline) task).getDay() == dayToday) {
                        System.out.println(task);
                    }
                } else {
                    if (((Deadline) task).getDeadline().startsWith(dateToday)) {
                        System.out.println(task);
                    }
                }
            } else if (task instanceof Event) {
                if (task instanceof RecurringEvent) {
                    if (((RecurringEvent) task).getDay() == dayToday) {
                        System.out.println(task);
                    }
                } else {
                    if (((Event) task).getStart().startsWith(dateToday)) {
                        System.out.println(task);
                    }
                }
            }
        }
        borderLine();
    }

    /**
     * Prints out all currently stored tasks in the list arranged by their priority from high, medium to low
     *
     * @param tasks the list of tasks
     */
    static void printPriorityList(ArrayList<Task> tasks) {
        borderLine();
        System.out.println("\t Here are the tasks in your list arranged by priority:");
        borderLine();
        printHighPriority(tasks);
        printMediumPriority(tasks);
        printLowPriority(tasks);
    }

    /**
     * Goes through the task list and prints out the tasks that are high in priority
     *
     * @param tasks the list of tasks
     */
    static void printHighPriority(ArrayList<Task> tasks) {
        ArrayList<Integer> indexOfHighPriority = new ArrayList<>();
        int taskCount = Task.getTaskCount();
        for (int i = 0; i < taskCount; i++) {
            if (tasks.get(i).returnPriority() == 3) {
                indexOfHighPriority.add(i);
            }
        }
        if (!indexOfHighPriority.isEmpty()) {
            System.out.println("\t QUACK QUACK QUACK!!!");
            System.out.println("\t You have " + indexOfHighPriority.size() + " tasks that are high in priority!");
            for (int i = 0; i < indexOfHighPriority.size(); i++) {
                System.out.println("\t" + (i + 1) + "." + tasks.get(indexOfHighPriority.get(i)));
                if (!tasks.get(i).getAdditionalNotes().isEmpty()) {
                    ArrayList<String> toBePrinted = tasks.get(i).getAdditionalNotes();
                    for (int j = 0; j < toBePrinted.size(); j++) {
                        System.out.println("\t" + "\t - " + (j + 1) + ". " + toBePrinted.get(j));
                    }
                }
            }
        } else {
            System.out.println("\t There are no tasks that are high in priority!");
        }
        borderLine();
    }

    /**
     * Goes through the task list and prints out the tasks that are medium in priority
     *
     * @param tasks the list of tasks
     */
    static void printMediumPriority(ArrayList<Task> tasks) {
        ArrayList<Integer> indexOfMediumPriority = new ArrayList<>();
        int taskCount = Task.getTaskCount();
        for (int i = 0; i < taskCount; i++) {
            if (tasks.get(i).returnPriority() == 2) {
                indexOfMediumPriority.add(i);
            }
        }
        if (!indexOfMediumPriority.isEmpty()) {
            System.out.println("\t QUACK QUACK!!");
            System.out.println("\t You have " + indexOfMediumPriority.size() + " tasks that are medium in priority!");
            for (int i = 0; i < indexOfMediumPriority.size(); i++) {
                System.out.println("\t" + (i + 1) + "." + tasks.get(indexOfMediumPriority.get(i)));
                if (!tasks.get(i).getAdditionalNotes().isEmpty()) {
                    ArrayList<String> toBePrinted = tasks.get(i).getAdditionalNotes();
                    for (int j = 0; j < toBePrinted.size(); j++) {
                        System.out.println("\t" + "\t - " + (j + 1) + ". " + toBePrinted.get(j));
                    }
                }
            }
        } else {
            System.out.println("\t There are no tasks that are medium in priority!");
        }
        borderLine();
    }

    /**
     * Goes through the task list and prints out the tasks that are low in priority
     *
     * @param tasks the list of tasks
     */
    static void printLowPriority(ArrayList<Task> tasks) {
        ArrayList<Integer> indexOfLowPriority = new ArrayList<>();
        int taskCount = Task.getTaskCount();
        for (int i = 0; i < taskCount; i++) {
            if (tasks.get(i).returnPriority() == 1) {
                indexOfLowPriority.add(i);
            }
        }
        if (!indexOfLowPriority.isEmpty()) {
            System.out.println("\t Quack!");
            System.out.println("\t You have " + indexOfLowPriority.size() + " tasks that are low in priority!");
            for (int i = 0; i < indexOfLowPriority.size(); i++) {
                System.out.println("\t" + (i + 1) + "." + tasks.get(indexOfLowPriority.get(i)));
                if (!tasks.get(i).getAdditionalNotes().isEmpty()) {
                    ArrayList<String> toBePrinted = tasks.get(i).getAdditionalNotes();
                    for (int j = 0; j < toBePrinted.size(); j++) {
                        System.out.println("\t" + "\t - " + (j + 1) + ". " + toBePrinted.get(j));
                    }
                }
            }
        } else {
            System.out.println("\t There are no tasks that are low in priority!");
        }
        borderLine();
    }

    /**
     * Finds tasks in the list that contain keywords input by the user
     *
     * @param tasks The array list of tasks
     * @param words The array of words generated from the user input
     */
    static void find(ArrayList<Task> tasks, String[] words) {
        ArrayList<Task> matchingResults = new ArrayList<>();
        ArrayList<Integer> matchingResultsIndex = new ArrayList<>();
        int matchCount = 0;
        String keyword = Parser.processKeywords(words, 1);

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(keyword)) {
                matchingResults.add(tasks.get(i));
                matchingResultsIndex.add(i + 1);
                matchCount++;
            }
        }
        printFindResults(matchingResults, matchCount, matchingResultsIndex);
    }


    /**
     * Prints the results of the find command
     *
     * @param matchingResults The array list of tasks that contain the keywords
     * @param matchCount      The number of tasks in the list that contain the keywords
     */
    private static void printFindResults(ArrayList<Task> matchingResults, int matchCount,
                                         ArrayList<Integer> matchingResultsIndex) {
        if (matchingResults.isEmpty()) {
            noMatchMessage();
        } else {
            printMatchingList(matchingResults, matchCount, matchingResultsIndex);
        }
    }

    /**
     * Prints the list of tasks that contain the keywords
     *
     * @param matchingResults      The array list of tasks that contain the keywords
     * @param matchCount           The number of tasks in the list that contain the keywords
     * @param matchingResultsIndex The index of the task in the main list
     */
    static void printMatchingList(ArrayList<Task> matchingResults, int matchCount,
                                  ArrayList<Integer> matchingResultsIndex) {
        borderLine();
        System.out.println("\t Here are the matching tasks in your list:");
        for (int i = 0; i < matchCount; i++) {
            System.out.println("\t " + (i + 1) + "." + matchingResults.get(i) +
                    "   || The index of this item is " + matchingResultsIndex.get(i));
        }
        borderLine();
    }

    /**
     * Display upcoming deadline
     *
     * @param tasks tasks store in the file
     */
    static void displayUpcomingDeadline(ArrayList<Task> tasks) {
        System.out.println("\t Here are the upcoming deadline:  ");
        int count = 0;
        for (Task t:tasks) {
            if (t instanceof Deadline && !(t instanceof RecurringDeadline)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmm");
                String deadline = ((Deadline) t).getDeadline();
                Date d;
                Date n = new Date();
                try {
                    d = format.parse(deadline);
                    long diff = d.getTime() - n.getTime();
                    String di = getTimeDiff(diff);
                    String description = t.getDescription().replace("deadline ", "");
                    System.out.println("\t " + (count + 1) + "." + description + " (" + di + "before the deadline)");
                    count++;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        borderLine();
    }

    /**
     * Prints the list of tasks in x days in the future
     *
     * @param tasks the array list of all the tasks
     * @param days  the required the number of days x from now onwards
     */
    static void printUpcomingTasks(ArrayList<Task> tasks, String days) {
        borderLine();
        System.out.println("\t Here are your tasks in " + days + " days:");
        int count = 0;
        Date d;
        Date n = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmm");
        for (Task t:tasks) {
            String timeUntilTask = null;
            if (t instanceof Deadline && !(t instanceof RecurringDeadline)) {
                timeUntilTask = ((Deadline) t).getDeadline();
            } else if (t instanceof Event && !(t instanceof RecurringEvent)) {
                timeUntilTask = ((Event) t).getStart();
            } else if (t instanceof SchoolClass) {
                timeUntilTask = ((SchoolClass) t).getStart();
            }
            if (timeUntilTask != null) {
                try {
                    d = format.parse(timeUntilTask);
                    long diff = d.getTime() - n.getTime();
                    String di = getTimeDiff(diff);
                    String[] diffSplit = di.split(" ");
                    if (diffSplit.length >= 2 && ((diffSplit[1].contains("day") && Integer.parseInt(diffSplit[0])
                            <= Integer.parseInt(days)) || diffSplit[1].contains("hour")
                            || diffSplit[1].contains("minute"))) {
                        count++;
                        System.out.println("\t " + count + "." + t);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        borderLine();
    }

    /**
     * Function help for calculating time difference
     *
     * @param timeDifferenceMilliseconds time difference between now and deadline
     * @return time difference in structured format
     */
    static String getTimeDiff(long timeDifferenceMilliseconds) {
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000) % 60;
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000) % 60;
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24) % 24;
        long diffMonths = (long) ((timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666)) % 30.41666666);
        long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365) % 365;
        String result = "";
        if (diffYears != 0) {
            result += diffYears;
            result += " year";
            if (diffYears != 1) {
                result += "s";
            }
            result += " ";
        }
        if (diffMonths != 0) {
            result += diffMonths;
            result += " month";
            if (diffMonths != 1) {
                result += "s";
            }
            result += " ";
        }
        if (diffDays != 0) {
            result += diffDays;
            result += " day";
            if (diffDays != 1) {
                result += "s";
            }
            result += " ";
        }
        if (diffHours != 0) {
            result += diffHours;
            result += " hour";
            if (diffHours != 1) {
                result += "s";
            }
            result += " ";
        }
        if (diffMinutes != 0) {
            result += diffMinutes;
            result += " minute";
            if (diffMinutes != 1) {
                result += "s";
            }
            result += " ";
        }
        return result;
    }

    /**
     * Display Next Upcoming Class
     *
     * @param classes the array list of all the tasks
     */
    static void displayNextUpcomingClass(PriorityQueue<SchoolClass> classes) {
        borderLine();
        ArrayList<SchoolClass> result = new ArrayList<>(classes);
        System.out.println("\t Here are your next upcoming class: ");
        if (result.isEmpty()) {
            System.out.println("\t \t No upcoming class!");
        } else {
            for (SchoolClass c:result) {
                if (c.getStatusIcon() != "X") {
                    System.out.println("\t"+c);
                }
            }
        }
        borderLine();
    }

    /**
     * Prints the border for opening or closing messages
     */
    static void borderLine() {
        System.out.println("\t____________________________________________________________");
    }

    private static void noMatchMessage() {
        borderLine();
        System.out.println("\t There are no matching tasks in your list.");
        borderLine();
    }

    static void emptyCommandMessage() {
        borderLine();
        System.out.println("\t Please enter a non-empty command.");
        borderLine();
    }

    static boolean doubleCheck() {
        System.out.println("\t THIS IS AN IRREVERSIBLE PROCESS. ARE YOU SURE? Y/N");
        Scanner in = new Scanner(System.in);
        String line;
        line = in.nextLine();
        return Objects.equals(line, "Y");
    }

    static void editTodoMessage() {
        borderLine();
        System.out.println("\t Please enter a new Todo description:");
    }

    static void editDeadlineMessage() {
        borderLine();
        System.out.println("\t Please edit one of the following:");
        System.out.println("\t For non-recurring deadlines: /description or /deadline");
        System.out.println("\t For recurring deadlines: /description or /deadline or /day");
        System.out.println("\t Please follow the format: ");
        System.out.println("\t /description <new_description> or /deadline <new_deadline> or /day <NEW_DAY_OF_WEEK>");
        System.out.println("\t e.g. /deadline 2023-06-30 1200 or /deadline 1200 (for recurring deadlines)");
    }

    static void editEventMessage() {
        borderLine();
        System.out.println("\t Please edit one of the following:");
        System.out.println("\t For non-recurring events: /description or /from or /to");
        System.out.println("\t For recurring deadlines: /description or /from or /to or /day");
        System.out.println("\t Please follow the format: ");
        System.out.println("\t /description <new_description> or /from <new_start_time> or /day <NEW_DAY_OF_WEEK>");
        System.out.println("\t e.g. /from 2023-06-30 1200 or /from 1200 (for recurring events)");
    }

    static void printEditedTask(Task task) {
        borderLine();
        System.out.println("\t Quack!");
        System.out.println("\t I have changed your task to:");
        System.out.println("\t " + task);
        borderLine();
    }

    static String askForEditMessage() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        return line;
    }

    static void help() {
        borderLine();
        System.out.println("\t   Quack! Here are the commands you can give me:");
        System.out.println("\t - list: I'll list out all the tasks you have recorded.");
        System.out.println("\t - list <number_of_days>: I'll list out all the tasks in that number of days.");
        System.out.println("\t - list_classes: I'll list out the classes you have on your schedule.");
        System.out.println("\t - list_today: I'll list out all the classes, deadlines and events you have today.");
        System.out.println("\t - priority_list: " +
                "I'll list out all the tasks you have recorded arranged by their priority.");
        System.out.println("\t - upcoming_class: I'll list out the next upcoming class.");
        System.out.println("\t - low_priority: I'll list out all the tasks you have that are low in priority.");
        System.out.println("\t - medium_priority: I'll list out all the tasks you have that are medium in priority.");
        System.out.println("\t - high_priority: I'll list out all the tasks you have that are high in priority.");
        System.out.println("\t - clear: The list will be cleared. This is an IRREVERSIBLE process.");
        System.out.println("\t - mark <task_number>: I'll mark that task as done.");
        System.out.println("\t - unmark <task_number>: I'll mark that task as undone.");
        System.out.println("\t - delete <task_number>: I'll delete that task from your list.");
        System.out.println("\t - remove /class <class_name> /description <description> " +
                "/day <DAY_OF_WEEK> /from <HHmm> /to <HHmm>");
        System.out.println("\t   (/description can be followed by whitespace if the class has no description.");
        System.out.println("\t   : I'll remove this class from your class schedule.");
        System.out.println("\t - add_note <task_number>: I'll add an additional note to that task!");
        System.out.println("\t - delete_note <task_number> <note_number>: I'll delete the note to that task!");
        System.out.println("\t - edit_note <task_number> <note_number>: I'll edit the note for that task!");
        System.out.println("\t - view_notes <task_number>: I'll print the additional notes for that task!");
        System.out.println("\t - purge: I'll delete all expired tasks from your list after a confirmation.");
        System.out.println("\t - find <keyword>: I'll find the tasks in your list that contain the keyword.");
        System.out.println("\t - priority <task_number> <1/2/3>: I'll set the priority of a given task as");
        System.out.println("\t                                   1:Low, 2:Medium and 3:High.");
        System.out.println("\t                                   Default: Low priority.");
        System.out.println("\t - bye: I will shut down my program.\n");
        System.out.println("\t Here are the following ways to input tasks/classes:");
        System.out.println("\t Deadlines: <description> /by <yyyy-MM-dd HHmm>");
        System.out.println("\t            (eg. Eat bread /by 2023-03-15 2015)");
        System.out.println("\t Recurring deadlines: /re <description> /by <HHmm> /day <DAY_OF_WEEK>");
        System.out.println("\t            (eg. /re Eat bread /by 2015 /day MONDAY)");
        System.out.println("\t Events   : <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        System.out.println("\t            (eg. Meeting /from 2023-03-15 2015 /to 2023-03-15 2215)");
        System.out.println("\t Recurring events: /re <description> /from <HHmm> /to <HHmm> /day <DAY_OF_WEEK>");
        System.out.println("\t            (eg. /re Meeting /from 2015 /to 2215 /day MONDAY)");
        System.out.println("\t Todo     : <description>");
        System.out.println("\t            (eg. Water the plants)");
        System.out.println("\t Classes  : <description> /class <class_name> /day <DAY_OF_WEEK> " +
                "/from <HHmm> /to <HHmm>");
        System.out.println("\t            (eg. Bring laptop /class CS2113 /day TUESDAY /from 1100 /to 1200) \n");
        System.out.println("\t How else may I assist you today, human?");
        borderLine();
    }

    static void addedTaskMessage(Task currentTask) {
        borderLine();
        System.out.println("\t Alright, I have added this task: \n\t" + currentTask);
        System.out.println("\t You now have " + (Task.getTaskCount() + 1) + " tasks in your list.");
        borderLine();
    }

    static void addedSchoolClassMessage(SchoolClass currentClass, PriorityQueue<SchoolClass> classes) {
        borderLine();
        System.out.println("\t Alright, I have added this class: \n\t" + currentClass);
        System.out.println("\t You now have " + (classes.size()) + " classes in your schedule.");
        borderLine();
    }

    static void deleteTaskMessage(Task taskToDelete) {
        borderLine();
        System.out.println("\t Understood. I have removed this task:");
        System.out.println("\t" + taskToDelete);
        System.out.println("\t You now have " + Task.getTaskCount() + " tasks in your list.");
        borderLine();
    }

    static void deleteClassMessage() {
        borderLine();
        System.out.println("\t Class has been deleted successfully.");
        borderLine();
    }

    static void unsuccessfulDeleteClassMessage() {
        borderLine();
        System.out.println("\t Unsuccessful. No class has been deleted.");
        borderLine();
    }

    static void refreshedMessage() {
        borderLine();
        System.out.println("\t Your task list and class schedule have been refreshed!");
        borderLine();
    }

    static void exceedTaskNumberMessage(int taskNumber) {
        borderLine();
        System.out.println("\t Task " + taskNumber + " does not exist.");
        borderLine();
    }

    static void exceedNoteNumberMessage(int noteNumber) {
        borderLine();
        System.out.println("\t Note " + noteNumber + " does not exist for this task");
        borderLine();
    }

    static void todoErrorMessage() {
        borderLine();
        System.out.println("\t Error. Please enter a valid description.");
        borderLine();
    }

    static void unknownCommandMessage() {
        borderLine();
        System.out.println("\t Error. Please check that the command has been entered correctly.");
        borderLine();
    }

    static void expiredErrorMessage() {
        borderLine();
        System.out.println("\t Quack! I know humans wish to undo their past mistakes, " +
                "but the start date has already passed!");
        System.out.println("\t Please try again!");
        borderLine();
    }

    static void emptyDescriptionErrorMessage() {
        borderLine();
        System.out.println("\t Error. Description cannot be empty");
        borderLine();
    }

    static void startAfterEndErrorMessage() {
        borderLine();
        System.out.println("\t Quack! Somehow this human has time travelled, " +
                "and the start date seems to be after the end date! ");
        System.out.println("\t Please try again!");
        borderLine();
    }

    static void eventErrorMessage() {
        borderLine();
        System.out.println("\t Error. Please enter a valid description, start time and end time");
        borderLine();
    }

    static void deadlineErrorMessage() {
        borderLine();
        System.out.println("\t Error. Please enter a valid description and deadline.");
        borderLine();
    }

    static void schoolClassErrorMessage() {
        borderLine();
        System.out.println("\t Error. Please enter a valid class name, description, start time and end time");
        borderLine();
    }

    static void invalidDayMessage() {
        borderLine();
        System.out.println("\t Error. Please enter a valid day of week in all capital letters (Eg. MONDAY).");
        borderLine();
    }

    static void emptyDayErrorMessage() {
        borderLine();
        System.out.println("\t Error. Please enter a day of week.");
        borderLine();
    }

    static void invalidRemoveClassMessage() {
        borderLine();
        System.out.println("\t Error. Please follow the correct format to remove classes.");
        borderLine();
    }

    static void invalidDateTimeMessage() {
        borderLine();
        System.out.println("\t Please check the inputted format human!\n" +
                "\t There are only 24 hours in a day in Duck World, and 12 months a year...\n");
        System.out.println("\t Please try again!");
        borderLine();
    }

    /**
     * Prints the startup message, includes instructions on available commands
     */
    static void greetingMessage() {
        printDuck();
        borderLine();
        printMotivationalQuote();
        borderLine();
        System.out.println("\t Quack! Nice to meet you human. As you can see,  I'm a Duck.");
        System.out.println("\t As a Duck, I can only understand simple commands. Quack. " +
                "Human speech is so confusing!");
        System.out.println("\t That being said, I am a smart Duck. " +
                "If you wish to know what I understand, just enter 'help'.");
        System.out.println("\t How may I assist you today, human?");
    }

    static void printNotes(ArrayList<Task> tasks, String[] words) {
        int index = Integer.parseInt(words[1]);
        ArrayList<String> toBePrinted = tasks.get(index - 1).getAdditionalNotes();
        borderLine();
        System.out.println("\t Here are the notes for that task quack!");
        System.out.println(tasks.get(index-1).toString());

        if (!toBePrinted.isEmpty()) {
            for (int i = 0; i < toBePrinted.size(); i++) {
                System.out.println("\t \t" + (i + 1) + ". " + toBePrinted.get(i));
            }
        } else {
            System.out.println("\t There are no notes for this task!");
        }
        borderLine();
    }

    static void printList(ArrayList<Task> tasks, int index) {
        ArrayList<String> toBePrinted = tasks.get(index).getAdditionalNotes();
        for (int j = 0; j < toBePrinted.size(); j++) {
            System.out.println("\t" + "\t - " + (j + 1) + ". " + toBePrinted.get(j));
        }
    }

    /**
     * Prints the exiting message when closing the program
     */
    static void exitMessage() {
        printDuck();
        borderLine();
        System.out.println("\t Bye. Hope to see you again soon!");
        borderLine();
    }


}
