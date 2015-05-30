package com.yobibyte.analysis;

import java.util.HashMap;
import java.util.Map;

public class CompressedAlphabet {

	public static final Map<Character, String> DAYHOFF_6 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AGPST");
			put('G', "AGPST");
			put('P', "AGPST");
			put('S', "AGPST");
			put('T', "AGPST");
			put('C', "C");
			put('D', "DENQ");
			put('E', "DENQ");
			put('N', "DENQ");
			put('Q', "DENQ");
			put('F', "FWY");
			put('W', "FWY");
			put('Y', "FWY");
			put('H', "HKR");
			put('K', "HKR");
			put('R', "HKR");
			put('I', "ILMV");
			put('L', "ILMV");
			put('M', "ILMV");
			put('V', "ILMV");
		}
	};

	public static final Map<Character, String> SE_B_14 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "A");
			put('C', "C");
			put('D', "D");
			put('E', "EQ");
			put('Q', "EQ");
			put('F', "FY");
			put('Y', "FY");
			put('G', "G");
			put('H', "H");
			put('I', "IV");
			put('V', "IV");
			put('K', "KR");
			put('R', "KR");
			put('L', "LM");
			put('M', "LM");
			put('N', "N");
			put('P', "P");
			put('S', "ST");
			put('T', "ST");
			put('W', "W");
		}
	};

	public static final Map<Character, String> REGULAR = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "A");
			put('B', "B");
			put('C', "C");
			put('D', "D");
			put('E', "E");
			put('F', "F");
			put('G', "G");
			put('H', "H");
			put('I', "I");
			put('J', "J");
			put('K', "K");
			put('L', "L");
			put('M', "M");
			put('N', "N");
			put('O', "O");
			put('P', "P");
			put('Q', "Q");
			put('R', "R");
			put('S', "S");
			put('T', "T");
			put('U', "U");
			put('V', "V");
			put('W', "W");
			put('X', "X");
			put('Y', "Y");
			put('Z', "Z");
		}
	};

	public static final Map<Character, String> SE_B_10 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AST");
			put('S', "AST");
			put('T', "AST");
			put('C', "C");
			put('D', "DN");
			put('N', "DN");
			put('E', "EQ");
			put('Q', "EQ");
			put('F', "FY");
			put('Y', "FY");
			put('G', "G");
			put('H', "HW");
			put('W', "HW");
			put('I', "ILMV");
			put('L', "ILMV");
			put('M', "ILMV");
			put('V', "ILMV");
			put('K', "KR");
			put('R', "KR");
			put('P', "P");
		}
	};

	public static final Map<Character, String> SE_V_10 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AST");
			put('S', "AST");
			put('T', "AST");
			put('C', "C");
			put('D', "DEN");
			put('N', "DEN");
			put('E', "DEN");
			put('F', "FY");
			put('Y', "FY");
			put('G', "G");
			put('H', "H");
			put('I', "ILMV");
			put('L', "ILMV");
			put('M', "ILMV");
			put('V', "ILMV");
			put('K', "KQR");
			put('R', "KQR");
			put('Q', "KQR");
			put('P', "P");
			put('P', "W");
		}
	};

	public static final Map<Character, String> LI_A_10 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AC");
			put('C', "AC");
			put('D', "DE");
			put('E', "DE");
			put('F', "FWY");
			put('W', "FWY");
			put('Y', "FWY");
			put('G', "G");
			put('H', "HN");
			put('N', "HN");
			put('I', "IV");
			put('V', "IV");
			put('K', "KQR");
			put('R', "KQR");
			put('Q', "KQR");
			put('L', "LM");
			put('M', "LM");
			put('P', "P");
			put('S', "ST");
			put('T', "ST");

		}
	};

	public static final Map<Character, String> LI_B_10 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AST");
			put('S', "AST");
			put('T', "AST");
			put('C', "C");
			put('D', "DEQ");
			put('E', "DEQ");
			put('Q', "DEQ");
			put('F', "FWY");
			put('W', "FWY");
			put('Y', "FWY");
			put('G', "G");
			put('H', "HN");
			put('N', "HN");
			put('I', "IV");
			put('V', "IV");
			put('K', "KR");
			put('R', "KR");
			put('L', "LM");
			put('M', "LM");
			put('P', "P");
		}
	};

	public static final Map<Character, String> SOLIS_D_10 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AM");
			put('M', "AM");
			put('C', "C");
			put('D', "DNS");
			put('N', "DNS");
			put('S', "DNS");
			put('E', "EKQR");
			put('K', "EKQR");
			put('Q', "EKQR");
			put('R', "EKQR");
			put('F', "F");
			put('G', "GP");
			put('P', "GP");
			put('H', "HT");
			put('T', "HT");
			put('I', "IV");
			put('V', "IV");
			put('L', "LY");
			put('Y', "LY");
			put('W', "W");
		}
	};

	public static final Map<Character, String> SOLIS_G_10 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AEFIKLMQRVW");
			put('E', "AEFIKLMQRVW");
			put('F', "AEFIKLMQRVW");
			put('I', "AEFIKLMQRVW");
			put('K', "AEFIKLMQRVW");
			put('L', "AEFIKLMQRVW");
			put('M', "AEFIKLMQRVW");
			put('Q', "AEFIKLMQRVW");
			put('R', "AEFIKLMQRVW");
			put('V', "AEFIKLMQRVW");
			put('W', "AEFIKLMQRVW");
			put('C', "C");
			put('D', "D");
			put('G', "G");
			put('H', "H");
			put('N', "N");
			put('P', "P");
			put('S', "S");
			put('T', "T");
			put('Y', "Y");
		}
	};

	
	public static final Map<Character, String> MURPHY_10 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "A");
			put('C', "C");
			put('D', "DENQ");
			put('E', "DENQ");
			put('N', "DENQ");
			put('Q', "DENQ");
			put('F', "FWY");
			put('W', "FWY");
			put('Y', "FWY");
			put('G', "G");
			put('H', "H");
			put('I', "ILMV");
			put('L', "ILMV");
			put('M', "ILMV");
			put('V', "ILMV");
			put('K', "KR");
			put('R', "KR");
			put('P', "P");
			put('S', "ST");
			put('T', "ST");
		}
	};
	
	public static final Map<Character, String> SE_B_8 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AST");
			put('S', "AST");
			put('T', "AST");
			put('C', "C");
			put('D', "DHN");
			put('H', "DHN");
			put('N', "DHN");
			put('E', "EKQR");
			put('K', "EKQR");
			put('Q', "EKQR");
			put('R', "EKQR");	
			put('F', "FWY");
			put('W', "FWY");
			put('Y', "FWY");
			put('G', "G");
			put('I', "ILMV");
			put('L', "ILMV");
			put('M', "ILMV");
			put('V', "ILMV");
			put('P', "P");
		}
	};
	
	public static final Map<Character, String> SE_B_6 = new HashMap<Character, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', "AST");
			put('S', "AST");
			put('T', "AST");
			put('C', "CP");
			put('P', "CP");
			put('D', "DEHKNQR");
			put('E', "DEHKNQR");
			put('H', "DEHKNQR");
			put('K', "DEHKNQR");
			put('N', "DEHKNQR");
			put('Q', "DEHKNQR");
			put('R', "DEHKNQR");
			put('F', "FWY");
			put('W', "FWY");
			put('Y', "FWY");
			put('G', "G");
			put('I', "ILMV");
			put('L', "ILMV");
			put('M', "ILMV");
			put('V', "ILMV");
		}
	};
}
