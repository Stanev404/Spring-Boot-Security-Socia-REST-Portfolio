 $(document).ready(
    function() {

        // GET REQUEST

            ajaxGet();

        cnt = 0;
        // DO GET
        function ajaxGet() {
            $.ajax({
                type : "GET",
                url : "comments",
                success : function(result) {
                    // console.log(result);

                    $.each(result, function(i, comment) {

                            var html = "<div class='row text-center'>";
                            html += "<div class='col-12' style='text-align: center;margin: 20px 0;'>";
                            html += "<div class='card'>";
                            html += "<div class='card-header'>Comment " + comment.id + "</div><div class='card-body'><blockquote class='blockquote text-center'><p class='mb-0'>" + comment.contentOfComment + "</p><footer class='blockquote-footer text-center' style='color:#c7d8cd;height: 80px;' >Said by: <cite title='Source Title' style='color:#ffff;'>" + comment.publisherName + "</cite></footer></blockquote></div></div></div></div>";
                            $("#comment-container").prepend(html);
                            });


                }
            });
        }
    });