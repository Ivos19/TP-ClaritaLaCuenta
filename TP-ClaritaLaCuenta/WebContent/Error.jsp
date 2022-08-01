<%@page import="entity.Persona"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="icon" href="../imagenes/pngegg.png">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/start.css" rel="stylesheet">

<link rel="icon" href="imagenes/pngegg.png">
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
<script type="text/javascript">
	function submitForm() {
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
	}
</script>

</head>
<body>

	<div class="container">

		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<h1 class="navbar-brand">
				<img src="../imagenes/pngegg.png" width="30" height="30"
					class="d-inline-block align-top" alt=""> Clarita la cuenta
			</h1>
			<p class="navbar-text">
				<%
				if (request.getSession().getAttribute("user") != null) {
				%>
				Bienvenido
				<%=((Persona) session.getAttribute("user")).getNombre()%>
				(<%=((Persona) session.getAttribute("user")).getCategoria().getDescripcion()%>)
				<%
				}
				%>
			</p>
		</div>
		</nav>

		<div class="card">
			<div class="card-body">
				<form class="form-Error" id="myForm" name="myForm"
					action="<%=(String) request.getAttribute("url")%>" method="post">
					<div class="alert alert-danger container-fluid" role="alert">
						<h3 class="sr-only"><%=(String) request.getAttribute("error")%></h3>
					</div>
					<div class="d-grid gap-2 d-md-flex justify-content-md-end">
						<button class="btn btn-danger" onclick="javascript: submitForm()">Aceptar</button>
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>