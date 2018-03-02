import java.util.Queue;
import java.util.Random;

public class Producer implements Runnable {

	Queue<Integer> queue;
	int n;
	Random rand = new Random();
	
	public Producer(Queue<Integer> queue, int n) {
		super();
		this.queue = queue;
		this.n= n;
	}



	@Override
	public void run() {
		if(queue.size() < n) {
			synchronized (queue) {
				int number = rand.nextInt(n);
				queue.add(number);
				System.out.println("Produced " + number);
			}
		}
		else {
			System.out.println("The queue is full, wait to sunsume first");
		}
	}

}
