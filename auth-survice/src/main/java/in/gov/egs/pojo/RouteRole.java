package in.gov.egs.pojo;

public class RouteRole {
	private String path;
	private String role;

	public RouteRole() {
	}

	public RouteRole(String path, String role) {
		this.path = path;
		this.role = role;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
