package com.angelo.client.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZookeeperClientTest {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 默认的watch
        ZooKeeper client = new ZooKeeper("192.168.66.88:2181", 50000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("连接的时候:" + event);
            }
        });

        Stat stat = new Stat();

//        String s = new String(client.getData("/angelo", new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                Event.EventType type = event.getType();
//                // Watcher 只能响应一次，第二次修改/angelo值时，以下未打印
//                if (Event.EventType.NodeDataChanged.equals(type)) {
//                    System.out.println("数据发生了改变");
//                }
//            }
//        }, stat));

        // 此处用true, 使用上述zookeeper默认watcher
//        String s = new String(client.getData("/angelo", true, stat));

        client.create("/angelo", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        client.getData("/angelo", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("123123123");
            }
        }, null);
        System.in.read();
    }
}
