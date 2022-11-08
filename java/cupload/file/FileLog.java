package apps.cupload.file;

import samp.bean.SampBean;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("fileLog")
public class FileLog extends SampBean {
    private String uid;
    private String fileUrl;
    private String regId;
    private String regDt;
}
