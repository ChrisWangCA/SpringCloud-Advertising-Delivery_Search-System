package pers.me.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
//返回给前端的用户信息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {

    private Long userId;
    private String username;
    private String token;
    private Date createdTime;
    private Date updatedTime;



}
