package apps.cupload.file;

import apps.cupload.file.ext.DownloadExtension;
import apps.cupload.file.ext.LazyDiskLockingService;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
    
    @Value("${tus.server.data.directory}")
    protected String tusDataPath;
    
    public static final String uploadURI = "/file/upload";
    
    @Bean
    public TusFileUploadService TusFileUploadService() {
        return new TusFileUploadService()
            .withStoragePath(tusDataPath)
            .withUploadURI(uploadURI)
            .withThreadLocalCache(true)
            .withUploadLockingService(new LazyDiskLockingService("cupload"))
            .addTusExtension(new DownloadExtension());
    }
    
}

enum FileState {
    Y, // saved
    N, // not
    P // in progress
}