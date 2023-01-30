package com.education.service.theme.imp;

import com.education.entity.Address;
import com.education.entity.Theme;
import com.education.repository.theme.ThemeRepository;
import com.education.service.theme.ThemeService;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Random;

import static model.constant.Constant.ADDRESS_URL;
import static model.constant.Constant.THEME_URL;

/**
 * @author AlexeySpiridonov
 */
@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Theme theme) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(THEME_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), theme, Theme.class);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(THEME_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

    @Override
    @Transactional(readOnly = true)
    public Theme findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(THEME_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Theme.class);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Collection<Theme> findAllById(Iterable<Long> ids) {
//        return themeRepository.findAllById(ids);
//    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Theme> findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(THEME_URL)
                .setPath("/");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(THEME_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.getForObject(builder.build(), Theme.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Theme findByIdAndArchivedDateNull(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(THEME_URL)
                .setPath("/noArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Theme.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Theme> findByIdInAndArchivedDateNull(Iterable<Long> ids) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(THEME_URL)
                .setPath("/noArchived/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }
}
