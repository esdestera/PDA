import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

	Queue<Integer> queue;
	int n;
	Random rand = new Random();
	Semaphore semFree;
	Semaphore semFull;

	public Producer(Queue<Integer> queue, int n, Semaphore semFree, Semaphore semFull) {
		super();
		this.queue = queue;
		this.n = n;
		this.semFree = semFree;
		this.semFull = semFull;
	}

	public Producer(Queue<Integer> queue, int n) {
		super();
		this.queue = queue;
		this.n = n;
	}

	@Override
	public void run() {
		int number = 0;
		while (true) {
			number = rand.nextInt(n);
			try {
				semFree.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			synchronized (queue) {
				while (queue.size() == n) {
					System.out.println("The queue is full");
				}

				queue.add(number);
				semFull.release();
			}
			
			System.out.println("Produced " + number);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}