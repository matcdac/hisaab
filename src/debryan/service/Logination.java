package debryan.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import debryan.model.User;

public class Logination {

	private static final Logger LOG = LoggerFactory.getLogger(Logination.class);

	private static Cluster cluster;
	private static Session session;
	
	public Logination() {
		
	}
	
	public User enter(HttpServletRequest httpServletRequest, Map<String,User> alive) {
		
		User user = new User();
		user.real = false;
		
		try {
			User original = new User();
			
			HttpSession httpSession = httpServletRequest.getSession(true);
			
			String email = (String)(httpServletRequest.getParameter("email")==null ? httpSession.getAttribute("Hisaab-email") : httpServletRequest.getParameter("email"));
			String password = (String)(httpServletRequest.getParameter("password")==null ? httpSession.getAttribute("Hisaab-password") : httpServletRequest.getParameter("password"));
			
			cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

			session = cluster.connect("debryan");

			CassandraOperations cassandraOperations = new CassandraTemplate(session);

			String cqlAll = "select * from user where email=\'"+email+"\'";

			List<User> result = cassandraOperations.select(cqlAll, User.class);
			for (User u : result) {
				original = u;
				original.real = true;
				LOG.info(String.format("Found People with NAME [%s] for EMAIL [%s]", u.getName(), u.getEmail()));
			}
			
			if( original.getPassword()!=null && original.getPassword().equals(password) ) {
				user = original;
				alive.put(email, original);
			}
			
			return user;
		}
		catch (Exception e) {
			e.printStackTrace();
			return user;
		}
		finally{
			cluster.close();
		}

	}

}
