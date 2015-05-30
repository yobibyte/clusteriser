package com.yobibyte;

import org.apache.commons.io.FileUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ParamHolder {

	private static ParamHolder instance;

	private int gap;
    private boolean isGapInPercent;
    private double similarityCoefficient;
	private String filepath;
	private boolean isRegisterCounted;
	private Map<Character, String> alphabet;

    private int alignmentLen;
    private Integer gapInChars;

	private File resultFolder;
    private File orig;
    private File trimmed;
    private int inputSize;

    private GraphDatabaseService db;

	private ParamHolder() {
		gap = Constants.GAP_MAX;
		similarityCoefficient = Constants.SIMILARITY_THRESHOLD;
		filepath = "";
		isRegisterCounted = Constants.COUNT_REGISTER;
		alphabet = Constants.DEFAULT_ALPHABET;
		gapInChars = Constants.GAP_IN_CHARS;
        isGapInPercent = Constants.IS_GAP_IN_PERCENT;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String fp = System.getProperty("user.dir");
		resultFolder = new File(fp + "/result/" + sdf.format(new Date()));
		resultFolder.mkdirs();

        //TODO simplify
        orig = new File(resultFolder.getAbsolutePath() + "/orig");
        orig.mkdir();
        trimmed = new File(resultFolder.getAbsolutePath() + "/trimmed");
        trimmed.mkdir();

        try {
            FileUtils.deleteDirectory(new File(Constants.DB_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        db = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder( Constants.DB_NAME ).
                setConfig( GraphDatabaseSettings.node_keys_indexable, "custom_id," ).
                setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
                newGraphDatabase();
        registerShutdownHook(db);

    }

	public static ParamHolder getInstance() {
		if(instance == null) {
			instance = new ParamHolder();
		} 
		return instance;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

	public double getSimilarityCoefficient() {
		return similarityCoefficient;
	}

	public void setSimilarityCoefficient(double similarityCoefficient) {
		this.similarityCoefficient = similarityCoefficient;
	}

	public boolean isRegisterCounted() {
		return isRegisterCounted;
	}

	public void setRegisterCounted(boolean isRegisterCounted) {
		this.isRegisterCounted = isRegisterCounted;
	}

	public Map<Character, String> getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(Map<Character, String> alphabet) {
		this.alphabet = alphabet;
	}

	public File getResultFolder() {
		return this.resultFolder;
	}
	
	public void setResultFolder(File resultFolder) {
		this.resultFolder = resultFolder;
	}

    public Integer getGapInChars() {
        return gapInChars;
    }

    public void setGapInChars(Integer gapInChars) {
        this.gapInChars = gapInChars;
    }

    public boolean isGapInPercent() {
        return isGapInPercent;
    }

    public void setGapInPercent(boolean isGapInPercent) {
        this.isGapInPercent = isGapInPercent;
    }

    public int getAlignmentLen() {
        return alignmentLen;
    }

    public void setAlignmentLen(int alignmentLen) {
        this.alignmentLen = alignmentLen;
        if(isGapInPercent) {
            this.gapInChars = alignmentLen * gap /100;
        }
    }

    public File getOrig() {
        return orig;
    }

    public File getTrimmed() {
        return trimmed;
    }

    public GraphDatabaseService getDb() {
        return db;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb ) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }

}
