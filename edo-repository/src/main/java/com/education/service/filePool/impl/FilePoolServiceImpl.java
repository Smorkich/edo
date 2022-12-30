package com.education.service.filePool.impl;

import com.education.entity.FilePool;
import com.education.repository.filePool.FilePoolRepository;
import com.education.service.filePool.FilePoolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {

    FilePoolRepository filePoolRepository;

    @Override
    public FilePool findById(Long id) {
        return filePoolRepository.findById(id).orElse(null);
    }
}
