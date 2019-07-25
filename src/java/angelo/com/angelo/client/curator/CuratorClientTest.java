package com.angelo.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class CuratorClientTest {

    private static final String path = "/data";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.66.88:2181",
                new RetryNTimes(3, 1000));
        client.start();

//        client.create().withMode(CreateMode.PERSISTENT).forPath(path, "123".getBytes());

//        NodeCache nodeCache = new NodeCache(client, path);
//        // 节点数据是否初始化
//        nodeCache.start(true);
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("--- nodeChanged ---");
//            }
//        });

        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("用的是Watch");
            }
        }).forPath(path);

        System.in.read();
    }
}
