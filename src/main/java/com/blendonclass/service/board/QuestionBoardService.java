package com.blendonclass.service.board;

import com.blendonclass.dto.AnswerWriteDto;
import com.blendonclass.dto.QuestionShowDto;
import com.blendonclass.dto.QuestionWriteDto;
import com.blendonclass.entity.Authority;
import com.blendonclass.entity.QuestionBoard;
import com.blendonclass.repository.AccountRepository;
import com.blendonclass.repository.AuthorityRepository;
import com.blendonclass.repository.board.QuestionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionBoardService {
    private final QuestionBoardRepository questionBoardRepository;
    private final AuthorityRepository authorityRepository;
    private final AccountRepository accountRepository;

    public void saveQuestion(QuestionWriteDto questionWriteDto){
         Authority authority = authorityRepository.findByAccountIdAndClassroomId(questionWriteDto.getWriterId(),questionWriteDto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("권한이 존재하지 않습니다."));
         System.out.println(questionWriteDto.getId());
        QuestionBoard questionBoard = QuestionBoard.toQuestionSaveBoard(questionWriteDto, authority, null);
        questionBoardRepository.save(questionBoard);
    }

    public void deleteQuestion(Long id){
        questionBoardRepository.deleteById(id);
    }

    public void saveAnswer(Long qbId, Long accountId, String aContext){
        QuestionBoard questionBoard = questionBoardRepository.findById(qbId).get();

        questionBoard.setAccount(accountRepository.findById(accountId).get());
        questionBoard.setAContext(aContext);
        questionBoard.setAWriteTime(LocalDateTime.now());

        questionBoardRepository.save(questionBoard);
    }

    public void deleteAnswer(Long id, Long accountId){

        QuestionBoard questionBoard = questionBoardRepository.findById(id).orElse(null);
        questionBoard.setAccount(null);
        questionBoard.setAWriteTime(null);
        questionBoard.setAContext(null);

        questionBoardRepository.save(questionBoard);

    }


    public QuestionShowDto getQuestionDetail(Long qbId, Long accountId){
        QuestionBoard questionBoard = questionBoardRepository.findById(qbId).get();
        QuestionShowDto questionShowDto = QuestionShowDto.from(questionBoard);
        //writer check
        if(questionBoard.getAuthority().getAccount().getId().equals(accountId)){
            questionShowDto.setIsWriter(true);
        } else {
            questionShowDto.setIsWriter(false);
        }

        //answerer check
        if(questionBoard.getAccount() != null) {
            if(questionBoard.getAccount().getId().equals(accountId)){
                questionShowDto.setIsAnswerer(true);
            } else {
                questionShowDto.setIsAnswerer(false);
            }
        } else {
            questionShowDto.setIsAnswerer(false);
        }
        return questionShowDto;
    }

    public Page<QuestionShowDto> getQuestionList(Pageable pageable, Long classroomId){
        //todo - repository 이용해서 board 데이터 가져오기
        List<QuestionShowDto> questionList = questionBoardRepository.findQuestionBoardByClassroomId(classroomId, pageable)
                .stream().map(item -> {
                            System.out.println(item.toString());
                            return QuestionShowDto.from(item);
                        }).collect(Collectors.toList());

        return new PageImpl<>(questionList, pageable, questionList.size());
    }

    public QuestionWriteDto findById(Long id) {
        QuestionBoard questionBoard = questionBoardRepository.findById(id).orElse(null);
        QuestionWriteDto questionWriteDto = QuestionWriteDto.toQuestionWriteDto(questionBoard);
        return questionWriteDto;
    }


    public AnswerWriteDto answerShow(Long id) {
        QuestionBoard questionBoard = questionBoardRepository.findById(id).get();
        return AnswerWriteDto.from(questionBoard);
    }
}
