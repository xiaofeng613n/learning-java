package com.xiaofeng.action.chapter1;

/**
 * @Auther: xiaofeng
 * @Date: 2019-05-03 23:45
 * @Description:
 */
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
//import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

// From chapter 1

/**
 * This code was originally written for
 * Erik's Lucene intro java.net article
 */
public class Searcher {

    public static void main(String[] args) throws IllegalArgumentException, IOException, ParseException {
//        if (args.length != 2) {
//            throw new IllegalArgumentException("Usage: java " + Searcher.class.getName()
//                    + " <index dir> <query>");
//        }

        String indexDir = "/Users/xiaofeng/workspace/learning-java/learning-lucene/indexes/chapter1";         //1
        String q = "WARRANTIES";                      //2

        search(indexDir, q);
    }

    public static void search(String indexDir, String q) throws IOException, ParseException {

        Directory dir = FSDirectory.open(Paths.get(indexDir)); //3
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher is = new IndexSearcher(reader);   //3
        Analyzer analyzer = new StandardAnalyzer();

        QueryParser parser = new QueryParser("contents", analyzer);
        Query query = parser.parse(q);              //4
        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, 10); //5
        long end = System.currentTimeMillis();

        System.err.println("Found " + hits.totalHits + " document(s) (in " + (end - start) +
                " milliseconds) that matched query '" + q + "':");

        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);               //7
            System.out.println(doc.get("path"));  //8
            System.out.println(doc.get("modifiedValue"));
        }

//        is.close();                                //9

    }
}

