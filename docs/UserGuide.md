# Duck User Guide(v 2.0)

Duck is a **desktop app for managing tasks and deadlines, as well as a school class scheduler. It is optimised for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). Duck will have a number of quality-of-life features for busy students seeking a simple solution to task management. These include automated task removal upon expiration, or limiting the number of tasks displayed to within a date range. Duck also has recurring task functionality to automate routine tasks so users do not have to manually re-add expired tasks.



* [**Features**](#features)
    * [Viewing help :](#viewing-help--help) `help`
    * [Listing all tasks :](#listing-all-tasks--list) `list`
    * [Listing all tasks and classes happening today :](#listing-all-tasks--list) `list_today`
    * [Listing all tasks up to X days into the future :](#listing-all-tasks-up-to-x-days-in-the-future--list-x) `list X`
    * [Displaying upcoming deadline](#displaying-upcoming-deadline)
    * [Displaying class schedule :](#displaying-class-schedule--list-classes) `list_classes`
    * [Displaying upcoming class :](#displaying-upcoming-class--upcomingclass) `upcoming_class`
    * [Mark a specified task as done :](#marking-a-task--mark-task_number) `mark <task number> `
    * [Unmark a specified task as  not done :](#unmarking-a-task--unmark-task_number) `unmark <task number>`
    * [Edit a specific piece information of a task :](#editing-a-task--edit-task_number) `edit <task number>`
    * [Deleting a task :](#deleting-a-task--delete) `delete <task number> `
    * [Deleting a school class :](#deleting-a-school-class--remove-class) `remove /class <class_name> /description <description> /day <DAY_OF_WEEK> /from <HHmm> /to <HHmm> `
    * [Designate a priority to a given task :](#designate-a-task-priority--priority-task_number-priority) `priority <task number> <priority>` 
    * [Adding notes for a specific task :](#adding-notes-for-a-specific-task--add_note) `add_note <task number>`
    * [Deleting notes for a specific task :](#deleting-notes-for-a-specific-task--delete_note) `delete_note <task number>`
    * [Editing notes for a specific task :](#editing-notes-for-a-specific-task--edit_note) `edit_note <task number> <note number>`
    * [Viewing notes for a specific task :](#printing-notes-for-a-specific-task--view_notes) `view_notes <task number>`
    * [List tasks of low/medium/high priority :](#listing-all-low-priority-tasks--low_priority) `low_priority`/`medium_priority`/`high_priority`
    * [List tasks in priority order:](#listing-all-tasks-arranged-by-priority--priority_list) `priority_list`
    * [Purge expired tasks :](#purge-expired-tasks--purge) `purge`
    * [Clearing all tasks (including datafile) :](#clearing-tasks-from-storage-clear) `clear `
    * [Find tasks matching a given keyword :](#finding-tasks-from-storage-that-match-a-keyword-find-keyword) `find <keyword>`
    * [Add tasks that can be broken down into the following 6 types:](#adding-a-todo-task--description)
    
            * Add ToDo: <description>
            * Add Deadline: <description> /by <yyyy-MM-dd HHmm>
            * Add RecurringDeadline: /re <description> /by <HHmm> /day <DAY_OF_WEEK>
            * Add Event: <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>
            * Add RecurringEvent: /re <description> /from <HHmm> /to <HHmm> /day <DAY_OF_WEEK>
            * Add Class: <description> /class <class_name> /day <DAY_OF_WEEK> /from <HHmm> /to <HHmm>
    * [Terminate the program :](#exiting-the-program--bye) `bye`


# Features


## **Viewing help : `help`**

Displays all available commands and their input format on the terminal.

**Input:** `help`

**Output:**


```
（`･v･´ ）: Here are the commands you can give me: 
COMMAND_FORMAT : COMMAND_FUNCTIONALITY EXPLANATION
.
.
.
（`･v･´ ）: How else may I assist you today, human?
```



## **Listing all tasks : `list`**

Displays all tasks currently stored in the application.

**Input:** `list`

**Output: Demonstrated with 1 of each type currently in the stored list of tasks**


```
Here are the tasks in your list:
	 1.	 [T][ ] todo (No priority established.)
	 2.	 [D][ ] deadline (by: 2023-03-25 2359) (No priority established.)
	 3.	 [E][ ] event (from: 2023-03-25 1200 to: 2023-03-26 2359) (No priority established.)
```

## **Listing all tasks and classes happening today : `list_today`**

Displays all deadlines, events and classes happening today.

**Input:** `list_today`

**Output: Demonstrated with 1 of each type currently in the stored data array**


```
	____________________________________________________________
	 Here is your class schedule for today
	 [WEDNESDAY][X] cs2113 (from: 1200 to: 1300)

	 Here are your tasks today
	 [D][ ] sleep (by: 2023-03-29 2000) (No priority established.)
	 [D][ ] shower (by: 2000) (every WEDNESDAY) (No priority established.)
	 [E][ ] study (from: 2023-03-29 2100 to: 2023-04-23 2300) (No priority established.)
	 [E][ ] study (from: 0800 to: 2300) (every WEDNESDAY) (No priority established.)
	____________________________________________________________
```
## **Displaying upcoming deadline**

Displays upcoming deadline stored in the application when starting the application. 

**Output: The upcoming deadline with remaining time before the deadline**

```
Here are the upcoming deadline:  
1.Eat bread (1 day 26 hours 50 minutes before the deadline)
```

## **Displaying class schedule : `list_classes`**

Displays all School Classes currently stored in the application. Classes will be automatically sorted according to chronological order. Classes will also automatically be marked as done (represented by a cross) if the current time is past the ending time of the class, and their 'done' status will be reset at the start of each week.

**Input:** `list_classes`

**Output: Demonstrated with classes with different names and start/end times, with some already past their end time**


```
Here is your class schedule:

    [MONDAY][X] eg2501 (from: 1600 to: 1800)
    [TUESDAY][X] ee2026 (from: 0900 to: 1200)
    [TUESDAY][ ] cs2113 (from: 1600 to: 1700)

```
## **Displaying upcoming class : `upcoming_class`**

Displays the next upcoming class stored in the application. Classes will be automatically sorted according to chronological order. Classes will also automatically be marked as done (represented by a cross) if the current time is past the ending time of the class, and their 'done' status will be reset at the start of each week.

**Input:** `upcoming_class`

**Output: The next upcoming class with names and start/end times**


```
Here is your next upcoming class:
    [TUESDAY][ ] cs2113 (from: 1600 to: 1700)

```


## **Listing all low priority tasks : `low_priority`**
Displays all tasks that have been assigned low priority  
If there are no tasks in the low priority list, another message will be shown

**Input:** `low_priority`  
**Output: Demonstrated with 1 of each data type currently in the stored data array**
```
Quack!
	 You have 3 tasks that are low in priority!
	1.	 [T][ ] Water plants (Low priority.)
	2.	 [D][ ] Submit CS2113 quiz (by: 2023-03-31 2359) (Low priority.)
	3.	 [E][ ] CS2113 lecture (from: 2023-03-30 1100 to: 2023-03-30 1200) (Low priority.)
```
**If no tasks in low priority list:**
```
There are no tasks that are low in priority!
	____________________________________________________________
```

## **Listing all medium priority tasks : `medium_priority`**
Displays all tasks that have been assigned medium priority  
If there are no tasks in the medium priority list, another message will be shown

**Input:** `medium_priority`  
**Output: Demonstrated with 1 of each data type currently in the stored data array**
```
QUACK QUACK!!
	 You have 3 tasks that are medium in priority!
	1.	 [T][ ] Water plants (Medium priority.)
	2.	 [D][ ] Submit CS2113 quiz (by: 2023-03-31 2359) (Medium priority.)
	3.	 [E][ ] CS2113 lecture (from: 2023-03-30 1100 to: 2023-03-30 1200) (Medium priority.)
	____________________________________________________________
```
**If no tasks in medium priority list:**
```
There are no tasks that are medium in priority!
	____________________________________________________________
```

## **Listing all high priority tasks : `high_priority`**
Displays all tasks that have been assigned high priority  
If there are no tasks in the high priority list, another message will be shown

**Input:** `high_priority`  
**Output: Demonstrated with 1 of each data type currently in the stored data array**
```
QUACK QUACK QUACK!!!
	 You have 3 tasks that are high in priority!
	1.	 [T][ ] Water plants (High priority.)
	2.	 [D][ ] Submit CS2113 quiz (by: 2023-03-31 2359) (High priority.)
	3.	 [E][ ] CS2113 lecture (from: 2023-03-30 1100 to: 2023-03-30 1200) (High priority.)
	____________________________________________________________
```
**If no tasks in high priority list:**
```
There are no tasks that are high in priority!
	____________________________________________________________
```

## **Listing all tasks arranged by priority : `priority_list`**
Displays all tasks arranged by priority

**Input:** `priority_list`  
**Output: Demonstrated with 1 task in each priority list**
```
____________________________________________________________
	 Here are the tasks in your list arranged by priority:
	____________________________________________________________
	 QUACK QUACK QUACK!!!
	 You have 1 tasks that are high in priority!
	1.	 [E][ ] CS2113 lecture (from: 2023-03-30 1100 to: 2023-03-30 1200) (High priority.)
	____________________________________________________________
	 QUACK QUACK!!
	 You have 1 tasks that are medium in priority!
	1.	 [T][ ] Water plants (Medium priority.)
	____________________________________________________________
	 Quack!
	 You have 1 tasks that are low in priority!
	1.	 [D][ ] Submit CS2113 quiz (by: 2023-03-31 2359) (Low priority.)
	____________________________________________________________
```


## **Listing all tasks up to X days in the future : `list X`**

Displays all tasks currently stored in the application, up to X days into the future.

For instance <code>list 0<strong></strong></code>returns all tasks that are starting within 24 hours.

**Input:** `list X `

**Output:  Demonstrated by inputting <code>list 0</code> on 24/3/2023 12:40, using the same array as above</strong>**


```
 Here are your tasks in 0 days:
	 1.	 [E][ ] event (from: 2023-03-25 1200 to: 2023-03-26 2359) (No priority established.)
	 2.	 [D][ ] Submit CS2113 quiz (by: 2023-03-31 2359) (Low priority.)
```



## **Marking a task : `mark <Task_Number>`**

Marks a task from the tasklist as complete.

**Input: `mark <Task_Number>`**

**Output:  Demonstrated with input `mark 1`**


```
Understood. I've marked this task as done:
	 	 [T][X] todo (No priority established.)
```



## **Unmarking a task : `unmark <Task_Number>`**

Unmarks a task from the tasklist as not complete.

**Input:** `unmark <Task_Number>`

**Output:  Demonstrated with input `unmark 1`**


```
 Understood. I've marked this task as not done yet:
	 	 [T][ ] todo (No priority established.)
```

## **Editing a task : `edit <Task_Number>`**

Edits a specific piece of information of a task.

**Input:** `edit <Task_Number>`

**Output: Demonstrated with input `edit 2`**

```
	____________________________________________________________
	 Please edit one of the following:
	 For non-recurring deadlines: /description or /deadline
	 For recurring deadlines: /description or /deadline or /day
	 Please follow the format: 
	 /description <new_description> or /deadline <new_deadline> or /day <NEW_DAY_OF_WEEK>
	 e.g. /deadline 2023-06-30 1200 or /deadline 1200 (for recurring deadlines)
```
**Following output: Demonstrated with input `/deadline 2023-04-01 2000`**

```
	____________________________________________________________
	 Quack!
	 I have changed your task to:
	 	 [D][ ] deadline (by: 2023-04-01 2000) (Low priority.)
	____________________________________________________________
```

## **Deleting a task : `delete`**

Removes a task from the tasklist.

**Input:** `delete <Task_Number>`

**Output:  Demonstrated with removing the following task**


```
Understood. I have removed this task:
	  [T][ ] todo (No priority established.)
You now have 3 tasks in your list.
```



## **Deleting a school class : `remove class`**

Removes a school class from the schedule.

**Input:** `remove /class <class_name> /description <description> /day <DAY_OF_WEEK> /from <HHmm> /to <HHmm>`

`<description>` can be left empty if the class has no description.

eg. `remove /class cs2113 /description /day THURSDAY /from 1100 /to 1200`

**Output:  Demonstrated with input `remove class /class CS2113 /description bring laptop /day THURSDAY /from 1100 /to 1200`**


```
Class has been deleted successfully.
```



## **Designate a task priority : `priority <Task_Number> <priority>`**



Specifies a priority for a given task, with the following assignments: 
* 1 - Low 
* 2 - Medium 
* 3 - High 

By default, all tasks are low priority.

**Input:** `priority <Task_Number> <priority>`

**Output:  Demonstrated with input `priority 1 1`**


```
Understood. The task's new priority is:
	 Low priority.
```

## **Adding notes for a specific task : `add_note`**
Adds a note to the specified task

**Input:** `add_note <Task_Number>`  
**Output: Demonstrated below adding "Bring own recyclable bag"**
```
    What note would you like to add to the following task?
        [T][ ] Buy groceries (Low priority.)
    ____________________________________________________________
Bring own recyclable bag
    The note has been added!
    ____________________________________________________________
```

## **Deleting notes for a specific task : `delete_note`**
Deletes the 

**Input:** `delete_note <Task_Number> <Note_number>`  
**Output: Demonstrated below to delete 1 task**

```
        ____________________________________________________________
	 Deleting note: 
	 	Bring own recyclable bag
	____________________________________________________________
```
## **Editing notes for a specific task : `edit_note`**
Edits the specified note for a specific task.

**Input:** `edit_note <Task_Number> <Note_Number>`  
**Output: Demonstrated below to edit "Bring recyclable bag" to "Bring tote bag"**
```
        ____________________________________________________________
	 What would you like to change the note to? 
		Bring recyclable bag
Bring tote bag
	The specified note has been edited!
	____________________________________________________________
```

## **Printing notes for a specific task : `view_notes`**
Prints the notes for a specific task if they exist.   
Otherwise, a message stating that there are no notes for that task will be shown

**Input:** `view_notes <Task_Number>`  
**Output: Demonstrated below for a task with 1 note**
```
        ____________________________________________________________
	Here are the notes for that task quack!
			 [T][ ] Buy groceries (Low priority.)
	1. Bring own recyclable bag
	____________________________________________________________
```
**Output: Demonstrated below for a task with no notes**
```
        ____________________________________________________________
	Here are the notes for that task quack!
			 [T][ ] Buy groceries (Low priority.)
	There are no notes for this task!
	____________________________________________________________
```

## **Purge Expired Tasks : `purge`**

Prompts the user for confirmation. Proceeds to remove all expired tasks from storage upon confirmation. This operation is automatically executed once upon Duck’s startup.

**Input:** `purge`

**Output: Demonstrated by having an expired deadline task in the array**


```
 Displaying all expired tasks below...

	 [D][ ] deadline (by: 2023-03-24 1100) (No priority established.)
____________________________________________________________
	 Quack! A total of 1 task has expired!
	 Should I remove these tasks from the pending list human?
____________________________________________________________
	 THIS IS AN IRREVERSIBLE PROCESS. ARE YOU SURE? Y/N

```



* **If the user inputs ‘Y’:** 
```
Expired Tasks have been purged from the list!
I love purging things,human...
```


* **If the user inputs ‘N’:** 
```
Quack! Expired tasks have not been purged.
```

## **Clearing tasks from storage: `clear`**

Prompts the user for confirmation. Proceeds to clear all tasks from storage upon confirmation.

**Input:** `clear`

**Output:** 


```
THIS IS AN IRREVERSIBLE PROCESS. ARE YOU SURE? Y/N
```



* **If the user inputs ‘Y’:** `Got it, all tasks have been cleared.`
* **If the user inputs ‘N’:** `Quack! Process cancelled.`

## **Finding tasks from storage that match a keyword: `find <keyword>`**

Traverses through the stored tasks to return all tasks that contain the keyword

**Input:** `find <keyword>`

**Output: Demonstrated by using the keyword `todo`**


```
Here are the matching tasks in your list:
1.	 [T][ ] todo (No priority established.)
```



## **Adding a ToDo Task : `<description>`**

Adds a ToDo task to the storage of Duck

**Input:** `<description>`

**Output:  Demonstrated by inputting `Todo`**


```
Alright, I have added this task: 
	[T][ ] todo (No priority established.)
You now have 2 tasks in your list.
```



## **Adding a Deadline Task : `<description> /by <yyyy-MM-dd HHmm>`**

Adds a Deadline task to the storage of Duck

**Input:** `<description> /by <yyyy-MM-dd HHmm>`

**Output:  Demonstrated by inputting `deadline /by 2023-03-25 2359`**


```
Alright, I have added this task: 
	[D][ ] deadline (by: 2023-03-25 2359) (No priority established.)
You now have 3 tasks in your list.
```

## **Adding a RecurringDeadline Task : `/re <description> /by <HHmm> /day <DAY_OF_WEEK>`**

Adds a RecurringDeadline task to the storage of Duck

**Input:** `/re <description> /by <HHmm> /day <DAY_OF_WEEK>`

**Output:  Demonstrated by inputting `/re new_deadline /by 2359 /day MONDAY`**


```
Alright, I have added this task: 
	[D][ ] new_deadline (by: 2359) (every MONDAY) (Low priority.)
You now have 4 tasks in your list.
```


## **Adding an Event Task : `<description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`**

Adds an Event task to the storage of Duck

**Input:** `<description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`

**Output:  Demonstrated by inputting `event /from 2023-03-25 2359 /to 2023-03-26 1100`**


```
Alright, I have added this task: 
	[E][ ] event (from: 2023-03-25 2359 to: 2023-03-26 1100) (No priority established.)
You now have 5 tasks in your list.
```

## **Adding an RecurringEvent Task : `/re <description> /from <HHmm> /to <HHmm> /day <DAY_OF_WEEK>`**

Adds a RecurringEvent task to the storage of Duck

**Input:** `/re <description> /from <HHmm> /to <HHmm> /day <DAY_OF_WEEK>`

**Output:  Demonstrated by inputting `/re new_event /from 2000 /to 2300 /day MONDAY`**


```
Alright, I have added this task: 
	[E][ ] new_event (from: 2000 to: 2300) (every MONDAY) (No priority established.)
You now have 6 tasks in your list.
```



## **Adding a School Class : `<description> /class <class_name> /day <DAY_OF_WEEK> /from <HHmm> /to <HHmm>`**

Adds a Class task to the storage of Duck

**Input:** `<description> /class <class_name> /day <DAY_OF_WEEK> /from <HHmm> /to <HHmm>`

**Output:  Demonstrated by inputting `Bring laptop /class CS2113 /day THURSDAY /from 1100 /to 1200`**


```
Alright, I have added this task: 
	[THURSDAY][ ] CS2113: Bring laptop (from: 1100 to: 1200)
You now have 1 class in your schedule.
```



## **Exiting the program : `bye`**

Exits the program.

**Input:** `bye`

**Output:** 


```
Bye. Hope to see you again soon!
```


## **Saving the data :**

Duck’s data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.
