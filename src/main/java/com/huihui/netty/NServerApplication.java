package com.huihui.netty;

import com.huihui.netty.server.NettyServer;
import com.spring4all.mongodb.EnableMongoPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongoPlus
@SpringBootApplication
public class NServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NServerApplication.class, args);
        try {
            runNetty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runNetty() {
        Thread thread = new Thread(new NettyServer());
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
