
function deleteAccount(id) {
    alert("계정 삭제");
}


//계정 검색
async function getSearchResult(pageNum) {
    await new Promise((resolve, reject) => {
        const data = {
            pageNum
        }
        $.ajax({
            type: "GET",
            url: "/admin/announce",
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
        const $modifyButton = $('<a/>', {
            "class" : "square-button-10 bg-(--primary-color) flex",
            "href" : "/admin/modifyAccount?id="+row.id,
        })
        $('<img/>', {
            "src" : "/images/icons/icon_modify.png",
            "class" : "min-w-6 h-6 invert m-auto"
        }).appendTo($modifyButton);

        const $deleteButton = $('<button/>', {
            "class" : "square-button-10 bg-red-400 flex",
            "onclick" : "deleteAccount("+row.id+")",
        })
        $('<img/>', {
            "src" : "/images/icons/icon_delete.png",
            "class" : "min-w-6 h-6 invert m-auto"
        }).appendTo($deleteButton);

        $td.append($modifyButton, $deleteButton);
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