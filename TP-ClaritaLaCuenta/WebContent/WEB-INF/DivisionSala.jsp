<%@page import="entity.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="../imagenes/pngegg.png">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Dividiendo!</title>

<script type="text/javascript">
	function submitForm() {
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
	}
</script>

</head>
<body>
	<div class="container">
	<div class="card">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<form class="form-Accion" id="myForm" name="myForm" action="start"
					method="post">
					<button onclick="javascript: submitForm()"
						style="border: 0px; background-color: transparent;">
						<h1 class="navbar-brand fst-italic">
							<img src="../imagenes/pngegg.png" width="30" height="30"
								class="d-inline-block align-top" alt=""> Clarita la cuenta
						</h1>
					</button>
				</form>
				<p class="navbar-text">
					Bienvenido
					<%=((Persona) session.getAttribute("user")).getNombre()%>
					(<%=((Persona) session.getAttribute("user")).getCategoria().getDescripcion()%>)
					<a href="../logOut.jsp" class="btn btn-sm btn-default" style="border: 0px; background-color: transparent;">Logout</a>
				</p>
			</div>
		</nav></div>
		<div class="card">
			<div class="card-body ">
				<div class="col">
					<div class="card">
						<div class="card-header">Balance</div>

						<div class="card-body">
							<table class="table table-sm">

								<tr>
									<th>Nombre</th>
									<th>Balance</th>
								</tr>

								<%
								ArrayList<MiembroGasto> miembroGasto = (ArrayList<MiembroGasto>) request.getAttribute("mg");
								for (MiembroGasto mg : miembroGasto) {
								%>
								<tr <%if (mg.getGasto() <= 0) {%> class="table-success"
									<%} else {%> class="table-danger" <%}%>>
									<td><%=mg.getPersona().getNomApe()%></td>
									<td>
										<%
										if (mg.getGasto() <= 0) {
										%>Recibe: <%
										} else {
										%> Debe: <%
										}
										%> <%=Math.abs(mg.getGasto())%>
									</td>
								</tr>
								<%
								}
								%>
							</table>
						</div>
					</div>
				</div><br>
				<div class="d-grid gap-2 d-md-flex justify-content-md-end">
					<form class="form-Accion" id="myForm" name="myForm" action="start"	method="post">
						<button class="btn btn-primary"	onclick="javascript: submitForm()">Aceptar</button>
					</form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>