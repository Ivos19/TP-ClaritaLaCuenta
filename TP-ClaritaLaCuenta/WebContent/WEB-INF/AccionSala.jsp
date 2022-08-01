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
<title><%=request.getAttribute("accion")%></title>

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
				<form class="form-Accion" id="myFormN" name="myFormN" action="start"	method="post">
					<button	onclick="javascript: submitForm()" style="border: 0px; background-color: transparent;">
						<h1 class="navbar-brand">
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
		</nav>
		<div class="card">
			<div class="card-body ">
			
				<%
				if (request.getAttribute("accion").equals("insertar")) {
				%>
				<div class="container text-center">
					<div class="alert alert-primary" role="alert">Sala creada con exito!</div>
				</div>
				<% 
				}
				%>
				<%
				if (request.getAttribute("accion").equals("eliminar")) {
				%>
				<div class="container text-center">
					<div class="alert alert-primary" role="alert">Sala (y sus productos) eliminados con exito!</div>
				</div>
				<% 
				}
				%>
				<%
				if (request.getAttribute("accion").equals("gastos")) {
				%>
				<div class="container ">
					<div class="card">
						<div class="card-body">
							<form class="form-ABMCSala" id="myForm2" name="myForm2" action="" method="post">
								<h2><%=((Sala)request.getAttribute("sala")).getNombreSala()%></h2> <!-- Aca quiedaste, te falta buscar como obtener la sala que mandaste desde el ABMbn -->
								<table class="table table-sm">
									<tr>
									<th>Producto</th>
									<th>Comprador</th>
									<th>Monto</th>
									</tr>
									<%
									ArrayList<Producto> listaProd = (ArrayList<Producto>) request.getAttribute("listaPro");
									for (Producto p : listaProd) {
									%>
									<tr>
										<td><%=p.getNombre()%></td>
										<td><%=p.getPersona().getNombre()%></td>
										<td><%=p.getCosto()%></td>
										<td style="text-align: right"></td>	
									</tr>
									<%}	%>
								</table>
							</form>
						</div>
					</div>
				</div>
				<% 
				}
				%>
				<br>
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