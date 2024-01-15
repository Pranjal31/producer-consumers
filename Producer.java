public class Producer implements Runnable {
    private CircularBuffer cb; // Reference to the shared CircularBuffer
    private int maxInt; // Maximum number of integer values to produce

    public Producer(CircularBuffer buffer, int maxInt) {  // Constructor to initialize the Producer
        this.cb = buffer;
        this.maxInt = maxInt;
    }
    // Method to produce an item and add it to the CircularBuffer:
    public void produce(int item) throws InterruptedException {
        cb.empty.acquire();
        cb.mutex.acquire();
        // Add the item to the buffer and update the buffer size
        cb.buffer[cb.producerIndex] = item;
        cb.size++;
        System.out.println("Producer produced: " + item + ", added to idx = " + cb.producerIndex + ", buffer size = " + cb.size);// Display production details
        cb.producerIndex = (cb.producerIndex + 1) % cb.buffer.length;
        cb.mutex.release();
        cb.full.release();
    }
    @Override
    public void run() {
        for (int i = 0; i < maxInt; i++) {
            try {
                produce(i);
            } catch (InterruptedException e) {
                // Handle interrupted exception by interrupting the current thread
                Thread.currentThread().interrupt();
            }
        }
        // Signal that the producer has stopped producing
        cb.stopProducer();
    }
}