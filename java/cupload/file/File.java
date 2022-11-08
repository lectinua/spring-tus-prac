package apps.cupload.file;

import samp.bean.SampBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File extends SampBean {
    private Long uid;
    private String id;
    private String saved;
    private String name;
    private String url;
    private String type;
    private String extension;
    private Long size;
    private String regId;
    private String regDt;
}
