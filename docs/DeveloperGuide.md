# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Architecture

![](https://lh3.googleusercontent.com/FwYOJJpyhNrHNAUjWo1grnsWCDHdKMosDWaJaI_3kqtVbfD8108yk0MeJaybK3ac0MOAL3EVKYYCUjSiAbBrwAT8wRBkPTj2UDfw3AdhPT0fB8twOxfRDfh3BGAawOUOUlt-rxSACvVOhvqRnFamqWM)

The **Architecture Diagram** given above explains the high-level design of Duck.

Given below is a quick overview of main components and how they interact with each other.

<br />

**Main components of the architecture**

```Duck``` is responsible for:

**At app launch:** 

-   Executes the ```Ui#greetingMessage()``` operation.

-   Initializes a new ArrayList tasks 

-   Populates the ArrayList by executing the ```Storage#tryLoad()``` operation.

-   Begins scanning for user inputs, and passing these inputs to ```Parser``` class.

-   At shut down: Shuts down the components and invokes the ```Ui#exitMessage()``` operation.

```Commons``` represents a collection of classes used by multiple other components, such as ```java.time.LocalDateTime```

The rest of the App consists of four components.

-   ```UI```: The UI of the App.

-   ```Parser```: The command executor.

-   ```Storage```: Reads data from, and writes data to, the hard disk.

-   ```TaskList```: Holds the data of Duck in memory.

```TaskList``` stores items that belong to 4 subclasses that draw upon a shared superclass.

```Task```: The skeleton superclass of all Tasks. 

-   ```ToDo```: Basic form of ```Task```

-   ```Deadline```: Expands upon ```Task``` by storing a ```by``` variable

-   ```Event```: Expands upon ```Task``` by storing ```start``` and ```end``` variable

-   ```SchoolClass```: Expands upon ```Task``` by storing ```start``` and ```end``` variable, along with a ```className``` variable

## Design & implementation

This section describes how Duck is implemented and how its commands are executed.

### Purge feature

**Sequence Diagram**

![image](https://user-images.githubusercontent.com/1620654/227128864-cfc9cac7-bc68-4962-ac45-77e1ae067db0.png)

The ```TaskList#purge()``` command will be implemented following the sequence diagram as shown above.


**Implementation**

The ```TaskList#purge()``` command will be implemented to facilitate the removal of expired ```Task``` items from the ```TaskList```. This will be executed once upon Duck's startup, and can be further executed by inputting the term ```purge``` into the CLI when Duck is running. The following is the new operation to be implemented.

-   ```TaskList#purge()``` - Navigates through the ```TaskList``` array, and checks the date/time of each Task item within. If the date/time of a given ```Task``` has passed, it will be removed from the ```TaskList``` array. The user will be then able to view a total of how many ```Tasks``` were purged, along with what ```Task``` they were.

<br />

**Given below is an example usage scenario for ```purge()```**

**Step 1.** The user launches the application for the first time. The ```TaskList``` will be initialized with the data from a given pre-existing datafile if it exists, and the ```Task``` items will be inserted into the ```TaskList```.![](https://lh5.googleusercontent.com/XcnArNZc5ZqHhxXZ1iRuq0aRxNKkcD_snVgDv-dlpMVYFQFQjyGXdtHWvFGzG-k2UW5SXkjQbthIRtkrol_SYsNgmsmKw1kKEkME0vSQ0tfRfUIWj3jZTTVrerHbESuA-AlTpPEkj8JrKl-mMlu-3yM)

**Step 2.** The ```TaskList#purge()``` command will be executed. The process is illustrated through two steps:

**Identifying all expired ```Task``` items in ```TaskList```.**

![](https://lh3.googleusercontent.com/Kbz7j3EmMGJGrIX1aQ8DCkHG8Y3gaIx3Ohf-zmYvr-S-LrxRqt8P1wlTMWUnlnna2tnR0i72yUJWB72Z2mWwGuwXiD_RnlIpYEk-MY6k9zMaLqTk_VkyHaRwIuzxi1trXHX1ySdZWBqZFDaCZ6K-QrY)

**Removing all expired ```Task``` items in ```TaskList```.**
![](https://lh4.googleusercontent.com/kVOcvIKAF1nlLSHXQsRPDuG6RwiyaqRXf9CYTLrv2WUezegCNx05GIC1KPYME6Eojid1hRbqUIzHUjKWRpYjUG7zCYE8586kLzbIrVdjZVnC5j_1ke1WBdFZSHoar_MuXKBA9eMARxCOgcja5qjP2_A)

The user will see this on his terminal after the ```purge``` command has executed.

```
----------------------------------------------------------------------
Quack! <expiredCount> Tasks have expired! Purge proceeding...
2. [X if <isDone>, " " if !<isDone>] <Deadline_1>
4. [X if <isDone>, " " if !<isDone>] <Event_1>

Purge Completed! Now we are one quack closer to finishing all tasks!
----------------------------------------------------------------------
```


**Step 3.** The ```Duck``` will continue to run as per usual. The user can choose to manually input the keyword purge into the CLI to repeat Step 2 above at any given time.

<br />

### Clear feature

**Implementation**

The ```Storage#clear()``` command was implemented to facilitate the removal of all ```Task``` items from the ```TaskList```. Upon the entry of the keyword ```clear```, the new operation ```Ui#doubleCheck``` will prompt the user for an input of ```Y/N```.

-   If a ```Y``` is inputted, the operation ```Storage#clear()``` executes and removes all ```Task``` items from the ```TaskList```. Additionally, the ```savedata``` file located in ```\data\savedata.txt``` will also be deleted and recreated as a blank slate.

-   If a ```N``` is inputted, the operation ```Storage#clear()``` executes and removes all ```Task``` items from the ```TaskList```. Additionally, the ```savedata``` file located in ```\data\savedata.txt``` will also be deleted and recreated as a blank slate.

<br />

 The following are the new operations implemented.

-   ```Storage#clear()``` - Removes all ```Task``` items from the ```TaskList```. Additionally, the ```savedata``` file located in ```\data\savedata.txt``` will also be deleted and recreated as a blank slate.

-   ```Ui#doubleCheck``` - Prompts the user for further input. If a ```Y``` is inputted, the ```Storage#clear()``` operation is executed. Else if a ```N``` is inputted, the operation is cancelled.

<br />

Given below is an example usage scenario for ```Storage#clear()```

**Step 1.** The user launches the application for the first time. The ```TaskList``` will be initialised with the data from a given pre-existing datafile if it exists, and the ```Task``` items will be inserted into the ```TaskList```.![](https://lh5.googleusercontent.com/XcnArNZc5ZqHhxXZ1iRuq0aRxNKkcD_snVgDv-dlpMVYFQFQjyGXdtHWvFGzG-k2UW5SXkjQbthIRtkrol_SYsNgmsmKw1kKEkME0vSQ0tfRfUIWj3jZTTVrerHbESuA-AlTpPEkj8JrKl-mMlu-3yM)

**Step 2.** The user wants to start afresh. The ```Storage#clear()```  command is then executed. The user will see an output in the following format:

```
THIS IS AN IRREVERSIBLE PROCESS. ARE YOU SURE? Y/N
```

**Step 3.1.** The user decides against clearing the ```TaskList```, and inputs a ```N```. The ```TaskList``` has no changes. ```Duck``` resumes after the output in the following format has been displayed:

```

Process cancelled.

```

**Step 3.2.** The user decides upon clearing the ```TaskList```, and inputs a ```Y```. The ```TaskList``` has been cleared.

![](https://lh4.googleusercontent.com/G4PalSoaVQxExSGKCDefJuO4TlyCJwrCfZDT4BsTrrSHMJ8aQSPsaxGoFJYb9YIO1yo3_3nV0Jar3Haqfac90v7G_yfZdx8-OwxSKEART5zLqMZ73k3YW0ssPbIE3kOhkbeusR3jVMdD6kTSkG7rK4s)

```Duck``` resumes after the output in the following format has been displayed:

```

Got it, all tasks have been cleared.

 ```

### SchoolClass Feature

**Implementation**

The ```SchoolClass``` Class is implemented to facilitate the adding of students' classes to a separate schedule, which is a priority queue. It extends the ```Task``` Class with additional String attributes to store the class name, day of week, start time, and end time. It also overrides the ```toString()``` method to have its own specialised output when being printed, as well as overriding the ```toSaveString()``` method to correctly save its details to the save file. The ```SchoolClass``` Class will also facilitate the implementation of automatically recurring classes, which is a planned feature for milestone v2.0. ```SchoolClass``` Tasks will be added by default as recurring tasks to the task list, and will be automatically added back at their same set timing each week.

The following are the new operations implemented.

-   ```TaskList#addSchoolClass()``` - Adds a SchoolClass Task to the task list.

-   ```Ui#addedTaskMessage()``` - Outputs a message to show that the SchoolClass Task was successfully added to the task list

-   ```Storage#loadSchoolClass()``` - Adds a SchoolClass to the task list without generating a successfully added message, to be used when loading from the save data.

<br />

**Given below is an example usage scenario for  TaskList#addSchoolClass().**

**Step 1.** The user inputs a command following the proper formatting for adding a ```SchoolClass```. The Duck class will call ```Parser#processCommand()```, instantiating a ```Parser``` class, which will then call ```TaskList#addTask()```, instantiating a ```TaskList``` Class. From there, ```TaskList#addSchoolClass()``` is called and a new ```SchoolClass``` Task is instantiated, which calls ```Ui#addedTaskMessage()``` and instantiating a ```Ui``` Class. This ```SchoolClass``` Task will thus be added to the ArrayList<Task> tasks that was instantiated in the ```Duck``` class.

![](https://lh4.googleusercontent.com/u4zVr8TYxFw3rMvnqdwCYlJmq0JxUgEtC_cFmOY7rPqCM9nvzcQL1t-GcTmgbedeVEHi2L6MG6xG3QaJ7XaOPs8nYvHz1Uf4wGK9bMsHDHwxZVNdS2zR79TtHL_Ub2Za0_jm6bUsnY_RQWX6QmFqCl4)

![](https://lh3.googleusercontent.com/owAwOcAeTYadanERD7zj2eVD_SsbxhXUvohhaV962-DfYkgh-fV4wWVv8LnLjPpt9jl3yEkBrVUuiPg7jor-uWSpIhwdze4C3yKMWdRQEYrcR7I6tW4RMIGeyazNhEYUZzTd2BTBqUNpKL-6O2KRXug)
  
<br />

**Given below is an example usage scenario for  Storage#loadSchoolClass.**

**Step 1.** The user launches the application. The task list will be initialised with the data from a given pre-existing datafile if it exists, and the ```SchoolClass``` items will be inserted into the task list if they exist using ```Storage#loadSchoolClass()```.

<br />

### Recurring deadlines and events feature

**Implementation**

The `RecurringDeadline` and `RecurringEvent` classes are implemented to facilitate the adding of deadlines and events that happen every week to the `TaskList`. They extend `Deadline` and `Event` respectively with an additional `DayOfWeek` attribute `day` to indicate which day of the week the task recurs on. They both override the `toString()` and `toSaveString()` methods to return the correct `String` representation for output to the user and storage. `RecurringDeadline` and `RecurringEvent` also have a public method `getDay()` to return the private attribute `day`.

**The following new operations are implemented. **

-   `TaskList#addRecurrDeadline()` -- adds a `RecurringDeadline` object to the task list

-   `Storage#loadRecurrDeadline()` -- loads a `RecurringDeadline` from the saved data file to the task list

-   `TaskList#addRecurrEvent()` -- adds a `RecurringEvent` object to the task list

-   `Storage#loadRecurrEvent()` -- loads a `RecurringEvent` from the saved data file to the task list

Below is a class diagram for the implementation
 
 <img width="859" alt="image" src="https://user-images.githubusercontent.com/88618401/228712251-dc8a6eb0-7483-4d01-b2f0-99f9c105aa9a.png">

 
 <br />
 
## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
