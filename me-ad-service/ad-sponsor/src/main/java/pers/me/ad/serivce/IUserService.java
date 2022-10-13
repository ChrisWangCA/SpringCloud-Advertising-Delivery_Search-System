package pers.me.ad.serivce;

import pers.me.ad.exception.AdException;
import pers.me.ad.vo.CreateUserRequest;
import pers.me.ad.vo.CreateUserResponse;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
public interface IUserService {
    //创建用户
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;

}
