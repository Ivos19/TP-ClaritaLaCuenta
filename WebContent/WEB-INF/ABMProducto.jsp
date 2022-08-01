<%@page import="entity.*"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link rel="icon" href="../imagenes/pngegg.png">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/bootstrap.min.css" rel="stylesheet">

<title>ABM_Producto</title>

<script type="text/javascript">
	function submitForm(met) {
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
		document.myForm.action = met;
		document.myForm2.action = met;
		document.myForm3.action = met;
	}
	
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

		<br>

		<div class="row .d-flex">
			<div class="col">
				<div class="card">
					<div class="card-body">
						<form class="form-ABMCPersona" id="myForm" name="myForm2"
							action="" method="post">
							<h2 class="form-ABMPersona">Agregar producto</h2>
							<br> <label for="inputNom" class="sr-only">Nombre:</label>
							 <input name="nom" id="inputNOmbre" class="form-control" placeholder="Nombre" autofocus="" type=""> 
							 <br> 
							 <label	for="inputCos" class="sr-only">Costo:</label> 
							 <input name="cos" id="inputCosto" class="form-control" placeholder="Costo"	type="number" step="0.01"> 
							 <br> 
							 <label	for="inputCos" class="sr-only">ID sala o evento:</label> 
							<select name="selectSala" class="form-select">
										<%
										ArrayList<Sala> listaSal = (ArrayList<Sala>) request.getAttribute("listaSalas");
										for (Sala s : listaSal) {
										%>
										<option value="<%= s.getIdSala() %>"><%=s.getNombreSala()%></option>
										<%
										}
										%>
							</select>
							<br> 
							<input type="hidden" id="postId" name="nombreO" value="<%=((Persona) session.getAttribute("user")).getNombre()%>">
							<input type="hidden" id="postId" name="apellidoO" value="<%=((Persona) session.getAttribute("user")).getApellido()%>">
							<input type="hidden" id="postId" name="dniO" value="<%=((Persona) session.getAttribute("user")).getDni()%>">
							<div class="text-center">
								<button class="btn btn-primary "
									onclick="javascript: submitForm('productos/insertarProd')">Insertar</button>
							</div>

						</form>
					</div>
				</div>
			</div>
			<div class="col">
				
						<form class="form-ABMCPersona" id="myForm" name="myForm3"
							action="" method="post">
							<div class="card ">
								<div class="card-body">
									<h2 class="form-ABMPersona">Modificar Producto</h2>
									<br><label for="lblNombre" class="sr-only">Seleccione producto:</label> 
									<select name="selectProducto" class="form-select">
										<%
										ArrayList<Producto> listaPro = (ArrayList<Producto>) request.getAttribute("listaProductos");
										for (Producto p : listaPro) {
										%>
											<% if (((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin")) { %>
											<option value="<%= p.getIdProd() %>">#<%=p.getIdProd()%>: <%=p.getNombre()%></option>
											<% } else if (((Persona) session.getAttribute("user")).getId() == p.getPersona().getId()) {%>
											<option value="<%= p.getIdProd() %>">#<%=p.getIdProd()%>: <%=p.getNombre()%></option>
											<%
											}
											%>
										<%
										}
										%>
									</select>
									<br><label for="lblNombre" class="sr-only">Nuevo nombre: </label> 
										<input name="nombre" id="txtNombre"	class="form-control" placeholder="Nombre" autofocus="" type="">
									<br><label for="lblNombre" class="sr-only">Nuevo costo: </label> 
										<input name="costo" id="txtNombre" class="form-control" placeholder="Costo" autofocus="" type="">
									<br>
									<div class="text-center">
										<button class="btn btn-primary " onclick="javascript: submitForm('productos/modificarProd')">Actualizar</button>
									</div>
								</div>
							</div>
						</form>
					
			</div>
		</div>

		<br>
		<div class="row">
			<form class="form-ABMCPersona" id="myForm" name="myForm" action="" method="post">
				<div class="card">
					<div class="card-body" style="padding-bottom: 0px">

						<table class="table table-sm">
							<tr>
								<th># Producto</th>
								<th>Nombre</th>
								<th>Costo</th>
								<th>Dueño</th>
								<th>Sala</th>
								<th style="text-align: right"></th>
							</tr>
							<%
							for (Producto p : listaPro) {
							%>
							<tr <% if (((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin") 
									&& p.getPersona().getId() == ((Persona) session.getAttribute("user")).getId()){%>class="table-success"<%} %>>
								<% if (((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin")) {%>
								<td><%=p.getIdProd()%></td>
								<td><%=p.getNombre()%></td>
								<td><%=p.getCosto()%></td>
								<td><%=p.getPersona().getNombre()%> <%=p.getPersona().getApellido()%></td>
								<td># <%=p.getSala().getIdSala()%> <%=p.getSala().getNombreSala()%></td>
								<td style="text-align: right">
									<button value="<%=p.getNombre()%>" name="nomS"
										class="btn btn-primary"
										onclick="javascript: submitForm('productos/buscarProd')">Detalle</button>
									<button value="<%=p.getIdProd()%>" name="nomS"
										class="btn btn-danger"
										onclick="javascript: submitForm('productos/eliminarProducto')">Eliminar</button>
								</td>
								<%
								} else if (((Persona) session.getAttribute("user")).getId() == p.getPersona().getId()){
								%>
								<td><%=p.getIdProd()%></td>
								<td><%=p.getNombre()%></td>
								<td><%=p.getCosto()%></td>
								<td><%=p.getPersona().getNombre()%> <%=p.getPersona().getApellido()%></td>
								<td># <%=p.getSala().getIdSala()%> <%=p.getSala().getNombreSala()%></td>
								<td style="text-align: right">
									<button value="<%=p.getNombre()%>" name="nomS"
										class="btn btn-primary"
										onclick="javascript: submitForm('productos/buscarProd')">Detalle</button>
									<button value="<%=p.getNombre()%>" name="nomS"
										class="btn btn-danger"
										onclick="javascript: submitForm('productos/eliminarProducto')">Eliminar</button>
								</td>
								<%
								}
								%>
							</tr>
							<%
							}
							%>
						</table>
						<% if (((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin")) 
							{%>
							<p class="fst-italic text-end"  style="padding-bottom: 1px ; font-size: 10px;"> Como admin pude ver todos los productos, en verde los propios. *</p>  
							<%}
						%>
						
						
					</div>
				</div>
			</form>
		</div>
		<p class="fst-italic "  style="padding-bottom: 1px ; font-size: 11px;"> 
		Esta pantalla permite crear, modificar y ver detalles de productos de una forma mas simplificada que en el casos del manejo de Personas/Usuarios<br>
		En caso de no ser admin solo se mostrara la informacion relacionada a uds. Esto aplica a Modificar, ver detalles y eliminar.<br>
		Lo productos aqui creados podran ser visualizados ordenados por sala en el detalle de cada sala.
		</p>
	</div>
</body>
</html>