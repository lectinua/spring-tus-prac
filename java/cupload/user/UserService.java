package apps.cupload.user;

import samp.service.SampBeanService;
import samp.user.SessionUser;
import samp.user.SessionUserUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService extends SampBeanService<User, UserDao> {

    public User login(User bean) {
        User user = dao.login(bean);
        if (user != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser.setUserId(user.getUserId());
            sessionUser.setName(user.getName());
            sessionUser.setUserUid(user.getUid());
            SessionUserUtil.setSession(sessionUser);
        }
        return user;
    }
}
