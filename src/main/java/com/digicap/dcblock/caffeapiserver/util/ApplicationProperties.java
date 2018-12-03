package com.digicap.dcblock.caffeapiserver.util;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Setter
@Getter
public class ApplicationProperties {

    private List<String> allow_remotes = new ArrayList<>();

    private String api_version;

    private String purchase_list_viewer_server;

    private int random_uri_expired_minute;

    private String admin_server;
}
