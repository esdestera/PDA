import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
	Queue<Integer> queue;
	Semaphore semFree;
	Semaphore semFull;

	public Consumer(Queue<Integer> queue, Semaphore semFree, Semaphore semFull) {
		super();
		this.queue = queue;
		this.semFree = semFree;
		this.semFull = semFull;
	}

	public Consumer(Queue<Integer> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		int number = 0;
		while (true) {
			try {
				semFull.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (queue) {
				while (queue.isEmpty()) {
					System.out.println("There queue is empty");
				}

				number = queue.remove();
				semFree.release();
			}
			
			System.out.println("Consumed " + number);

		}
	}
}
