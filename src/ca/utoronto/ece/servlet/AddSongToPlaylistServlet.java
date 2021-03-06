package ca.utoronto.ece.servlet;

import java.io.IOException;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;

import ca.utoronto.ece.datastore.Memcache;
import ca.utoronto.ece.datastore.PlaylistDAO;
import ca.utoronto.ece.entity.Playlist;

public class AddSongToPlaylistServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws IOException, ServletException{
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws IOException, ServletException{
		
		String songId = request.getParameter("songId");
		String name = request.getParameter("name");
		String singer = request.getParameter("singer");
		String album = request.getParameter("album");
		String image = request.getParameter("image");
		String playlistName = request.getParameter("playlistName");
		
		User user = (User)request.getSession().getAttribute("user");
		
		PlaylistDAO playlistDao = new PlaylistDAO();
		//playlistDao.addNewPlaylist(playlistName, user.getEmail());
		Playlist currentPlaylist = playlistDao.getPlaylistById(user.getEmail(), playlistName);
		playlistDao.addSongToPlaylist(songId, name, singer, image, album, currentPlaylist);
		
		//memcache
		Cache cache = new Memcache().getCache();
		cache.put("NewPlaylistName", currentPlaylist);
		
        response.setContentType("text/plain");
        response.getWriter().write("Added "+name+" into playlist "+playlistName);
		
	}
}
