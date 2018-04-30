import java.util.Queue;
import java.util.concurrent.Semaphore;

import javax.swing.plaf.synth.SynthColorChooserUI;

public class Consumer implements Runnable {
	Queue<Integer> queue;
	Object prodCond;
	Object consCond;

	public Consumer(Queue<Integer> queue, Object prodCond, Object consCond) {
		super();
		this.queue = queue;
		this.prodCond = prodCond;
		this.consCond = consCond;
	}

	public Consumer(Queue<Integer> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		int number = 0;
		while (true) {
			synchronized (queue) {
				while (queue.size() == 0) {

					try {
						synchronized (consCond) {
							consCond.wait();
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				number = queue.remove();
				System.out.println("Consumed :" + number);
				synchronized (prodCond) {
					prodCond.notify();
				}
			}

		}
	}
}
