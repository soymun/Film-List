package com.example.films.service;

import com.example.films.entity.Film;
import com.example.films.entity.User;
import com.example.films.repository.FilmRepository;
import com.example.films.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private FilmRepository filmRepository;
    @Autowired
    public UserService(UserRepository userRepository, FilmRepository filmRepository) {
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRole().authorities());
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
    @Transactional
    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public Film getFilm(Long id){
        return filmRepository.findFilmById(id);
    }

    @Transactional
    public void deleteFilm(Long id){
        filmRepository.deleteById(id);
    }

    @Transactional
    public void saveFilmAndUpdate(Film film){
        filmRepository.save(film);
    }

    @Transactional
    public User getUserById(Long id){
        return userRepository.findUserById(id);
    }
}
