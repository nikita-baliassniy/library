package ru.geekbrains.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.library.model.Comment;
import ru.geekbrains.library.model.User;
import ru.geekbrains.library.repositories.CommentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Map<Long, Integer> findBooksScoresByUser(User user) {
        List<Comment> comments = commentRepository.findAllByUserEquals(user);
        Map<Long, Integer> scores = new HashMap<>();
        comments.forEach(c -> scores.put(c.getBook().getId(), c.getScore()));
        return scores;
    }

}
