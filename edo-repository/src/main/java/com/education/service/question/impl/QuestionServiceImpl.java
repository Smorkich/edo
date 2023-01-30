package com.education.service.question.impl;

import com.education.entity.Appeal;
import com.education.entity.Question;
import com.education.repository.question.QuestionRepository;
import com.education.service.question.QuestionService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static model.constant.Constant.APPEAL_URL;
import static model.constant.Constant.QUESTION_URL;

/**
 * @author Nadezhda Pupina
 * Реализует связь контроллера и репозитория
 */
@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question save(Question question) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(QUESTION_URL)
                .setPath("/");
        return restTemplate.postForObject(builder.build(), question, Question.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Question question) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(QUESTION_URL)
                .setPath("/")
                .setPath(String.valueOf(question.getId()));
        restTemplate.delete(builder.build());
    }

    @Override
    @Transactional(readOnly = true)
    public Question findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(QUESTION_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Question.class);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<Question> findAllById(Iterable<Long> ids) {
//        return questionRepository.findAllById(ids);
//    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll(Iterable<Long> ids) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(QUESTION_URL)
                .setPath("/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(QUESTION_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.put(builder.build(), Question.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Question findByIdAndArchivedDateNull(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(QUESTION_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Question.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Question> findByAllIdNotArchived(Collection<Long> ids) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(QUESTION_URL)
                .setPath("/notArchivedAll/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

}
