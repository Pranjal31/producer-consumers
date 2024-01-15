
public class Consumer implements Runnable {
    private CircularBuffer cb; // Reference to the shared CircularBuffer
    private int consumerId; // Consumer identifier

    public Consumer(CircularBuffer buffer, int consumerId) {
        this.cb = buffer;
        this.consumerId = consumerId;
    }
    // Method to consume an item from the CircularBuffer:

    public int consume() throws InterruptedException {
        cb.mutex.acquire();
        if (cb.producerStopped && cb.isEmpty()) {
            cb.mutex.release();
            return -1; // Signal to consumers that the producer has stopped
        }
        // Buffer is not empty
        if (!cb.isEmpty()) {
            cb.full.acquire();
            int item = cb.buffer[cb.consumerIndex];
            cb.size--;
            // Display consumption details
            System.out.println("Consumer" + consumerId + " took: " + item + ", removed from  idx = " + cb.consumerIndex + ", size = " + cb.size);
            cb.consumerIndex = (cb.consumerIndex + 1) % cb.buffer.length;
            cb.mutex.release();
            cb.empty.release();
            return 0;
        }
        // If buffer is empty and producer hasn't stopped, sleep and retry
        cb.mutex.release();
        Thread.sleep(50);
        return 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
               if  (consume() == -1) {
                   // stop this consumer
                   break;
               }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Consumer" + consumerId + " stopped");
    }
}