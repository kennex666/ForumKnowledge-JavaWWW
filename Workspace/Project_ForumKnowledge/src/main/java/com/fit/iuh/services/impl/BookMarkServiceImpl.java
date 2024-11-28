package com.fit.iuh.services.impl;

import com.fit.iuh.entites.BookMark;
import com.fit.iuh.repositories.BookMarkRepository;
import com.fit.iuh.services.BookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookMarkServiceImpl implements BookMarkService {
    @Autowired
    private BookMarkRepository bookMarkRepository;

    @Override
    public List<BookMark> findAllByUserId(int userId) {
        return bookMarkRepository.findAllByUserId(userId);
    }

    @Override
    public List<BookMark> findAllByPostId(int postId) {
        return bookMarkRepository.findAllByPostId(postId);
    }

    @Override
    public boolean existsByUserIdAndPostId(int userId, int postId) {
        return bookMarkRepository.existsByUserIdAndPostId(userId, postId);
    }

    @Override
    public long countByPostId(int postId) {
        return bookMarkRepository.countByPostId(postId);
    }

    @Override
    public List<BookMark> findAll() {
        return bookMarkRepository.findAll();
    }

    @Override
    public List<BookMark> getBookMarksCreatedInWeek() {
        return bookMarkRepository.getBookMarksCreatedInWeek();
    }

    @Override
    public List<BookMark> getBookMarksBetweenDates(Date startDate, Date endDate) {
        return bookMarkRepository.getBookMarksBetweenDates(startDate, endDate);
    }
}
