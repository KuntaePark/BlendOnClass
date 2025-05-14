//onload
const toAddClasses = [];
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

function updateList() {
    const toAddList = $("#to-add-list");
    toAddList.empty();
    if(toAddClasses.length > 0) {
        toAddClasses.forEach((elem) => {
            const listElem = document.createElement("li");
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