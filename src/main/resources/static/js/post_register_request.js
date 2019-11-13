function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
function validatePassword(p){
    if (p.length < 8) {
        $('#pass_error').text("Password must be at least 8 characters");
        return 0;
    }
    if (p.search(/[a-z]/i) < 0) {
        $('#pass_error').text("Your password must contain at least one letter");
        return 0;
    }
    if (p.search(/[0-9]/) < 0) {
        $('#pass_error').text("Your password must contain at least one digit");
        return 0;
    }
    return 1;
}

function ajaxPostRegisterUser() {

    // PREPARE FORM DATA
    var formData = {
        // language=JQuery-CSS
        email: $("#email").val(),
        name: $("#name").val(),
        encrytedPassword : $("#pass").val()
    };


    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "registerUser",
        data: JSON.stringify(formData),
        dataType: 'json',
        statusCode: {
            409: function() {
                $('#email_error').text("User with this email exists");
                $('#pass').val("");
                $('#re_pass').val("");
                $('#re_pass_error').val("");
            },
            201: function () {
// show modal
                $('#successfulRegisterModal').modal('toggle');

                $('#name').val("");
                $('#email').val("");
                $('#pass').val("");
                $('#re_pass').val("");
                $('#re_pass_error').val("");
                $('#agree-term').prop('checked', false);
            }
        }
    });
}


$(document).ready(



    function () {



        $('#pass, #re_pass').on('keyup', function () {
            if ($('#pass').val() == $('#re_pass').val()) {
                $('#re_pass_error').text('Matching').css('color', 'green');
            } else
                $('#re_pass_error').text('Not Matching').css('color','red');
        });

        // SUBMIT FORM
        $("#register-form").submit(function (event) {
            // Prevent the form from submitting via the browser.
            event.preventDefault();
            $('#name_error').text("");
            $('#email_error').text("");
            $('#pass_error').text("");
            $('#re_pass_error').text("");
            $('#agree_term_error').text("");
            $('#success_message').html("");

            isOk = 1;



            var name = $('#name').val();
            var email = $('#email').val();
            var pass = $('#pass').val();

            if (name.length <= 2) {
                $('#name_error').text("Name must be at least 3 letters");
                // $("#label_name").css("top", "32%");
                isOk = 0;
            }
            if (validateEmail(email) == false) {
                $('#email_error').text("Bad email");
                // $("#email").css("color", "red");
                isOk = 0;
            }

            isOk = validatePassword(pass);


            //re_pass logic

            $('#pass, #re_pass').on('keyup', function () {
                if ($('#pass').val() == $('#re_pass').val()) {
                    $('#re_pass_error').text('Matching').css('color', 'green');
                    isOk = 1;
                } else {
                    $('#re_pass_error').text('Not Matching');
                    isOk = 0;
                }
            });



            if(($('#agree-term').is(":checked")) == false){
                $('#agree_term_error').text("You must accept the terms")
                isOk = 0;
            }




            if (isOk) {
                ajaxPostRegisterUser();
            }

        });

    });