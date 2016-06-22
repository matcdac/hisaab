package debryan.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Participation {
	
	public void process(HttpServletRequest httpServletRequest) {
		
		String users = httpServletRequest.getParameter("users");
		int totalParticipantCount = Integer.parseInt(users);
		
		String date = (String)httpServletRequest.getParameter("date");
		List<String> participantNames = new ArrayList<String>();
		
		int i=1;
		while (i<=totalParticipantCount) {
			String attributeNameWouldBe = "user"+i+"";
			String value = httpServletRequest.getParameter(attributeNameWouldBe);
			participantNames.add(value);
			i++;
		}
		
		String[] session = date.split("\\s+");
		
		int month = 0;
		if(session[1].equalsIgnoreCase("Jan"))
			month=1;
		else if(session[1].equalsIgnoreCase("Feb"))
			month=2;
		else if(session[1].equalsIgnoreCase("Mar"))
			month=3;
		else if(session[1].equalsIgnoreCase("Apr"))
			month=4;
		else if(session[1].equalsIgnoreCase("May"))
			month=5;
		else if(session[1].equalsIgnoreCase("Jun"))
			month=6;
		else if(session[1].equalsIgnoreCase("Jul"))
			month=7;
		else if(session[1].equalsIgnoreCase("Aug"))
			month=8;
		else if(session[1].equalsIgnoreCase("Sep"))
			month=9;
		else if(session[1].equalsIgnoreCase("Oct"))
			month=10;
		else if(session[1].equalsIgnoreCase("Nov"))
			month=11;
		else if(session[1].equalsIgnoreCase("Dec"))
			month=12;
		
		String sessionName = session[3] + " " + month + " " + session[2] + " " + session[4] + " " + session[5] + " " + session[6] + " " + session[7] + " " + session[8] + " " + session[0] ;
		
		System.out.println( "Session name would be :    "+sessionName );
		System.out.println( "Total number of participants would be :    "+totalParticipantCount );
		System.out.println( "Participants names would be :   "+participantNames.toString() );
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		httpSession.setAttribute("participantsCount", totalParticipantCount);
		httpSession.setAttribute("participants", participantNames);
		httpSession.setAttribute("history", sessionName);
		
	}
	
}
