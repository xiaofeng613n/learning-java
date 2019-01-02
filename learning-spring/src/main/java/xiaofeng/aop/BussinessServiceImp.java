package xiaofeng.aop;

import xiaofeng.aop.IBussinessService;

/**
 * Created by xiao on 2019/1/1.
 */
public class BussinessServiceImp implements IBussinessService {

	@Override
	public void bussiness() {
		System.out.println("I am reading");
	}
}
