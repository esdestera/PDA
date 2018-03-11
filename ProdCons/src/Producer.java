import java.util.Queue;
import java.util.Random;

public class Producer implements Runnable {

	Queue<Integer> queue;
	int n;
	Random rand = new Random();

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
			synchronized (queue) {
				while (queue.size() == n) {
					System.out.println("The queue is full");
				}

				queue.add(number);
				System.out.println("Produced " + number);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
