 const dataMap = {
        '수학': {
            '대단원1': { '소단원1-1': 80, '소단원1-2': 90 },
            '대단원2': { '소단원2-1': 70, '소단원2-2': 85 }
        },
        '과학': {
            '대단원1': { '소단원1-1': 88, '소단원1-2': 76 },
            '대단원2': { '소단원2-1': 82, '소단원2-2': 91 }
        }
    };


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
            plugins: { legend: { display: true } },
            scales: { y: { beginAtZero: true, max: 100 } }
        }
    });

// 과목 초기화
    Object.keys(dataMap).forEach(subject => {
        const option = document.createElement('option');
        option.value = subject;
        option.textContent = subject;
        subjectSelector.appendChild(option);
    });

    function onSubjectChange() {
        selectedSubject = subjectSelector.value;
        categorySelector.disabled = !selectedSubject;

        if (selectedSubject) {
            drawMajorAverages();
        } else {
            chart.data.labels = [];
            chart.data.datasets = [];
            chart.update();
        }
    }

    function onCategoryChange() {
        const category = categorySelector.value;
        if (category === 'major') {
            drawMajorAverages();
        } else if (category === 'minor') {
            drawMinorValues();
        }
    }

    function drawMajorAverages() {
        const majors = dataMap[selectedSubject];
        const labels = [];
        const data = [];

        Object.entries(majors).forEach(([major, minors]) => {
            const scores = Object.values(minors);
            const avg = scores.reduce((a, b) => a + b, 0) / scores.length;
            labels.push(major);
            data.push(avg);
        });

        chart.data.labels = labels;
        chart.data.datasets = [{
            label: `${selectedSubject} - 대단원 평균`,
            data,
            backgroundColor: 'rgba(54, 162, 235, 0.6)'
        }];
        chart.update();
    }

    function drawMinorValues() {
        const minorsMap = new Map(); // key: 소단원, value: 평균값 (또는 단일값)

        Object.values(dataMap[selectedSubject]).forEach(minors => {
            Object.entries(minors).forEach(([minor, score]) => {
                if (!minorsMap.has(minor)) {
                    minorsMap.set(minor, []);
                }
                minorsMap.get(minor).push(score);
            });
        });

        const labels = [];
        const data = [];

        minorsMap.forEach((scores, minor) => {
            const avg = scores.reduce((a, b) => a + b, 0) / scores.length;
            labels.push(minor);
            data.push(avg);
        });

        chart.data.labels = labels;
        chart.data.datasets = [{
            label: `${selectedSubject} - 소단원 점수`,
            data,
            backgroundColor: 'rgba(255, 159, 64, 0.6)'
        }];
        chart.update();
    }