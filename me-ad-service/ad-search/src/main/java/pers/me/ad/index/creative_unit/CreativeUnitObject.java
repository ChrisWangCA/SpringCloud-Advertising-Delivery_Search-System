package pers.me.ad.index.creative_unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeUnitObject {

    private Long adId;

    private Long unitId;

    //key = adId-unitId

}
