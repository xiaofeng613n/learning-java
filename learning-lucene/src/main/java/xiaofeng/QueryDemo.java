package xiaofeng;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;


public class QueryDemo {

    private static String indexPath = "/Users/xiaofeng/workspace/learning-java/learning-lucene/indexpath";
    //QueryDemo.class.getClassLoader().getResource("index").getPath();

    public static void main(String[] args) throws Exception {
        QueryParser queryParser = new QueryParser("name",new StandardAnalyzer());
        Query query = queryParser.parse("xiao feng");
        // 建立索引
        index();
        //精确String 类型查询
        getByTermQuery();
        //范围查询
        getByRange();
        //前缀匹配查询
        getByPrefix();
        //通配符查询
        getByWildcard();
        //短语查询
        getByPhrase();
        //模糊查询
        getByFuzzy();
    }

    //精确查询
    public static void getByTermQuery() {
        System.out.println("-------------查询用户名为xf的数据-------------");
        //精确查询，根据名称来直接
        Query query = new TermQuery(new Term("username", "xf"));
        //执行查询
        excQuery(query);
    }

    public static void getByRange() {
        System.out.println("-------------查询id在1-3的数据-------------");
        //查询前三条数据，后面两个true,表示的是是否包含头和尾
       // Query query = TermRangeQuery.newStringRange("id", String.valueOf(1), String.valueOf(3), true, true);
        Query query = IntPoint.newRangeQuery("id", 1, 3);
        //执行查询
        excQuery(query);
    }

    public static void getByPrefix() {
        System.out.println("-------------查询前缀 邮箱 以z开头的数据-------------");
        //查询前缀 邮箱 以z开头的数据
        Query query = new PrefixQuery(new Term("email", "z"));
        //执行查询
        excQuery(query);
    }

    /**
     * 通配符查询数据
     */
    public static void getByWildcard() {
        //通配符就更简单了，只要知道“*”表示0到多个字符，而使用“？”表示一个字符就行了
        System.out.println("-------------------查询email 以 @qq结尾的数据--------------");
        //查询email 以 @qq结尾的数据
        Query query = new WildcardQuery(new Term("email", "*@qq.com"));
        //执行查询
        excQuery(query);
    }

    /**
     * 短语查询，查询中间有几个单词的那种
     */
    public static void getByPhrase() {
        System.out.println("------------查询内容中，有I　LIKE　YOU 的数据---------------");
        //短语查询，但是对于中文没有太多的用，其中查询的时候还有
        //I  XX you  就可以被查询出来
        PhraseQuery query = new PhraseQuery(1, "content", "i", "you");
        //设定有几跳，表示中间存在一个单词
        excQuery(query);
    }

    // 默认提供的模糊查询对中文来说，没有任何用
    public static void getByFuzzy() throws Exception {
        System.out.println("-------------------------模糊查询---------------");
        FuzzyQuery query = new FuzzyQuery(new Term("username", "zhangsan"));
        excQuery(query);
    }

    public static void excQuery(Query query) {

        try (IndexReader reader = getIndexReader()) {

            //获取查询数据
            IndexSearcher searcher = new IndexSearcher(reader);
            //检索数据
            TopDocs topDocs = searcher.search(query, 100);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = reader.document(scoreDoc.doc);
                System.out.println(doc.get("id") + ":" + doc.get("username") + ":" + doc.get("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("static-access")
    public static IndexWriter getIndexWriter() throws Exception {
        FSDirectory fs = FSDirectory.open(Paths.get(indexPath));
        IndexWriter writer = new IndexWriter(fs, new IndexWriterConfig(new StandardAnalyzer()));
        return writer;
    }

    public static IndexReader getIndexReader() throws Exception {
        // 创建IdnexWriter
        FSDirectory fs = FSDirectory.open(Paths.get(indexPath));
        return DirectoryReader.open(fs);
    }


    public static void deleteAll() {
        try (IndexWriter writer = getIndexWriter()) {
            // 删除所有的数据
            writer.deleteAll();
            int cnt = writer.numDocs();
            System.out.println("索引条数\t" + cnt);
            // 提交事物
            writer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void index() {

        if (new File(indexPath).exists()) {
            return;
        }
        //准备数据
        List<Passage> psgList = psgList = new ArrayList<Passage>();
        psgList.add(new Passage(1, "hehe", "717350389@qq.com", "逗比", 23, "I LIKE YOU ", new Date()));
        psgList.add(new Passage(2, "xf", "zhashang@qq.com", "逗比", 23, "content1", new Date()));
        psgList.add(new Passage(3, "李四", "lisi@neusoft.com", "逗比", 23, "content1", new Date()));
        psgList.add(new Passage(4, "王五", "wangwu@aliyun.com", "逗比", 23, "content1", new Date()));
        psgList.add(new Passage(5, "赵六", "zhaoliu@baidu.com", "逗比", 23, "content1", new Date()));
        System.out.println("-------------------------添加的数据----------------------");
        for (Passage passage : psgList) {
            System.out.println(passage);
        }
        //建立索引
        try (IndexWriter writer = getIndexWriter()) {
            for (Passage psg : psgList) {
                Document doc = new Document();


                //doc.add(new StringField("id", String.valueOf(psg.getId()), Field.Store.YES));
                doc.add(new IntPoint("id", psg.getId()));
                // 用户String类型的字段的存储，StringField是只索引不分词
                doc.add(new TextField("username", psg.getUsername(), Field.Store.YES));
                // 主要对int类型的字段进行存储，需要注意的是如果需要对InfField进行排序使用SortField.Type.INT来比较，如果进范围查询或过滤，需要采用NumericRangeQuery.newIntRange()
                doc.add(new StringField("age", String.valueOf(psg.getAge()), Field.Store.YES));
                // 对String类型的字段进行存储，TextField和StringField的不同是TextField既索引又分词
                doc.add(new TextField("content", psg.getContent(), Field.Store.NO));

                doc.add(new StringField("keyword", psg.getKeyword(), Field.Store.YES));

                doc.add(new StringField("email", psg.getEmail(), Field.Store.YES));
                // 日期数据添加索引
                //  doc.add(new ("addDate", psg.getAddDate().getTime(), Field.Store.YES));

                writer.addDocument(doc);
            }
            // 索引条数
            int cnt = writer.numDocs();
            System.out.println("索引条数\t" + cnt);
            // 提交事物
            writer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    static class Passage {
        private int id;
        private String username;
        private String email;
        private String keyword;
        private int age;
        private String content;
        private Date addDate;

        public Passage(int id, String username, String email, String keyword, int age, String content, Date addDate) {
            super();
            this.id = id;
            this.username = username;
            this.email = email;
            this.keyword = keyword;
            this.age = age;
            this.content = content;
            this.addDate = addDate;
        }

        @Override
        public String toString() {
            return "Passage{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", keyword='" + keyword + '\'' +
                    ", age=" + age +
                    ", content='" + content + '\'' +
                    ", addDate=" + addDate +
                    '}';
        }
    }
}

//    TermQuery	精确查询
//    TermRangeQuery	查询一个范围
//    PrefixQuery	前缀匹配查询
//    WildcardQuery	通配符查询
//    BooleanQuery	多条件查询
//    PhraseQuery	短语查询
//    FuzzyQuery	模糊查询

    //设定默认搜索域,将默认搜索域 设定在Content
//    QueryParser parse = new QueryParser(Version.LUCENE_35, "content",new StandardAnalyzer(Version.LUCENE_35));
////      parse.setDefaultOperator(Operator.AND);//将空格默认 定义为AND
//        parse.setAllowLeadingWildcard(true);//设定第一个* 可以匹配
//                Query query = parse.parse("yellow");
//                //其中空格默认就是OR
//                query = parse.parse("yellow cong");
//                //改变搜索域，搜索域 为 name
//                query = parse.parse("name:yellow1");
//                //使用通配符 ， 设定查询类容为 以 y 开头的数据
//                query = parse.parse("name:y*"); //其中* 不可以放在字符串的首位
//                //将字符串放在首位，默认情况下回报错
//                query = parse.parse("email:*@yellow.com"); //其中我们可以更改 第一个通配值得功能
//                //其中 + - 表示有 和没有 其中需要有空格 ，而且第一个+ 或者 - 需要放在第一个位置
//                query = parse.parse("- content: cong  + i "); //这个表示的是content 中不含有 cong ，但是含有i
//                //匹配区间， 其中TO 必须是大写的，还有有空格
//                query = parse.parse("id:[1 TO 4]"); //设定查询的Id为 1-4
//                //开区间匹配
//                query = parse.parse("id:(1 TO 4)");
//                //匹配连起来的String
//                query = parse.parse("\"I like yellow cong\""); //这个是查询的一个一个词  ，匹配String
//                //匹配一个或者多个数据
//                query = parse.parse("\"I cong\"~2"); //表示中间含有一个单词
//                //模糊查询
//                query = parse.parse("name:yellow~");
//                //匹配数字,这个方法中没有字符类容，所以需要自定义了


