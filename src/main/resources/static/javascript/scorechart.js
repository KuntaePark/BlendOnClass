const filters = {
    grade: null,
    subject: null,
}

let data = null;
let chart = null;

//onload
$(function() {
    const ctx = document.getElementById('myChart').getContext('2d');
    chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [],
            datasets: []
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        }
    });
    if(classroomId) {filters.grade = grade};
    $('#grade-select').on('change', function() {onFilterChange($(this).val(), "grade")});
    $('#subj-select').on('change', function() {onFilterChange($(this).val(), "subject")});
});

async function onFilterChange(value, filterType) {
    filters[filterType] = value;
    console.log(filters);
    if(filters.grade && filters.subject) {
        console.log("here");
        //해당 필터 내용으로 데이터 요청
        await requestData();
        onCategoryChange();
    }
}

function requestData() {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "get",
            url: "/getScore",
            data: {
                id: classroomId,
                grade: filters.grade,
                subject: filters.subject,
            },
            success: function(result, status) {
                console.log("data fetched\n"+result);
                data = result;
                resolve("success");
            },
            error: function(xhr, status, error) {
                // 데이터 로딩 오류
                console.log(xhr.status+ " " + xhr.statusText);
                reject(xhr.responseText);
            }
        });
    })
}

function onCategoryChange() {
    const category = $('#category-select').val();
    if(!category) {return;}
    if (category === 'chapter') {
        drawByChapter();
    } else if (category === 'lesson') {
        drawByLesson();
    }
}

function drawByChapter() {
    const labels = Array.from(
        new Set(
            data.map(
                lessonData=>lessonData.chapterTitle)));
    const completeRateData = [];
    const attemptCountData = [];
    labels.forEach(label => {
        let completeRates = 0;
        let attemptCounts = 0;
        let count = 0;
        data.filter(data => {
            if(data.chapterTitle === label) {
                completeRates += data.completeRate??0;
                attemptCounts += data.attemptCount??0;
                count++;
            }
        })
        console.log(attemptCounts);
        completeRateData.push(completeRates / count);
        attemptCountData.push(attemptCounts / count);
    })

    console.log(completeRateData, attemptCountData, labels);

    chart.data.labels = labels;
    chart.data.datasets = [{
        label: `${'수학'} - 대단원별 평균 완수율`,
        data: completeRateData,
        backgroundColor: 'rgba(13, 101, 211, 0.8)'
    },
    {
        label: `${'수학'} - 대단원별 평균 시도횟수`,
        data: attemptCountData,
        backgroundColor: 'rgba(124, 62, 255, 0.8)'
    }
    ];
    chart.update();
}

function drawByLesson() {
    const labels = [];
    const completeRateData = [];
    const attemptCountData = [];

    data.map(lessonData => {
        labels.push(lessonData.lessonTitle);
        completeRateData.push(lessonData.completeRate??0);
        attemptCountData.push(lessonData.attemptCount??0);
    })

    console.log(completeRateData, attemptCountData, labels);

    chart.data.labels = labels;
    chart.data.datasets = [{
        label: `${'수학'} - 소단원별 완수율`,
        data: completeRateData,
        backgroundColor: 'rgba(13, 101, 211, 0.8)'
    },
    {
        label: `${'수학'} - 소단원별 시도 횟수`,
        data: attemptCountData,
        backgroundColor: 'rgba(124, 62, 255, 0.8)'
    }];
    chart.update();
}