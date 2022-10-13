package pers.me.ad.vo;

import com.netflix.discovery.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitRequest {
    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    public boolean createValidate(){
        return (null != planId && planId > 0) && !StringUtils.isEmpty(unitName)
                && (positionType != null && positionType != 0) && (budget != null && budget > 0);
    }

}
