const filters = {
    grade: null,
    subject: null,
}


const categorySelector = document.getElementById('categorySelector');

const ctx = document.getElementById('myChart').getContext('2d');
const chart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: [],
        datasets: []
    },
    options: {
        responsive: true,
        plugins: {
            zoom : {
                pan : {
                    enabled: true,
                    mode: 'xy'
                },
                zoom: {
                    wheel: {enabled: true},
                    pinch: {enabled: true},
                    mode: 'xy'
                }
            }
        },
        scales: { y: { beginAtZero: true, max: 100 } }
    }
});

//onload
$(function() {
    $('#grade-select').on('change', function() {onFilterChange($(this).val(), "grade")});
    $('#subj-select').on('change', function() {onFilterChange($(this).val(), "subject")});
});

function onFilterChange(value, filterType) {
    console.log("changed");
    filters[filterType] = value;
    console.log(filters);
    if(filters.grade && filters.subject) {
        console.log("filter hit");
        //해당 필터 내용으로 데이터 요청
        requestData();


    }
}

function requestData() {
    $.ajax({
        type: "get",
        url: "/getScore",
        data: {
            id: classroomId,
            grade: filters.grade,
            subject: filters.subject,
        },
        success: function(result, status) {
            console.log(result);

        },
        error: function(xhr, status, error) {
            console.log(xhr.status+ " " + xhr.statusText);
        }
    });
}

function onCategoryChange() {
    const category = $('#category-select').val();
    if (category === 'major') {
        drawMajorAverages();
    } else if (category === 'minor') {
        drawMinorValues();
    }
}

function drawMajorAverages() {
    const labels = originalData
        .map(chapterData=>chapterData.chapterTitle);
    const completeRateData = [];
    const attemptCountData = [];
    originalData.map(chapterData=>{
        const completeRates = chapterData.scoreUnits.map(score=>score.completeRate);
        const attemptCounts = chapterData.scoreUnits.map(score=>score.attemptCount);
        completeRateData.push(completeRates.reduce((a, b) => a + b, 0) / completeRates.length);
        attemptCountData.push(attemptCounts.reduce((a, b) => a + b, 0)/ attemptCounts.length);
    })

    chart.data.labels = labels;
    chart.data.datasets = [{
        label: `${'수학'} - 대단원별 평균 완수율`,
        data: completeRateData,
        backgroundColor: 'rgba(54, 162, 235, 0.6)'
    },
    {
        label: `${'수학'} - 대단원별 평균 시도횟수`,
        data: attemptCountData,
        backgroundColor: 'rgba(54, 162, 235, 0.6)'
    }
    ];
    chart.update();
}

function drawMinorValues() {
    const labels = [];
    const completeRateData = [];
    const attemptCountData = [];

    originalData.map(chapterData=>{
        chapterData.scoreUnits.forEach(score=> {
            labels.push(score.lessonTitle);
            completeRateData.push(score.completeRate);
            attemptCountData.push(score.attemptCount);
        })
    });

    chart.data.labels = labels;
    chart.data.datasets = [{
        label: `${'수학'} - 소단원별 완수율`,
        data: completeRateData,
        backgroundColor: 'rgba(54, 162, 235, 0.6)'
    },
    {
        label: `${'수학'} - 소단원별 시도 횟수`,
        data: attemptCountData,
        backgroundColor: 'rgba(54, 162, 235, 0.6)'
    }];
    chart.update();
}