package ca.utoronto.ece.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Entity implementation class for Entity: PlaylistLine
 *
 */
@Entity(name = "PlaylistLine")

public class PlaylistLine implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public PlaylistLine() {
		super();
	}
//	public PlaylistLine(String id){
//		this.id = KeyFactory.createKey("PlaylistLine", id);
//	}
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	Key id;
	
	@Embedded
	private Song song;
	
	@ManyToOne
	private Playlist playlist;

//	public String getId() {
//		return KeyFactory.keyToString(id);
//	}
//	public void setId(com.google.appengine.api.datastore.Key id) {
//		this.id = id;
//	}
	public Playlist getPlaylist() {
		return playlist;
	}
	public void setPlaylist(Playlist playlist) {
		if (this.playlist != null) { 
			this.playlist.internalRemovePlaylistLine(this); 
		}
		this.playlist = playlist;
		if (playlist != null) { 
			playlist.internalAddPlaylistLine(this);
		}
		
//		this.playlist = playlist;
//		playlist.addPlaylistLine(this);
	}
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}

}
