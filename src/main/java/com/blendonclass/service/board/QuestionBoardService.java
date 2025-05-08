package com.blendonclass.service.board;

import com.blendonclass.dto.AnswerWriteDto;
import com.blendonclass.dto.AssignmentShowDto;
import com.blendonclass.dto.QuestionShowDto;
import com.blendonclass.dto.QuestionWriteDto;
import com.blendonclass.repository.board.QuestionBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Page<QuestionShowDto> getQuestionList(Pageable pageable, Long classroomId){
        //todo - repository 이용해서 board 데이터 가져오기
        List<QuestionShowDto> questionList = questionBoardRepository.findQuestionBoardByClassroomId(classroomId, pageable)
                .stream().map(QuestionShowDto::from).collect(Collectors.toList());

        return new PageImpl<>(questionList, pageable, questionList.size());
    }
}
