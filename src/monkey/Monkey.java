package monkey;

public class Monkey extends Thread {
	
	int time;
	Direction direction;
	
	public Monkey() {
		this.time = 5000;
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
             System.out.println("EXECUTING " + this.direction.toString() + " MONKEY");
             sleep(this.time);
             System.out.println(this.direction.toString() + " MONKEY EXECUTED");
         } catch (InterruptedException ex) {
        	 System.out.println(ex);
         }
    }
}
