<%@page import="in.co.rays.project_3.controller.FlightCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flight View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/flight.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>
</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<div>
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.FlightDTO"
			scope="request"></jsp:useBean>

		<main>
		<form action="<%=ORSView.FLIGHT_CTL%>" method="post">

			<div class="row pt-3 pb-3">
				<div class="col-md-4 mb-4"></div>

				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));
								if (dto != null && id > 0) {
							%>
							<h3 class="text-center text-primary">Update Flight</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Flight</h3>
							<%
								}
							%>

							<!-- Success Message -->
							<h4 align="center">
								<%
									if (!ServletUtility.getSuccessMessage(request).equals("")) {
								%>
								<div class="alert alert-success alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getSuccessMessage(request)%>
								</div>
								<%
									}
								%>
							</h4>

							<!-- Error Message -->
							<h4 align="center">
								<%
									if (!ServletUtility.getErrorMessage(request).equals("")) {
								%>
								<div class="alert alert-danger alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getErrorMessage(request)%>
								</div>
								<%
									}
								%>
							</h4>

							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
							<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
							<input type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Airline Name -->
							<span><b>Airline Name</b><span style="color: red;">*</span></span><br>
							<input type="text" class="form-control" name="airlineName"
								placeholder="Airline Name"
								value="<%=DataUtility.getStringData(dto.getAirlineName())%>">
							<font color="red">
								<%=ServletUtility.getErrorMessage("airlineName", request)%>
							</font><br><br>

							<!-- Source -->
							<span><b>Source</b><span style="color: red;">*</span></span><br>
							<input type="text" class="form-control" name="source"
								placeholder="Source"
								value="<%=DataUtility.getStringData(dto.getSource())%>">
							<font color="red">
								<%=ServletUtility.getErrorMessage("source", request)%>
							</font><br><br>

							<!-- Destination -->
							<span><b>Destination</b><span style="color: red;">*</span></span><br>
							<input type="text" class="form-control" name="destination"
								placeholder="Destination"
								value="<%=DataUtility.getStringData(dto.getDestination())%>">
							<font color="red">
								<%=ServletUtility.getErrorMessage("destination", request)%>
							</font><br><br>

							<!-- Buttons -->
							<%
								if (id > 0) {
							%>
							<div class="text-center">
								<input type="submit" class="btn btn-success" name="operation"
									value="<%=FlightCtl.OP_UPDATE%>">
								<input type="submit" class="btn btn-warning" name="operation"
									value="<%=FlightCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" class="btn btn-success" name="operation"
									value="<%=FlightCtl.OP_SAVE%>">
								<input type="submit" class="btn btn-warning" name="operation"
									value="<%=FlightCtl.OP_RESET%>">
							</div>
							<%
								}
							%>

						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4"></div>
			</div>

		</form>
		</main>
	</div>

</body>

<%@include file="FooterView.jsp"%>
</html>