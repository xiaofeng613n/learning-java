package xiaofeng;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Hello world!
 *
 */
public class HDFSTest {

    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        uploadFile();
    }



    public static void readFile() throws IOException {

        String uri="file://";
        Configuration cfg= new Configuration();
        FileSystem fs=  FileSystem.get(URI.create(uri),cfg);
        InputStream in=null;
        try{
            in=fs.open(new Path(uri));
            IOUtils.copyBytes(in, System.out,4096,false);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            IOUtils.closeStream(in);
        }
    }

    public static void uploadFile () throws IOException {

        Configuration conf=new Configuration();
        String uri="hdfs://10.40.2.71:9000";
//        conf.set("uri", uri);
        FileSystem hdfs=FileSystem.get(URI.create(uri), conf);
//        FileSystem hdfs=FileSystem.get( conf);
        //本地文件
        Path src =new Path("file:///F:/md5sum.txt");
        //HDFS为止
        Path dst =new Path("/");

        hdfs.copyFromLocalFile(src, dst);

        System.out.println("Upload to"+conf.get("fs.default.name"));

        FileStatus files[]=hdfs.listStatus(dst);

        for(FileStatus file:files){
            System.out.println(file.getPath());
        }

    }
}
