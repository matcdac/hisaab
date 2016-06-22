package debryan.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import debryan.model.User;
import debryan.service.Itemation;
import debryan.service.Logination;
import debryan.service.Oldination;
import debryan.service.Participation;
import debryan.service.Registration;

@Controller
public class Home {
	
	static Map<String,User> alive = new HashMap<String,User>();

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView homeMethod() {
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView homeMethod2() {
		return homeMethod();
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerMethod() {
		String message = "<div id=\"welcome\">Welcome to our Website</div>";
		String about = "<div id=\"about\">Hope this Automation can be of use to you,<br>since it is meant to reduce complex calculations,<br>and not fry your brians.......";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		modelAndView.addObject("about", about);
		return modelAndView;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerMethod2() {
		return registerMethod();
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView saveMethod(HttpServletRequest httpServletRequest) {
		String message;
		
		Registration registration = new Registration();
		if(registration.save(httpServletRequest))
			message = "<div id=\"success\">Successfully Registered</div>";
		else
			message = "<div id=\"error\">There was some Problem in Registration. Kindly Register again.</div>";
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveMethod2(HttpServletRequest httpServletRequest) {
		return saveMethod(httpServletRequest);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession httpSession = httpServletRequest.getSession(true);
		boolean loggedIn = false;
		if(httpSession.getAttribute("Hisaab-loggedin")!=null && httpSession.getAttribute("Hisaab-loggedin").equals("true"))
			loggedIn = true;
		if(loggedIn) {
			RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/enter");
			try {
				requestDispatcher.forward(httpServletRequest, httpServletResponse);
			}
			catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return loginMethod(httpServletRequest, httpServletResponse);
	}
	
	@RequestMapping(value = "/enter", method = RequestMethod.GET)
	public ModelAndView enterMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		String message;
		
		HttpSession httpSession = httpServletRequest.getSession(true);
		
		Logination logination = new Logination();
		
		User user = logination.enter(httpServletRequest, alive);
		
		if(user.real) {
			message = user.getName() ;
			
			httpSession.setAttribute("Hisaab-email",user.getEmail());
			httpSession.setAttribute("Hisaab-password",user.getPassword());
			httpSession.setAttribute("Hisaab-name",user.getName());
			httpSession.setAttribute("Hisaab-phone",user.getPhone());
			httpSession.setAttribute("Hisaab-loggedin","true");
		}
		else {
			message = "";
			
			httpSession.setAttribute("Hisaab-email",null);
			httpSession.setAttribute("Hisaab-password",null);
			httpSession.setAttribute("Hisaab-name",null);
			httpSession.setAttribute("Hisaab-phone",null);
			httpSession.setAttribute("Hisaab-loggedin",null);
			
			RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/sorry");
			try {
				requestDispatcher.forward(httpServletRequest, httpServletResponse);
			}
			catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/enter", method = RequestMethod.POST)
	public ModelAndView enterMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return enterMethod(httpServletRequest, httpServletResponse);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		ModelAndView modelAndView = new ModelAndView();
		HttpSession httpSession = httpServletRequest.getSession(false);
		
		String currentEmail = (String)httpSession.getAttribute("Hisaab-email");
		alive.remove(currentEmail);
		
		httpSession.invalidate();
		
		RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/home");
		try {
			requestDispatcher.forward(httpServletRequest, httpServletResponse);
		}
		catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logoutMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return logoutMethod(httpServletRequest, httpServletResponse);
	}
	
	@RequestMapping(value = "/sorry", method = RequestMethod.GET)
	public ModelAndView sorryMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}
	
	@RequestMapping(value = "/sorry", method = RequestMethod.POST)
	public ModelAndView sorryMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return sorryMethod(httpServletRequest, httpServletResponse);
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession httpSession = httpServletRequest.getSession(true);
		boolean loggedIn = false;
		if(httpSession.getAttribute("Hisaab-loggedin")!=null && httpSession.getAttribute("Hisaab-loggedin").equals("true")) {
			String currentEmail = (String)httpSession.getAttribute("Hisaab-email");
			String currentPassword = (String)httpSession.getAttribute("Hisaab-password");
			String currentName = (String)httpSession.getAttribute("Hisaab-name");
			Long currentPhone = (Long)httpSession.getAttribute("Hisaab-phone");
			
			User original = alive.get(currentEmail);
			if(original.getName()==currentName && original.getPhone()==currentPhone && original.getPassword()==currentPassword)
				loggedIn = true;
		}
		if(!loggedIn) {
			RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/home");
			try {
				requestDispatcher.forward(httpServletRequest, httpServletResponse);
			}
			catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", httpSession.getAttribute("Hisaab-name"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView newMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return newMethod(httpServletRequest, httpServletResponse);
	}
	
	@RequestMapping(value = "/old", method = RequestMethod.GET)
	public ModelAndView oldMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession httpSession = httpServletRequest.getSession(true);
		boolean loggedIn = false;
		if(httpSession.getAttribute("Hisaab-loggedin")!=null && httpSession.getAttribute("Hisaab-loggedin").equals("true")) {
			String currentEmail = (String)httpSession.getAttribute("Hisaab-email");
			String currentPassword = (String)httpSession.getAttribute("Hisaab-password");
			String currentName = (String)httpSession.getAttribute("Hisaab-name");
			Long currentPhone = (Long)httpSession.getAttribute("Hisaab-phone");
			
			User original = alive.get(currentEmail);
			if(original.getName()==currentName && original.getPhone()==currentPhone && original.getPassword()==currentPassword)
				loggedIn = true;
		}
		if(!loggedIn) {
			RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/home");
			try {
				requestDispatcher.forward(httpServletRequest, httpServletResponse);
			}
			catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		Oldination oldination = new Oldination(httpServletRequest, httpServletResponse);
		oldination.process();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", httpSession.getAttribute("Hisaab-name"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/old", method = RequestMethod.POST)
	public ModelAndView oldMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return oldMethod(httpServletRequest, httpServletResponse);
	}
	
	@RequestMapping(value = "/participants", method = RequestMethod.GET)
	public ModelAndView participantsMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession httpSession = httpServletRequest.getSession(true);
		boolean loggedIn = false;
		if(httpSession.getAttribute("Hisaab-loggedin")!=null && httpSession.getAttribute("Hisaab-loggedin").equals("true")) {
			String currentEmail = (String)httpSession.getAttribute("Hisaab-email");
			String currentPassword = (String)httpSession.getAttribute("Hisaab-password");
			String currentName = (String)httpSession.getAttribute("Hisaab-name");
			Long currentPhone = (Long)httpSession.getAttribute("Hisaab-phone");
			
			User original = alive.get(currentEmail);
			if(original.getName()==currentName && original.getPhone()==currentPhone && original.getPassword()==currentPassword)
				loggedIn = true;
		}
		if(!loggedIn) {
			RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/home");
			try {
				requestDispatcher.forward(httpServletRequest, httpServletResponse);
			}
			catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		Participation participation = new Participation();
		participation.process(httpServletRequest);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", httpSession.getAttribute("Hisaab-name"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/participants", method = RequestMethod.POST)
	public ModelAndView participantsMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return participantsMethod(httpServletRequest, httpServletResponse);
	}
	
	@RequestMapping(value = "/items", method = RequestMethod.GET)
	public ModelAndView itemsMethod(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession httpSession = httpServletRequest.getSession(true);
		boolean loggedIn = false;
		if(httpSession.getAttribute("Hisaab-loggedin")!=null && httpSession.getAttribute("Hisaab-loggedin").equals("true")) {
			String currentEmail = (String)httpSession.getAttribute("Hisaab-email");
			String currentPassword = (String)httpSession.getAttribute("Hisaab-password");
			String currentName = (String)httpSession.getAttribute("Hisaab-name");
			Long currentPhone = (Long)httpSession.getAttribute("Hisaab-phone");
			
			User original = alive.get(currentEmail);
			if(original.getName()==currentName && original.getPhone()==currentPhone && original.getPassword()==currentPassword)
				loggedIn = true;
		}
		if(!loggedIn) {
			RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/home");
			try {
				requestDispatcher.forward(httpServletRequest, httpServletResponse);
			}
			catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		Itemation itemation = new Itemation();
		itemation.process(httpServletRequest,httpServletResponse);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", httpSession.getAttribute("Hisaab-name"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/items", method = RequestMethod.POST)
	public ModelAndView itemsMethod2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return itemsMethod(httpServletRequest, httpServletResponse);
	}

}
