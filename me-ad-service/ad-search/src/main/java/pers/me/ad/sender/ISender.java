package pers.me.ad.sender;

import pers.me.ad.mysql.dto.MySqlRowData;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
