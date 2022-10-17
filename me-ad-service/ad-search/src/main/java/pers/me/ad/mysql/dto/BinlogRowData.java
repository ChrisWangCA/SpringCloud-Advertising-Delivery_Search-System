package pers.me.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
@Data
public class BinlogRowData {

    private TableTemplate table;

    private EventType eventType;

    private List<Map<String,String>> after;

    private List<Map<String,String>> before;


}
