package debryan.model;

import java.util.List;
import java.util.Map;

public class FinalHisaab {
	
	private String sessionName;
	private int totalItemCount;
	private List<String> itemNames;
	private int totalParticipantCount;
	private List<String> participantNames;
	private double[][][] hisaabFinal;
	private Map<String, List<String>> finalHisaabText;
	
	public FinalHisaab() {
	}

	public FinalHisaab(String sessionName, int totalItemCount,
			List<String> itemNames, int totalParticipantCount,
			List<String> participantNames, double[][][] hisaabFinal, Map<String, List<String>> finalHisaabText) {
		super();
		this.sessionName = sessionName;
		this.totalItemCount = totalItemCount;
		this.itemNames = itemNames;
		this.totalParticipantCount = totalParticipantCount;
		this.participantNames = participantNames;
		this.hisaabFinal = hisaabFinal;
		this.finalHisaabText = finalHisaabText;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public int getTotalItemCount() {
		return totalItemCount;
	}

	public void setTotalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

	public List<String> getItemNames() {
		return itemNames;
	}

	public void setItemNames(List<String> itemNames) {
		this.itemNames = itemNames;
	}

	public int getTotalParticipantCount() {
		return totalParticipantCount;
	}

	public void setTotalParticipantCount(int totalParticipantCount) {
		this.totalParticipantCount = totalParticipantCount;
	}

	public List<String> getParticipantNames() {
		return participantNames;
	}

	public void setParticipantNames(List<String> participantNames) {
		this.participantNames = participantNames;
	}

	public double[][][] getHisaabFinal() {
		return hisaabFinal;
	}

	public void setHisaabFinal(double[][][] hisaabFinal) {
		this.hisaabFinal = hisaabFinal;
	}
	
	public Map<String, List<String>> getFinalHisaabText() {
		return finalHisaabText;
	}

	public void setFinalHisaabText(Map<String, List<String>> finalHisaabText) {
		this.finalHisaabText = finalHisaabText;
	}

	@Override
	public String toString() {
		return JsonUtil.jsonToString(this);
	}
	
}
