<%@page import="java.util.ArrayList"%>
<%@page import="entity.Persona"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="imagenes/pngegg.png">
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<meta charset="ISO-8859-1">


<title>Bienvenido!</title>

<link href="style/bootstrap.css" rel="stylesheet">

<script type="text/javascript">
	function submitForm(met) {
		document.menu.action = met;
	}
</script>
</head>
<body>
	<div class="container">
		<div class="card">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<form class="form-Accion" id="myForm" name="myForm" action="start"	method="post">
					<button	onclick="javascript: submitForm()" style="border: 0px; background-color: transparent;">
						<h1 class="navbar-brand fst-italic" >
							<img src="imagenes/pngegg.png" width="30" height="30"
								class="d-inline-block align-top" alt=""> Clarita la cuenta
						</h1>
					</button>
				</form>
							
				<p class="navbar-text">
					Bienvenido
					<%=((Persona) session.getAttribute("user")).getNombre()%>
					(<%=((Persona) session.getAttribute("user")).getCategoria().getDescripcion()%>)
					<a href="logOut.jsp" class="btn btn-sm btn-default" style="border: 0px; background-color: transparent;">Logout</a>
				</p>
			</div>
		</nav>
		</div>
		<div class="card">
			<div class="card-body">
				<div class="text-center">
				<form class="form-menu" id="menu" name="menu" action=""
					method="post">
					<h1 class="form-menu-heading">Menu Principal</h1>
					
					<br>

					<button class="btn btn-primary "
						onclick="javascript: submitForm('menu/sala')"> Salas </button>
					
					<button class="btn btn-primary "
						onclick="javascript: submitForm('menu/nuevoProducto')"> Productos </button>
					<%
					if (((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin")) {
					%>
					<button class="btn btn-primary "
						onclick="javascript: submitForm('menu/nuevaPersona')"> Personas </button>
					<%
					}
					%>
				</form>
				</div>
			</div>
		</div>
		<p class="fst-italic "  style="padding-bottom: 1px ; font-size: 11px;"> 
		Bienvenido a la pantalla principal, aqui se encuentran las principales acciones. Si es admin vera 3, si no, 2. <br>
		Esta pantalla se vera luego de cada accion permitiendo manejar Sala, Productos y Personas. <br>
		En la barra superior puedo volver aqui haciendo click en el logo o nombre de la web y desloguearse con el linck al lado de su usuario.
		</p>
	</div>
</body>
</html>