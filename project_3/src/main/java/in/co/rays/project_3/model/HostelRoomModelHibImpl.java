package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import in.co.rays.project_3.dto.HostelRoomDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class HostelRoomModelHibImpl implements HostelRoomModelInt {

	@Override
	public long add(HostelRoomDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			HostelRoomDTO existDto = findByRoomNo(dto.getRoomNo());
			if (existDto != null) {
				throw new DuplicateRecordException("Room No already exists");
			}

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			return dto.getId();

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw new ApplicationException("Exception in adding Hostel Room");
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(HostelRoomDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw new ApplicationException("Exception in deleting Hostel Room");
		} finally {
			session.close();
		}
	}

	@Override
	public void update(HostelRoomDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			HostelRoomDTO existDto = findByRoomNo(dto.getRoomNo());

			if (existDto != null && existDto.getId() != dto.getId()) {
				throw new DuplicateRecordException("Room No already exists");
			}

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw new ApplicationException("Exception in updating Hostel Room");
		} finally {
			session.close();
		}
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Query q = session.createQuery("from HostelRoomDTO");

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				q.setFirstResult(pageNo);
				q.setMaxResults(pageSize);
			}

			list = q.list();

		} catch (Exception e) {
			throw new ApplicationException("Exception in HostelRoom list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(HostelRoomDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(HostelRoomDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();

			StringBuffer hql = new StringBuffer("from HostelRoomDTO where 1=1");

			if (dto != null) {
				if (dto.getRoomNo() != null && dto.getRoomNo().length() > 0) {
					hql.append(" and roomNo like '" + dto.getRoomNo() + "%'");
				}
				if (dto.getType() != null && dto.getType().length() > 0) {
					hql.append(" and type = '" + dto.getType() + "'");
				}
				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					hql.append(" and status = '" + dto.getStatus() + "'");
				}
			}

			Query q = session.createQuery(hql.toString());

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				q.setFirstResult(pageNo);
				q.setMaxResults(pageSize);
			}

			list = q.list();

		} catch (Exception e) {
			throw new ApplicationException("Exception in HostelRoom search");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public HostelRoomDTO findByPK(long pk) throws ApplicationException {

		Session session = null;

		try {
			session = HibDataSource.getSession();
			return (HostelRoomDTO) session.get(HostelRoomDTO.class, pk);

		} catch (Exception e) {
			throw new ApplicationException("Exception in find HostelRoom by PK");
		} finally {
			session.close();
		}
	}

	@Override
	public HostelRoomDTO findByRoomNo(String roomNo) throws ApplicationException {

		Session session = null;
		HostelRoomDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Query q = session.createQuery("from HostelRoomDTO where roomNo=?");
			q.setString(0, roomNo);
			List list = q.list();

			if (list.size() > 0) {
				dto = (HostelRoomDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in find HostelRoom by Room No");
		} finally {
			session.close();
		}

		return dto;
	}
}
