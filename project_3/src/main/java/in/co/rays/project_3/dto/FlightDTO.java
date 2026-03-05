package in.co.rays.project_3.dto;

public class FlightDTO extends BaseDTO {

	private String airlineName;
	private String source;
	private String destination;

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return airlineName;
	}
}