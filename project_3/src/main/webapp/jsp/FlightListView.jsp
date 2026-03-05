<%@page import="in.co.rays.project_3.dto.FlightDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.FlightListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Flight List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>

</head>

<body class="p4">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.FLIGHT_LIST_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.FlightDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = DataUtility.getInt(
				request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<FlightDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-primary pt-3"><font color="black">Flight List</font></h1>
		</center>

		<!-- Success Message -->
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="alert alert-success text-center">
			<%=ServletUtility.getSuccessMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- Error Message -->
		<%
			if (!ServletUtility.getErrorMessage(request).equals("")) {
		%>
		<div class="alert alert-danger text-center">
			<%=ServletUtility.getErrorMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- Search Section -->
		<div class="row">
			<div class="col-sm-2"></div>

			<div class="col-sm-2">
				<input class="form-control" type="text"
					name="airlineName"
					placeholder="Enter Airline Name"
					value="<%=ServletUtility.getParameter("airlineName", request)%>">
			</div>

			<div class="col-sm-2">
				<input class="form-control" type="text"
					name="source"
					placeholder="Enter Source"
					value="<%=ServletUtility.getParameter("source", request)%>">
			</div>

			<div class="col-sm-2">
				<input class="form-control" type="text"
					name="destination"
					placeholder="Enter Destination"
					value="<%=ServletUtility.getParameter("destination", request)%>">
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary"
					name="operation"
					value="<%=FlightListCtl.OP_SEARCH%>">

				<input type="submit" class="btn btn-dark"
					name="operation"
					value="<%=FlightListCtl.OP_RESET%>">
			</div>
		</div>

		<br>

		<!-- Table -->
		<div class="table-responsive">
			<table class="table table-dark table-bordered table-hover">

				<thead>
					<tr style="background-color: #8C8C8C;">
						<th width="10%">
							<input type="checkbox" id="select_all"> Select All
						</th>
						<th>S.NO</th>
						<th>Airline Name</th>
						<th>Source</th>
						<th>Destination</th>
						<th>Edit</th>
					</tr>
				</thead>

				<tbody>
					<%
						while (it.hasNext()) {
							dto = it.next();
					%>
					<tr>
						<td align="center">
							<input type="checkbox" name="ids"
								value="<%=dto.getId()%>">
						</td>

						<td align="center"><%=index++%></td>
						<td align="center"><%=dto.getAirlineName()%></td>
						<td align="center"><%=dto.getSource()%></td>
						<td align="center"><%=dto.getDestination()%></td>

						<td align="center">
							<a href="FlightCtl?id=<%=dto.getId()%>">Edit</a>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>

			</table>
		</div>

		<!-- Buttons -->
		<table width="100%">
			<tr>
				<td>
					<input type="submit" name="operation"
						class="btn btn-warning"
						value="<%=FlightListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>>
				</td>

				<td>
					<input type="submit" name="operation"
						class="btn btn-primary"
						value="<%=FlightListCtl.OP_NEW%>">
				</td>

				<td>
					<input type="submit" name="operation"
						class="btn btn-danger"
						value="<%=FlightListCtl.OP_DELETE%>">
				</td>

				<td align="right">
					<input type="submit" name="operation"
						class="btn btn-warning"
						value="<%=FlightListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>>
				</td>
			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>">
		<input type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>