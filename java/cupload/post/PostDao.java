package apps.cupload.post;

import samp.dao.SampBeanDao;
import samp.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class PostDao extends SampBeanDao<Post> {

    public PostDao() {
        super("post");
    }

    public int save(Post bean) {
        if (StringUtil.isNotEmpty(bean.getUid()))
            return super.update("update", bean);
        return super.insert("insert", bean);
    }

    @Override
    public Post delete(Post bean) {
        return update("delete", bean) > 0 ? bean : null;
    }
}
