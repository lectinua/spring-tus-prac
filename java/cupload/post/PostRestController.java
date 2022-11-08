package apps.cupload.post;

import samp.Response;
import samp.bean.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post")
public class PostRestController {

    private final PostService service;

    public PostRestController(PostService service) {
        this.service = service;
    }

    @GetMapping("list.json")
    public Response list(Post bean, Pageable pageable) {
        return Response.ok(service.page(bean, pageable));
    }

    @PostMapping("save.json")
    public Response save(Post bean) {
        return Response.ok(service.save(bean));
    }

    @DeleteMapping("delete.json")
    public Response delete(Post bean) {
        return Response.ok(service.delete(bean));
    }

    @GetMapping("view.json")
    public Response view(Post bean) {
        return Response.ok(service.view(bean));
    }
}
