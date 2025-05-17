//onload
const toAddClasses = [];
let toDelete = null;
$(function() {
    $('#class-input').on('submit', function(e) {
        e.preventDefault();
        const grade = $('#grade-select').val();
        const classroomNum = $('#classroomNum').val();
        if(grade && classroomNum) {
            if(!toAddClasses.some(elem => elem.grade === grade && elem.classroomNum === classroomNum)) {
                toAddClasses.push(
                    {
                        id: null,
                        grade : grade,
                        classroomNum : classroomNum
                    }
                );
            }
            updateList();
            $('#classroomNum').focus();
        }
    })
});

//계정 목록 업데이트
function updateAuthTable(result) {
    //update table
    const $tbody = $('.admin-table tbody');
    $tbody.empty();
    result.forEach((row) => {
        console.log(row);
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
        const $aType = $('<td>').text(row.atype);
        const $td = $('<td>').addClass('action-column');

        const $deleteButton = $('<button/>', {
            "class" : "square-button-10 bg-red-400 flex",
        })
        $deleteButton.on('click', function(e) {
            const $deleteAuthModal = $('#delete-auth-modal');
            openModal($deleteAuthModal);
            toDelete = row.id;
        });

        $('<img/>', {
            "src" : "/images/icons/icon_delete.png",
            "class" : "min-w-6 h-6 invert m-auto"
        }).appendTo($deleteButton);

        $td.append($deleteButton);
        $tr.append($name, $loginId, $email, $role, $aType, $td);
        $tbody.append($tr);
    })
}

function updateList() {
    const toAddList = $("#to-add-list");
    toAddList.empty();
    if(toAddClasses.length > 0) {
        toAddClasses.forEach((elem, idx) => {
            const listElem = document.createElement("li");
            $(listElem).text(elem.grade+"학년 "+elem.classroomNum+"반");
            $(listElem).val(idx);
            const deleteBtn = document.createElement("button");
            $(deleteBtn).addClass('ml-2');
            $(deleteBtn).on('click', function() {
                //해당 요소 지우기
                const liIdx = $(this).parent().val();
                $(this).parent().remove();
                toAddClasses.splice(idx, 1);
                updateList();
            })
            deleteBtn.innerHTML = "<img src=\"/images/icons/close.png\" class=\"w-2 h-2\" alt=\"삭제 아이콘\">"
            $(listElem).append(deleteBtn);
            $("#to-add-list").append(listElem);
        })
    }
    $("#classroomNum").val(null);
}

async function requestClassroomAdd() {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    await new Promise((resolve, reject) => {
    $.ajax({
        type: "POST",
        url: '/admin/classroom/add',
        contentType: "application/json",
        data: JSON.stringify(toAddClasses),
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result, status) {
            console.log(status);
            alert("반 생성에 성공했습니다!");
            $('#to-add-list').empty();
            toAddClasses.splice(0, toAddClasses.length);
            $('#classroomNum').val(null);
        },
        error: function(xhr, status, error) {
            $('#classroomNum').val(null);
            alert(xhr.responseText);
            console.log(xhr.status+ " " + xhr.statusText);
        }
        });
    });
}

function deleteAuth() {
    alert("delete auth"+toDelete);
    toDelete = null;
}