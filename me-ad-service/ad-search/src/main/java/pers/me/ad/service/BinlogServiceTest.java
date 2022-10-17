package pers.me.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
public class BinlogServiceTest {
    public static void main(String[] args) throws Exception {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1",3306,"root","123456");

        client.setBinlogFilename("binlog.000037");

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData){
                System.out.println("UpdateRowsEventData");
                System.out.println(data.toString());
            }else if (data instanceof WriteRowsEventData){
                System.out.println("WriteRowsEventData");
                System.out.println(data.toString());
            }else if (data instanceof DeleteRowsEventData){
                System.out.println("DeleteRowsEventData");
                System.out.println(data.toString());
            }
        });
        client.connect();
    }
}
