package com.furuida.utils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 支付成功进入该队列
* @ClassName: CacheQueueManager  
* @Description: TODO
* @author fuzhengquan
* @date 2018年4月17日 下午8:27:06  
*    
*/
public class CacheQueueManager {
    /**
     *<默认构造函数>.
     */
    private CacheQueueManager() {
    }
    /** The blocking deque. */
    public static BlockingDeque<String> blockingDeque = new LinkedBlockingDeque<String>();
    /**
     * <一句话功能简述>.
     * <功能详细描述>
     *
     * @param args the arguments
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args) {
        try {
//            blockingDeque.put("000001");
            blockingDeque.put("0000|02");
//            blockingDeque.put("000003");
            String data = blockingDeque.take();
            System.out.println("data=" + data);
            if (!data.contains("|")) {
                System.out.println("---数据不合法:");
            }
            String userId = data.split("\\|")[0];
            String parentId = data.split("\\|")[1];
            System.out.println(userId);
            System.out.println(parentId);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        List<String> sL = new ArrayList<String>();
//        sL.add("sss");
//        MapCacheManager.cache.put("key", sL);
//        MapCacheManager.
//        System.out.println("=" + blockingDeque.getLast().toString());
//        System.out.println(blockingDeque == CacheQueueManager.blockingDeque);
    }

}
