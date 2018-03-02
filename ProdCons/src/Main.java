import java.util.LinkedList;
import java.util.Queue;

public class Main {
	public Main() {
		Queue queue = new LinkedList<Integer>();
		Thread prod = new Thread(new Producer(queue, 10));
		Thread cons = new Thread(new Consumer(queue));
		prod.start();
		cons.start();
		try {
			prod.join();
			cons.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
