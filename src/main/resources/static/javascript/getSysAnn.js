async function getSysAnn(url) {
    await fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            $('#title').text(data.title);
            $('#context').text(data.context);
            openModal($('#modal-show-announce'));
        });
}