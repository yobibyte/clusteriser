package com.yobibyte.clusteriser.test;


import com.yobibyte.CommandLineParser;
import com.yobibyte.Constants;
import com.yobibyte.ParamHolder;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CommandLineParserTest extends Assert {

	@Test
	public void setConstantsTest() {
		CommandLineParser cpl;

		String[] args = {"-g", "20", "-c", "0.2", "-f", "/home/test"};
		cpl = new CommandLineParser(args);
		cpl.getParams();
        ParamHolder ph = ParamHolder.getInstance();
		
		assertEquals(ph.getFilepath(), "/home/test");
        assertEquals(20, (int) ph.getGapInChars());
		assertEquals(ph.getSimilarityCoefficient(), 0.2d, 0.0001);
		
		args = null;
		cpl = new CommandLineParser(args);
		cpl.getParams();

        String expected = "";
        try {
            expected = new File("/home/test").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
		assertEquals(ph.getFilepath(), expected);
		assertEquals(ph.getGap(), Constants.GAP_MAX);
		assertEquals(ph.getSimilarityCoefficient(), 0.2d, 0.0001);
	}
	
}
