package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.FlightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Flight model
 * 
 * @author Chaitanya
 *
 */
public interface FlightModelInt {

	public long add(FlightDTO dto)
			throws ApplicationException, DuplicateRecordException;

	public void delete(FlightDTO dto)
			throws ApplicationException;

	public void update(FlightDTO dto)
			throws ApplicationException, DuplicateRecordException;

	public List list()
			throws ApplicationException;

	public List list(int pageNo, int pageSize)
			throws ApplicationException;

	public List search(FlightDTO dto)
			throws ApplicationException;

	public List search(FlightDTO dto, int pageNo, int pageSize)
			throws ApplicationException;

	public FlightDTO findByPK(long pk)
			throws ApplicationException;

	public FlightDTO findByAirlineName(String airlineName)
			throws ApplicationException;
}