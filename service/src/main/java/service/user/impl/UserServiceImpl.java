package service.user.impl;

import dao.mapper.TUserMapper;
import model.user.TUser;
import org.springframework.stereotype.Service;
import service.user.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    public String hello(String name) {
        return "Hello " + name + "!";
    }

    public TUser getById(int id) {
        return tUserMapper.selectByPrimaryKey(id);
    }
}
