package pers.me.ad.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.me.ad.entity.Creative;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {
    //什么都不写，spring data jpa会自动实现 有些方法 比如查找更新等

}
