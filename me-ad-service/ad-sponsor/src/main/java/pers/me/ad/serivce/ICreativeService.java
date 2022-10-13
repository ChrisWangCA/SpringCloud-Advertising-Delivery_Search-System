package pers.me.ad.serivce;

import pers.me.ad.vo.CreativeRequest;
import pers.me.ad.vo.CreativeResponse;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request);
}
