package debryan.model;

import java.util.Map;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "end")
public class End {

	@PrimaryKey("email")
	private String email;

	@Transient
	private Map<String, FinalHisaab> finalHisaab;

	@Column(value = "finalhisaab")
	private String finalHisaabAsString;

	public End() {

	}

	public End(String email, Map<String, FinalHisaab> finalHisaab) {
		super();
		this.email = email;
		this.finalHisaab = finalHisaab;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, FinalHisaab> getFinalHisaab() {
		return finalHisaab;
	}

	public void setFinalHisaab(Map<String, FinalHisaab> finalHisaab) {
		this.finalHisaab = finalHisaab;
	}

	public String getFinalHisaabAsString() {
		return finalHisaabAsString;
	}

	public void setFinalHisaabAsString(String finalHisaabAsString) {
		this.finalHisaabAsString = finalHisaabAsString;
	}

	@Override
	public String toString() {
		return JsonUtil.jsonToString(this);
	}

}
