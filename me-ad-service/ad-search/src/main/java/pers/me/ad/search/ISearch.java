package pers.me.ad.search;

import pers.me.ad.search.vo.SearchRequest;
import pers.me.ad.search.vo.SearchResponse;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */

public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);

}
