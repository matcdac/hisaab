package debryan.model;

import java.util.Map;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "initial")
public class Initial {

	@PrimaryKey("email")
	private String email;

	@Transient
	private Map<String, History> history;

	@Column(value = "history")
	private String historyAsString;

	public Initial() {

	}

	public Initial(String email, Map<String, History> history) {
		super();
		this.email = email;
		this.history = history;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, History> getHistory() {
		return history;
	}

	public void setHistory(Map<String, History> history) {
		this.history = history;
	}

	public String getHistoryAsString() {
		return historyAsString;
	}

	public void setHistoryAsString(String historyAsString) {
		this.historyAsString = historyAsString;
	}

	@Override
	public String toString() {
		return JsonUtil.jsonToString(this);
	}

}
