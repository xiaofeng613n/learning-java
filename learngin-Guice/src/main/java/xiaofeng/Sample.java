package xiaofeng;

/**
 * Created by xiao on 2019/1/12.
 */
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

//https://zhuanlan.zhihu.com/p/32299568
@Singleton
class HelloPrinter {

	public void print() {
		System.out.println("Hello, World");
	}

}

@Singleton
public class Sample {

	@Inject
	private HelloPrinter printer;

	public void hello() {
		printer.print();
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector();
		Sample sample = injector.getInstance(Sample.class);
		sample.hello();
	}

}