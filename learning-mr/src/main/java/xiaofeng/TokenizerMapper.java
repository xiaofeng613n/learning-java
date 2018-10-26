package xiaofeng;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by xiaofeng on 2018/10/15
 * Description:
 */
/*继承Mapper类，<Object,Text,Text,IntWritable>表示输入输出的key-value 类型*/
public class TokenizerMapper extends Mapper<Object,Text,Text,IntWritable> {
    IntWritable one=new IntWritable(1);
    Text text=new Text();

    public void map(Object key,Text value,Context context)throws IOException,InterruptedException{
/*key为输入的key，value为输入的value，因为用不上输入的key的类型，所以直接定义为Object类型，而Context是定义在Mapper类内部的，用于存储key-value键值对*/
        StringTokenizer tokenizer=new StringTokenizer(value.toString());
        while(tokenizer.hasMoreTokens()){
            text.set(tokenizer.nextToken());
            context.write(text,one);
        }
    }
}