import java.util.Queue;

public class Consumer implements Runnable {
	Queue<Integer> queue;

	public Consumer(Queue<Integer> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		int number = 0;
		while (true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					System.out.println("There queue is empty");
				}

				number = queue.remove();
				System.out.println("Consumed " + number);
			}
		}
	}
}
