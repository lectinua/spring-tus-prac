package apps.cupload.user;

import samp.bean.SampBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends SampBean {
    private Long uid;
    private String userId;
    private String name;
    private String password;
    private String regDt;
    private String updtDt;
}
