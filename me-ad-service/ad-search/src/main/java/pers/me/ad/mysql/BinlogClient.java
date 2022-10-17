package pers.me.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.netflix.discovery.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.me.ad.mysql.listener.AggregationListener;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
@Slf4j
@Component
public class BinlogClient {
    private BinaryLogClient client;

    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(AggregationListener listener, BinlogConfig config) {
        this.listener = listener;
        this.config = config;
    }

    public void connect(){
        new Thread(()->{
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)){
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(listener);
            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql end");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }).start();
    }

    public void close(){
        try{
            log.info("closing to mysql start");
            client.disconnect();
            log.info("closing to mysql end");
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
