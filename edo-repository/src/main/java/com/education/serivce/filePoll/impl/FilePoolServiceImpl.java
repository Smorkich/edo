package com.education.serivce.filePoll.impl;

import com.education.entity.FilePool;
import com.education.repository.filePool.FilePoolRepository;
import com.education.serivce.filePoll.FilePoolService;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

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
        return filePoolRepository.findById(id).orElse(null);
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
    public FilePool findByIdNotArchived(Long id) {
        return filePoolRepository.findByIdNotArchived(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<FilePool> findAllByIdNotArchived(Iterable<Long> ids) {
        return filePoolRepository.findAllByIdNotArchived(ids);
    }

}
