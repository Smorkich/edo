package com.education.service.filePool.impl;

import com.education.entity.Appeal;
import com.education.entity.FilePool;
import com.education.repository.filePool.FilePoolRepository;
import com.education.service.filePool.FilePoolService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Random;

import static model.constant.Constant.APPEAL_URL;
import static model.constant.Constant.FILEPOOL_URL;

/**
 * @author Nadezhda Pupina
 * Сервис реализует методы jpa repository и обычные методы
 */
@Service
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {
    private FilePoolRepository filePoolRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FilePool save(FilePool filePool) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(FILEPOOL_URL)
                .setPath("/");
        return restTemplate.postForObject(builder.build(), filePool, FilePool.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

    @Override
    @Transactional(readOnly = true)
    public FilePool findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), FilePool.class);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Collection<FilePool> findAllById(Iterable<Long> ids) {
//        var instances = eurekaClient.getApplication("edo-repository").getInstances();
//        var instance = instances.get(new Random().nextInt(instances.size()));
//        var builder = new URIBuilder();
//        builder.setHost(instance.getHostName())
//                .setPort(instance.getPort())
//                .setPath(FILEPOOL_URL)
//                .setPath("/")
//                .setPath(String.valueOf(ids));
//        return restTemplate.getForObject(builder.build(), Collection.class);
//    }

    @Override
    @Transactional(readOnly = true)
    public Collection<FilePool> findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(FILEPOOL_URL)
                .setPath("/");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        FilePool filePoolObj =  findByIdAndArchivedDateNull(id);
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(FILEPOOL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.put(builder.build(), filePoolObj);

    }

    @Override
    @Transactional(readOnly = true)
    public FilePool findByIdAndArchivedDateNull(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(FILEPOOL_URL)
                .setPath("/noArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), FilePool.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<FilePool> findByIdInAndArchivedDateNull(Iterable<Long> ids) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(FILEPOOL_URL)
                .setPath("/noArchived/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

}
