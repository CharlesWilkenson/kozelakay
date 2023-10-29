$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "/api/content/management/service/members/view-profile",
        contentType: "application/json",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (response) {
            $("#firstname").val(response.data.firstname);
            $("#lastname").val(response.data.lastname);
            $("#phone").val(response.data.phone);
            $("#description").val(response.data.description);
            document.getElementById("email").innerHTML = response.data.email;
        },
        error: function (e) {
            console.log("ERROR: ", e.responseText);
        }
    });

    // SUBMIT FORM
    $("#profileForm").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        //ajaxPost();
        const formData = {
            firstname: $("#firstname").val(),
            lastname: $("#lastname").val(),
            phone: $("#phone").val(),
            description:$("#description").val()
        };

        // DO POST
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/api/content/management/service/members/edit",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (result) {
                successMessage (result);
            },
            error: function (error) {
                createErrorValidationResponse(error);
            }
        });
    });

    // SUBMIT FORM
    $("#formRegister").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault()

        //ajaxPost();
        const formData = {
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            rePassword: $("#rePassword").val(),
        };

        // DO POST
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/content/management/service/members/register",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (result) {
                successMessage (result);
            },
            error: function (error) {
                createErrorValidationResponse(error);
            }
        });
    });

});