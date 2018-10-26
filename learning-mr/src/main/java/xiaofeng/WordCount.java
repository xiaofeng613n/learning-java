package xiaofeng;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Created by xiaofeng on 2018/10/15
 * Description:
 */
public class WordCount{

    public static void main(String[] args) throws Exception{
        Configuration conf=new Configuration();//从hadoop配置文件中读取参数
        //从命令行读取参数
        String[] otherArgs=new GenericOptionsParser(conf,args).getRemainingArgs();

        if(otherArgs.length!=2){
            System.out.println("Usage:wordcount <in> <out>");
            System.exit(2);
        }

        Job job=new Job(conf,"WordCount");
        job.setJarByClass(WordCount.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setReducerClass(CountReducer.class);
        FileInputFormat.addInputPath(job,new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job,new Path(otherArgs[1]));
        System.exit( (job.waitForCompletion(true)?0:1));

    }
}