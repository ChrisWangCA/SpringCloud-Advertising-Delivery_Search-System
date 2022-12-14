package pers.me.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-14
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictObject {

    private Long unitId;

    private String province;

    private String city;

    //<String, Set<Long>>
    // province-city


}
