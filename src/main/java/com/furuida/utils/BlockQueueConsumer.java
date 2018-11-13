/**
 * @Title: BlockQueueConsumer.java
 * @Package com.jd.jdalarm.util
 * @Description: TODO
 * @author fuzhengquan
 * @date 2018年4月18日 上午10:55:44
 * @version V1.0
 */
package com.furuida.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.furuida.model.User;
import com.furuida.service.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

/**
 * 消费数据
 * @ClassName: BlockQueueConsumer
 * @Description: TODO
 * @author fuzhengquan
 * @date 2018年4月18日 上午10:55:44
 *
 */
@Component("consumer")
public class BlockQueueConsumer {

    @Resource
    NodeService nodeService;
    /** The instance. */
    private static volatile BlockQueueConsumer instance = null;
    /**
     * 获取日志接口.
     */
    private static Log LOG = LogFactory.getLog(BlockQueueConsumer.class);

    /**
     * @Title: getInstance
     * @Description: 單例.
     * @param @return    设定文件
     * @return BlockQueueConsumer    返回类型
     * @throws
     */
    public static synchronized BlockQueueConsumer getInstance() {
        if (instance == null) {
            instance = new BlockQueueConsumer();
        }
        return instance;
    }

    /**
     * @throws InterruptedException
     * @Title: getData
     * @Description: 消費.
     * @param @param jedisPool
     * @param @return    设定文件
     * @return List<Map < String , Object>>    返回类型
     * @throws
     */
    public void consumer() {
        // 消费数据
        BlockingDeque<String> bd = CacheQueueManager.blockingDeque;
        while (true) {
            try {
                if (!bd.isEmpty()) {
                    String data = bd.take();
                    LOG.debug("data=" + data);
                    if (!data.contains("|")) {
                        LOG.error("---数据不合法:");
                        continue;
                    }
                    String userId = data.split("\\|")[0];
                    String parentId = data.split("\\|")[1];;
                    nodeService.payAndUpgrade(userId, parentId);
                    LOG.error("---length:" + bd.size());
                } else {
                    Thread.sleep(1000);
                    LOG.debug("--------------doing");
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                continue;
            }
        }
    }

    /**
     * @Title: getMapData
     * @Description: TODO
     * @param @param data
     * @param @return    设定文件
     * @return ArrayList<MonitorMessage>    返回类型
     * @throws
     */
    @SuppressWarnings("unused")
    @Deprecated
    private ArrayList<User> getMapDataList(String data) {
        return JSON.parseObject(data, new TypeReference<ArrayList<User>>() {
        });
    }

    /**
     * @Title: getMapData
     * @Description: TODO
     * @param @param data
     * @param @return    设定文件
     * @return ArrayList<MonitorMessage>    返回类型
     * @throws
     */
//    private User getMapData(String data) {
//        return JSON.parseObject(data, new TypeReference<User>() {
//        });
//    }

    /**
     * @Title: main
     * @Description: TODO
     * @param @param args    设定文件
     * @return void    返回类型
     * @throws
     */
    public static void main(String[] args) {
        try {
            String json = "[{\"endpoint\":\"10.187.196.225\",\"metric\":\"mem.swapused.percent\",\"step\":\"5\",\"tags\":\"\",\"time\":\"1523950969\",\"type\":\"GAUGE\",\"value\":\"0\"},{\"endpoint\":\"10.187.196.225\",\"metric\":\"mem.memoryused.percent\",\"step\":\"5\",\"tags\":\"\",\"time\":\"1523950969\",\"type\":\"GAUGE\",\"value\":\"2561\"},{\"endpoint\":\"10.187.196.225\",\"metric\":\"load.1min\",\"step\":\"5\",\"tags\":\"\",\"time\":\"1523950969\",\"type\":\"GAUGE\",\"value\":\"0\"},{\"endpoint\":\"10.187.196.225\",\"metric\":\"load.1min\",\"step\":\"5\",\"tags\":\"\",\"time\":\"1523950969\",\"type\":\"GAUGE\",\"value\":\"0\"},{\"endpoint\":\"10.187.196.225\",\"metric\":\"load.5min\",\"step\":\"5\",\"tags\":\"\",\"time\":\"1523950969\",\"type\":\"GAUGE\",\"value\":\"0\"},{\"endpoint\":\"10.187.196.225\",\"metric\":\"load.15min\",\"step\":\"5\",\"tags\":\"\",\"time\":\"1523950969\",\"type\":\"GAUGE\",\"value\":\"0\"},{\"endpoint\":\"10.187.196.225\",\"metric\":\"df.bytes.used.percent\",\"step\":\"5\",\"tags\":\"\",\"time\":\"1523950969\",\"type\":\"GAUGE\",\"value\":\"4000\"}]";
            ArrayList<User> jsonArray = JSON.parseObject(json, new TypeReference<ArrayList<User>>() {
            });
            for (User m : jsonArray) {
                LOG.error("=====" + m.toString());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
