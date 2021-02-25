package monkey;

import java.util.Random;

public enum Direction {
	WESTWARD,
	EASTWARD;
	
	private static final Random RANDOM = new Random();

	public static Direction randomDirection() {
	    int index = RANDOM.nextInt(Direction.values().length);
	    return Direction.values()[index];
	}
}
