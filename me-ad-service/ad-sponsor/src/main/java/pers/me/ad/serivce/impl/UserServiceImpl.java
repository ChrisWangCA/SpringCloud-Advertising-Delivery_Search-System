package pers.me.ad.serivce.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.me.ad.constant.Constants;
import pers.me.ad.dao.AdUserRepository;
import pers.me.ad.entity.AdUser;
import pers.me.ad.exception.AdException;
import pers.me.ad.serivce.IUserService;
import pers.me.ad.utils.CommonUntils;
import pers.me.ad.vo.CreateUserRequest;
import pers.me.ad.vo.CreateUserResponse;

import javax.transaction.Transactional;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */

@Slf4j
@Service //注入到spring容器中
public class UserServiceImpl implements IUserService {
    @Autowired
    private final AdUserRepository userRepository;

    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if (!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(request.getUsername(), CommonUntils.md5(request.getUsername())));

        return new CreateUserResponse(newUser.getId(),newUser.getUsername(),newUser.getToken()
        ,newUser.getCreateTime(),newUser.getUpdateTime());
    }
}
