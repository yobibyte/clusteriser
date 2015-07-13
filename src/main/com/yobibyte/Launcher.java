package com.yobibyte;

import com.yobibyte.analysis.DataAnalyser;
import org.apache.log4j.Logger;

public class Launcher {

    private static Logger log = Logger.getLogger(Launcher.class.getName());

	public static void main(String args[]) {
		Long stTime = System.currentTimeMillis();
        log.info("Hi! I'm clusteriser!");

		CommandLineParser clp = new CommandLineParser(args);
		clp.getParams();
        DataAnalyser.mainLoop();

        log.info("Check the result. I've finished.");
        Long endTime = System.currentTimeMillis();
        log.info("Processing time is " + (endTime - stTime)/1000 + " sec");
    }


}
