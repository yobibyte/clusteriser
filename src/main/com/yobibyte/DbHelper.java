package com.yobibyte;

import com.yobibyte.structures.Fasta;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.ReadableIndex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yobibyte on 22/04/15.
 */
public class DbHelper {
    private static GraphDatabaseService db;

    static {
        db = ParamHolder.getInstance().getDb();
    }

    public static Fasta getSequenceById(int id ) {
        Transaction tx = db.beginTx();
        ReadableIndex<Node> autoIndex = db.index().getNodeAutoIndexer().getAutoIndex();
        Node n = autoIndex.get("custom_id", id).getSingle();
        if(n != null) {
            Fasta f = nodeToFasta(n);
            tx.success();
            return f;
        }
        tx.failure();
        return null;
    }


    public static List<Fasta> getAllNodes() {
        List<Fasta> res = new ArrayList<>();
        Transaction tx = db.beginTx();
        Iterator<Node> it = db.getAllNodes().iterator();
        while(it.hasNext()) {
            Node n = it.next();
            res.add(nodeToFasta(n));
        }

        tx.success();
        return res;
    }


    public static void addFastaToDb(Fasta f) {
        Transaction tx = db.beginTx();
        Node n = db.createNode();
        n.setProperty("custom_id", f.getId());
        n.setProperty("general_info", f.getGeneralInfo());
        n.setProperty("proteins", f.getProteins());
        tx.success();
    }

    public static Fasta nodeToFasta(Node n) {
        Fasta f = new Fasta();
        f.setId((Integer) n.getProperty("custom_id"));
        f.setGeneralInfo((String) n.getProperty("general_info"));
        String s = (String) n.getProperty("proteins");
        f.setProteins(s);
        return f;
    }


}
