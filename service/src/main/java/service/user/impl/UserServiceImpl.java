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

    public int Modify(TUser tUser) {
        return tUserMapper.updateByPrimaryKey(tUser);
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

    public boolean VerifyUserToken(int user_id, String user_token) {
        if (user_id > 0 && user_token != null && user_token.length() > 0) {
            TUser user = GetById(user_id);
            if (user != null) {
                String veryfy_user_token = GenerateUserToken(user);
                if (veryfy_user_token.equals((user_token))) {
                    return true;
                }
            }
        }
        return false;
    }
}
