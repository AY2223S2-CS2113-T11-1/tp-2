package seedu.duck;

import seedu.duck.task.SchoolClass;
import seedu.duck.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.PriorityQueue;

public class Duck {
    /** Runs the Duck bot */
    private static void runDuck() throws IOException {
        Ui.greetingMessage();

        PriorityQueue<SchoolClass> classes = new PriorityQueue<>();
        ArrayList<Task> tasks = new ArrayList<>();
        Storage.tryLoad(tasks, classes);
        TaskList.purge(tasks, classes);
        Ui.displayUpcomingDeadline(tasks);
        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();

        Parser.processCommand(tasks, classes, line, in);

        Ui.exitMessage();
    }

    public static void main(String[] args) throws IOException {
        runDuck();
    }
}
