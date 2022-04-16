<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<title>Patient Tracker App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>FooBar Hospital</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Patient -->
			
			<input type="button" value="Add Patient" 
				   onclick="window.location.href='add-patient-form.jsp'; return false;"
				   class="add-patient-button"
			/>
			
			<table>
			
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
				
				<c:forEach var="tempPatient" items="${PATIENT_LIST}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="PatientControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="patientId" value="${tempPatient.id}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="PatientControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="patientId" value="${tempPatient.id}" />
					</c:url>
																		
					<tr>
						<td> ${tempPatient.name} </td>
						<td> ${tempPatient.email} </td>
						<td> 
							<a href="${tempLink}">Update</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this patient?'))) return false">
							Delete</a>	
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








