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
    const buttonGroups = document.querySelectorAll('.lesson-step-group');

    if (startId < 0 || endId < 0) {
        buttonGroups.forEach(group => {
            const btn = group.querySelector('button');
            const connector = group.querySelector('.connector-line');
            const val = parseInt(btn.value);

            // 버튼은 선택된 것만 파란색, 나머지는 회색
            if (val === startId || val === endId) {
                setSelectedStyle(btn);
            } else {
                setDefaultStyle(btn);
            }

            // 연결선은 전부 회색으로 초기화
            setLineStyle(connector, false);
        });

        $('#submit-button').attr('disabled', true);
        return;
    }


    // 선택 범위 있는 경우
    buttonGroups.forEach(group => {
        const btn = group.querySelector('button');
        const connector = group.querySelector('.connector-line');
        const val = parseInt(btn.value);

        if (val >= startId && val <= endId) {
            setSelectedStyle(btn);
            // endId의 오른쪽 연결선은 제외
            if (val !== endId) {
                setLineStyle(connector, true);
            } else {
                setLineStyle(connector, false);
            }
        } else {
            setDefaultStyle(btn);
            setLineStyle(connector, false);
        }
    });

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
    element.classList.remove('bg-gray-300');
    element.classList.add('bg-blue-400');
}

function setDefaultStyle(element) {
    element.classList.remove('bg-blue-400');
    element.classList.add('bg-gray-300');
}

function setLineStyle(line, selected) {
    if (!line) return;
    if (selected) {
        line.classList.remove('bg-gray-300');
        line.classList.add('bg-blue-400');
    } else {
        line.classList.remove('bg-blue-400');
        line.classList.add('bg-gray-300');
    }
}