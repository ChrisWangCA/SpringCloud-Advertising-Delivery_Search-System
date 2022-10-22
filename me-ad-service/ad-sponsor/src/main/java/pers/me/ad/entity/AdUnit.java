package pers.me.ad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.me.ad.constant.CommonStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-11
 */

@Data
@Entity
@Table(name = "ad_unit")
public class AdUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Basic
    @Column(name = "unit_name", nullable = false)
    private String unitName;

    @Basic
    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;

    @Basic
    @Column(name = "position_type", nullable = false)
    private Integer positionType;  //广告位类型 1开屏 2贴片 3中贴 4暂停

    @Basic
    @Column(name = "budget", nullable = false)
    private Long budget;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;


   public AdUnit(Long planId,String unitName,Integer positionType,Long budget){
       this.planId = planId;
       this.unitName = unitName;
       this.unitStatus = CommonStatus.VALID.getStatus();
       this.positionType = positionType;
       this.budget = budget;
       this.createTime = new Date();
       this.updateTime = this.createTime;
   }



}
