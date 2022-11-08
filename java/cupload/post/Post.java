package apps.cupload.post;

import samp.bean.SampBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post extends SampBean {
    private Long uid;
    private String subject;
    private String contents;
    private Long fileUid;
    private String fileNames;
    private String regId;
    private String regNm;
    private String regDt;
    private String updtId;
    private String updtDt;
    private String useAt;

    private String[] fileAdd;
    private String[] fileDel;
    private boolean register;
}
