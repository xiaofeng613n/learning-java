package xiaofeng.tag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by xiao on 2019/1/2.
 */
public class CommonNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		this.registerBeanDefinitionParser("service", new ServerBeanDefinitionParser(ServerBean.class));
	}
}
