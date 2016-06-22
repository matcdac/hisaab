package debryan.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

import debryan.model.User;

public class Registration {

	private static final Logger LOG = LoggerFactory.getLogger(Registration.class);

	private static Cluster cluster;
	private static Session session;

	public boolean save(HttpServletRequest httpServletRequest) {

		try {
			User user = new User();
			user.setEmail(httpServletRequest.getParameter("email"));
			user.setName(httpServletRequest.getParameter("name"));
			user.setPassword(httpServletRequest.getParameter("password"));
			user.setPhone(Long.parseLong(httpServletRequest.getParameter("phone")));
			
			cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

			session = cluster.connect("debryan");

			CassandraOperations cassandraOps = new CassandraTemplate(session);

			Statement insert2 = cassandraOps
					.getSession()
					.prepare("insert into user (email, name, password, phone) values (?, ?, ?, ?)")
					.bind(user.getEmail(), user.getName(), user.getPassword(), user.getPhone());
			cassandraOps.execute(insert2);
			
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			cluster.close();
		}

	}

}
