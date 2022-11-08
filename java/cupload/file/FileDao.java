package apps.cupload.file;

import samp.dao.SampBeanDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileDao extends SampBeanDao<File> {

    public FileDao() {
        super("file");
    }

    public Long key() {
        return selectOne("key");
    }

    public int changeSaved(File bean) {
        return update("changeSaved", bean);
    }

    public int deleteReal(File bean) {
        return delete("deleteReal", bean);
    }

    public int insertLog(FileLog bean) {
        return insert("insertLog", bean);
    }

    public List<FileLog> selectLog(FileLog bean) {
        return selectList("selectLog", bean);
    }

    public int merge(File bean) {
        return update("merge", bean);
    }

    public boolean isRegister(File bean) {
        return selectOne("isRegister");
    }
}
