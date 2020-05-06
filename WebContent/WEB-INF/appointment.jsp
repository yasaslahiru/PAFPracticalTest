<%@page import="model.AppointmentsAPI"%>
<%@page import="model.Appointment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointment management</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>Appointment management</h1>
				<form id="formAppointment" name="formAppointment" method="post" action="AppointmentsAPI">
					Description:
					<input id="appointmentDesc" name="appointmentDesc" type="text"
						class="form-control form-control-sm"> <br> 
					Patient ID: 
					<input id="patientId" name="patientId" type="text"
						class="form-control form-control-sm"> <br> 
					Doctor ID: 
					<input id="doctorId" name="doctorId" type="text"
						class="form-control form-control-sm"> <br> 
					Hospital ID: 
					<input id="hospitalId" name="hospitalId" type="text"
						class="form-control form-control-sm"> <br> 
					Date & Time: 
					<input id="dateTime" name="dateTime" type="text"
						class="form-control form-control-sm"> <br> 
					Charge: 
					<input id="charge" name="charge" type="text"
						class="form-control form-control-sm"> <br> 			
					<input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> 
					<input type="hidden"
						id="hidAppointmentIDSave" name="hidAppointmentIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success">
					<%
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>

				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<%
					Appointment appobj = new Appointment();
					out.print(appobj.readAppointment());
				%>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.0.min.js" integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="./components/item.js"></script>
</body>
</html>