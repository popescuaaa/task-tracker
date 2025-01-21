# Task tracker cli

### Things to cover

- [x] Setup 
- [x] Define behaviour
- [ ] Entities
- [ ] Libs + implementation
- [ ] Bundle

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


## Further improvements

- [ ] Fake a DB behaviour
- [ ] Integrate SQLite