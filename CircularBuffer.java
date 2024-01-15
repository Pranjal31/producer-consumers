
import java.util.concurrent.Semaphore;
public class CircularBuffer {
    public int[] buffer; // Array to store the buffer elements
    public int size;  // number of elements in the circular buffer at a given time
    public Semaphore empty; // Semaphore to track empty slots in the buffer
    public Semaphore full; // Semaphore to track filled slots in the buffer
    public Semaphore mutex; // Semaphore for mutual exclusion (to protect shared circular buffer)
    public int producerIndex; // Index where the producer will add the produced item to
    public int consumerIndex; //  Index where the consumer will consume the item from
    public volatile boolean producerStopped;  // Flag to indicate if the producer has stopped


    public CircularBuffer(int capacity) {
        buffer = new int[capacity];
        empty = new Semaphore(capacity);
        full = new Semaphore(0);
        mutex = new Semaphore(1);
        producerIndex = 0;
        consumerIndex = 0;
        producerStopped = false;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void stopProducer() {
        producerStopped = true;
    }
}