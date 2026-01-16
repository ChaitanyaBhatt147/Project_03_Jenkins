package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.HostelRoomDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Hostel Room model
 * 
 * @author Chaitanya Bhatt
 *
 */

public class HostelRoomModelJDBCImpl implements HostelRoomModelInt {

	private static Logger log = Logger.getLogger(HostelRoomModelJDBCImpl.class);

	/**
	 * get next primary key
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public static long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM HOSTEL_ROOM");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting next PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * add hostel room
	 */
	@Override
	public long add(HostelRoomDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");

		long pk = 0;
		Connection conn = null;

		try {
			HostelRoomDTO existDto = findByRoomNo(dto.getRoomNo());
			if (existDto != null) {
				throw new DuplicateRecordException("Room No already exists");
			}

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO HOSTEL_ROOM VALUES(?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getRoomNo());
			ps.setInt(3, dto.getCapacity());
			ps.setString(4, dto.getType());
			ps.setString(5, dto.getWashroom());
			ps.setString(6, dto.getFees());
			ps.setString(7, dto.getStatus());
			ps.setString(8, dto.getCreatedBy());
			ps.setString(9, dto.getModifiedBy());
			ps.setTimestamp(10, dto.getCreatedDatetime());

			ps.execute();
			ps.close();
			conn.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model add End");
		return pk;
	}

	/**
	 * update hostel room
	 */
	@Override
	public void update(HostelRoomDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model update Started");

		Connection conn = null;

		try {
			HostelRoomDTO existDto = findByRoomNo(dto.getRoomNo());
			if (existDto != null && existDto.getId() != dto.getId()) {
				throw new DuplicateRecordException("Room No already exists");
			}

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE HOSTEL_ROOM SET ROOM_NO=?, CAPACITY=?, TYPE=?, WASHROOM=?, FEES=?, STATUS=?, " +
					"MODIFIED_BY=?, MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, dto.getRoomNo());
			ps.setInt(2, dto.getCapacity());
			ps.setString(3, dto.getType());
			ps.setString(4, dto.getWashroom());
			ps.setString(5, dto.getFees());
			ps.setString(6, dto.getStatus());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getModifiedDatetime());
			ps.setLong(9, dto.getId());

			ps.execute();
			ps.close();
			conn.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in update Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model update End");
	}

	/**
	 * delete hostel room
	 */
	@Override
	public void delete(HostelRoomDTO dto) throws ApplicationException {

		log.debug("Model delete Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM HOSTEL_ROOM WHERE ID=?");
			ps.setLong(1, dto.getId());
			ps.execute();
			ps.close();
			conn.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model delete End");
	}

	/**
	 * find by PK
	 */
	@Override
	public HostelRoomDTO findByPK(long pk) throws ApplicationException {

		log.debug("Model findByPK Started");

		HostelRoomDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM HOSTEL_ROOM WHERE ID=?");
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				dto = new HostelRoomDTO();
				dto.setId(rs.getLong(1));
				dto.setRoomNo(rs.getString(2));
				dto.setCapacity(rs.getInt(3));
				dto.setType(rs.getString(4));
				dto.setWashroom(rs.getString(5));
				dto.setFees(rs.getString(6));
				dto.setStatus(rs.getString(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in find HostelRoom by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model findByPK End");
		return dto;
	}

	/**
	 * find by Room No
	 */
	public HostelRoomDTO findByRoomNo(String roomNo) throws ApplicationException {

		log.debug("Model findByRoomNo Started");

		HostelRoomDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM HOSTEL_ROOM WHERE ROOM_NO=?");
			ps.setString(1, roomNo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				dto = new HostelRoomDTO();
				dto.setId(rs.getLong(1));
				dto.setRoomNo(rs.getString(2));
				dto.setCapacity(rs.getInt(3));
				dto.setType(rs.getString(4));
				dto.setWashroom(rs.getString(5));
				dto.setFees(rs.getString(6));
				dto.setStatus(rs.getString(7));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in find HostelRoom by Room No");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model findByRoomNo End");
		return dto;
	}

	@Override
	public List search(HostelRoomDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(HostelRoomDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");

		ArrayList list = new ArrayList();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM HOSTEL_ROOM WHERE 1=1");

		if (dto != null) {
			if (dto.getRoomNo() != null && dto.getRoomNo().length() > 0) {
				sql.append(" AND ROOM_NO like '" + dto.getRoomNo() + "%'");
			}
			if (dto.getType() != null && dto.getType().length() > 0) {
				sql.append(" AND TYPE = '" + dto.getType() + "'");
			}
			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				sql.append(" AND STATUS = '" + dto.getStatus() + "'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				HostelRoomDTO rdto = new HostelRoomDTO();
				rdto.setId(rs.getLong(1));
				rdto.setRoomNo(rs.getString(2));
				rdto.setCapacity(rs.getInt(3));
				rdto.setType(rs.getString(4));
				rdto.setWashroom(rs.getString(5));
				rdto.setFees(rs.getString(6));
				rdto.setStatus(rs.getString(7));
				list.add(rdto);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in search Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");

		ArrayList list = new ArrayList();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM HOSTEL_ROOM");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				HostelRoomDTO dto = new HostelRoomDTO();
				dto.setId(rs.getLong(1));
				dto.setRoomNo(rs.getString(2));
				dto.setCapacity(rs.getInt(3));
				dto.setType(rs.getString(4));
				dto.setWashroom(rs.getString(5));
				dto.setFees(rs.getString(6));
				dto.setStatus(rs.getString(7));
				list.add(dto);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in list Hostel Room");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}
}
