package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.FlightDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Flight model
 * 
 * @author Chaitanya Bhatt
 *
 */
public class FlightModelHibImp implements FlightModelInt {

	public long add(FlightDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		long pk = 0;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Flight Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(FlightDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Flight Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(FlightDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Flight Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public FlightDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		FlightDTO dto = null;

		try {
			dto = (FlightDTO) session.get(FlightDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Flight by PK");
		} finally {
			session.close();
		}
		return dto;
	}

	public FlightDTO findByAirlineName(String airlineName) throws ApplicationException {

		Session session = HibDataSource.getSession();
		FlightDTO dto = null;

		try {
			Criteria criteria = session.createCriteria(FlightDTO.class);
			criteria.add(Restrictions.eq("airlineName", airlineName));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (FlightDTO) list.get(0);
			}
		} catch (HibernateException e) {
			throw new ApplicationException(
					"Exception in getting Flight by Airline Name " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FlightDTO.class);

			// Pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Flight list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(FlightDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(FlightDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FlightDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getAirlineName() != null && dto.getAirlineName().length() > 0) {
					criteria.add(Restrictions.like("airlineName",
							dto.getAirlineName() + "%"));
				}

				if (dto.getSource() != null && dto.getSource().length() > 0) {
					criteria.add(Restrictions.like("source",
							dto.getSource() + "%"));
				}

				if (dto.getDestination() != null && dto.getDestination().length() > 0) {
					criteria.add(Restrictions.like("destination",
							dto.getDestination() + "%"));
				}
			}

			// Pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Flight search");
		} finally {
			session.close();
		}

		return list;
	}
}