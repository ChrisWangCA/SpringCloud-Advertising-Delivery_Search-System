package pers.me.ad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */


//广告展现给用户看的创意

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_creative")
public class Creative {
    @Basic
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "type", nullable = false)
    private Integer type;


    @Basic
    @Column(name = "material_type", nullable = false)
    //物料类型，比如图片可以是bmp，jpg等
    private Integer materialType;

    @Basic
    @Column(name = "height", nullable = false)
    private Integer height;

    @Basic
    @Column(name = "width", nullable = false)
    private Integer width;

    @Basic
    @Column(name = "size", nullable = false)
    //物料大小
    private Long size;

    @Basic
    @Column(name = "duration", nullable = false)
    //只有视频不为0，其他类型为0
    private Integer duration;

    @Basic
    @Column(name = "audit_status", nullable = false)
    //审核状态
    private Integer auditStatus;

    @Basic
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "url", nullable = false)
    private String url;

    @Basic
    @Column(name = "createTime", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "updateTime", nullable = false)
    private Date updateTime;

}
