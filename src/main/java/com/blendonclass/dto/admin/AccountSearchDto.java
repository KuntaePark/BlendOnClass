package com.blendonclass.dto.admin;

import com.blendonclass.constant.ROLE;
import com.blendonclass.constant.STYPE;
import com.blendonclass.constant.SUBJECT;
import lombok.Getter;
import lombok.Setter;

/*
    계정 목록에서 검색용
 */
@Getter@Setter
public class AccountSearchDto {
    private int pageNum;
    private String keyword = "";
    private STYPE searchType = STYPE.NAME;
    private ROLE roleType = null;
}
