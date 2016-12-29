## Concurrency & Parallelism with a simplest real-life example ##

Inspired from: http://ayedo.github.io/netty/2013/06/19/what-is-netty.html

I recently had a good conversation with a group of developers on modern concurrency.
The following keywords were frequently used (often in the interchangeable context).

*concurrency*, *parallelism*, *non-blocking*, *event-driven*, *asynchronous*, *effective cpu utilization*, *blocking-threads*

I realised that it is easy to confuse things especially in the area of concurrency which is generally considered to be a hard
concept to grasp.

I'm taking a real-world (simple) example of a Chef and apply the concurrency principles and then map different terms to the actual concurrency concepts.

Hopefully, by the end of this exercise, things will be more clearer. I'll also try to make it clear that concurrency and parallelism are not the same.

Now Let's move on to the use case.

## Story of you (The Chef) ##
Imagine yourself to be a Chef. You're a rookie now and you're going to evolve as a Master. Don't worry.  :-)
You're given a task of preparing Pasta.
You have the necessary ingredients available with you.
Last but not the least, you have 4 slots in the stove available for you to use.

## Preparatory Tasks involved in preparing Pasta ##
* Boil Pasta (Takes 5 minutes)
* Boil Cream (Takes 2 minutes)
* Melt Butter (Takes 1 minute)
* Boil Sauce (Takes 3 minutes)

Please don't focus on other tasks such as stirring, mixing it together etc. Of course they are important to make an eatable pasta, 
but I've omitted that to stay focused on the concepts.

As you see above, all the 4 tasks require you to use the stove. There are 4 slots available in the stove.

*Pause here for a moment and think what would you do?*

I'm sure most of you would get it right. Which means you've already nailed the concurrency concepts. 

Let's not jump to the solution now. Instead, lets try a few solutions to this problem and reason them out.

## Solution 1 - 'The inefficient chef' ##
This is the solution of an inefficient chef who can only think of one task at a time. This chef can't even come out of a task until that is finished.
Here is the approach.
* Boil Pasta and wait for it to complete - 5 minutes.
* Boil Cream and wait for it to complete - 2 minutes.
* Boil Butter and wait for it to complete - 1 minute.
* Boil Sauce and wait for it to complete - 3 minute.

The approach is highly sequential and *blocking*. The chef keeps himself blocked from moving on to the next task until his current task is finished.
Look at the total latency in this approach: 11 minutes.
Is the chef utilizing the Stove efficiently here? Absolutely not. At any point in time, the three slots in the stove are under utilized.

| Chef Story                                                                                         | Actual Concurrency Concept                                                                                            |
|----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| You're the only chef to perform all the 4 tasks                                                    | There is only one thread to execute all 4 tasks                                                                       |
| There are 4 slots in the stove                                                                     | There are 4 cores available in the computer                                                                           |
| The chef is completely blocked before moving on to the other task                                  | The thread is completely blocked before executing the other task                                                      |
| The slots in the stove are under-utilized because the chef is busy waiting and doing nothing else. | The cores in the CPU are under-utilized because the thread is blocked and in the waiting state, doing nothing at all. |


## Solution 2 - 'Group of chefs' ##
You the chef need help to improve efficiency. You call your 3 friends to help you out.
Now you are 4 chefs in total and each one of you decide to pick up a task and do it at the same time.

* Boil Pasta and wait for it to complete - 5 minutes. - You
* Boil Cream and wait for it to complete - 2 minutes. - Chef 2
* Boil Butter and wait for it to complete - 1 minute. - Chef 3
* Boil Sauce and wait for it to complete - 3 minute.  - Chef 4

Definitely looks a better idea than Solution 1. Each Chef work independently on their respective tasks and get it done.
The overall task completion time is 5 minutes (That is the most time consuming task). You have cut down the latency my more than half.
We're utilizing the stove effectively. But as an extra cost, we've acquired more chefs. Although they are your friends, you will be paying them
a few bucks to recognize their time and help. You've increased the cost.


| Chef Story                                                                                         | Actual Concurrency Concept                                                                                            |
|----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| There are 4 chefs to perform 4 tasks                                                   | There are 4 threads to execute 4 tasks. |
| There are 4 slots in the stove                                                                     | There are 4 cores available in the computer                                                                           |
| The chefs perform the tasks in parallel and use the stove at the same time (in their respective slots)                                  | Each thread executing a particular task is run by a core in the CPU in parallel.                                                      |
| The slots in the stove are best-utilized because none of the slots are idle at any given point | The cores in the CPU are best-utilized because they are busy executing the thread at any given point. |
| You have increased the cost of getting more chefs to help | We have increased more threads. More threads need more memory. Extremely large number of threads will cause the CPU to context switch between the threads which is again an expensive operation for CPUs to do.|

## Solution 3 - 'Multitasking Chef' ##
This is a more natural solution to us humans. Almost all of you would have thought about this solution in the first attempt.

We the humans are good in multi-tasking. 
But we are bad in doing multi-processing (ie doing 2 things simultaneously. For example: Writing 2 different texts in 2 hands at the same time).

So this is a more efficient implementation which employs multi tasking with the following steps.

* Boil Pasta and leave it on the stove - Move on to the next task.
* Boil Cream and leave it on the stove - Move on to the next task.
* Boil Butter and leave it on the stove - Move on to the next task.
* Boil Sauce and leave it on the stove - Do something useful than staring at the stove.
* When Butter finished boiling, take it off the stove.
* When Cream finished boiling, take it off the stove.
* When Sauce finished boiling, take it off the stove.
* When Pasta finished boiling, take it off the stove.


This is a perfect example of the tasks performed concurrently. You are not unnecessarily waiting for any task to complete.
You're not blocked. You move on to the next task. You efficiently use your time and the stove. You are not increasing the cost of
hiring more chefs to help you out.


| Chef Story                                                                                         | Actual Concurrency Concept                                                                                            |
|----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| There is only one chef to perform 4 tasks                                                  | There is only 1 thread to perform 4 tasks |
| There are 4 slots in the stove                                                                     | There are 4 cores available in the computer                                                                           |
| The chef performs every task and move on to the next one without being blocked                                  | The thread executes the task asynchronously and is never blocked.                                                    |
| Once every task finishes, the chef takes it off the stove | Once the task finished execution, the thread is notified with a callback to then react. |
| There is only one chef. But the efficiency is greatly increased by multi-tasking. | There is only one thread. So less memory/resources. But by virtue of making it non-blocking, we have made it more efficient by effectively using all the CPU cores and making the tasks execute in parallel.|

## Some code pointers now ##
* Solution 1 - BlockingTaskChef.java
* Solution 2 - GroupOfChefs.java
* Solution 3 - MultiTaskingChef.java
* Main.java - The starting point to run the examples. You should be able to understand from the output.

## Parallelism is like Nirvana ##
Parallelism is the ultimate destination where we want our tasks to be ending up. But to achieve parallelism, we have to go through
 various stepping stones. The main philosophy is use less threads as much as possible. Don't block them. And use them efficiently by making them concurrently
 executing the tasks.
 
 In my opinion, the below describes the stepping stone to achieve better scalability through parallelism.
 
### Event Driven Pattern ---> Asynchrony ---> Non Blocking ---> Better Concurrency ---> Parallelism. ###

The above should be read as
* Event Driven Pattern helps to achieve Asynchrony.
* Asynchrony helps to achieve Non Blocking of threads.
* Non Blocking helps to achieve better concurrency.
* Better concurrency helps to achieve parallelism.

Remember, parallelism is scarce when compared to concurrency. If your computer has 8 cores, then you can get only 8 tasks done in parallel 
(exact instance of time).