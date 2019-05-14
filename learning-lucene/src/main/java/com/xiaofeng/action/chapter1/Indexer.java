package com.xiaofeng.action.chapter1;

/**
 * @Auther: xiaofeng
 * @Date: 2019-05-03 19:43
 * @Description:
 */
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.nio.file.Paths;

// From chapter 1

/**
 * This code was originally written for
 * Erik's Lucene intro java.net article
 */
public class Indexer {

    public static void main(String[] args) throws Exception {
//        if (args.length != 2) {
//            throw new IllegalArgumentException("Usage: java " + Indexer.class.getName() + " <index dir> <data dir>");
//        }

        String indexDir = "/Users/xiaofeng/workspace/learning-java/learning-lucene/indexes/chapter1";         //1
        String dataDir = Indexer.class.getClassLoader().getResource("data").getFile();          //2

        long start = System.currentTimeMillis();
        Indexer indexer = new Indexer(indexDir);
        int numIndexed;
        try {
            numIndexed = indexer.index(dataDir, new TextFilesFilter());
        } finally {
            indexer.close();
        }
        long end = System.currentTimeMillis();

        System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
    }


    private IndexWriter writer;

    public Indexer(String indexDir) throws IOException {

        Directory dir = FSDirectory.open(Paths.get(indexDir));

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        writer = new IndexWriter(dir, iwc);
    }

    public void close() throws IOException {
        writer.close();                             //4
    }

    public int index(String dataDir, FileFilter filter) throws Exception {

        File[] files = new File(dataDir).listFiles();

        for (File f: files) {
            if (!f.isDirectory() &&
                    !f.isHidden() &&
                    f.exists() &&
                    f.canRead() &&
                    (filter == null || filter.accept(f))) {
                indexFile(f);
            }
        }

        return writer.numDocs();                     //5
    }

    private static class TextFilesFilter implements FileFilter {

        public boolean accept(File path) {
            return path.getName().toLowerCase().endsWith(".txt");
        }

    }


    private void indexFile(File f) throws Exception {

        System.out.println("Indexing " + f.getCanonicalPath());

        Document doc = new Document();
        Field pathField = new StringField("path", f.toString(), Field.Store.YES);
        doc.add(pathField);
        Field filenameField = new StringField("filename", f.getName(), Field.Store.YES);
        doc.add(filenameField);
        doc.add(new LongPoint("modified", f.lastModified()));
        doc.add(new StringField("modifiedValue", String.valueOf(f.lastModified()), Field.Store.YES));
        doc.add(new TextField("contents", new BufferedReader(new FileReader(f))));

        writer.addDocument(doc);                              //10
    }
}