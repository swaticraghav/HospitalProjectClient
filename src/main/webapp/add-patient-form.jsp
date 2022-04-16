<!DOCTYPE html>
<html>

<head>
	<title>Add Patient</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-patient-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>FooBar Hospital</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Add Patient</h3>
		
		<form action="PatientControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Name:</label></td>
						<td><input type="text" name="name" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="PatientControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>











