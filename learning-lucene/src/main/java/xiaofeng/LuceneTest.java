package xiaofeng;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class LuceneTest {

    public static String indexPath = "/Users/xiaofeng/workspace/learning-java/learning-lucene/indexpath";

    public static void main(String[] args) throws IOException {
//        creatIndex();
        indexFile("java");
    }

    public static void creatIndex() throws IOException {
        String filePath = "/Users/xiaofeng/workspace/jstorm-2.2.1/jstorm-core/src/main/java/storm/trident/testing";
        File fileDir = new File(filePath);
        Directory dir = FSDirectory.open(Paths.get(indexPath));

        Analyzer luceneAnalyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(luceneAnalyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(dir, iwc);
        File[] textFIles = fileDir.listFiles();
        for (int i = 0; i < textFIles.length; i++) {
            if (textFIles[i].isFile()) {

                Document document = new Document();
                Field pathField = new StringField("path", textFIles[i].getPath(), Field.Store.YES);
                Field bodyField = new TextField("body", textFIles[i].getPath(), Field.Store.YES);
                System.out.println(textFIles[i].getPath());
                document.add(pathField);
                document.add(bodyField);
                indexWriter.addDocument(document);
            }
        }
        indexWriter.close();
    }

    public static void indexFile(String queryString) throws IOException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        ScoreDoc[] hits = null;
        Query query = null;
        Analyzer analyzer = new StandardAnalyzer();

        try {
            QueryParser qp = new QueryParser("body", analyzer);
            query = qp.parse(queryString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (searcher != null) {
//            TopDocs results = searcher.search(query, 10);
            Collector collector = new GroupCollector("body");
            searcher.search(query,collector);
//            hits = results.scoreDocs;
//            Document document = null;
//            for (int i = 0; i < hits.length; i++) {
//                document = searcher.doc(hits[i].doc);
//                String body = document.get("body");
//                String path = document.get("path");
//                String modifiedtime = document.get("modifiField");
//                System.out.println("path:" + path);
//                System.out.println("body:" + body);
//                System.out.println("modifiedtime:" + modifiedtime);
//                System.out.println(hits[i].score);
//            }
//            reader.close();
            System.out.println();
        }
    }
}
