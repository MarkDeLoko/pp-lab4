package lab4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
 
public class main {
 
	public final static int THREAD_POOL_SIZE = 5;
	
	public static Map<String, Integer> HashMapObject = null;
	public static Map<String, Integer> HashTableObject = null;
	public static Map<String, Integer> SynchronizedMapObject = null;
	public static Map<String, Integer> ConcurrentHashMapObject = null;
 
	public static void main(String[] args) throws InterruptedException {
		
		HashMapObject = new HashMap<String, Integer>();
		Test(HashMapObject);
		
		HashTableObject = new Hashtable<String, Integer>();
		Test(HashTableObject);
 
		SynchronizedMapObject = Collections.synchronizedMap(new HashMap<String, Integer>());
		Test(SynchronizedMapObject);
 
		ConcurrentHashMapObject = new ConcurrentHashMap<String, Integer>();
		Test(ConcurrentHashMapObject);
 
	}
 
	public static void Test(final Map<String, Integer> Threads) throws InterruptedException {
 
		System.out.println("Test started for: " + Threads.getClass());
		long averageTime = 0;
		for (int i = 0; i < 5; i++) {
 
			long startTime = System.nanoTime();
			ExecutorService ExServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
 
			for (int j = 0; j < THREAD_POOL_SIZE; j++) {
				ExServer.execute(new Runnable() {
					@SuppressWarnings("unused")
					@Override
					public void run() {
 
						for (int i = 0; i < 5000; i++) {
							Integer RandomNumber = (int) Math.ceil(Math.random() * 5);

							Integer Value = Threads.get(String.valueOf(RandomNumber));
 
					
							Threads.put(String.valueOf(RandomNumber), RandomNumber);
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Threads.remove(String.valueOf(RandomNumber));
						}
					}
				});
			}
 
			ExServer.shutdown();
			ExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
 
			long entTime = System.nanoTime();
			long totalTime = (entTime - startTime) / 1000000L;
			averageTime += totalTime;
			System.out.println("Test finished in "+ totalTime + " ms");
		}
		System.out.println("For " + Threads.getClass() + " the average time is " + averageTime / 5 + " ms\n");
	}
}
