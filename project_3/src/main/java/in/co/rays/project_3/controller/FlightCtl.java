package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.FlightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.FlightModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Flight functionality CRUD operation
 * 
 * @author Chaitanya Bhatt
 *
 */
@WebServlet(urlPatterns = { "/ctl/FlightCtl" })
public class FlightCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(FlightCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("FlightCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("airlineName"))) {
			request.setAttribute("airlineName", PropertyReader.getValue("error.require", "Airline Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("source"))) {
			request.setAttribute("airlineName", "should be name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("source"))) {
			request.setAttribute("source", PropertyReader.getValue("error.require", "Source"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("source"))) {
			request.setAttribute("source", "should be name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("destination"))) {
			request.setAttribute("destination", PropertyReader.getValue("error.require", "Destination"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("destination"))) {
			request.setAttribute("destination", "should be name");
			pass = false;
		}

		log.debug("FlightCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("FlightCtl Method populateDTO Started");

		FlightDTO dto = new FlightDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAirlineName(DataUtility.getString(request.getParameter("airlineName")));
		dto.setSource(DataUtility.getString(request.getParameter("source")));
		dto.setDestination(DataUtility.getString(request.getParameter("destination")));

		populateBean(dto, request);

		log.debug("FlightCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * Display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("FlightCtl Method doGet Started");

		long id = DataUtility.getLong(request.getParameter("id"));

		FlightModelInt model = ModelFactory.getInstance().getFlightModel();

		if (id > 0) {
			try {
				FlightDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		log.debug("FlightCtl Method doGet Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("FlightCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		FlightModelInt model = ModelFactory.getInstance().getFlightModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			FlightDTO dto = (FlightDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				ServletUtility.setDto(dto, request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Flight already exists", request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			FlightDTO dto = (FlightDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.FLIGHT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FLIGHT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FLIGHT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("FlightCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.FLIGHT_VIEW;
	}
}