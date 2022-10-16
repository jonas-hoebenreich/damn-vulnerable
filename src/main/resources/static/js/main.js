var baseUrl = $('#baseUrl').attr('content');

function leaveComment(issueId) {
    $('#comment-link-' + issueId).removeClass('show');
    $('#comment-link-' + issueId).addClass('hidden');

    $('#comment-' + issueId).removeClass('hidden');
    $('#comment-' + issueId).addClass('show');
}

function sendComment(issueId) {
    var text = $('#comment-' + issueId + ' > textarea').val();

    $.ajax({
        url: baseUrl + 'comment/add',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            issueId: issueId,
            text: text
        })
    }).done(function () {
        location.reload();
    });
}


function checkMime(id){
    var control = document.getElementById(id);
    var files = control.files;
    var blob = files[0];
    if (blob.type == "image/jpeg" || blob.type == "image/png")
        return true;
    alert("Only jpeg or png images allowed")
    return false;
}

function upload(formId){
var msg;
$.ajax({
    url: "/api/attachment/",
    type: "POST",
    data: new FormData($('form')[0]),
    contentType: false,
    cache: false,
    processData:false,
    success: function(data) {
        console.log(returnedData);
        msg = data;
    }
}).done(function () {
                if (msg != "success"){
                    alert(msg)
                }
                location.reload();
            });
}

function sendPostReload(url, formId) {
    var msg;
    $.ajax({
        url: url,
        method: 'POST',
        data: $(formId).serialize(),
        success: function(returnedData){
            console.log(returnedData);
            msg = returnedData;
        },
    }).done(function () {
        if (msg != "success"){
            alert(msg)
        }
        location.reload();
    });
}

function cancelComment(issueId) {
    $('#comment-link-' + issueId).removeClass('hidden');
    $('#comment-link-' + issueId).addClass('show');

    $('#comment-' + issueId).removeClass('show');
    $('#comment-' + issueId).addClass('hidden');
}