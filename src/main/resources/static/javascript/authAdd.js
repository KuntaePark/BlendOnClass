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
        switch(row.role) {
            case 'STUDENT':
                roleText = '학생';
                break;
            case 'TEACHER':
                roleText = '교사';
                break;
        }
        const $role = $('<td>').text(roleText);
        const $td = $('<td>').addClass('action-column');
        const $modifyButton = $('<button/>', {
            "class" : "square-button-10 bg-(--primary-color) flex",
        })
        $modifyButton.on('click', function(e) {
            //권한 추가 모달 열기
            alert("open add");
        })
        $('<img/>', {
            "src" : "/images/icons/icon_add.png",
            "class" : "min-w-4 h-4 invert m-auto"
        }).appendTo($modifyButton);

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
        $prev.attr('onclick','getSearchResult(event,'+(result.number - 1)+')');
    }

    if(result.number === (result.totalPages - 1)) {
        $next.hide();
    } else {
        $next.show();
        $next.attr('onclick','getSearchResult(event,'+(result.number + 1)+')');
    }

    //페이지 정보 갱신
    const startNum = result.number * result.size + 1;
    const endNum = result.number * result.size + result.numberOfElements;
    $('#page-info').text('총 '+result.totalElements+' 개의 결과 중 '+startNum+'...'+endNum+'');

}