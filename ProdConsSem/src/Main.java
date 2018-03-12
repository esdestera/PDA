import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		int n = 10;
		Queue queue = new LinkedList<Integer>();
		Semaphore semFree = new Semaphore(n);
		Semaphore semFull = new Semaphore(0);
		Thread prod = new Thread(new Producer(queue, n, semFree, semFull) );
		Thread cons = new Thread(new Consumer(queue, semFree, semFull));
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
