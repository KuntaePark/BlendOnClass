package com.blendonclass.service;

import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.ClassroomScoreRepository;
import com.blendonclass.repository.ProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final ClassroomScoreRepository classroomScoreRepository;
    private final ProgressRepository progressRepository;
    private final AuthorityRepository authorityRepository;
}
