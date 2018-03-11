import java.util.Queue;

public class Consumer implements Runnable {
	Queue<Integer> queue;

	public Consumer(Queue<Integer> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		while (true) {
			if (!queue.isEmpty()) {
				synchronized (queue) {
					int number = queue.remove();
					System.out.println("Consumed " + number);
				}
			} else {
				System.out.println("There are not elements in the queue");
			}
		}
	}
}

