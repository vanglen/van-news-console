package service.user;

import model.user.TUser;

public interface UserService {
    String hello(String name);
    TUser getById(int id);
}
