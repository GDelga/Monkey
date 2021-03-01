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
	private Boolean started;
	static Logger logger = Logger.getLogger(Controller.class);
	
	public Controller() {
		this.crossingMonkeys = new LinkedList<Monkey>();
		this.started = false;
	}
	
	
	public void controlPassage() {
		monkeyCreator.start();
		logger.info("MONKEY CREATOR STARTED");
		this.decideDirection();
		logger.info("STARTING DIRECTION DECIDED");
		while(true) {
			if (this.direction == Direction.EASTWARD && monkeyCreator.isEmptyEast() && !monkeyCreator.isEmptyWest()
					&& this.crossingMonkeys.isEmpty()) {
				this.direction = Direction.WESTWARD;
			}
			else if (this.direction == Direction.WESTWARD && !monkeyCreator.isEmptyEast() && monkeyCreator.isEmptyWest()
					&& this.crossingMonkeys.isEmpty()) {
				this.direction = Direction.EASTWARD;
			}
			if (this.direction == Direction.EASTWARD && !monkeyCreator.isEmptyEast() && monkeyCreator.isEmptyWest()) {
				logger.info("STARTING EASTWARD CROSSING");
				this.executeEastward();
				this.hasMonkeyCrossed();
			}
			else if (this.direction == Direction.WESTWARD && !monkeyCreator.isEmptyWest() && monkeyCreator.isEmptyEast()) {
				logger.info("STARTING WESTWARD CROSSING");
				this.executeWestward();
				this.hasMonkeyCrossed();
			}
			else if (this.direction == Direction.EASTWARD && !monkeyCreator.isEmptyWest()) {
				logger.info("WESTWARD MONKEY IS WAITING");
				this.waitUntilRopeIsFree();
				this.direction = Direction.WESTWARD;
				this.executeWestward();
			}
			else if (this.direction == Direction.WESTWARD && !monkeyCreator.isEmptyEast()) {
				logger.info("EASTWARD MONKEY IS WAITING");
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
	
	private void decideDirection() {
		while(this.direction == null) {
			if (!monkeyCreator.isEmptyWest()) {
				this.direction = Direction.WESTWARD;
				this.started = true;
			}
			else if (!monkeyCreator.isEmptyEast()) {
				this.direction = Direction.EASTWARD;
				this.started = true;
			}
			logger.info("DECIDING DIRECTION");
		}
	}

}
