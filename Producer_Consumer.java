import java.util.Scanner;
public class Producer_Consumer {
    public static void main(String[] args) throws InterruptedException {
        // Input from the user to configure the system
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the buffer capacity: ");
        int bufferCapacity = scanner.nextInt();
        System.out.print("Enter the number of consumer threads: ");
        int numConsumers = scanner.nextInt();
        System.out.print("Enter the maximum number of integers to produce (starting from 0): ");
        int maxInt = scanner.nextInt();
        scanner.close();

        // Input sanity check
        if((bufferCapacity < 1) || (numConsumers < 1) || (maxInt < 1) ){
            System.err.println("Invalid input: Buffer_Capacity, Number_of_Consumers, and Maximum_Number_of_Integers must be positive!");
            return;
        }
        // Create a CircularBuffer with the specified capacity
        CircularBuffer cb = new CircularBuffer(bufferCapacity);
        // Create and start the producer thread
        Thread producerThread = new Thread(new Producer(cb, maxInt));
        producerThread.start();

        // Consumer should start consuming only after the buffer has been filled by producer
        // or if maximum number of integers have been produced
        while((cb.size != bufferCapacity) && (cb.size != maxInt)) {
            Thread.sleep(100);
        }
        System.out.println("Producer has " + (cb.size == bufferCapacity ? "filled the buffer" : "filled the maximum integers") + ", now starting consumers");

        // Create and start the producer thread
        Thread[] consumerThreads = new Thread[numConsumers];
        for (int i = 0; i < numConsumers; i++) {
            consumerThreads[i] = new Thread(new Consumer(cb, i));
        }
        for (Thread consumerThread : consumerThreads) {
            consumerThread.start();
        }
        // Wait for the producer and consumers to finish
        try {
            producerThread.join();
            for (Thread consumerThread : consumerThreads) {
                consumerThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Producer and consumers have finished.");
    }
}