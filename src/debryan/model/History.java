package debryan.model;

import java.util.List;

public class History {

	private String sessionName;
	private int totalItemCount;
	private List<String> itemNames;
	private int totalParticipantCount;
	private List<String> participantNames;
	private boolean[][] paidOriginally;
	private double[][] paidAmountOriginally;
	private boolean[][] owners;

	public History() {
	}

	public History(String sessionName, int totalItemCount,
			List<String> itemNames, int totalParticipantCount,
			List<String> participantNames, boolean[][] paidOriginally,
			double[][] paidAmountOriginally, boolean[][] owners) {
		super();
		this.sessionName = sessionName;
		this.totalItemCount = totalItemCount;
		this.itemNames = itemNames;
		this.totalParticipantCount = totalParticipantCount;
		this.participantNames = participantNames;
		this.paidOriginally = paidOriginally;
		this.paidAmountOriginally = paidAmountOriginally;
		this.owners = owners;
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

	public boolean[][] getPaidOriginally() {
		return paidOriginally;
	}

	public void setPaidOriginally(boolean[][] paidOriginally) {
		this.paidOriginally = paidOriginally;
	}

	public double[][] getPaidAmountOriginally() {
		return paidAmountOriginally;
	}

	public void setPaidAmountOriginally(double[][] paidAmountOriginally) {
		this.paidAmountOriginally = paidAmountOriginally;
	}

	public boolean[][] getOwners() {
		return owners;
	}

	public void setOwners(boolean[][] owners) {
		this.owners = owners;
	}

	@Override
	public String toString() {
		return JsonUtil.jsonToString(this);
	}

}
