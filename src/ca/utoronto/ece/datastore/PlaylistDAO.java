package ca.utoronto.ece.datastore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ca.utoronto.ece.entity.Playlist;
import ca.utoronto.ece.entity.PlaylistLine;
import ca.utoronto.ece.entity.Song;

public class PlaylistDAO {
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	
	//add New Playlist
	public void addNewPlaylist(Playlist playlist){
		try{
			em = emf.createEntityManager();
			em.persist(playlist);
		}finally{
			em.close();
		}
	}
	public void addNewPlaylist(String name, String userId){
		try{
			em = emf.createEntityManager();
			Playlist playlist = new Playlist();
			Set <PlaylistLine> playlistLines = new HashSet<PlaylistLine>();
			
			playlist.setName(name);
			playlist.setUserId(userId);
			playlist.setPlaylistLines(playlistLines);
			
			em.getTransaction().begin();
			em.merge(playlist);
			em.getTransaction().commit();
			
		}finally{
			em.close();
		}
	}
	
	//add song(playlistline) to playlist
	public void addSongToPlaylist(String id, String songName, String singer, String image, String album, Playlist playlist){
		em = emf.createEntityManager();
		em.getTransaction().begin();
		try{
			Playlist pl = em.find(Playlist.class, playlist.getId());
			PlaylistLine playlistLine = new PlaylistLine();
			Song song = new Song(id, songName, singer, image, album);
			playlistLine.setSong(song);
			playlistLine.setPlaylist(pl);
			//pl.getPlaylistLines().add(playlistLine);
			
			em.getTransaction().commit();
			
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
		}finally{	
			em.close();
		}
	}	
	//check playlistname exists
	public List checkPlaylistNameExists(String playlistName, String userId){
		List results = null;
		try{
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("SELECT p FROM Playlist p WHERE p.userId='"+userId+"'"+"and p.name='"+playlistName+"'");
			results = query.getResultList();
			
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
		}finally{
			em.close();
		}
		return results;
	}
	
	//find all playlists by user id
	public List<Playlist> findAllPlaylistsByUserId(String userId){
		List<Playlist> results = null;
		try{
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Playlist> query = em.createQuery("SELECT p from Playlist p WHERE p.userId ='"+userId+"'", Playlist.class);
			results = query.getResultList();
			
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
		}finally{
			em.close();
		}
		return results;
	}
	
	//find all songs by playlist
	public List<Song> findAllSongsByPlaylist(Playlist playlist){
		List<Song> songs = null;
		try{
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("SELECT p from PlaylistLine p WHERE playlist = :currentPlaylist");
			query.setParameter("currentPlaylist", playlist);
			List <PlaylistLine>results = (List<PlaylistLine>)query.getResultList();
			for(PlaylistLine p :results){
				songs.add(p.getSong());
			}
			
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
		}finally{
			em.close();
		}
		return songs;
	}

	//delete song(playlistline) from playlist
	public void deleteSongFromPlaylist(PlaylistLine playlistLine, Playlist playlist){
		try{
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			
			
			em.getTransaction().commit();
			
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
		}finally{
			em.close();
		}
	}
	
	//find all playlist by user id
	public Playlist getPlaylistById(String userId,String playlistName){
		Playlist playlist = new Playlist();
		try{
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE p.userId='"+userId+"'"+"and p.name='"+playlistName+"'", Playlist.class);
			playlist = query.getSingleResult();
			em.detach(playlist.getPlaylistLines());

			em.getTransaction().commit();
			
		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
		}finally{
			em.close();
		}
		
		return playlist;
		
	}
	
	
	
	
}
