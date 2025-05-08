let startId = -1;
let endId = -1;

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

    $.ajax({
        type: "POST",
        url: "/teacher/setprogress",
        data: {
            classroomId: classroomId,
            startId: startId,
            endId: endId
        },
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(result, status) {
            console.log(status);
        },
        error: function(xhr, status, error) {
            console.log(xhr.status+ " " + xhr.statusText);
        }
    });

}

function onSubjectChange(classroomId,subject) {
    console.log(subject);
    if(subject) {
        // $('#progress-form').removeClass('hidden');
        location.href="/teacher/progress?id="+classroomId+"&subject="+subject;
    } else {
        // $('#progress-form').addClass('hidden');
    }
}

function setSelectedStyle(element) {
    element.classList.remove('bg-white');
    element.classList.add('bg-red-200');
}

function setDefaultStyle(element) {
    element.classList.remove('bg-red-200');
    element.classList.add('bg-white');
}