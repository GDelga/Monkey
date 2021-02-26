package controler;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import monkey.Direction;
import monkey.Monkey;

public class Controller extends Thread{
	
	private static final MonkeyCreator monkeyCreator = new MonkeyCreator();
	private Queue<Monkey> crossingMonkeys;
	private Direction direction;
	static Logger logger = Logger.getLogger(Controller.class);
	
	public Controller() {
		this.direction = Direction.randomDirection(); //Random direction by default
		this.crossingMonkeys = new LinkedList<Monkey>();
	}
	
	
	public void controlPassage() {
		monkeyCreator.start();
		while(true) {
			if (this.direction == Direction.EASTWARD && !monkeyCreator.isEmptyEast() && monkeyCreator.isEmptyWest()) {
				this.executeEastward();
				this.hasMonkeyCrossed();
			}
			else if (this.direction == Direction.WESTWARD && !monkeyCreator.isEmptyWest() && monkeyCreator.isEmptyEast()) {
				this.executeWestward();
				this.hasMonkeyCrossed();
			}
			else if (this.direction == Direction.EASTWARD && !monkeyCreator.isEmptyWest()) {
				this.waitUntilRopeIsFree();
				this.direction = Direction.WESTWARD;
				this.executeWestward();
			}
			else if (this.direction == Direction.WESTWARD && !monkeyCreator.isEmptyEast()) {
				this.waitUntilRopeIsFree();
				this.direction = Direction.EASTWARD;
				this.executeEastward();
			}
		}
		
	}
	
	private void executeEastward() {
		try {
			Monkey monkey = monkeyCreator.pollEastMonkey();
			crossingMonkeys.add(monkey);
			logger.info("EASTWARD MONKEY IS GETTING THE ROPE");
			Thread.sleep(1000);
			monkey.start();
			logger.info("NEXT EASTWARD MONKEY CAN CROSS");
		} catch (InterruptedException ex) {
			logger.info(ex);
		}		
	}
	
	private void executeWestward() {
		try {
			Monkey monkey = monkeyCreator.pollWestMonkey();
			this.crossingMonkeys.add(monkey);
			logger.info("WESTWARD MONKEY IS GETTING THE ROPE");
			Thread.sleep(1000);
			monkey.start();
			logger.info("NEXT WESTWARD MONKEY CAN CROSS");
		} catch (InterruptedException ex) {
			logger.info(ex);
		}	
	}
	
	private void waitUntilRopeIsFree() {
		while(!this.crossingMonkeys.isEmpty()) {
			if (!this.crossingMonkeys.peek().isAlive()) {
				this.crossingMonkeys.poll();
			}
		}
	}
	
	private void hasMonkeyCrossed() {
		if(!this.crossingMonkeys.isEmpty()) {
			if (!this.crossingMonkeys.peek().isAlive()) {
				this.crossingMonkeys.poll();
			}
		}
	}

}
