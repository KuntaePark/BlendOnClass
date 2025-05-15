function openModal(e) {
    console.log(e);
    e.toggle();
    $('#background-overlay').toggle();
}
function closeModal(e) {
    e.toggle();
    $('#background-overlay').toggle();
}

function closeAllModal() {
    $('.modal').hide();
    $('#background-overlay').toggle();
}