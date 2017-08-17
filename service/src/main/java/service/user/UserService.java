package service.user;

import model.user.TUser;

public interface UserService {
    /**
     * 添加用户
     *
     * @param tUser
     * @return
     */
    int Add(TUser tUser);

    /**
     * 检查用户是否存在
     *
     * @param username
     * @return
     */
    boolean ExistByUsername(String username);

    /**
     * 根据ID获取用户信息
     *
     * @param id
     * @return
     */
    TUser GetById(int id);

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param username
     * @param password
     * @return
     */
    TUser GetByUsernameAndPassword(String username,String password);

    /**
     * 生成用户Token
     *
     * @param user
     * @return
     */
    String GenerateUserToken(TUser user);
}
