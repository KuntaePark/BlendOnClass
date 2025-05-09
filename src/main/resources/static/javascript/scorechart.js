    let selectedSubject = null;

    const subjectSelector = document.getElementById('subjectSelector');
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

// 과목 초기화
//     subjectList.forEach(subject => {
//         const option = document.createElement('option');
//         option.value = subject;
//         option.textContent = subject;
//         subjectSelector.appendChild(option);
//     });

    // function onSubjectChange() {
    //     selectedSubject = subjectSelector.value;
    //     categorySelector.disabled = !selectedSubject;
    //
    //     if (selectedSubject) {
    //         drawMajorAverages();
    //     } else {
    //         chart.data.labels = [];
    //         chart.data.datasets = [];
    //         chart.update();
    //     }
    // }

    function onCategoryChange() {
        const category = categorySelector.value;
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