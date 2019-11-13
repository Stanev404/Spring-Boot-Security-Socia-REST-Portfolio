// $(document).ready(
//     function () {
//
//         // SUBMIT FORM
//         $("#commentForm").submit(function (event) {
//             // Prevent the form from submitting via the browser.
//             event.preventDefault();
//             if ($('#contentOfComment').val() == "") {
//                 alert("You must enter comment!");
//                 return;
//             }
//             if ($('#publisherName').val() == "") {
//                 alert("You must enter name!");
//                 return;
//             }
//             ajaxPost();
//         });
//
//         function ajaxPost() {
//
//             // PREPARE FORM DATA
//             var formData = {
//                 // language=JQuery-CSS
//                 publisherName: $("#publisherName").val(),
//                 contentOfComment: $("#contentOfComment").val()
//             };
//
//             // DO POST
//             $.ajax({
//                 type: "POST",
//                 contentType: "application/json",
//                 url: "comment",
//                 data: JSON.stringify(formData),
//                 dataType: 'json',
//                 success: function (result) {
//                     render(formData);
//                     $('#publisherName').val('');
//                     $('#contentOfComment').val('');
//
//                 }
//
//             });
//         }