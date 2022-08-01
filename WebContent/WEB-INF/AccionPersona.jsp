<%@page import="entity.Persona"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

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
</head>
<body>
	<div class="container">
		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<form class="form-Accion" id="myForm" name="myForm" action="start"	method="post">
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
				<div class="alert alert-success align-items-center" role="alert">
				
				<h2>Persona encontrada</h2>
				</div>
				<br>				
				<div class="card ">
				  <div class="card-header text-center">
				    #<%=((Persona) request.getAttribute("persona")).getId()%>: 
				    <%=((Persona) request.getAttribute("persona")).getNombre()%> 
				    <%=((Persona) request.getAttribute("persona")).getApellido()%>
				  </div>
				  <ul class="list-group list-group-flush">
				    <li class="list-group-item">
				    	DNI: <%=((Persona) request.getAttribute("persona")).getDni()%></li>
				    <li class="list-group-item">
				    	Usuario: <%=((Persona) request.getAttribute("persona")).getUser()%></li>
				    <li class="list-group-item">
				    	Contraseña: <%=((Persona) request.getAttribute("persona")).getPass()%></li>
				    <li class="list-group-item">
				    	Categoria: <%=((Persona) request.getAttribute("persona")).getCategoria().getDescripcion()%></li>
				    <li class="list-group-item">
				    	Habilitado: <%=((Persona) request.getAttribute("persona")).isHabilitado()%></li>
				  </ul>
				</div>
				
				<%
				} else if (request.getAttribute("accion").equals("insertar")) {
				%>
				<div class="alert alert-primary" role="alert">
				Persona Agregada con éxito.
				</div>
				<%
				} else if (request.getAttribute("accion").equals("eliminar")) {
				%>
				<div class="alert alert-primary" role="alert">
				Persona Eliminada con éxito.
				</div>
				<%
				} else {
				%>
				<div class="alert alert-primary" role="alert">
				Persona Modificada con éxito.
				</div>
				<%
				}
				%>
				<br>
				<div class="d-grid gap-2 d-md-flex justify-content-md-end">
				<form class="form-Accion" id="myForm" name="myForm" action="start" method="post">
					<button class="btn btn-primary" onclick="javascript: submitForm()">Aceptar</button>
				</form>
				</div>
			</div>
			
		</div>
		</div>

</body>
</html>