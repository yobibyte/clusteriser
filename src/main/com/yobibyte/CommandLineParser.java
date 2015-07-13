package com.yobibyte;

import com.yobibyte.analysis.CompressedAlphabet;
import com.yobibyte.io.FileIO;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class CommandLineParser {

    private Logger log = Logger.getLogger(CommandLineParser.class.getName());

	private Option _gap;
    private Option isGapInPercent;
	private Option _coeff;
	private Option _filepath;
	private Option alphabet;
    private Option help;

	private String[] cmdArgs;

	private org.apache.commons.cli.CommandLineParser _cmdLinePosixParser;
	private Options _posixOptions;

	public CommandLineParser(String args[]) {
		_cmdLinePosixParser = new PosixParser();
		_posixOptions = new Options();
		cmdArgs = args;

		_gap = new Option("g", "gap", false, "Gap size. 50 symbols by default. If used in percent, need to set -p key");
		_gap.setArgs(1);

        //TODO add percent gap realisation
        isGapInPercent = new Option("p", "percent", false, "Is gap in percent.  If set, -g arg used in percent. \tFalse by default");
        isGapInPercent.setArgs(0);

		_coeff = new Option("c", "coeff", false, "Likeness coefficient. 0<=c<=1. c=0.4 by default");
		_coeff.setArgs(1);

		_filepath = new Option("f", "file", true, "Input fasta file. Compulsory arg.");
        _filepath.setArgName("filepath");
		_filepath.setArgs(1);

		alphabet = new Option("a", "alphabet", false, "By default regular alphabet is used");
		alphabet.setArgs(1);
        alphabet.setArgName("alphabet name or filepath");
        help = new Option("h", "help", false, "Print help");

        _posixOptions.addOption(_gap);
        _posixOptions.addOption(isGapInPercent);
        _posixOptions.addOption(_coeff);
		_posixOptions.addOption(_filepath);
		_posixOptions.addOption(alphabet);
        _posixOptions.addOption(help);
	}

	public void getParams() {
		CommandLine commandLine;
		try {
			commandLine = _cmdLinePosixParser.parse(_posixOptions, cmdArgs);
			setConstants(commandLine);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private ParamHolder setConstants(CommandLine commandLine) {
		ParamHolder ph = ParamHolder.getInstance();

        if (commandLine.hasOption('h')) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "clusteriser", _posixOptions, true);
            System.exit(1);
        }

		if (commandLine.hasOption('g')) {
			ph.setGapInChars(Integer.parseInt(commandLine
                    .getOptionValue('g')));
		}

        if (commandLine.hasOption('c')) {
			ph.setSimilarityCoefficient(Double.parseDouble(commandLine
					.getOptionValue('c')));
		}

		if (commandLine.hasOption('f')) {
			ph.setFilepath(commandLine.getOptionValue('f'));
            String canonicalPath = "";
            try {
                canonicalPath = new File(ph.getFilepath()).getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Using input file: " + canonicalPath);
		}

		if (commandLine.hasOption('r')) {
			ph.setRegisterCounted(true);
		}

		if (commandLine.hasOption('a')) {
			switch (commandLine.getOptionValue('a')) {
			case "DAYHOFF_6":
				ph.setAlphabet(CompressedAlphabet.DAYHOFF_6);
				break;
			case "SE_B_14":
				ph.setAlphabet(CompressedAlphabet.SE_B_14);
				break;
			case "SE_B_10":
				ph.setAlphabet(CompressedAlphabet.SE_B_10);
				break;
			case "SE_V_10":
				ph.setAlphabet(CompressedAlphabet.SE_V_10);
				break;
			case "LI_A_10":
				ph.setAlphabet(CompressedAlphabet.LI_A_10);
				break;
			case "LI_B_10":
				ph.setAlphabet(CompressedAlphabet.LI_B_10);
				break;
			case "SOLIS_D_10":
				ph.setAlphabet(CompressedAlphabet.SOLIS_D_10);
				break;
			case "SOLIS_G_10":
				ph.setAlphabet(CompressedAlphabet.SOLIS_G_10);
				break;
			case "MURPHY_10":
				ph.setAlphabet(CompressedAlphabet.MURPHY_10);
				break;
			case "SE_B_8":
				ph.setAlphabet(CompressedAlphabet.SE_B_8);
				break;
			case "SE_B_6":
				ph.setAlphabet(CompressedAlphabet.SE_B_6);
				break;
			default:
				FileIO fio = new FileIO();
				Map<Character, String> alphabet;
				try {
					alphabet = fio.parseCompressedAlphabetFromFile(commandLine
							.getOptionValue('a'));
				} catch (Exception e) {
					alphabet = CompressedAlphabet.REGULAR;
				}

				ph.setAlphabet(alphabet);
				log.info(alphabet.toString());
				break;
			}
		}

		return ph;
	}

}
