package ca.utoronto.ece.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.utoronto.ece.datastore.PlaylistDAO;
import ca.utoronto.ece.datastore.UserDAO;
import ca.utoronto.ece.entity.Playlist;
import ca.utoronto.ece.entity.PlaylistLine;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String loginURL;
		String logoutURL;
		
		if(user!=null){
			logoutURL = userService.createLogoutURL("/");
			request.getSession().setAttribute("logoutURL", logoutURL);
			request.getSession().setAttribute("user", user);
			UserDAO userDao = new UserDAO();
			if(userDao.checkUserExists(user.getEmail())==false){
				//does not exist
				userDao.addNewUser(user.getEmail(), user.getNickname());
				System.out.println("Filter_add new user");
			}
			
			
//			List <ca.utoronto.ece.entity.User> users = (List<ca.utoronto.ece.entity.User>)userDao.getCurrentUsers();
//			request.getSession().setAttribute("users", users);
			
			/*
			//test for DAO methods
			String id = "a231000";
			String name = "Need A Boss";
			String singer = "Shareefa";
			String image = "image_Adam";
			String album = "asdga";
			
			String plName = "Study";
			Set <PlaylistLine>playlistLines = new HashSet<PlaylistLine>();
			Playlist playlist = new Playlist();
			playlist.setName(plName);
			playlist.setUserId(user.getEmail());
			playlist.setPlaylistLines(playlistLines);
			
			PlaylistDAO playlistDao = new PlaylistDAO();
			playlistDao.addNewPlaylist(playlist);
			
			playlistDao.addSongToPlaylist(id,name, singer, image, album, playlist);
			*/
			
			
			
			System.out.println("Filter_set logoutURL&set user");
			
		}
		else{
			loginURL = userService.createLoginURL("/");
			request.getSession().setAttribute("loginURL", loginURL);
			System.out.println("Filter_set loginURL&");
		}
        HttpSession session = request.getSession();
      
        String path = ((HttpServletRequest) request).getRequestURI();
        if (path.startsWith("/LoginServlet")) {
            chain.doFilter(request, response); // Just continue chain.
        } else if (session == null || session.getAttribute("user") == null) {
  
        	request.getRequestDispatcher("/RandomMusic.jsp").forward(request,response);
            //response.sendRedirect((String)session.getAttribute("loginURL"));
        } else {
            chain.doFilter(request, response);
        }

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
