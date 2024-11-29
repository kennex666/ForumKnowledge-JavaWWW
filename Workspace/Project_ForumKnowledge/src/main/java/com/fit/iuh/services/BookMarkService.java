package com.fit.iuh.services;

import com.fit.iuh.entites.BookMark;

import java.util.List;

public interface BookMarkService {
    public List<BookMark> findAllByUserId(int userId);

    public List<BookMark> findAllByPostId(int postId);

    public boolean existsByUserIdAndPostId(int userId, int postId);

    public long countByPostId(int postId);
    
    public BookMark save(BookMark bookMark);
    
    public void deleteByUserIdAndPostId(int userId, int postId);
    
    public void removeBookmark(int postId);
    
}
