package com.angelo.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaderSelectorExample {

    public static void main(String[] args) throws Exception {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderSelector> leaderSelectors = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            final int clientId = i;
            CuratorFramework client =  CuratorFrameworkFactory.newClient("192.168.66.88:2181",
                    new RetryNTimes(3, 1000));
            clients.add(client);
            client.start();

            LeaderSelector leaderSelector = new LeaderSelector(client, "/LeaderSelector", new LeaderSelectorListener() {
                @Override
                public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                    // 当上Leader了就会进入这个方法
                    System.out.println("当前Leader是" + client + " - " + clientId);
                    TimeUnit.SECONDS.sleep(5);
                }

                @Override
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

                }
            });
            leaderSelector.start();
            leaderSelectors.add(leaderSelector);
        }

//        TimeUnit.SECONDS.sleep(30);

//        System.out.println("睡眠结束");

        System.in.read();

        for (CuratorFramework curatorFramework : clients) {
            curatorFramework.close();
        }
    }
}
