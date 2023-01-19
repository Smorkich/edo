package model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Kostenko Aleksandr
 * Класс с методом возврата url пользователя и департамента
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriBuilderUtil {


    public static String getUrlEmployeeDepartment(String http, String host, int random, String path, int port) {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(http)
                .setHost(host)
                .setPort(port)
                .setPath(path);

        return uriBuilder.toString();
    }

}
