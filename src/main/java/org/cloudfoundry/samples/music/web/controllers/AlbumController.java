package org.cloudfoundry.samples.music.web.controllers;

import javax.validation.Valid;

import org.cloudfoundry.samples.music.domain.Album;
import org.cloudfoundry.samples.music.repositories.AlbumRepository;
import org.cloudfoundry.samples.music.web.helper.SongsService;
import org.cloudfoundry.samples.music.web.helper.ValdateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/albums")
public class AlbumController {
    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private final AlbumRepository repository;
    private SongsService songs;

    @Autowired
    public AlbumController(AlbumRepository repository, SongsService songService) {
        this.repository = repository;
        this.songs = songService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Album> albums() {
        return repository.findAll();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Album add(@RequestBody @Valid Album album) {
        logger.info("Adding album " + album.getId());
        return repository.save(album);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Album update(@RequestBody @Valid Album album) {
        logger.info("Updating album " + album.getId());
        if (ValdateUtils.isValid(album) && ValdateUtils.validateArtistAlbumDoesNotExist(album, repository.findAll())) {
            return repository.save(album);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Album getById(@PathVariable String id) {
        logger.info("Getting album " + id);
        Album album = repository.findOne(id);
        return album;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable String id) {
        logger.info("Deleting album " + id);
        repository.delete(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getsongs/{id}", method = RequestMethod.GET)
    public Album getSongs(@PathVariable String id) {
        logger.info("Finding songs for album " + id);
        Album album = repository.findOne(id);
        if (album != null) {
            album.setSongs(songs.getSongsFromItunes(album.getArtist(), album.getTitle()));
        }
        return album;

    }
}