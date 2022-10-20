package pers.me.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {

    private Float latitude;

    private Float longitude;

    private String city;

    private String province;


}
