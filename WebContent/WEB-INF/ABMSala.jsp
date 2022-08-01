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
<title>Insert title here</title>
<script type="text/javascript">
	function submitForm(met) {
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
		document.myForm1.action = met;
		document.myForm2.action = met;
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
						<form class="form-ABMCSala" id="myForm1" name="myForm1" action="" method="post">
							<h2>Crear Sala</h2>
							<label for="inputNom" class="sr-only">Nombre</label>
							<input name="nom" id="inputNOmbre" class="form-control" placeholder="Nombre" autofocus="" type=""> 
							<br>
							<input type="hidden" id="postId" name="dniO" value="<%=((Persona) session.getAttribute("user")).getDni()%>">
							<div class="text-center">
								<button class="btn btn-primary" onclick="javascript: submitForm('sala/crearSala')">Insertar</button>
							</div>
						</form>
					</div>
				</div>			
			</div>
		</div>
		<div class="row .d-flex">
			<br>
		</div>
		<div class="row">
			<div class="col">
				<div class="card">
					<div class="card-body">
						<form class="form-ABMCSala" id="myForm2" name="myForm2" action="" method="post">
							<h2>Salas</h2>
							<table class="table table-sm">
								<tr>
								<th>ID Sala</th>
								<th>Nombre</th>
								<th>Creador</th>
								<th style="text-align: right"></th>
								</tr>
								<%ArrayList<Sala> listaSal = (ArrayList<Sala>) request.getAttribute("listaSalas");
								for (Sala s : listaSal) {%>
								<tr>
									<td><%=s.getIdSala()%></td>
									<td><%=s.getNombreSala()%></td>
									<td><%=s.getCreador().getNomApe() %></td>
									<td style="text-align: right">
										<button value="<%=s.getIdSala()%>" name="nomS" class="btn btn-primary" 
											onclick="javascript: submitForm('sala/gastosSala')"> Ver Gastos</button>
										<button value="<%=s.getIdSala()%>" name="nomS" class="btn btn-primary"
											onclick="javascript: submitForm('sala/dividir')"> Cuanto se paga?</button>
										<button value="<%=s.getIdSala()%>" name="nomS" class="btn btn-danger"
											onclick="javascript: submitForm('sala/eliminar')">Eliminar</button>
									</td>	
								</tr>
								<%}	%>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
		<p class="fst-italic "  style="padding-bottom: 1px ; font-size: 10px;"> 
		Esta pantalla permite controlar los principales aspectos de una sala/evento. <br>
		Podra crearlas utilizando la primer tarjeta<br>
		En la tarjeta inferior vera todas las salas y podra acceder a sus detalles o al balance de pagos para los integrantes.
		</p>		
	</div>

</body>
</html>