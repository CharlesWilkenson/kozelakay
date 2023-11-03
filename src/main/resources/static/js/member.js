$(document).ready(function () {

/*    */

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

function getUser() {
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
                        });}

function getUsers() {
$.ajax({
		type: "GET",
		url: "/api/content/management/service/members/view-users",
		contentType: "application/json",
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function(response) {

			$('#tableUsers tbody').empty();
			// add table rows
			$.each(response.data, (i, user) => {
				let contentRows = '<tr id="' + user.id + '">' +
					'<td>' + user.firstname + '</td>' +
					'<td>' + user.lastname + '</td>' +
					'<td>' + user.phone + '</td>' +
					'<td>' + user.email + '</td>' +
					'<td>' + user.description + '</td>' +
					'<td>' + user.status + '</td>' +
					'<td>' + user.createdDate + '</td>' +
                    '<td>' +
                    '<button type="button" id="myBtn" class="btn btn-success" onclick="getContentsByAuthor(\'' + user.email + '\');"  data-target="#update_content_modal"  data-toggle="modal"  data-placement="top"   title="Editer"><span class="fa fa-edit fa-fw"></span></button>' +
                    '<a type="button" class="btn btn-success" onclick="changeStatus(' + user.id + ');" style="color:red;; background-color:gray"  title="Editer"><span class="fa fa-trash fa-fw"></span></button>' +
                    '</td>' +
					'</tr>';
				$('#tableUsers tbody').append(contentRows);
			});
		},
		error: function(e) {
			console.log("ERROR: ", e);
		}
	});
}


                        function changeStatus(id)  {
                        $.ajax({
                                                    type: "GET",
                                                    url: "/api/content/management/service/members/change-status/"+id,
                                                    contentType: "application/json",
                                                    dataType: 'json',
                                                    cache: false,
                                                    timeout: 600000,
                                                    success: function (response) {

                            var table = document.getElementById("tableUsers");
                            for (var i = 0; i < table.rows.length; i++) {
                              for (var j = 0; j < table.rows[i].cells.length; j++){
                                table.rows[i].cells[j].onclick = function () {
                                  r = this.parentNode.rowIndex;
                                  c = this.cellIndex;

                                  table.rows[r].cells[5].innerHTML = response.data.status;
                               //   alert("you clicked on\n column: "+c+"\n row:"+r);
                                };
                              }
                            }
                                                    },
                                                    error: function (e) {
                                                        console.log("ERROR: ", e.responseText);
                                                    }
                                                });}

























