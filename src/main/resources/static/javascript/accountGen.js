function onCreateOptionChange(e) {
    // alert("option change "+$(e).val());
    switch($(e).val()) {
        case '1':
            $('#single-create').show();
            $('#multi-create').hide();
            break;
        case '2':
            $('#single-create').hide();
            $('#multi-create').show();
            break;
        default:
            $('#single-create').hide();
            $('#multi-create').hide();
    }
}

function onMultiSubmit(e) {
    console.log("submit");
}
