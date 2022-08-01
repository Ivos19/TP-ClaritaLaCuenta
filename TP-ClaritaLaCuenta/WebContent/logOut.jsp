<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
	function submitForm(met) {
		if (document.getElementById("box").checked) {
			document.getElementById("boxHidden").disabled = true;
		}
		window.history.pushState({}, document.title, "/" + "PruebaDB-10" + "/");
		document.myForm.action = met;
		document.myFormC.action = met;
		
	}
</script>

</head>
 <body>
     <%
      if (request.getSession()!=null) {
        session.invalidate();
        request.setAttribute("url", "login.html");
		request.setAttribute("error", "Ha cerrado su cuenta");
		request.getRequestDispatcher("Error.jsp").forward(request, response);
      }
     %>
 </body>
</html>