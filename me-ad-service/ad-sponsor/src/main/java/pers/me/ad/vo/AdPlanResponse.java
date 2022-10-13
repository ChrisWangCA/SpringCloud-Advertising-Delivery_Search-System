package pers.me.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
//用于创建和更新时的响应
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanResponse {

    private Long id;
    private String planName;

}
