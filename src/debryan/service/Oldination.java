package debryan.service;

import java.util.ArrayList;
import java.util.HashMap;
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

import debryan.model.End;
import debryan.model.FinalHisaab;
import debryan.model.JsonUtil;

public class Oldination {
	
private static final Logger LOG = LoggerFactory.getLogger(Itemation.class);
	
	private static Cluster cluster;
	private static Session session;
	private static CassandraOperations cassandraOperations;
	
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private HttpSession httpSession;
	
	public Oldination() {
	}
	
	public Oldination(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
	}
	
	private void initialize() {
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("debryan");
		cassandraOperations = new CassandraTemplate(session);
		
	}
	
	public void process() {
		httpSession = httpServletRequest.getSession(true);
		
		String email = (String)httpSession.getAttribute("Hisaab-email");
		End original = new End();
		
		initialize();
		
		Map<String, FinalHisaab> finalHisaab = new HashMap<String, FinalHisaab>();
		
		String cqlAll = "select * from end where email=\'"+email+"\'";

		List<End> result = cassandraOperations.select(cqlAll, End.class);
		for (End i : result) {
			original = i;
			LOG.info(String.format("Found People with EMAIL [%s] for FINALHISAAB [%s]", i.getEmail(), i.getFinalHisaabAsString()));
		}
		
		if( original.getEmail()!=null ) {
			Map<String, FinalHisaab> map = JsonUtil.readJsonFromString(original.getFinalHisaabAsString(), HashMap.class);
			original.setFinalHisaab(map);
			
			httpSession.setAttribute("old", map);
			httpSession.setAttribute("oldExists", "true");
			System.out.println("SESSION ATTRIBUTE : old : (from Oldination.java) "+map);
		}
		else {
			httpSession.setAttribute("oldExists", "false");
		}
		
		
	}

}
