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
                        grade : grade,
                        classroomNum : classroomNum
                    }
                );
            }
            updateList();
        }
    })
});

function updateList() {
    const toAddList = $("#to-add-list");
    toAddList.empty();
    if(toAddClasses.length > 0) {
        toAddClasses.forEach((elem) => {
            const listElem = document.createElement("li");
            $(listElem).text(elem.grade+"학년 "+elem.classroomNum+"반");
            console.log(listElem);
            console.log($(listElem).text());
            $("#to-add-list").append(listElem);
        })
    }
}