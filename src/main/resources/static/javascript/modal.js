function openModal(e) {
    e.toggle();
    $('#background-overlay').toggle();
}
function closeModal(e) {
    e.toggle();
    $('#background-overlay').toggle();
}

function closeAllModal() {
    $('.modal-container').hide();
    $('#background-overlay').toggle();
}