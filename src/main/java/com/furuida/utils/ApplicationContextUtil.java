package com.furuida.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**  
* @ClassName: ApplicationContextUtil  
* @Description: TODO
* @author fuzhengquan
* @date 2018年4月17日 上午10:57:02  
*    
*/
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    /**
     * 获取applicationContext.
     * @deprecated
     *  @see AppContext.context()
     * @return ApplicationContext
     */
    public static ApplicationContext applicationContext() {
        return AppContext.context();
    }
    /**
     * 通过name获取 Bean.
     * @deprecated
     *  @see AppContext.context().getBean
     * @param name bean的名字
     * @return Bean
     */
    public static Object getBean(String name) {
        return AppContext.context().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @deprecated
     *  @see AppContext.context().getBean
     * @param clazz bean的class
     * @return Bean
     * @param <T> class的泛型
     */
    public static <T> T getBean(Class<T> clazz) {
        return AppContext.context().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean.
     * @deprecated
     *      @see AppContext.context().getBean
     * @param name bean的名字
     * @param clazz bean的class
     * @return Bean
     * @param <T> class的泛型
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return AppContext.context().getBean(name, clazz);
    }

//    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.init(applicationContext);
    }
}
