package com.angelo.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaderLatchExample {

    public static void main(String[] args) throws Exception {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderLatch> leaderLatches = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            CuratorFramework client =  CuratorFrameworkFactory.newClient("192.168.66.88:2181",
                    new RetryNTimes(3, 1000));
            clients.add(client);
            client.start();

            LeaderLatch leaderLatch = new LeaderLatch(client, "/LeaderLatch", "client#"+i);
            leaderLatches.add(leaderLatch);
            leaderLatch.start();
        }

        TimeUnit.SECONDS.sleep(30);

        System.out.println("睡眠结束");

        for (LeaderLatch leaderLatch : leaderLatches) {
            if (leaderLatch.hasLeadership()) {
                System.out.println("当前Leader是" + leaderLatch.getId());
                break;
            }
        }

        System.in.read();

        for (CuratorFramework curatorFramework : clients) {
            curatorFramework.close();
        }
    }
}
