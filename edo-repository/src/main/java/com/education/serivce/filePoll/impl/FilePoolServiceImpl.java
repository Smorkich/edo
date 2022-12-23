package com.education.serivce.filePoll.impl;

import com.education.entity.FilePool;
import com.education.repository.filePool.FilePoolRepository;
import com.education.serivce.filePoll.FilePoolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Nadezhda Pupina
 * Сервис реализует методы jpa repository и обычные методы
 */
@Service
public class FilePoolServiceImpl implements FilePoolService {
    private FilePoolRepository filePoolRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(FilePool filePool) {
        filePoolRepository.save(filePool);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        filePoolRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public FilePool findById(Long id) {
        return filePoolRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<FilePool> findAllById(Iterable<Long> ids) {
        return filePoolRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<FilePool> findAll() {
        return filePoolRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) {
        Optional<FilePool> file = filePoolRepository.findById(id);
        FilePool filePool = file.get();
        filePool.setArchivedDate(ZonedDateTime.now());
        filePoolRepository.save(filePool);
    }

    @Override
    @Transactional(readOnly = true)
    public FilePool findByIdAndArchivedDateNull(Long id) {
        return filePoolRepository.findByIdAndArchivedDateNull(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<FilePool> findByIdInAndArchivedDateNull(Iterable<Long> ids) {
        return filePoolRepository.findByIdInAndArchivedDateNull(ids);
    }

}
