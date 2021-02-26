package main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import controler.Controller;


public class Main {
	
	static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
	    logger.info("START");
		Controller controller = new Controller();
		controller.controlPassage();
	}

}
