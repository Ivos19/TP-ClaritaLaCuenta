<%@page import="entity.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link rel="icon" href="../imagenes/pngegg.png">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/start.css" rel="stylesheet">

<title><%=request.getAttribute("accion")%></title>
<script type="text/javascript">
	function submitForm() {
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
	}
	
	
</script>

<title>Accion Producto</title>
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
				if (request.getAttribute("accion").equals("buscar")) {
				%>

				<div class="container text-center">
					<div class="row">
						<div class="col">
							<div class="alert alert-success align-items-center" role="alert">
								<h2>Producto encontrado</h2>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="card ">
									<div>
										<div class="card-header text-center">Producto:</div>
										<ul class="list-group list-group-flush">
											<li class="list-group-item">ID Producto: <%=((Producto) request.getAttribute("producto")).getIdProd()%></li>
											<li class="list-group-item">Nombre: <%=((Producto) request.getAttribute("producto")).getNombre()%></li>
											<li class="list-group-item">Costo: $<%=((Producto) request.getAttribute("producto")).getCosto()%></li>
											<li class="list-group-item">ID Sala: <%=((Producto) request.getAttribute("producto")).getSala().getIdSala()%></li>
										</ul>
									</div>
								</div>

							</div>
						</div>
						<div class="row">
							<div class="col">
								<br>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="card ">
									<div>
										<div class="card-header text-center">Comprador</div>
										<ul class="list-group list-group-flush">
											<li class="list-group-item">Nombre: <%=((Producto) request.getAttribute("producto")).getPersona().getNomApe()%></li>
											<li class="list-group-item">DNI: <%=((Producto) request.getAttribute("producto")).getPersona().getDni()%></li>
											<li class="list-group-item">Usuario: <%=((Producto) request.getAttribute("producto")).getPersona().getUser()%></li>
										</ul>
									</div>

								</div>

							</div>
							<div class="col">
								<div class="card ">
									<div>
										<div class="card-header text-center">Sala</div>
										<ul class="list-group list-group-flush">
											<li class="list-group-item">ID: <%=((Producto) request.getAttribute("producto")).getSala().getIdSala()%></li>
											<li class="list-group-item">Nombre: <%=((Producto) request.getAttribute("producto")).getSala().getNombreSala()%></li>
											<li class="list-group-item">Creador: <%=((Producto) request.getAttribute("producto")).getSala().getCreador().getNomApe()%></li>
										</ul>
									</div>

								</div>
							</div>
						</div>
					</div>

					<%
					} else if (request.getAttribute("accion").equals("insertar")) {
					%>
					<div class="container text-center">
					<div class="alert alert-primary" role="alert">Producto agregado con éxito.</div>
					</div>
					<%
					} else if (request.getAttribute("accion").equals("eliminar")) {
					%>
					<div class="container text-center">
					<div class="alert alert-primary" role="alert">Producto eliminado con éxito.</div>
					</div>
					<%
					} else if (request.getAttribute("accion").equals("modificar")){
					%>
					<div class="container text-center">
					<div class="alert alert-primary" role="alert">Producto modificado con éxito.</div>
					</div>
					<% } else if (request.getAttribute("accion").equals("eliminarMiembro")){
					%>
					<div class="container text-center">
					<div class="alert alert-primary" role="alert">Se han eliminado al miembro y todos sus productos ingresados</div>
					</div>
					<%
					} else if (request.getAttribute("accion").equals("Pagar")){
					%>
					<div class="container text-center">
					<div class="alert alert-primary" role="alert"><%=request.getAttribute("acPago")%></div>
					</div>
					<% } else if (request.getAttribute("accion").equals("Confirmar")){
					%>
					<div class="container text-center">
					<div class="alert alert-primary" role="alert"><%=request.getAttribute("acConfirmar")%></div>
					</div>
					<% } %>
					<br>
					<div class="d-grid gap-2 d-md-flex justify-content-md-end">
						<form class="form-Accion" id="myForm" name="myForm" action="start"	method="post">
							<button class="btn btn-primary"	onclick="javascript: submitForm()">Aceptar</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>