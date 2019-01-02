package xiaofeng.tag;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by xiao on 2019/1/2.
 */
public class ServerBeanDefinitionParser implements BeanDefinitionParser{

	private final Class<?> clazz;

	public ServerBeanDefinitionParser(Class<?> clazz) {
		this.clazz = clazz;
	}


	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition bd = new RootBeanDefinition();
		bd.setLazyInit(false);

		String id =  element.getAttribute("id");
		String serverName = element.getAttribute("serverName");

		bd.setBeanClass(this.clazz);
		bd.setInitMethodName("init");

		MutablePropertyValues  propertyValues = bd.getPropertyValues();
		propertyValues.addPropertyValue("serverName", serverName);
		propertyValues.addPropertyValue("id", id);

		parserContext.getRegistry().registerBeanDefinition(id,  bd);

		return bd;
	}
}
