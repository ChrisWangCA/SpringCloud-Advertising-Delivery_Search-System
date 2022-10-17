package pers.me.ad.mysql.listener;

import pers.me.ad.mysql.dto.BinlogRowData;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
public interface Ilistener {

    void register();

    void onEvent(BinlogRowData eventData);


}
