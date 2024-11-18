package com.fit.iuh.services.impl;

import com.fit.iuh.repositories.BookMarkRepository;
import com.fit.iuh.services.BookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookMarkServiceImpl implements BookMarkService {
    @Autowired
    private BookMarkRepository bookMarkRepository;
}
