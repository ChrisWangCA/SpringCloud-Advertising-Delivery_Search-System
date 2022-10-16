package pers.me.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanTable {


    private Long id;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
}
