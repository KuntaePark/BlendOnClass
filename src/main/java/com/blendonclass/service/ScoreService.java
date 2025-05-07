package com.blendonclass.service;

import com.blendonclass.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScoreService {
    private final ScoreRepository scoreRepository;
    //todo - 단원별 / 소단원 별 점수 / 시도횟수 불러오기
}
