package controler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.apache.log4j.Logger;

import monkey.Direction;
import monkey.Monkey;

public class MonkeyCreator extends Thread{
	
	private Queue<Monkey> eastQueue;
	private Queue<Monkey> westQueue;
	
	private static final Random RANDOM = new Random();
	
	private static final int MAX_TIME = 8;
	static Logger logger = Logger.getLogger(MonkeyCreator.class);
	
	public MonkeyCreator() {
		this.eastQueue = new LinkedList<Monkey>();
		this.westQueue = new LinkedList<Monkey>();
	}
	
	public static int randomNumber() {
	    return RANDOM.nextInt(MAX_TIME);
	}
	
	@Override
    public void run() {
		while (true) {
			try {
				int time = randomNumber();
				sleep(time*1000);
				Monkey monkey = new Monkey();
				logger.info("NEW " + monkey.getDirection().toString() + " MONKEY CREATED");
				if (monkey.getDirection() == Direction.EASTWARD) {
					this.eastQueue.add(monkey);
				}	
				else {
					this.westQueue.add(monkey);
				}
			} catch (InterruptedException ex) {
				logger.info(ex);
	        }
		}
		 
    }

	public Queue<Monkey> getEastQueue() {
		return eastQueue;
	}

	public void setEastQueue(Queue<Monkey> eastQueue) {
		this.eastQueue = eastQueue;
	}

	public Queue<Monkey> getWestQueue() {
		return westQueue;
	}
	
	public void setWestQueue(Queue<Monkey> westQueue) {
		this.westQueue = westQueue;
	}
	
	public Monkey pollWestMonkey() {
		return this.westQueue.poll();
	}
	
	public Monkey pollEastMonkey() {
		return this.eastQueue.poll();
	}
	
	public Boolean isEmptyWest() {
		return this.westQueue.isEmpty();
	}
	
	public Boolean isEmptyEast() {
		return this.eastQueue.isEmpty();
	}

	public static Random getRandom() {
		return RANDOM;
	}

	public static int getMaxTime() {
		return MAX_TIME;
	}
	
}
