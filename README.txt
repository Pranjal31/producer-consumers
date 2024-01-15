Introduction
============
A multithreaded solution for the famous producer-consumers concurrency problem. Used semaphores to guarantee accurate concurrent production and consumption within a shared circular buffer

Code Structure
==============

CircularBuffer.java
---------------------
Defines the CircularBuffer which shared between consumer and producer

- Buffer Array: An array to store buffer elements.
- Semaphores: Three semaphores (empty, full, and mutex) to manage buffer emptiness, fullness, and mutual exclusion, respectively.
- Producer and Consumer Index: Indices to keep track of the current position for both producer and consumer.
- Producer Stopped Flag: A volatile boolean flag to indicate whether the producer has stopped.
- Size: A member field to track the current size of the buffer (number of elements)

Consumer.java
---------------
Defines the Consumer class, implementing the behavior of consumer threads. Key functionalities include:

- CircularBuffer Reference: Holds a reference to the shared CircularBuffer instance.
- Consumer ID: An identifier for a given consumer.
- Consume Method: Method to consume an item from the circular buffer, handling synchronization and signaling the producer's stop condition.


Producer.java
---------------
Defines the Producer class, implementing the behavior of the producer thread. Key functionalities include:

- CircularBuffer Reference: Holds a reference to the shared CircularBuffer instance.
- Maximum Integer Value: The maximum number of integer values to produce.
- Produce Method: Method to produce an item and add it to the circular buffer, handling synchronization using semaphores.
- In summary, these classes work together to simulate a producer-consumer scenario with shared resources and synchronization mechanisms to ensure proper coordination between threads. The circular buffer design       facilitates safe data exchange between the producer and consumers.



Producer_Consumer.java
----------------------
- This is the driver class used for producer consumer implementation.
- User Input: Takes user input to configure the system, including buffer capacity, the number of consumer threads, and the maximum number of integers to produce.
- CircularBuffer Initialization: Creates an instance of the CircularBuffer class, initializing it with the specified buffer capacity.
- Producer Thread Initialization: Creates and starts a producer thread using the Producer class, passing the circular buffer instance and the maximum number of integers to produce.
- Consumer Thread Initialization: Waits for the buffer to be filled by the producer or until the maximum number of integers is produced, then creates and starts multiple consumer threads using the Consumer class.
- Thread Joining: Waits for the producer and consumer threads to finish execution using the join() method.


How to Run
==========

- Open Terminal
- Run following Command: java -jar HMW02.JAR
- Provide the necessary inputs: buffer capacity, numbers of consumers and maximum number of integers to produce.
 


Results
=======

Case 1: Buffer capacity less than maximum number of integers to produce
-------------------------------------------------------------------------
The buffer operates with a specific capacity, and the producer initially fills it with integers up to its capacity. The producer then pauses, allowing consumers to start and begin consuming items from the buffer. Once both the producer and consumers are active, they operate simultaneously. The producer continues to generate integers until reaching the maximum value of integer produce at which point it stops. Meanwhile, consumers persist in consuming items until the producer halts and the buffer is empty.

Sample output:

Enter the buffer capacity: 5
Enter the number of consumer threads: 2
Enter the maximum number of integers to produce (starting from 0): 15
Producer produced: 0, added to idx = 0, buffer size = 1
Producer produced: 1, added to idx = 1, buffer size = 2
Producer produced: 2, added to idx = 2, buffer size = 3
Producer produced: 3, added to idx = 3, buffer size = 4
Producer produced: 4, added to idx = 4, buffer size = 5
Producer has filled the buffer, now starting consumers
Consumer0 took: 0, removed from  idx = 0, size = 4
Consumer1 took: 1, removed from  idx = 1, size = 3
Producer produced: 5, added to idx = 0, buffer size = 4
Consumer0 took: 2, removed from  idx = 2, size = 3
Consumer1 took: 3, removed from  idx = 3, size = 2
Producer produced: 6, added to idx = 1, buffer size = 3
Consumer0 took: 4, removed from  idx = 4, size = 2
Consumer0 took: 5, removed from  idx = 0, size = 1
Consumer0 took: 6, removed from  idx = 1, size = 0
Producer produced: 7, added to idx = 2, buffer size = 1
Producer produced: 8, added to idx = 3, buffer size = 2
Producer produced: 9, added to idx = 4, buffer size = 3
Producer produced: 10, added to idx = 0, buffer size = 4
Producer produced: 11, added to idx = 1, buffer size = 5
Consumer1 took: 7, removed from  idx = 2, size = 4
Consumer0 took: 8, removed from  idx = 3, size = 3
Consumer0 took: 9, removed from  idx = 4, size = 2
Consumer0 took: 10, removed from  idx = 0, size = 1
Consumer0 took: 11, removed from  idx = 1, size = 0
Producer produced: 12, added to idx = 2, buffer size = 1
Producer produced: 13, added to idx = 3, buffer size = 2
Producer produced: 14, added to idx = 4, buffer size = 3
Consumer1 took: 12, removed from  idx = 2, size = 2
Consumer1 took: 13, removed from  idx = 3, size = 1
Consumer1 took: 14, removed from  idx = 4, size = 0
Consumer1 stopped
Consumer0 stopped
Producer and consumers have finished.

Process finished with exit code 0

Case 2: Buffer capacity is more than maximum integers to be produced
---------------------------------------------------------------------

If the maximum integer value is less than the buffer size, the producer will generate integers up to Maximum integer value and then stop. The consumer will consume these items, and once the buffer is empty, the program will conclude.

Sample Output

Enter the buffer capacity: 10
Enter the number of consumer threads: 2
Enter the maximum number of integers to produce (starting from 0): 3
Producer produced: 0, added to idx = 0, buffer size = 1
Producer produced: 1, added to idx = 1, buffer size = 2
Producer produced: 2, added to idx = 2, buffer size = 3
Producer has filled the maximum integers, now starting consumers
Consumer0 took: 0, removed from  idx = 0, size = 2
Consumer0 took: 1, removed from  idx = 1, size = 1
Consumer0 took: 2, removed from  idx = 2, size = 0
Consumer0 stopped
Consumer1 stopped
Producer and consumers have finished.

Process finished with exit code 0

Case 3: When the input values are zero or negative  
-----------------------------------------------------
Program will not execute and throw an error.

Enter the buffer capacity: 5
Enter the number of consumer threads: 0
Enter the maximum number of integers to produce (starting from 0): 10
Invalid input: Buffer_Capacity, Number_of_Consumers, and Maximum_Number_of_Integers must be positive!

Process finished with exit code 0

In What Order Were the Integers Printed
-----------------------------------------
The order in which integers are printed Is same the order in which integers are provided to the buffer. This is because we have defined producer index and consumer index. ProducerIndex and consumerIndex: Index variables to keep track of where the producer and consumer should insert or remove elements from the buffer 


Discussion
==========

1. How Many of Each Integer Should You See Printed?
Each number is produced and consumed exactly once. The program prints production and consumption details. The sequence comprises integers greater than zero and less than the MAX INT value provided by the user. In this scenario, no integer is produced or consumed more than once; every number is unique in both production and consumption.

2. In What Order Should You Expect to See Them Printed? Why?
The integers(items) will be produced in a sequence of index and will also consumed in a sequence as this is defined in the circular buffer.The buffer operates with a specific capacity, and the producer initially fills it with integers up to its capacity. The producer then pauses, allowing consumers to start and begin consuming items from the buffer. Once both the producer and consumers are active, they operate simultaneously. The producer continues to generate integers until reaching the value of maximum numbers of integers (Max INT), at which point it stops. Meanwhile, consumers persist in consuming items until the producer halts and the buffer is empty.
However, the sequence of consumers consuming items can vary for same input when program is run multiple times. We can see the below outputs for same inputs:

Output 1:
Enter the buffer capacity: 3
Enter the number of consumer threads: 2
Enter the maximum number of integers to produce (starting from 0): 5
Producer produced: 0, added to idx = 0, buffer size = 1
Producer produced: 1, added to idx = 1, buffer size = 2
Producer produced: 2, added to idx = 2, buffer size = 3
Producer has filled the buffer, now starting consumers
Consumer0 took: 0, removed from  idx = 0, size = 2
Consumer0 took: 1, removed from  idx = 1, size = 1
Consumer0 took: 2, removed from  idx = 2, size = 0
Producer produced: 3, added to idx = 0, buffer size = 1
Producer produced: 4, added to idx = 1, buffer size = 2
Consumer0 took: 3, removed from  idx = 0, size = 1
Consumer0 took: 4, removed from  idx = 1, size = 0
Consumer1 stopped
Consumer0 stopped
Producer and consumers have finished.

Process finished with exit code 0

Output2:
Enter the buffer capacity: 3
Enter the number of consumer threads: 2
Enter the maximum number of integers to produce (starting from 0): 5
Producer produced: 0, added to idx = 0, buffer size = 1
Producer produced: 1, added to idx = 1, buffer size = 2
Producer produced: 2, added to idx = 2, buffer size = 3
Producer has filled the buffer, now starting consumers
Consumer0 took: 0, removed from  idx = 0, size = 2
Consumer0 took: 1, removed from  idx = 1, size = 1
Consumer0 took: 2, removed from  idx = 2, size = 0
Producer produced: 3, added to idx = 0, buffer size = 1
Producer produced: 4, added to idx = 1, buffer size = 2
Consumer1 took: 3, removed from  idx = 0, size = 1
Consumer1 took: 4, removed from  idx = 1, size = 0
Consumer0 stopped
Consumer1 stopped
Producer and consumers have finished.


The variability in the order of producer and consumer actions arises due to the asynchronous and concurrent nature of the producer-consumer problem. 
In a multi-threaded environment, the exact scheduling of threads is managed by the operating system, and there's no guaranteed order in which threads will be executed(except for the conditions buffer is empty or full).


3. Did Your Results Differ from Your Answers in (1) and (2)? Why or Why Not?
The results were aligned with the expectations. The circular buffer design ensures a consistent order, and each integer was produced and consumed as expected. Results were aligned with the expectations. After the initial step of filling the producer, there is no set pattern for the order in which consumers consume or producers produce. The same input can generate different output in terms of production and consumption order. However, the order of items (integers removed) will be the same in all cases unless there is some ambiguity.


------------------------------------------------------------------------------------------------------------------



