package com.yobibyte;

import com.yobibyte.analysis.CompressedAlphabet;

import java.util.Map;

public class Constants {
    public static Integer GAP_IN_CHARS = 50;
    public static boolean IS_GAP_IN_PERCENT = false;
	public static double SIMILARITY_THRESHOLD = 0.4d;
	public static Map<Character, String> DEFAULT_ALPHABET = CompressedAlphabet.REGULAR;
	public static int GAP_MAX = Integer.MAX_VALUE;
	public static Integer MIN_SEQ_IN_CLUSTER = 5;
	public static boolean COUNT_REGISTER = false;

    public static String DB_NAME = "data/db.neo4j";

}
