function createErrorValidationResponse(error) {
    const obj = JSON.parse(error.responseText);

    if(error.status === 400 && Array.isArray(obj)){
        $.each(JSON.parse(error.responseText), function (key, value) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if (!$('input[name=' + value.fieldName + ']').next("span")[0]) {
                let query = $('input[name=' + value.fieldName + ']');
                query.after('<span>' + value.message + '</span>');
                query.addClass("has-error").removeClass("has-success");
                query.next("span").css("color", "#FF0000");
            }
        });
    }else{
        document.getElementById("alert-success").style.display = "none";
        document.getElementById("alert-danger").style.display = "inline";
        document.getElementById("alert-danger").innerHTML = obj.message;
        console.log(obj.message)
    }

}








