import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

	Queue<Integer> queue;
	int n;
	Random rand = new Random();
	Object prodCond;
	Object consCond;

	public Producer(Queue<Integer> queue, int n, Object prodCond, Object consCond) {
		super();
		this.queue = queue;
		this.n = n;
		this.prodCond = prodCond;
		this.consCond = consCond;
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
			synchronized (queue) {
				while (queue.size() == n) {
					try {
						synchronized (prodCond) {
							prodCond.wait();
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				queue.add(number);
				System.out.println("Produced " + number);
				synchronized (prodCond) {
					consCond.notify();
				}
			}
			
		}
	}
}