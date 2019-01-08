package xiaofeng;


import java.util.List;

/**
 * Created by xiao on 2017/1/27.
 */
public class Test
{
    public static void main(String[] args) {
       /* ClassPathResource resource = new ClassPathResource("Test.xml");

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        Student student = factory.getBean(Student.class);
        System.out.println(student.getId()+" " + student.getName());*/

        int i = test1(4,3);
      //  System.out.println(i);
    }

    public static void test(int m,int n)
    {
        int[] a = new int[m];
        for (int i = 0; i < m; i ++)
        {
            a[i] = 1;
        }

        int index = 0;
        int c = m;
        while ( c > 1)
        {
            int count = 0;
            for (int i = index; i < m ; i ++)
            {
                if( a[i] > 0)
                {
                    count ++;
                }
                if( count == n)
                {
                    a[i] = 0;
                    index = i + 1;
                    c --;
                    break;
                }
            }
        }

//        List list = Lists.newArrayList(a);
//        System.out.println(list);


    }


    public static int test1(int m,int n)
    {
        if( m ==1 )
        {
            return 1;
        }
        else
        {
            return  (test1(m-1,n) + m ) % n;
        }
    }

}
