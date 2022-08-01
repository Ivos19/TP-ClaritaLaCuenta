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
<title>Detalle Gastos</title>

<script type="text/javascript">
	function submitForm(met) {
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
		document.myForm1.action = met;
		document.myForm2.action = met;
		document.myForm3.action = met;
		document.myFormgoBack.action = met;
	}
</script>
</head>
<body>

	<div class="container">
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
		<div class="card">
			<div class="card-body ">
				<div class="row">
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
										<th style="text-align: right; width:10%"></th>
										<th style="text-align: right; width:10%"></th>
										<th style="text-align: right; width:10%"></th>
										</tr>
										<%
										ArrayList<Producto> listaProd = (ArrayList<Producto>) request.getAttribute("listaPro");
										for (Producto p : listaProd) {
										%>
										<tr>
											<td><%=p.getNombre()%></td>
											<td><%=p.getPersona().getNomApe()%></td>
											<td><%=p.getCosto()%></td>
				<!-- Botones en tabla -->	

											<% if ((((Persona) session.getAttribute("user")).getId()==((Sala)request.getAttribute("sala")).getCreador().getId())  //Dueño de la sala?
													|| ((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equals("Admin")) //Admin?
											{%>
												
											<td style="text-align: right; width:10%">	
												<button <% if (p.getIdProd()!=((Sala)request.getAttribute("sala")).getCreador().getId()){%>  disabled <%}%>
													<% if (p.getEstadoPago().isPagado()){%> class="btn btn-success btn-sm"
													<%} else {%> class="btn btn-warning btn-sm" <%}%>
													onclick="javascript: submitForm('productos/actualizarEstadoP')"
													value="<%=p.getIdProd()%>" name="nomS">
													<% if (p.getEstadoPago().isPagado()){%> Pagado <%} else {%> Pagar &nbsp<%}%>
													</button>
											</td>		
											<td style="text-align: right; width:10% ">		
												<button 
													<% if (p.getEstadoPago().isConfirmado()){%> class="btn btn-success btn-sm" 
													<%} else {%> class="btn btn-primary btn-sm" <%}%>
													onclick="javascript: submitForm('productos/actualizarEstadoC')"
													value="<%=p.getIdProd()%>" name="nomS">
													<% if (p.getEstadoPago().isConfirmado()){%>  Confirmado <%} else {%>  Confirmar &nbsp<%}%>
													</button>
												
											</td>
											
											<td style="text-align: right;">
											
												<button class="btn btn-danger btn-sm" 
													onclick="javascript: submitForm('productos/eliminarProducto')"
													value="<%=p.getIdProd()%>" name="nomS">Eliminar</button>
											</td>
			<!-- Para los no dueños -->	<%}	else {%>
			
		
											
											<td style="text-align: right; width:10%">	
												<button <% if ( p.getPersona().getId() != ((Persona) session.getAttribute("user")).getId()) {%> disabled <%} %>
													<% if (p.getEstadoPago().isPagado()){%> class="btn btn-success btn-sm"
													<%} else {%> class="btn btn-warning btn-sm" <%}%>
													onclick="javascript: submitForm('productos/actualizarEstadoP')"
													value="<%=p.getIdProd()%>" name="nomS">
													<% if (p.getEstadoPago().isPagado()){%> Pagado <%} else {%> Pagar &nbsp<%}%>
													</button>
											</td>		
											<td style="text-align: right; width:10% ">		
												<button disabled
													<% if (p.getEstadoPago().isConfirmado()){%> class="btn btn-success btn-sm" 
													<%} else {%> class="btn btn-primary btn-sm" <%}%>
													onclick="javascript: submitForm('productos/actualizarEstadoC')"
													value="<%=p.getIdProd()%>" name="nomS">
													<% if (p.getEstadoPago().isConfirmado()){%>  Confirmado <%} else {%>  Confirmar &nbsp<%}%>
													</button>
												
											</td>
											
											<td style="text-align: right;">
											
												<button class="btn btn-danger btn-sm" disabled
													onclick="javascript: submitForm('productos/eliminarProducto')"
													value="<%=p.getIdProd()%>" name="nomS">Eliminar</button>
											</td>

											<%}	%>
						<!-- Botones en tabla -->					
												
										</tr>
										<%}	%>
									</table>
									
									<% if (((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin")) 
										{%>
										<p class="fst-italic text-end"  style="padding-bottom: 1px; font-size: 10px;"> Como admin pude eliminar cualquier producto. *</p>  
										<%}%>
									<%  if (((Sala)request.getAttribute("sala")).getCreador().getId() == ((Persona) session.getAttribute("user")).getId() 
												&& !((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equalsIgnoreCase("Admin")) 
										{%>
										<p class="fst-italic text-end"  style="padding-bottom: 1px; font-size: 10px;"> Como dueño de la sala pude eliminar cualquier producto. *</p>  
										<%}%>
								</form>
							</div>
						</div>
						
					</div>
				</div>
				<div class="row"><br></div>				
				<div class="row">
					<div class="col">
						<form class="form-ABMCSala" id="myForm1" name="myForm1" action="" method="post">
							<div class="card">
  								<div class="card-header">
									Lista Miembros
								</div>
								
								<div class="card-body">
								<table class="table table-sm" >
									<tr>
									<th>Nombre</th>
									<th>DNI</th>
									<th style="text-align: right"></th>
									</tr>
									<%
									ArrayList<Persona> miembros = (ArrayList<Persona>) request.getAttribute("miembros");
									for (Persona p : miembros) {
									%>
									<tr>
										<td><%=p.getNomApe()%></td>
										<td><%=p.getDni()%></td>
										<td style="text-align: right">
										
										<% if 
											(((Persona) session.getAttribute("user")).getCategoria().getDescripcion().equals("Admin")
													|| ((Persona) session.getAttribute("user")).getId() == ((Sala)request.getAttribute("sala")).getCreador().getId())
										{ %>
											
											
											<button class="btn btn-primary btn-sm" <% if (p.getId()==((Sala)request.getAttribute("sala")).getCreador().getId()){%>  disabled <%}%>
													onclick="javascript: submitForm('sala/eliminarMiembro')"
													value="<%=p.getId()%>" name="persona">
													<% if (p.getId()==((Sala)request.getAttribute("sala")).getCreador().getId()){%>  Dueño <%} else {%>  Expulsar <%}%>
													</button>
												
													
										<% } else {
											if (p.getId()==((Persona) session.getAttribute("user")).getId() ) {	%>
											<button class="btn btn-primary btn-sm" 
													onclick="javascript: submitForm('sala/eliminarMiembro')"
													value="<%=p.getId()%>" name="persona">
													Salir
													</button>
										
										
										<%} } }%>
										
										</td>
									</tr>
								</table>
								</div>
							</div>
							<input type="hidden" id="postId" name="salaO" value="<%=((Sala)request.getAttribute("sala")).getIdSala()%>">			
						</form>
					</div>
					
					<div class="col">						
						<div class="card">
  								<div class="card-header">
									Detalles
								</div>
								
								<div class="card-body">
								<table class="table table-sm" >
																		
									<tr><td>Monto total: <%=request.getAttribute("gastoTotal")%></td></tr>
									<tr><td>Cantidad de miembros: <%= miembros.size()%></td></tr>
									<tr><td>Si dividen todos igual pagan: $<%= request.getAttribute("div") %></td></tr>
									
									
								</table>
								</div>
							</div>
						
					</div>
					
				</div>
				
				<br>
				<div class="d-grid gap-2 d-md-flex justify-content-md-end">
					<form class="form-Accion" id="myForm" name="myForm" action="start"	method="post">
						<button class="btn btn-primary"	onclick="javascript: submitForm()">Aceptar</button>
					</form>
				</div>
			</div>
		</div>
		
		<p class="fst-italic "  style="padding-bottom: 1px ; font-size: 11px;"> 
		En esta pantalla podra ver todos los detalles de la sala en relacion a quienes aportaron, que aportaron y el costo de lo aportado. <br>
		Ademas, dependiendo del nivel de acceso y de si el creador de la sala podra realizar distintas acciones.<br>
		Usuarios no dueño de la sala: solo podra salir (retirando automaticamente todos sus aportes) y marcar el pago de sus aportes.<br>
		Usuario dueño de la sala: es el creador de la misma, por ende puede expulsar a cualquiera y confirmar pagos.<br>
		Admin: podras realizar todas las acciones menos marcar un producto como pagado y expulsar al dueño.<br>
		Un producto marcado como pagado solo es ilustrativo y sirve para avisarle al creador de ello.<br>
		Un dueño puede marcar un producto como confirmado y esto excluye al producto de la division final (division realizada con la segunda opcion de la lista de salas, no afecta la division por iguales).<br>
		Cualquier usuario expulsado o que se retire causara la eliminacion de todos los productos aportados. Podra volver a ingresar pero debera crear nuevamente los prodcutos.
		</p>
	</div>
</body>
</html>