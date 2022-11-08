package apps.cupload.post;

import samp.service.SampBeanService;
import samp.user.SessionUserUtil;
import apps.cupload.file.File;
import apps.cupload.file.FileService;
import org.springframework.stereotype.Service;

@Service
public class PostService extends SampBeanService<Post, PostDao> {

    private final FileService fileService;

    public PostService(FileService fileService) {
        this.fileService = fileService;
    }

    public void processFile(Post bean) {
        File file = new File();
        file.setRegId(bean.getRegId());

        Long uid = bean.getFileUid();
        if (uid == null) {
            uid = fileService.generateUid();
            bean.setFileUid(uid);
        }
        file.setUid(uid);

        if (bean.getFileAdd() != null)
            for (String add : bean.getFileAdd()) {
                file.setUrl(add);
                fileService.changeSaved(file);
            }

        if (bean.getFileDel() != null)
            for (String del : bean.getFileDel()) {
                file.setUrl(del);
                fileService.delete(file);
            }
    }

    public int save(Post bean) {
        String userId = SessionUserUtil.getSession().getUserId();
        bean.setRegId(userId);
        processFile(bean);
        return dao.save(bean);
    }

    /**
     * 작성자만 삭제 가능
     *
     * @param bean
     * @return 삭제된 항목이 없을 경우 null 반환
     */
    @Override
    public Post delete(Post bean) {
        String userId = SessionUserUtil.getSession().getUserId();
        bean.setRegId(userId);
        return super.delete(bean);
    }

    @Override
    public Post view(Post bean) {
        Post post = super.view(bean);
        String userId = SessionUserUtil.getSession().getUserId();
        boolean isRegister = userId.equals(post.getRegId());
        post.setRegister(isRegister);
        return post;
    }
}
