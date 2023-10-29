$(document).ready(function() {
	/**
	 * get data from Backend's REST API
	 */
	$.ajax({
		type: "GET",
		url: "/api/content/management/service/contents/",
		contentType: "application/json",
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function(response) {

			$('#tableContent tbody').empty();
			// add table rows
			$.each(response.data, (i, content) => {
				let contentRows = '<tr>' +
					'<td>' + content.id + '</td>' +
					'<td>'
					+ "<div className=avatar>"
					+ "<div className=picture-container>"
					+ "<div className=picture>"
					+ "<img src=/api/content/management/service/contents/getImage?id=" + content.id + " height=30  width=30 className=picture-src id=wizardPicturePreview-2 title=>"
					+ "</div>"
					+ "</div>"
					+ "</div>"
					+ '</td>' +
					'<td>' + content.title + '</td>' +
					'<td>' + content.brief + '</td>' +
					'<td>' + content.createdDate + '</td>' +
                    '<td>' +
                    '<button type="button" id="myBtn" class="btn btn-success" onclick="getContent(\'' + content.id + '\');"  data-target="#update_content_modal"  data-toggle="modal"  data-placement="top"   title="Editer"><span class="fa fa-edit fa-fw"></span></button>' +
                    '<button type="button" class="btn btn-success"   data-target="#update_shop_modal"  data-toggle="modal"  data-placement="top"   title="Editer"><span class="fa fa-trash fa-fw"></span></button>' +
                    '</td>' +
					'</tr>';
				$('#tableContent tbody').append(contentRows);
			});
		},
		error: function(e) {
			console.log("ERROR: ", e);
		}
	});

	$("#btnAdd").click(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();

		// Get form
		var form = $('#contentForm')[0];

		var formData = new FormData(form);

		$.ajax({
			type: "POST",
			contentType: "application/json",
			enctype: 'multipart/form-data',
			url: "/api/content/management/service/contents/add",
			data: formData,
			processData: false, //prevent jQuery from automatically transforming the data into a query string
			contentType: false,
			cache: false,
			dataType: 'json',
			success: function(result) {
				successMessage(result);
			},
			error: function(error) {
				createErrorValidationResponse(error);
			}
		});
	});

	$("#btnUpdate").click(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();

		// Get form
		var form = $('#contentForm')[0];
		var formData = new FormData(form);

		$.ajax({
			type: "PUT",
			contentType: "application/json",
			enctype: 'multipart/form-data',
			url: "/api/content/management/service/contents/edit",
			data: formData,
			processData: false, //prevent jQuery from automatically transforming the data into a query string
			contentType: false,
			cache: false,
			dataType: 'json',
			success: function(result) {

			successMessage(result);
            modal.style.display = "none";
			},
			error: function(error) {
			alert(error.responseText);
				createErrorValidationResponse(error);
			}
		});
	});
});


function getContent(id){
	$.ajax({
		type: "GET",
		url: "/api/content/management/service/contents/view-content/"+id,
		contentType: "application/json",
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function(returnData) {
		    // Get the modal
            var modal = document.getElementById("update_content_modal");
            // When the user clicks the button, open the modal
              modal.style.display = "block";
            $("#contentForm").trigger('reset'); //jquery
            document.getElementById("contentForm").reset(); //native JS
            $("#title").val(returnData.data.title);
            $("#brief").val(returnData.data.brief);
            $('#img-preview').attr('src', '/api/content/management/service/contents/getImage?id=' + returnData.data.id);
            $("#content").val(returnData.data.content);
            $("#id").val(returnData.data.id);
			},
			error: function(error) {
				createErrorValidationResponse(error);
			}
		});
}