package pers.me.ad.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.me.ad.entity.AdUser;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
//JpaRepository<AdUser, Long>  第一个参数是实体类，第二个参数是主键类型
public interface AdUserRepository extends JpaRepository<AdUser, Long> {
    //根据用户名查找用户
    AdUser findByUsername(String username);

}
