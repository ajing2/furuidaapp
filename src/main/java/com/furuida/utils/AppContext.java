package com.furuida.utils;


import org.springframework.context.ApplicationContext;

/**  
* @ClassName: AppContext  
* @Description: TODO
* @author fuzhengquan
* @date 2018年4月17日 上午11:02:29  
*    
*/
public class AppContext {

    private static ApplicationContext context;

    public static void init(ApplicationContext context) {
        AppContext.context = context;
    }

    public static ApplicationContext context() {
        return context;
    }
    
    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return context().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return context().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return context().getBean(name, clazz);
    }
}
