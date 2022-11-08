package apps.cupload.file;

import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("file")
public class FileController {
    
    private final TusFileUploadService uploadService;
    private final FileService              fileService;
    
    public FileController(TusFileUploadService uploadService, FileService fileService) {
        this.uploadService = uploadService;
        this.fileService = fileService;
    }
    
    @RequestMapping(value = { "upload", "upload/**" },
                    method = { RequestMethod.POST, RequestMethod.OPTIONS })
    public void process(final HttpServletRequest req, final HttpServletResponse res) throws IOException, TusException {
        uploadService.process(req, res);
    }
    
    @DeleteMapping({ "upload", "upload/**" })
    public void delete(final HttpServletRequest req, final HttpServletResponse res) throws IOException, TusException {
        uploadService.process(req, res);
        fileService.deleteFile(req);
    }
    
    @RequestMapping(value = { "upload", "upload/**" },
                    method = { RequestMethod.HEAD, RequestMethod.PATCH })
    public void upload(final HttpServletRequest req, final HttpServletResponse res) throws IOException, TusException {
        uploadService.process(req, res);
        fileService.saveFile(req);
    }
    
    @GetMapping({ "upload", "upload/**" })
    public void download(final HttpServletRequest req,
                         final HttpServletResponse res) throws IOException, TusException {
        uploadService.process(req, res);
        fileService.download(req);
    }
}
