package debryan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

import debryan.model.End;
import debryan.model.FinalHisaab;
import debryan.model.History;
import debryan.model.Initial;
import debryan.model.JsonUtil;

public class Itemation {
	
	private static final Logger LOG = LoggerFactory.getLogger(Itemation.class);
	
	private static Cluster cluster;
	private static Session session;
	private static CassandraOperations cassandraOperations;
	
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private HttpSession httpSession;
	
	private String sessionName;
	
	//rows
	private int totalItemCount;
	private List<String> itemNames;
	
	//columns
	private int totalParticipantCount;
	private List<String> participantNames;
	private Map<String, Integer> namesPeople;
	
	private boolean[][] paidOriginally;
	private double[][] paidAmountOriginally;
	private boolean[][] owners;
	
	private double[][][] hisaabFinal;
	private Map<String, List<String>> finalHisaabText;
	
	private void initialize() {
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("debryan");
		cassandraOperations = new CassandraTemplate(session);
		
	}
	
	private void saveFinalData() {
		
		try {
			String email = (String)httpSession.getAttribute("Hisaab-email");
			
			End original = new End();
			End end = new End();
			end.setEmail(email);
			
			String newKey = sessionName;
			FinalHisaab newValue = new FinalHisaab(sessionName, totalItemCount, itemNames, totalParticipantCount, participantNames, hisaabFinal, finalHisaabText);
			
			initialize();
			
			boolean contains = false;
			Map<String, FinalHisaab> finalHisaab = new HashMap<String, FinalHisaab>();
			
			//Check if data is already there
			String cqlAll = "select * from end where email=\'"+email+"\'";

			List<End> result = cassandraOperations.select(cqlAll, End.class);
			for (End i : result) {
				original = i;
				LOG.info(String.format("Found People with EMAIL [%s] for FINALHISAAB [%s]", i.getEmail(), i.getFinalHisaabAsString()));
			}
			
			if( original.getEmail()!=null ) {
				finalHisaab = JsonUtil.readJsonFromString(original.getFinalHisaabAsString(), HashMap.class);
				System.out.println(":::::::DEBUG, FINAL, MAP:::::: "+finalHisaab);
				contains = finalHisaab.containsKey(newKey);
			}
			
			//Adding the new data
			if(!contains) {
				finalHisaab.put(newKey, newValue);
			}
			
			end.setFinalHisaab(finalHisaab);
			
			String historyAsString = JsonUtil.jsonToString(finalHisaab);
			end.setFinalHisaabAsString(historyAsString);
			
			
			Statement insert2 = cassandraOperations
					.getSession()
					.prepare("insert into end (email, finalhisaab) values (?, ?)")
					.bind(end.getEmail(), end.getFinalHisaabAsString());
			cassandraOperations.execute(insert2);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			cluster.close();
		}
		
	}
	
	private void saveInitialData() {
		
		try {
			String email = (String)httpSession.getAttribute("Hisaab-email");
			
			Initial original = new Initial();
			Initial initial = new Initial();
			initial.setEmail(email);
			
			String newKey = sessionName;
			History newValue = new History(sessionName, totalItemCount, itemNames, totalParticipantCount, participantNames, paidOriginally, paidAmountOriginally, owners);
			
			initialize();
			
			boolean contains = false;
			Map<String, History> history = new HashMap<String, History>();
			
			//Check if data is already there
			String cqlAll = "select * from initial where email=\'"+email+"\'";

			List<Initial> result = cassandraOperations.select(cqlAll, Initial.class);
			for (Initial i : result) {
				original = i;
				LOG.info(String.format("Found People with EMAIL [%s] for HISTORY [%s]", i.getEmail(), i.getHistoryAsString()));
			}
			
			if( original.getEmail()!=null ) {
				history = JsonUtil.readJsonFromString(original.getHistoryAsString(), HashMap.class);
				System.out.println(":::::::DEBUG, INITIAL, MAP:::::: "+history);
				contains = history.containsKey(newKey);
			}
			
			//Adding the new data
			if(!contains) {
				history.put(newKey, newValue);
			}
			
			initial.setHistory(history);
			
			String historyAsString = JsonUtil.jsonToString(history);
			initial.setHistoryAsString(historyAsString);
			
			
			Statement insert2 = cassandraOperations
					.getSession()
					.prepare("insert into initial (email, history) values (?, ?)")
					.bind(initial.getEmail(), initial.getHistoryAsString());
			cassandraOperations.execute(insert2);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			cluster.close();
		}

	}
	
	private void calculate() {
		
		int i,j,k;
		
		finalHisaabText = new HashMap<String, List<String>>();
		
		hisaabFinal = new double[totalItemCount+1][totalParticipantCount][totalParticipantCount];
		
		for(i=0;i<totalItemCount;i++)
			for(j=0;j<totalParticipantCount;j++)
				for(k=0;k<totalParticipantCount;k++)
					hisaabFinal[i][j][k]=0.0;
		
		int currentItem=1;
		for(String item:itemNames) {
			double[] leftPayment = new double[totalParticipantCount];
			boolean[] realOwners = new boolean[totalParticipantCount];
			
			double totalAmount = 0.0;
			int countOwners = 0;
			double averageAmountPerHead = 0.0;
			
			for(i=0;i<totalParticipantCount;i++) {
				leftPayment[i] = paidAmountOriginally[currentItem][i+1];
				realOwners[i] = owners[currentItem][i+1];
				
				if(realOwners[i])
					countOwners++;
				
				totalAmount = totalAmount + leftPayment[i];
			}
			
			averageAmountPerHead = totalAmount / countOwners;
			
			System.out.println("*********************************************************************************");
			
			int countNegativeOwners=0;
			
			//First Pass : Negative Values Means (outgoing) they must give money, Positive means (incoming) they must take money
			for(i=0;i<totalParticipantCount;i++) {
				System.out.println("Original Amount : Person "+i+" : "+leftPayment[i]);
				if(leftPayment[i]==0 && !realOwners[i]) {
					System.out.println("Would not participate for this item");
					continue;
				}
				if(realOwners[i] && leftPayment[i]>=0)
					leftPayment[i] = leftPayment[i]-averageAmountPerHead;
				System.out.println("Relative Amount : Person "+i+" : "+leftPayment[i]);
				if(leftPayment[i]<0)
					countNegativeOwners++;
			}
			
			System.out.println("*********************************************************************************");
			
			System.out.println("Item : "+currentItem);
			System.out.println("Item Name : "+item);
			
			System.out.println("*********************************************************************************");
			
			for(i=0;i<totalParticipantCount;i++) {
				
				while(leftPayment[i]<0) {
					
					for(j=0;j<totalParticipantCount;j++) {
						
						double value = leftPayment[i]+leftPayment[j];
						
						if( i!=j )
							if( leftPayment[i]<0 )
								if( leftPayment[j]>0 ) {
									if( value > 0 ) {
										double transfer = -leftPayment[i];
										leftPayment[j] = value;
										leftPayment[i] = 0.0;
										
										String write = participantNames.get(i)+" pays "+participantNames.get(j)+" -> "+transfer;
										System.out.println( write );
										
										List<String> listOfHisaabOfCurrentItem;
										if( finalHisaabText.containsKey(item) ) {
											listOfHisaabOfCurrentItem = finalHisaabText.get(item);
										}
										else {
											listOfHisaabOfCurrentItem = new ArrayList<String>();
										}
										listOfHisaabOfCurrentItem.add(write);
										finalHisaabText.put(item, listOfHisaabOfCurrentItem);
										
										hisaabFinal[currentItem-1][i][j]=transfer;
									}
									else if( value < 0 ) {
										double transfer = leftPayment[j];
										leftPayment[i] = value;
										leftPayment[j] = 0.0;
										
										String write = participantNames.get(i)+" pays "+participantNames.get(j)+" -> "+transfer;
										System.out.println( write );
										
										List<String> listOfHisaabOfCurrentItem;
										if( finalHisaabText.containsKey(item) ) {
											listOfHisaabOfCurrentItem = finalHisaabText.get(item);
										}
										else {
											listOfHisaabOfCurrentItem = new ArrayList<String>();
										}
										listOfHisaabOfCurrentItem.add(write);
										finalHisaabText.put(item, listOfHisaabOfCurrentItem);
										
										hisaabFinal[currentItem-1][i][j]=transfer;
									}
									else {
										double transfer = leftPayment[j];
										leftPayment[i] = 0.0;
										leftPayment[j] = 0.0;
										
										String write = participantNames.get(i)+" pays "+participantNames.get(j)+" -> "+transfer;
										System.out.println( write );
										
										List<String> listOfHisaabOfCurrentItem;
										if( finalHisaabText.containsKey(item) ) {
											listOfHisaabOfCurrentItem = finalHisaabText.get(item);
										}
										else {
											listOfHisaabOfCurrentItem = new ArrayList<String>();
										}
										listOfHisaabOfCurrentItem.add(write);
										finalHisaabText.put(item, listOfHisaabOfCurrentItem);
										
										hisaabFinal[currentItem-1][i][j]=transfer;
									}
								}
						if( leftPayment[i]>(-1) && leftPayment[i]<0 )
							leftPayment[i]=0.0;
					}
					
				}
				
			}
			
			currentItem++;
			
			System.out.println("\n\n***************************************************************************************\n\n");
		}
		
		for(j=0;j<totalParticipantCount;j++)
			for(k=0;k<totalParticipantCount;k++)
				for(i=0;i<totalItemCount;i++) {
					hisaabFinal[totalItemCount][j][k]+=hisaabFinal[i][j][k];
					System.out.println("Now Value of : hisaabFinal["+totalItemCount+"]["+j+"]["+k+"] is ---> "+hisaabFinal[totalItemCount][j][k]);
				}
		
		System.out.println("\n\nFINAL HISAAB\n\n");
		
		List<String> listOfHisaabFinal = new ArrayList<String>();
		
		for(i=0;i<totalParticipantCount;i++)
			for(j=0;j<totalParticipantCount;j++)
				if( hisaabFinal[totalItemCount][i][j]>0.0 && hisaabFinal[totalItemCount][j][i]>0.0 ) {
					if( hisaabFinal[totalItemCount][i][j] > hisaabFinal[totalItemCount][j][i] ) {
						hisaabFinal[totalItemCount][i][j] = hisaabFinal[totalItemCount][i][j] - hisaabFinal[totalItemCount][j][i];
						hisaabFinal[totalItemCount][j][i] = 0.0;
					}
					else {
						hisaabFinal[totalItemCount][j][i] = hisaabFinal[totalItemCount][j][i] - hisaabFinal[totalItemCount][i][j];
						hisaabFinal[totalItemCount][i][j] = 0.0;
					}
				}
		
		for(i=0;i<totalParticipantCount;i++)
			for(j=0;j<totalParticipantCount;j++) 
				if(hisaabFinal[totalItemCount][i][j]>0.0) {
				String write = participantNames.get(i)+" pays "+participantNames.get(j)+" -> "+hisaabFinal[totalItemCount][i][j];
				System.out.println(write);
				listOfHisaabFinal.add(write);
			}
		
		finalHisaabText.put("final", listOfHisaabFinal);
		
		System.out.println("\n\n***************************************************************************************\n\n");
		
		httpSession.setAttribute("hisaabFinal", hisaabFinal);
		httpSession.setAttribute("finalHisaabText", finalHisaabText);
		
	}
	
	public void process (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		
		httpSession = httpServletRequest.getSession(true);
		
		sessionName = (String) httpSession.getAttribute("history");
		
		participantNames = (ArrayList<String>) httpSession.getAttribute("participants");
		totalParticipantCount = (Integer) httpSession.getAttribute("participantsCount");
		
		
		String items = httpServletRequest.getParameter("items");
		totalItemCount = Integer.parseInt(items);
		
		itemNames = new ArrayList<String>();
		
		int i=1;
		while (i<=totalItemCount) {
			String attributeNameWouldBe = "item"+i+"";
			String value = httpServletRequest.getParameter(attributeNameWouldBe);
			itemNames.add(value);
			i++;
		}
		
		httpSession.setAttribute("itemsCount", totalItemCount);
		httpSession.setAttribute("items", itemNames);
		
		
		namesPeople = new HashMap<String,Integer>();
		int j=1;
		Iterator<String> iterator = participantNames.iterator();
		while(iterator.hasNext()) {
			String currentName = iterator.next();
			namesPeople.put(currentName, j);
			j++;
		}
		
		// 0th row & 0th column would be for meta-data when displaying in html
		// the indexes used here start from 1 for both row and column
		
		paidOriginally = new boolean[totalItemCount+1][totalParticipantCount+1];
		for(i=0;i<totalItemCount+1;i++)
			for(j=0;j<totalParticipantCount+1;j++)
				paidOriginally[i][j]=false;
		
		paidAmountOriginally = new double[totalItemCount+1][totalParticipantCount+1];
		for(i=0;i<totalItemCount+1;i++)
			for(j=0;j<totalParticipantCount+1;j++)
				paidAmountOriginally[i][j]=0.0;
		
		owners = new boolean[totalItemCount+1][totalParticipantCount+1];
		for(i=0;i<totalItemCount+1;i++)
			for(j=0;j<totalParticipantCount+1;j++)
				owners[i][j]=false;
		
		for (i = 1; i <= totalItemCount; i++) {
			String s1 = "peoplePaid" + i;
			String s2 = "peopleOwn" + i;

			String[] peoplePaid = httpServletRequest.getParameterValues(s1);
			String[] peopleOwn = httpServletRequest.getParameterValues(s2);

			for (String s : peoplePaid) {
				int z = namesPeople.get(s);
				paidOriginally[i][z] = true;

				String propertyName = s + "Paid" + i;
				String howMuch = httpServletRequest.getParameter(propertyName);
				double moneySpent = Double.parseDouble(howMuch);
				paidAmountOriginally[i][z] = moneySpent;
			}

			for (String s : peopleOwn) {
				int z = namesPeople.get(s);
				owners[i][z] = true;
			}

		}
		
		saveInitialData();
		calculate();
		saveFinalData();
		
	}

}
