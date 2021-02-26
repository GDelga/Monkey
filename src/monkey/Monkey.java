package monkey;

import org.apache.log4j.Logger;

import controler.Controller;

public class Monkey extends Thread {
	
	int time;
	Direction direction;
	static Logger logger = Logger.getLogger(Controller.class);
	
	public Monkey() {
		this.time = 4000;
		this.direction = Direction.randomDirection();
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	@Override
    public void run() {
		 try {
			 logger.info("EXECUTING " + this.direction.toString() + " MONKEY");
             sleep(this.time);
             logger.info(this.direction.toString() + " MONKEY EXECUTED");
         } catch (InterruptedException ex) {
        	 logger.info(ex);
         }
    }
}
