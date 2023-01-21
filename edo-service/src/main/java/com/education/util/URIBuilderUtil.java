package com.education.util;

import com.netflix.discovery.EurekaClient;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Andrey Kryukov
 * Класс помагает билдить URI до сервиса с помощью EurekaClient
 */
@Component
@Log4j2
@AllArgsConstructor
public class URIBuilderUtil {
    private final EurekaClient eurekaClient;

    /**
     * @param service - service name (edo-repository example)
     * @param path - uri path (/api/repository/employee/search example)
     * @return
     */
    @ApiOperation("Билдит динамический URI до сервиса")
    public URIBuilder buildURI(String service, String path) {
        log.info("URI build started");
        int randomRepositoryInstance = ThreadLocalRandom.current().nextInt(eurekaClient.getApplication(service).getInstances().size());
        log.info("Defined random instance");
        String host = eurekaClient.getApplication(service).getInstances().get(randomRepositoryInstance).getHostName();
        log.info("Taken host from service by eureka");
        int port = eurekaClient.getApplication(service).getInstances().get(randomRepositoryInstance).getPort();
        log.info("Taken port from service by eureka");
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPort(port)
                .setPath(path);
        log.info("URI built");
        return uriBuilder;
    }
}