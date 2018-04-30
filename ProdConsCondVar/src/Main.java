import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		int n = 10;
		Queue queue = new LinkedList<Integer>();
		Object prodCond = new Object();
		Object consCond = new Object();
		Thread prod = new Thread(new Producer(queue, n, prodCond, consCond));
		Thread cons = new Thread(new Consumer(queue, prodCond, consCond));
		prod.start();
		cons.start();

		prod.join();
		cons.join();

	}
}
