package pers.me.ad.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.me.ad.exception.AdException;
import pers.me.ad.serivce.IAdUnitService;
import pers.me.ad.serivce.IUserService;
import pers.me.ad.vo.CreateUserRequest;
import pers.me.ad.vo.CreateUserResponse;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
@RestController
@Slf4j
public class UserOPController {

    private final IUserService userService;
    @Autowired
    public UserOPController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException{
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
