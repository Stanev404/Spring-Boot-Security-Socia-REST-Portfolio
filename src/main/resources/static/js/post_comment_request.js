function render(data) {

    var html = "<div class='row text-center'>";
    html += "<div class='col-12' style='text-align: center;margin: 20px 0;'>";
    html += "<div class='card'>";
    html += "<div class='card-header'>New Comment! </div><div class='card-body'><blockquote class='blockquote text-center'><p class='mb-0'>" + data.contentOfComment + "</p><footer class='blockquote-footer text-center' style='color:#c7d8cd;height: 80px;' >Said by: <cite title='Source Title' style='color:#ffff;'>" + data.publisherName + "</cite></footer></blockquote></div></div></div></div>";
    $("#comment-container").prepend(html);
}

$(document).ready(
    function () {

        // SUBMIT FORM
        $("#commentForm").submit(function (event) {
            // Prevent the form from submitting via the browser.
            event.preventDefault();
            if ($('#contentOfComment').val() == "") {
                $("#emptyComment").modal()
                // alert("You must enter comment!");
                return;
            }
            if ($('#publisherName').val() == "") {
                alert("You must enter name!");
                return;
            }
            ajaxPost();
        });

        function ajaxPost() {

            // PREPARE FORM DATA
            var formData = {
                // language=JQuery-CSS
                publisherName: $("#publisherName").val(),
                contentOfComment: $("#contentOfComment").val()
            };

            // DO POST
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "comment",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    render(formData);
                    $('#contentOfComment').val('');

                }

            });


// Scroll-Top
            var offset = 250;
            var duration = 500;
            $(window).scroll(function () {
                if ($(this).scrollTop() > offset) {
                    $('.to-top').fadeIn(duration);
                } else {
                    $('.to-top').fadeOut(duration);
                }
            });

            $('#scr').click(function () {

                $('html,body').animate({
                    scrollTop: 0
                }, duration);
            })

        }

    });