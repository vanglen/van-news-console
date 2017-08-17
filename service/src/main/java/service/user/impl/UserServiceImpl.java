package service.user.impl;

import common.util.MD5Encrypt;
import dao.mapper.TUserMapper;
import model.user.TUser;
import model.user.TUserExample;
import org.springframework.stereotype.Service;
import service.user.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    public int Add(TUser tUser) {
        return tUserMapper.insert(tUser);
    }

    public boolean ExistByUsername(String username) {
        TUserExample example = new TUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<TUser> result = tUserMapper.selectByExample(example);
        if (result != null && result.size() > 0)
            return true;
        return false;
    }

    public TUser GetById(int id) {
        return tUserMapper.selectByPrimaryKey(id);
    }

    public TUser GetByUsernameAndPassword(String username, String password) {
        TUserExample example = new TUserExample();
        example.createCriteria()
                .andUsernameEqualTo(username)
                .andPasswordEqualTo(password);
        List<TUser> result = tUserMapper.selectByExample(example);
        if (result != null && result.size() > 0)
            return result.get(0);
        return null;
    }

    public String GenerateUserToken(TUser user) {
        return MD5Encrypt.MD5Encode(user.getId() + user.getUsername() + user.getPassword());
    }
}
