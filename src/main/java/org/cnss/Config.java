package org.cnss;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@NoArgsConstructor
@Configuration
@PropertySource("classpath:accessOpenKM.properties")
public class Config {
    @Value("${openKMServer}")
    private String openKMServer;
    @Value("${openKMUserName}")
    private String openKMUserName;
    @Value("${openKMPassword}")
    private String openKMPassword;
    @Value("${uploadDocument}")
    private String uploadDocument;
    @Value("${createFolder}")
    private String createFolder;
    @Value("${deleteDocument}")
    private String deleteDocument;
    @Value("${getDocument}")
    private String getDocument;
    @Value("${rootPath}")
    private String rootPath;
    @Value("${rootEmployerUUID}")
    private String rootEmployerUUID;
    @Value("${mails}")
    private String mails;
    @Value("${deleteFolder}")
    private String deleteFolder;
    @Value("${renameDocument}")
    private String renameDocument;
}
