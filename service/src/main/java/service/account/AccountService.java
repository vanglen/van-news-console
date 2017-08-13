package service.account;

public interface AccountService {
    /**
     * 用户注册
     */
    void Register();

    /**
     * 用户登录
     */
    void Login();

    /**
     * 获取用户信息
     */
    void GetInfo();
}
