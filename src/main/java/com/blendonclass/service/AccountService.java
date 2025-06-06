package com.blendonclass.service;

/*
    관리자용 계정 관리 관련 서비스
 */

import com.blendonclass.constant.ROLE;
import com.blendonclass.dto.UserAccountDto;
import com.blendonclass.dto.admin.AccountDto;
import com.blendonclass.dto.admin.AccountGeneratedDto;
import com.blendonclass.dto.admin.AccountListDto;
import com.blendonclass.dto.admin.AccountSearchDto;
import com.blendonclass.entity.Account;
import com.blendonclass.repository.AccountRepository;
import com.blendonclass.repository.StatRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final StatRepository statRepository;
    private final Validator validator;


    //계정 목록 검색 및 반환
    public Page<AccountListDto> searchAccountList(AccountSearchDto accountSearchDto) {
        //todo
        //검색
        Pageable pageable = PageRequest.of(accountSearchDto.getPageNum(), 8);
        Page<Account> accounts = null;
        String keyword = accountSearchDto.getKeyword();
        ROLE role = accountSearchDto.getRoleType();
        System.out.println(role + " " + keyword);
        if(role != null) {
            accounts = accountRepository.findByRoleAndNameContainingAndRoleNot(role, keyword, ROLE.ADMIN, pageable);
        } else {
            accounts = accountRepository.findByNameContainingAndRoleNot(keyword, ROLE.ADMIN, pageable);
        }

        return accounts.map(AccountListDto::from);
    }

    //단일 계정 정보 조회, 계정 정보 수정 시 필요
    public AccountDto getAccountInfo(Long accountId) {
        return AccountDto.from(accountRepository.findById(accountId).get());
    }

    public UserAccountDto getUserAccountInfo(Long userId) {
        return UserAccountDto.from(accountRepository.findById(userId).get());
    }

    //단일 계정 생성, 생성 성공 시 로그인 아이디 및 비밀번호 반환
    public AccountGeneratedDto saveAccount(AccountDto accountDto) {
        Account account = accountDto.to();

        String loginId = createId(accountDto.getRole());
        String password = createPw();

        account.setLoginId(loginId);
        account.setPassword(password);

        accountRepository.save(account);
        statRepository.findById("idIndex").get().addAccount();
        return new AccountGeneratedDto(loginId, password);
    }

    private String createPw() {
        SecureRandom random = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        System.out.println("생성 비밀번호: " + password.toString());
        return password.toString();
    }

    private String createId(ROLE role) throws IllegalArgumentException{
        Long idIndex = statRepository.findById("idIndex").get().getStat()+1;
        //최대 계정 개수를 넘을 시
        if(idIndex > 999999) {
            throw new IllegalArgumentException("계정 생성 한도에 도달했습니다.");
        } else {
            String prefix = null;
            switch (role) {
                case TEACHER:
                    prefix = "teach";
                    break;
                case STUDENT:
                    prefix = "stud";
                    break;
            }
            System.out.println("생성 아이디: " + prefix + String.format("%06d", idIndex));
            return prefix + String.format("%06d", idIndex);
        }
    }

    //단일 계정 수정
    public void updateAccount(AccountDto accountDto) {
        Account account = accountRepository.findById(accountDto.getId()).get();
        account.setName(accountDto.getName());
        account.setEmail(accountDto.getEmail());
    }

    //엑셀 파일로 계정 일괄 생성, 생성 성공 시 로그인 아이디, 비밀번호 저장된 csv 파일 전달
    public String createAccountByFile(MultipartFile file) throws IllegalArgumentException {
        //허용되지 않은 확장자 사용 시 exception
        String fileName = file.getOriginalFilename();
        if(fileName != null && !fileName.isBlank()) {
            String extension = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
            if(!extension.equals("csv")) {
                throw new IllegalArgumentException("csv 파일만 등록 가능합니다.");
            }
        }

        //csv 바로 처리
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> records = csvReader.readAll();
            List<String[]> results = new ArrayList<>();

            //첫째 줄 형식이 올바른지 체크
            String[] head = records.get(0);
            String[] correctHead = {"이름","이메일","구분"};
            if(!Arrays.equals(head, correctHead))
                throw new IllegalArgumentException("표 형식이 올바르지 않습니다.");
            if(records.size() == 1)
                //자료가 없을 시
                throw new IllegalArgumentException("표가 비어 있습니다.");

            //이메일 중복 확인용
            List<String> emails = accountRepository.findAllEmails();

            for(int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);

                System.out.println(Arrays.toString(record));
                //각 열에 대해 계정 생성
                AccountDto accountDto = new AccountDto();

                //각 열에 대해 데이터 개수가 올바르지 않을 시
                if(record.length > 3) {
                    throw new IllegalArgumentException((i+1) + "열: 데이터 형식이 올바르지 않습니다.");
                }

                //역할이 유효한지 체크
                ROLE role = switch (record[2]) {
                    case "학생" -> ROLE.STUDENT;
                    case "교사" -> ROLE.TEACHER;
                    default -> throw new IllegalArgumentException((i + 1) + "열: 역할 구분이 유효하지 않은 데이터가 있습니다.");
                };

                accountDto.setName(record[0]);
                accountDto.setEmail(record[1]);
                accountDto.setRole(role);

                //데이터 검증
                Set<ConstraintViolation<AccountDto>> violations = validator.validate(accountDto);
                if(!violations.isEmpty()) {
                    throw new IllegalArgumentException((i+1)+"열: 일부 정보가 유효하지 않습니다.");
                }

                //이메일 중복 체크
                if(emails.contains(accountDto.getEmail())) {
                    throw new IllegalArgumentException((i+1)+"열: 중복된 이메일이 있습니다: "+accountDto.getEmail());
                }

                Account account = accountDto.to();
                String loginId = createId(role);
                String password = createPw();
                account.setLoginId(loginId);
                account.setPassword(password);

                System.out.println("save");
                accountRepository.save(account);
                statRepository.findById("idIndex").get().addAccount();

                //생성된 결과 저장
                results.add(
                        new String[] {record[0], record[1], record[2], loginId, password}
                );
            }

            //생성 성공 했을 시, 생성된 로그인 아이디, 비밀번호 csv 파일로 저장
            Files.createDirectories(Paths.get("C:/uploads/admin"));
            File genFile = new File("C:/uploads/admin/생성결과.csv");
            try(CSVWriter csvWriter = new CSVWriter(new FileWriter(genFile, false)); ) {
                csvWriter.writeNext(new String[] {"이름", "이메일", "구분", "아이디", "비밀번호"});
                csvWriter.writeAll(results);
            } catch(IOException e) {
                throw new IllegalArgumentException("완료 csv 작성 중 오류가 발생했습니다.");
            }

            return "/files/admin/"+genFile.getName();
            
        } catch (IOException | CsvException e) {
            throw new IllegalArgumentException("파일 처리 중 오류가 발생했습니다.");
        }
    }

    //계정 삭제
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public void saveUserAccount(UserAccountDto userAccountDto) {
        Account account = userAccountDto.toAccount();
        accountRepository.save(account);
    }

    public boolean checkEmail(UserAccountDto userAccountDto) {
        //이메일 중복 확인용
        List<String> emails = accountRepository.findAllEmailsExcept(userAccountDto.getId());
        return emails.contains(userAccountDto.getEmail());
    }
}
