package in.co.rays.project_3.dto;

/**
 * HostelRoom JavaBean encapsulates Hostel Room attributes
 * 
 * @author Chaitanya Bhatt
 *
 */

public class HostelRoomDTO extends BaseDTO {

	
	private static final long serialVersionUID = 1L;

	private String roomNo;
	private int capacity;        // number of students
	private String type;         // AC / NON-AC
	private String washroom;     // ATTACHED / COMMON
	private String fees;
	private String status;       // AVAILABLE / FULL / MAINTENANCE

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWashroom() {
		return washroom;
	}

	public void setWashroom(String washroom) {
		this.washroom = washroom;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String string) {
		this.fees = string;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return roomNo + "";
	}

}
