package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.tunehub.entities.Playlist;
import com.tunehub.entities.Song;
import com.tunehub.services.PlaylistService;
import com.tunehub.services.SongService;

@Controller
public class PlaylistController 
{
	@Autowired
	PlaylistService playlistService;
	@Autowired
	SongService songServices;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model)
	{
		List<Song> songList=songServices.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}
	
	@PostMapping("/addPlaylist")
	public String  addPlayList(@ModelAttribute Playlist playlist)
	{
		playlistService.addPlaylist(playlist);
		//updating song table
		List<Song> songList = playlist.getSongs();
		for(Song s:songList)
		{
			s.getPlaylist().add(playlist);
			songServices.updateSongs(s);
		}
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allPlaylists=playlistService.fetchAllPlaylist();
		model.addAttribute("allPlaylists", allPlaylists);
		return "displayPlaylists";
		
	}
	
	
}
