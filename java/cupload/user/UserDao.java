package apps.cupload.user;

import samp.dao.SampBeanDao;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends SampBeanDao<User> {

    public UserDao() {
        super("user");
    }

    public User login(User bean) {
        return selectOne("login", bean);
    }
}
