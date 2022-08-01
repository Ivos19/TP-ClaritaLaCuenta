<%@page import="java.util.ArrayList"%>
<%@page import="entity.Persona"%>
<%@page import="entity.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<link rel="icon" href="../imagenes/pngegg.png">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/bootstrap.min.css" rel="stylesheet">


<title>ABM_Personas</title>

<script type="text/javascript">
	function submitForm(met) {
		
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
		document.myForm.action = met;
		document.myFormC.action = met;
		
	};
</script>

</head>
<body>
	<div class="container">
		<div class="card">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<form class="form-Accion" id="myFormN" name="myFormN" action="start"	method="post">
					<button	onclick="javascript: submitForm()" style="border: 0px; background-color: transparent;">
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
		</nav>
		</div>

		<div class="card">
			<div class="card-body">
				<form class="form-ABMCPersona" id="myForm" name="myForm" action=""
					method="post">

					<h2 class="form-ABMCPersona-heading">
						ABM de Personas
						<% if (((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin")) 
							{%>
							<p class="fst-italic "  style="padding-bottom: 1px ; font-size: 10px;"> 
							* Solo para admin. <br>
							En esta pantalla el administrador es capaz de agregar, quitar, modificar y buscar personas. <br>
							Utlice la lista inferior para ayudarse en el completado del form. <br>
							Recuerde siempre completar la mayor cantidad de datos, siendo siempre dni requerido, el sistema validara luego los demas campos.
							</p>  
							<%}
						%>					
					</h2>
						<br> 
						<label for="inputUser" class="sr-only">User</label> 
						<input name="user" id="inputUser" class="form-control" placeholder="User" autofocus="" type="">
							<br> 
						<label for="inputPass" class="sr-only">	Password</label> 
						<input name="pass" id="inputPass" class="form-control" placeholder="Password" type="Password">
							<br>
						<label for="lblDni" class="sr-only">DNI</label> 
						<input name="dni" id="txtDni" class="form-control" placeholder="DNI" required="" autofocus="" type="">
							<br> 
						<label for="lblApellido" class="sr-only">Apellido</label> 
						<input name="apellido" id="txtApellido" class="form-control" placeholder="Apellido"	autofocus="" type="">
							<br> 
						<label for="lblNombre" class="sr-only">Nombre</label> 
						<input name="nombre" id="txtNombre" class="form-control" placeholder="Nombre" autofocus="" type="">
							<br>
						<input type="hidden" value="no" id="boxHidden" name=habilitado>

					<select name="categoria">
						<%
						ArrayList<Categoria> listaCat = (ArrayList<Categoria>) request.getAttribute("listaCategorias");
						for (Categoria c : listaCat) {
						%>
						<option value="<%=(Categoria) c%>"><%=c%></option>
						<%
						}
						%>
					</select> 
					

					<br>
					<div class="text-center">
						<button class="btn btn-primary "
							onclick="javascript: submitForm('personas/buscar')">Buscar</button>
						<button class="btn btn-primary "
							onclick="javascript: submitForm('personas/insertarPersona')">Insertar Persona</button>
						<button class="btn btn-primary "
							onclick="javascript: submitForm('personas/eliminar')">Eliminar</button>
						<button class="btn btn-primary "
							onclick="javascript: submitForm('personas/modificar')">Modificar</button>
					</div>
				</form>
			</div>
		</div>
		
		<br>
		
		<div class="card">
			<div class="card card-body">
				<table class="table table-sm">
					<tr>
						<th>DNI</th>
						<th>Apellido</th>
						<th>Nombre</th>
						<th>Usuario</th>
						<th>Pass</th>
					</tr>
					<%
					ArrayList<Persona> listaPers = (ArrayList<Persona>) request.getAttribute("listaPersonas");
					for (Persona p : listaPers) {
					%>
					<tr>
						<td><%=p.getDni()%></td>
						<td><%=p.getApellido()%></td>
						<td><%=p.getNombre()%></td>
						<td><%=p.getUser()%></td>
						<td><%=p.getPass()%></td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		</div>


	</div>



</body>
</html>