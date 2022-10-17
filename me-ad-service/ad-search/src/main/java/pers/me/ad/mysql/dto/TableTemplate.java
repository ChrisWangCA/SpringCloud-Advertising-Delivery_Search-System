package pers.me.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.me.ad.mysql.constant.OpType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {

    private String tableName;

    private String level;

    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引 -> 字段名
     */
    private Map<Integer,String > posMap = new HashMap<>();
}
