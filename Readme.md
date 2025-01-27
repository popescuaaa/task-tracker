# Task tracker cli

## V1 

### Things to cover - v1

- [x] Setup 
- [x] Define behaviour
- [x] Entities
- [x] Fake a DB behaviour
- [x] Libs + implementation
- [x] tests (basic suite)

## Desired behaviour

A task is defined by name, description and deadline. The later two are optional. 

Internally the tasks are saved in a database with some uniq ids. The default description is empty.

The default deadline is NULL. If the task has a deadline, which should be a float, ( i.e 2 => 2 days, 0.25 => 3 hours) 
then, after the deadline the task will be moved from to a finished state and can be displayed by the tracker.


The main commands that the tracker should support are:

1. tracker add
    ````bash 
    tracker> tracker add task <task_name> <?task_description> <?deadline> 
    ````
2. tracker delete: the task will be moved to a delete state ( not finish )
     ````bash 
    tracker> tracker delete <task_name>
    ````
3. tracker list: list all active tasks
     ````bash 
    tracker> tracker list
    ````
4. tracker check: list all finished tasks ( no one cares about deleted ones) 
     ````bash 
    tracker> tracker check 
    ````


## V2

### Updates

- [ ] Integrate SQLite
   - [ ] Add the new task class
   - [ ] Replicate commands from the V1
- [ ] Create the dump possibility to a Markdown file
- [ ] Make the app runnable in a docker like environment ~ bundle 
