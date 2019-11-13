function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validatePhone(phone) {
    var re = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
    return re.test(phone);
}

function ajaxPostSendMessage() {

    // PREPARE FORM DATA
    var formData = {
        // language=JQuery-CSS
        name: $("#name").val(),
        email: $("#email").val(),
        phone: $("#phone").val(),
        subject: $("#subject").val(),
        message: $("#message").val()
    };

    // console.log(formData);

    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "sendEmail",
        data: JSON.stringify(formData),
        dataType: 'json'
    });
}


    $(document).ready(
        function () {

            // SUBMIT FORM
            $("#contact").submit(function (event) {
                // Prevent the form from submitting via the browser.
                event.preventDefault();
                $('#name_error').text("");
                $('#email_error').text("");
                $('#phone_error').text("");
                $('#subject_error').text("");
                $('#message_error').text("");
                $('#success_message').html("");

                var flag = 1;


                var name = $('#name').val();
                var email = $('#email').val();
                var phone = $('#phone').val();
                var subject = $('#subject').val();
                var message = $('#message').val();

                if (name.length <= 2) {
                    $('#name_error').text("Name must be at least 3 letters");
                    flag = 0;
                }
                if (validateEmail(email) == false) {
                    $('#email_error').text("Bad email");
                    flag = 0;
                }
                if (validatePhone(phone) == false) {
                    $('#phone_error').text("Bad phone");
                    flag = 0;
                }
                if (subject.length <= 2) {
                    $('#subject_error').text("Subject must be at least 3 letters");
                    flag = 0;

                }
                if (message.length < 10) {
                    $('#message_error').text("Message must be at least 10 letters");
                    flag = 0;
                }

                if (flag) {
                    $('#success_message').html("<div class='alert alert-success'><strong>Done!</strong> Message was successfully sent. </div>");
                    ajaxPostSendMessage();
                    $('#phone').val("");
                    $('#subject').val("");
                    $('#message').val("");
                }

            });

        });