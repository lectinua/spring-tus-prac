package apps.cupload.file;

import samp.service.SampBeanService;
import samp.user.SessionUser;
import samp.user.SessionUserUtil;
import samp.util.FileUtil;
import samp.util.WebUtil;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class FileService extends SampBeanService<File, FileDao> {

    private final TusFileUploadService uploadService;

    public FileService(TusFileUploadService uploadService) {
        this.uploadService = uploadService;
    }

    public Long generateUid() {
        return dao.key();
    }

    @Override
    public List<File> list(File bean) {
        bean.setSaved(FileState.Y.name());
        return super.list(bean);
    }

    public List<File> editList(File bean) {
        SessionUser user = SessionUserUtil.getSession();

        bean.setRegId(user.getUserId());
        return super.list(bean);
    }

    public List<FileLog> logList(FileLog bean) {
        return dao.selectLog(bean);
    }

    public List<File> lastUpload() {
        SessionUser user = SessionUserUtil.getSession();

        File bean = new File();
        bean.setRegId(user.getUserId());
        bean.setSaved(FileState.N.name());
        bean.setUid(0L);

        return super.list(bean);
    }

    public int download(final HttpServletRequest req) {
        String url = req.getRequestURI();
        SessionUser user = SessionUserUtil.getSession(req.getSession());

        FileLog log = new FileLog();
        log.setFileUrl(url);
        log.setRegId(user != null ? user.getUserId() : WebUtil.getIpAddress(req));

        return dao.insertLog(log);
    }

    public void saveFile(final HttpServletRequest req) throws TusException, IOException {
        String uploadURI = req.getRequestURI();
        UploadInfo info = uploadService.getUploadInfo(uploadURI);

        if (info == null)
            return;

        SessionUser user = SessionUserUtil.getSession();
        File file = new File();

        file.setUid(0L);
        file.setName(info.getFileName());
        file.setSize(info.getLength());
        file.setType(info.getFileMimeType());
        file.setExtension(FileUtil.extension(info.getFileName()));
        file.setUrl(uploadURI);
        file.setRegId(user.getUserId());

        final String fileUidName = "uid";
        if (info.getMetadata().containsKey(fileUidName)) {
            Long uid = Long.parseLong(info.getMetadata().get(fileUidName));
            File temp = new File();
            temp.setRegId(user.getUserId());
            temp.setUid(uid);
            if (dao.isRegister(temp))
                file.setUid(uid);
        }

        if (info.isUploadInProgress()) {
            file.setId("InProgress");
            file.setSaved(FileState.P.name());
        } else {
            file.setId(info.getId().toString());
            file.setSaved(FileState.N.name());
        }
        dao.merge(file);
    }

    public int deleteFile(final HttpServletRequest req) throws TusException, IOException {
        String requestURI = req.getRequestURI();

        File file = new File();
        file.setUrl(requestURI);

        return dao.deleteReal(file);
    }

    /**
     * 1. delete uploaded file data
     * 2. delete file by user_id and url
     *
     * @param bean
     * @return
     * @throws TusException
     * @throws IOException
     */
    public int deleteFileAndUploadedData(File bean) throws TusException, IOException {
        SessionUser user = SessionUserUtil.getSession();
        bean.setRegId(user.getUserId());

        uploadService.deleteUpload(bean.getUrl());
        return dao.deleteReal(bean);
    }

    public int changeSaved(File bean) {
        return dao.changeSaved(bean);
    }

    /**
     * 1. delete uploaded file data
     * 2. update file.useAt to 'N' by url
     *
     * @param bean
     * @return
     */
    @Override
    public File delete(File bean) {
        try {
            uploadService.deleteUpload(bean.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return super.delete(bean);
    }
}
