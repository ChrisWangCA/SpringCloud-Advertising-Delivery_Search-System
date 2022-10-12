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
@AllArgsConstructor
@NoArgsConstructor
@Entity //标记为实体类
@Table(name = "ad_user") //指定表名
public class AdUser {
    @Id //标记为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增
    @Column(name = "id", nullable = false) //指定列名 不能为空
    private Long id;

    @Basic //标记数据库表的基本属性
    @Column(name = "username", nullable = false)
    private String username;

    @Basic
    @Column(name = "token", nullable = false)
    private String token;

    @Basic
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;


    public AdUser(String username,String token){
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

}
