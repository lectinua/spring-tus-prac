package apps.cupload.login;

import samp.Response;
import samp.user.SessionUser;
import samp.user.SessionUserUtil;
import apps.cupload.user.User;
import apps.cupload.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class LoginRestController {

    private final UserService userService;

    public LoginRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login.json")
    public Response login(User bean) {
        User user = userService.login(bean);
        if (user != null)
            return Response.ok(user);

        final String errMsg = "아이디 또는 비밀번호를 잘못 입력하셨습니다.";
        return Response.error(errMsg);
    }
}
