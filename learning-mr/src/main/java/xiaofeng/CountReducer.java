package xiaofeng;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by xiaofeng on 2018/10/15
 * Description:
 */
public class CountReducer extends Reducer<Text ,IntWritable,Text,IntWritable> {

    IntWritable result=new IntWritable();
    public void reduce(Text key, Iterable<IntWritable> values, Context context)throws IOException,InterruptedException{
        int sum=0;
        for(IntWritable iw : values){
            sum+=iw.get();
        }
        result.set(sum);
        context.write(key,result);
    }
}