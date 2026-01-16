<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.dto.HostelRoomDTO"%>
<%@page import="in.co.rays.project_3.controller.HostelRoomCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HostelRoomDTO"
	scope="request" />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hostel Room</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.hm {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/userRegistration.png');
	background-size: cover;
	background-attachment: fixed;
	padding-top: 80px;
}

.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
}
</style>
</head>

<body class="hm">

	<%@include file="Header.jsp"%>

	<main>
	<form action="<%=ORSView.HOSTEL_ROOM_CTL%>" method="post">

		<div class="row">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card grad p-4">

					<%
						if (dto.getRoomNo() != null && dto.getId() > 0) {
					%>
					<h3 class="text-center text-primary">Update Hostel Room</h3>
					<%
						} else {
					%>
					<h3 class="text-center text-primary">Add Hostel Room</h3>
					<%
						}
					%>

					<h5 class="text-center text-success"><%=ServletUtility.getSuccessMessage(request)%></h5>
					<h5 class="text-center text-danger"><%=ServletUtility.getErrorMessage(request)%></h5>

					<input type="hidden" name="id" value="<%=dto.getId()%>">

					<!-- Room No -->
					<b>Room No *</b>
					<div class="input-group mb-2">
						<span class="input-group-text"><i class="fa fa-home"></i></span> <input
							type="text" class="form-control" name="roomNo"
							value="<%=DataUtility.getStringData(dto.getRoomNo())%>">
					</div>
					<font color="red"><%=ServletUtility.getErrorMessage("roomNo", request)%></font>

					<!-- Capacity -->
					<b>Capacity *</b>
					<div class="input-group mb-2">
						<span class="input-group-text"><i class="fa fa-users"></i></span>
						<input type="text" class="form-control" name="capacity"
							value="<%=dto.getCapacity() == 0 ? "" : dto.getCapacity()%>">
					</div>
					<font color="red"><%=ServletUtility.getErrorMessage("capacity", request)%></font>

					<!-- Type -->
					<b>Type *</b>
					<div class="input-group mb-2">
						<span class="input-group-text"><i class="fa fa-snowflake-o"></i></span>
						<%
							HashMap typeMap = new HashMap();
							typeMap.put("AC", "AC");
							typeMap.put("NON-AC", "NON-AC");
						%>
						<%=HTMLUtility.getList("type", dto.getType(), typeMap)%>
					</div>

					<!-- Washroom -->
					<b>Washroom *</b>
					<div class="input-group mb-2">
						<span class="input-group-text"><i class="fa fa-bath"></i></span>
						<%
							HashMap washMap = new HashMap();
							washMap.put("ATTACHED", "ATTACHED");
							washMap.put("COMMON", "COMMON");
						%>
						<%=HTMLUtility.getList("washroom", dto.getWashroom(), washMap)%>
					</div>

					<!-- Fees -->
					<b>Fees *</b>
					<div class="input-group mb-2">
						<span class="input-group-text"><i class="fa fa-inr"></i></span> <input
							type="text" class="form-control" name="fees"
							value="<%=DataUtility.getStringData(dto.getFees())%>">
					</div>

					<!-- Status -->
					<b>Status *</b>
					<div class="input-group mb-3">
						<span class="input-group-text"><i class="fa fa-info-circle"></i></span>
						<%
							HashMap statusMap = new HashMap();
							statusMap.put("AVAILABLE", "AVAILABLE");
							statusMap.put("FULL", "FULL");
							statusMap.put("MAINTENANCE", "MAINTENANCE");
						%>
						<%=HTMLUtility.getList("status", dto.getStatus(), statusMap)%>
					</div>

					<div class="text-center">
						<%
							if (dto.getRoomNo() != null && dto.getId() > 0) {
						%>
						<input type="submit" name="operation"
							value="<%=HostelRoomCtl.OP_UPDATE%>" class="btn btn-success">
						<input type="submit" name="operation"
							value="<%=HostelRoomCtl.OP_CANCEL%>" class="btn btn-warning">
						<%
							} else {
						%>
						<input type="submit" name="operation"
							value="<%=HostelRoomCtl.OP_SAVE%>" class="btn btn-success">
						<input type="submit" name="operation"
							value="<%=HostelRoomCtl.OP_RESET%>" class="btn btn-warning">
						<%
							}
						%>
					</div>

				</div>
			</div>

			<div class="col-md-4"></div>
		</div>

	</form>
	</main>

	<%@include file="FooterView.jsp"%>

</body>
</html>
