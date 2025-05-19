//onload
$(function() {
    $('#searchbar-form').on('submit', function(e) {
        e.preventDefault();
        getSearchResult();
    })
})

//계정 검색
async function getSearchResult(pageNum) {
    await new Promise((resolve, reject) => {
        const data = {
            pageNum,
            keyword: $("#keyword").val(),
            searchType : 'NAME',
            roleType: $("#roleType").val(),
        }
        $.ajax({
            type: "GET",
            url: "/admin/accounts/search",
            data,
            success: function(result) {
                console.log(result);
                updateAccountTable(result);
                updatePaging(result);
                resolve(result);
            },
            error: function(xhr, result, status) {
                console.log(xhr.responseText);
                console.log(status);
                reject(xhr.responseText);
            }
        });
    })
}

//계정 목록 업데이트
function updateAccountTable(result) {
    //update table
    const $tbody = $('.admin-table tbody');
    $tbody.empty();
    result.content.forEach((row) => {
        const $tr = $('<tr>');
        const $name = $('<td>').text(row.name);
        const $loginId = $('<td>').text(row.loginId);
        const $email = $('<td>').text(row.email);

        let roleText;
        const authorities = getAuthorities(row.email);
        console.log(authorities);
        //권한 들고 있는지 체크
        //보유 시,
        //학생 -> 비활성화
        //교사 -> 1개면 활성, 2개면 비활
        const $td = $('<td>').addClass('action-column');
        let $modifyButton = null;

        switch(row.role) {
            case 'STUDENT':
                roleText = '학생';
                if(authorities.length > 0) {
                    //권한 있음, 버튼 비활
                    $modifyButton = setDisabled();
                } else {
                    $modifyButton = setAble();
                    $modifyButton.on('click', function() {
                        //모달 열고 확인 시 생성
                        $('#student-name').text(row.name);
                        $('#student-add-form').attr(
                            'action', '/admin/classroom/authAdd?id='+row.id+'&classroomId='+classroomDto.id
                        )
                        openModal($('#student-add-modal'));
                    })
                }
                break;
            case 'TEACHER':
                roleText = '교사';
                if(authorities.length === 2) {
                    //권한 풀
                    $modifyButton = setDisabled();
                } else {
                    let ownedAuthText= null;
                    if(authorities.length === 1) {
                        //권한 하나
                        $modifyButton = setHalfAble();
                        ownedAuthText = '보유 중인 권한: '+authorities[0].atype;
                    } else {
                        $modifyButton = setAble();
                        ownedAuthText ='';
                    }
                    $modifyButton.on('click', function() {
                        $('#teacher-name').text(row.name);
                        $('#teacher-add-form').attr(
                            'action', '/admin/classroom/authAdd?id='+row.id+'&classroomId='+classroomDto.id
                        )
                        $('#owned-auth').text(ownedAuthText);
                        openModal($('#teacher-add-modal'));
                    })
                }
                break;
        }

        const $role = $('<td>').text(roleText);

        $td.append($modifyButton);
        $tr.append($name, $loginId, $email, $role, $td);
        $tbody.append($tr);
    })
}

function updatePaging(result) {
    //이전 또는 다음 페이지 없을 시 각 버튼 비활성화
    const $prev = $('#prev');
    const $next = $('#next');
    if(result.number === 0) {
        $prev.hide();
    } else {
        $prev.show();
        $prev.attr('onclick','getSearchResult('+(result.number - 1)+')');
    }

    if(result.number === (result.totalPages - 1)) {
        $next.hide();
    } else {
        $next.show();
        $next.attr('onclick','getSearchResult('+(result.number + 1)+')');
    }

    //페이지 정보 갱신
    const startNum = result.number * result.size + 1;
    const endNum = result.number * result.size + result.numberOfElements;
    $('#page-info').text('총 '+result.totalElements+' 개의 결과 중 '+startNum+'...'+endNum+'');

}

function getAuthorities(email) {
    return authListDtos.filter(item => item.email === email);
}

function setDisabled() {
    //초록 체크 버튼
    const $modifyButton = $('<button/>', {
        "class" : "square-button-10-disabled flex bg-green-400",
        'disabled' : true,
    })
    $('<img/>', {
        "class" : "min-w-4 h-4 invert m-auto",
        'src' : '/images/icons/icon_check.png',
        'alt' : '체크 이미지',
    }).appendTo($modifyButton);
    return $modifyButton;
}

function setAble() {
    //회색 버튼
    const $modifyButton = $('<button/>', {
        "class" : "square-button-10 flex bg-(--primary-color)",
    })
    $('<img/>', {
        "class" : "min-w-4 h-4 invert m-auto",
        'src' : '/images/icons/icon_add.png',
        'alt' : '추가 아이콘',
    }).appendTo($modifyButton);
    return $modifyButton;
}

function setHalfAble() {
    //노랑 버튼
    const $modifyButton = $('<button/>', {
        "class" : "square-button-10 flex bg-yellow-400",
    })
    $('<img/>', {
        "class" : "min-w-4 h-4 invert m-auto",
        'src' : '/images/icons/icon_add.png',
        'alt' : '추가 아이콘',
    }).appendTo($modifyButton);
    return $modifyButton;
}