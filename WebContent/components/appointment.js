console.log('Hello World')

$(document).ready(function()

 {
	 $("#alertSuccess").hide();
	 $("#alertError").hide();
 });

//SAVE........
$(document).on("click", "#btnSave", function(event)
{
	//Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide(); 


//Form validation-------------------
var status = validateAppointmentForm();
if (status != true)
 {
	 $("#alertError").text(status);
	 $("#alertError").show();
 return;
 }  

//If valid------------------------
var type = ($("#hidAppointmentIDSave").val() == "") ? "POST" : "PUT"; 

$.ajax(
		{
		 url : "AppointmentsAPI",
		 type : type,
		 data : $("#formAppointment").serialize(),
		 dataType : "text",
		 complete : function(response, status)
		 {
			 onAppointmentSaveComplete(response.responseText, status);
		 }
	});
});

function onAppointmentSaveComplete(response, status)
{   
	if (status == "success")
	 {
		 var resultSet = JSON.parse(response);
		 
		 if (resultSet.status.trim() == "success")
		 {
			 $("#alertSuccess").text("Successfully saved.");
			 $("#alertSuccess").show();
			 
			 $("#divAppointmentsGrid").html(resultSet.data);
		 }	else if (resultSet.status.trim() == "error")
		 {
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		 }
	 } else if (status == "error")
	 {
		 $("#alertError").text("Error while saving.");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while saving..");
		 $("#alertError").show();
	 }
		 $("#hidAppointmentIDSave").val("");
		 $("#formAppiontmet")[0].reset(); 
} 

//remove

$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
			 url : "AppointmentsAPI",
			 type : "DELETE",
			 data : "apt_ID=" + $(this).data("apt_ID"),
			 dataType : "text",
			 complete : function(response, status)
			 {
				 onAppointmetDeleteComplete(response.responseText, status);
			 }
		});
	});




function onAppiontmentDeleteComplete(response, status)
{
	if (status == "success")
	 {
		 var resultSet = JSON.parse(response);
		 if (resultSet.status.trim() == "success")
	 {
		 $("#alertSuccess").text("Successfully deleted.");
		 $("#alertSuccess").show();
		 $("#divItemsGrid").html(resultSet.data);
	 } else if (resultSet.status.trim() == "error")
	 {
		 $("#alertError").text(resultSet.data);
		 $("#alertError").show();
	 }
	 } else if (status == "error")
		 {
		 $("#alertError").text("Error while deleting.");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while deleting..");
		 $("#alertError").show();
	 }
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#hidAppointmentIDSave").val($(this).closest("tr").find('#hidAppointmentIDUpdate').val());
 $("#discription").val($(this).closest("tr").find('td:eq(0)').text());
 $("#patient_id").val($(this).closest("tr").find('td:eq(1)').text());
 $("#doctor_id").val($(this).closest("tr").find('td:eq(2)').text());
 $("#hospital_id").val($(this).closest("tr").find('td:eq(3)').text());
 $("#datetime").val($(this).closest("tr").find('td:eq(4)').text());
 $("#charge").val($(this).closest("tr").find('td:eq(5)').text());
});

//CLIENT-MODEL================================================================
function validateAppiontmentForm()
{
// description
if ($("#discription").val().trim() == "")
 {
 return "Insert discription.";
 }
// patientId
if ($("#patient_id").val().trim() == "")
 {
 return "Insert patient ID.";
 }
// doctorId
if ($("#doctor_id").val().trim() == "")
 {
 return "Insert doctor id.";
 }
// hospitalId
if ($("#hospital_id").val().trim() == "")
{
return "Insert hospital id.";
}
//datetime
if ($("#datetime").val().trim() == "")
{
return "Insert datetime.";
}
// charge
var tmpPrice = $("#charge").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert charge.";
 }
// convert to decimal charge
 $("#charge").val(parseFloat(tmpPrice).toFixed(2));
// DESCRIPTION------------------------

return true;
}
