package test;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest {
	
	static final int poolSize = 1000;
	static final int numElements = 1000;
	
	public static void main(String[] args) throws Exception {
		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		int index = 0;
		for (int i = 0; i < poolSize; i++) {
			pool.execute(new TestRunnable(queue, index));
			index = index + numElements;
		}
		pool.shutdown();
		pool.awaitTermination(1L, TimeUnit.MINUTES);
		
		TreeSet<Integer> set = new TreeSet<Integer>(queue);
//		for (Integer integer : set) {
//			System.out.println(integer);
//		}
		System.out.println((numElements*poolSize)==set.size());
	}
	
	static class TestRunnable implements Runnable{
		
		
		ConcurrentLinkedQueue<Integer> q;
		int index;
		
		public TestRunnable(ConcurrentLinkedQueue<Integer> queue, int idx) {
			q = queue;
			index = idx;
		}
		
		@Override
		public void run() {
			int end = index+numElements;
			for (int i = index; i < end; i++) {
				q.offer(i);
				
			}
			
		}
	}

}


