package pers.me.ad.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.me.ad.entity.AdUnit;

import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);

}

