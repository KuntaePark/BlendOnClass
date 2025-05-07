package com.blendonclass.service;

import com.blendonclass.dto.AnswerWriteDto;
import com.blendonclass.dto.QuestionShowDto;
import com.blendonclass.dto.QuestionWriteDto;
import com.blendonclass.repository.QuestionBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBoardService {
    @Autowired
    private QuestionBoardRepository questionBoardRepository;

    public void saveQuestion(QuestionWriteDto questionWriteDto){

    }

    public void deleteQuestion(Long qbId){

    }

    public void saveAnswer(AnswerWriteDto answerWriteDto){

    }

    public void deleteAnswer(Long qbId){

    }

    public QuestionShowDto getQuestionDetail(Long qbId){
        return null;
    }

    public List<QuestionShowDto> getQuestionList(){
        return null;
    }
}
