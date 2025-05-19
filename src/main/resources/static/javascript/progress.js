let startId = -1;
let endId = -1;
let progress;

function selectRange(id) {
    console.log(id);
    const sElem = $('#startId');
    const eElem = $('#endId');
    if(startId === id) {
        startId = -1;
        sElem.innerHTML = '';
        return showRange();
    }
    if(endId === id) {
        endId = -1;
        eElem.innerHTML = '';
        return showRange();
    }

    if(endId < 0 && startId > id) {
        //start를 end로 옮기고 start를 id로
        endId = startId;
        startId = id;
        sElem.innerText = startId;
        eElem.innerText = endId;
        return showRange();
    }

    if(startId < 0 || startId > id) {
        startId = id;
        sElem.innerText = startId;
        return showRange();
    }

    endId = id;
    eElem.innerText = endId;
    return showRange();

}

function showRange() {
    const buttons = Array.from($('.range-button'));
    if(startId < 0 || endId < 0) {
        buttons.forEach(button => {
            if(button.value == startId || button.value == endId)
                setSelectedStyle(button);
            else
                setDefaultStyle(button);
        });
        $('#submit-button').attr('disabled', true);
        return;
    }
    buttons.forEach(button => {
        if(button.value >= startId && button.value <= endId) {
            //선택 스타일
            setSelectedStyle(button);
        } else {
            //미선택 스타일
            setDefaultStyle(button);
        }
    })
    $('#submit-button').attr('disabled', false);
}

function setProgress(classroomId) {
    console.log('working');
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    if(startId == progress.startLessonId && endId == progress.endLessonId) {
        console.log('here');
        $('#warning').text("진도가 변경되지 않았습니다.");
        return;
    }

    progress.startLessonId = startId;
    progress.endLessonId = endId;
    progress.classroomId = classroomId;
    progress.endDate = $('#end-date').val();

    $.ajax({
        type: "POST",
        url: "/teacher/setprogress",
        contentType: "application/json",
        data: JSON.stringify(progress),
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result, status) {
            console.log(status);
            location.reload();
        },
        error: function(xhr, status, error) {
            console.log(xhr.status+ " " + xhr.statusText);
        }
    });

}

function onSubjectChange(classroomId,subject) {
    console.log(subject);
    if(subject) {
        console.log("subject");
        location.href="/teacher/progress?id="+classroomId+"&subject="+subject;
    } else {
        console.log("nosubject");
        location.href="/teacher/progress?id="+classroomId;
    }
}

function setSelectedStyle(element) {
    element.classList.remove('bg-white');
    element.classList.add('bg-(--primary-color)');
}

function setDefaultStyle(element) {
    element.classList.remove('bg-(--primary-color)');
    element.classList.add('bg-white');
}