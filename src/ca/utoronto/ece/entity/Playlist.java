package ca.utoronto.ece.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Entity implementation class for Entity: Playlist
 *
 */
@Entity(name = "Playlist")

public class Playlist implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Playlist() {
		super();
	}
	public Playlist(String id){
		this.id = KeyFactory.createKey("Playlist", id);
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	Key id;
	
	private String userId;
	private String name;
	
	@OneToMany(mappedBy="playlist",cascade = CascadeType.ALL) @OrderBy("id")
	private Set <PlaylistLine> playlistLines;
	
	public String getId() {
		return KeyFactory.keyToString(id);
	}
	public void setId(com.google.appengine.api.datastore.Key id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Set<PlaylistLine> getPlaylistLines() {
		return playlistLines;
	}
	public void setPlaylistLines(Set<PlaylistLine> playlistLines) {
		if (playlistLines == null){
			playlistLines = new HashSet<PlaylistLine>();
		}
		this.playlistLines = playlistLines;
	}
	
	public int getCount(){
		int count = 0;
		Iterator it = playlistLines.iterator();
		while(it.hasNext()){
			PlaylistLine line = (PlaylistLine) it.next();
			count ++;
		}
		return count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
