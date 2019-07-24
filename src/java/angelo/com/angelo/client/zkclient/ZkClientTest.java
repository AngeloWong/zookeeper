package com.angelo.client.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;

public class ZkClientTest {

    public static void main(String[] args) throws IOException {
        ZkClient zk = new ZkClient("192.168.66.88:2181", 10000, 10000, new SerializableSerializer());
        zk.createPersistent("/angelo", "1".getBytes());

        zk.subscribeDataChanges("/angelo", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });

        System.in.read();
    }


}
