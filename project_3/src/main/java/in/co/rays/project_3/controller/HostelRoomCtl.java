package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HostelRoomDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.HostelRoomModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Hostel Room functionality controller to perform add, delete and update
 * operation
 * 
 * @author Chaitanya Bhatt
 *
 */

@WebServlet(urlPatterns = { "/ctl/HostelRoomCtl" })
public class HostelRoomCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(HostelRoomCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("roomNo"))) {
			request.setAttribute("roomNo",
					PropertyReader.getValue("error.require", "Room No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("capacity"))) {
			request.setAttribute("capacity",
					PropertyReader.getValue("error.require", "Capacity"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("capacity"))) {
			request.setAttribute("capacity", "Capacity must be a number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type",
					PropertyReader.getValue("error.require", "Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("washroom"))) {
			request.setAttribute("washroom",
					PropertyReader.getValue("error.require", "Washroom"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("fees"))) {
			request.setAttribute("fees",
					PropertyReader.getValue("error.require", "Fees"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("fees"))) {
			request.setAttribute("fees", "Fees must be numeric");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status",
					PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		HostelRoomDTO dto = new HostelRoomDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRoomNo(DataUtility.getString(request.getParameter("roomNo")));
		dto.setCapacity(DataUtility.getInt(request.getParameter("capacity")));
		dto.setType(DataUtility.getString(request.getParameter("type")));
		dto.setWashroom(DataUtility.getString(request.getParameter("washroom")));
		dto.setFees(DataUtility.getString(request.getParameter("fees")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {

	    log.debug("HostelRoomCtl doGet Started");

	    long id = DataUtility.getLong(request.getParameter("id"));

	    HostelRoomModelInt model =
	            ModelFactory.getInstance().getHostelRoomModel();

	    HostelRoomDTO dto = new HostelRoomDTO(); // always non-null

	    if (id > 0) {
	        try {
	            HostelRoomDTO dbDto = model.findByPK(id);
	            if (dbDto != null) {
	                dto = dbDto;   // only replace if found
	            }
	        } catch (Exception e) {
	            log.error(e);
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	    }

	    ServletUtility.setDto(dto, request);   // NEVER NULL

	    ServletUtility.forward(getView(), request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("HostelRoomCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		HostelRoomModelInt model =
				ModelFactory.getInstance().getHostelRoomModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			HostelRoomDTO dto = (HostelRoomDTO) populateDTO(request);

			try {
				if (id > 0) {
					try {
						model.update(dto);
					} catch (DuplicateRecordException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ServletUtility.setSuccessMessage(
							"Hostel Room updated successfully", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage(
								"Hostel Room added successfully", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage(
								"Room No already exists", request);
					}
				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			HostelRoomDTO dto = (HostelRoomDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(
						ORSView.HOSTEL_ROOM_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(
					ORSView.HOSTEL_ROOM_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(
					ORSView.HOSTEL_ROOM_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.HOSTEL_ROOM_VIEW;
	}
}
