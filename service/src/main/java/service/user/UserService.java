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
     * 修改用户信息
     *
     * @param tUser
     * @return
     */
    int Modify(TUser tUser);

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
    TUser GetByUsernameAndPassword(String username, String password);

    /**
     * 生成用户Token
     *
     * @param user
     * @return
     */
    String GenerateUserToken(TUser user);

    /**
     * 验证用户token
     *
     * @param user_id    用户ID
     * @param user_token 用户token
     * @return 是否成功
     */
    boolean VerifyUserToken(int user_id, String user_token);
}
