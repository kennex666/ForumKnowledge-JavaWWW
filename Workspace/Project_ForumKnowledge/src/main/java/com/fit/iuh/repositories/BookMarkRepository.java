package com.fit.iuh.repositories;

import com.fit.iuh.entites.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookMarkRepository extends JpaRepository<BookMark, Integer> {
}
