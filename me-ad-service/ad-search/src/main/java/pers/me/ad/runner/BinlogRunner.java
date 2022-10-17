package pers.me.ad.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pers.me.ad.mysql.BinlogClient;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("coming in BinlogRunner");
        client.connect();
    }
}
