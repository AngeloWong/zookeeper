package com.angelo.client.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;

public class ZkClientTest {

    private static final String path = "/data";

    public static void main(String[] args) throws IOException, InterruptedException {
        ZkClient zkc = new ZkClient("192.168.66.88:2181", 50000, 50000, new SerializableSerializer());
        zkc.deleteRecursive(path);
        zkc.createPersistent(path, "1".getBytes());

        //监听节点数据变化：包括节点数据变更和节点删除
        zkc.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("删除的节点为:" + path);
            }

            @Override
            public void handleDataChange(String path, Object data) {
                System.out.println("变更的节点为:" + path + ", 变更内容为:" + data);
            }
        });

        Thread.sleep(3000);
        zkc.writeData(path, "456", -1);
        //变更的节点为:/test, 变更内容为:456
        Thread.sleep(1000);
        //通过zkCli.sh 客户端set /test aaa,发现ZkClient并未监听到此次修改事件

        zkc.delete(path);
        //删除的节点为:/test
        Thread.sleep(Integer.MAX_VALUE);

        System.in.read();
    }


}
