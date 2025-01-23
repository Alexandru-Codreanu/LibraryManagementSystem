package com.project.librarymanagementsystem.dao.interfaces;

import com.project.librarymanagementsystem.entities.Book;
import com.project.librarymanagementsystem.entities.Lending;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface LendingDAO {
    List<Lending> getAllLendings();
    Optional<Lending> getLendingById(Integer id);
    Lending createLending(Lending lending);
    Lending updateLending(Lending lending);
    void deleteLending(Integer id);
    List<Lending> getUserLendings(Integer id);
    Optional<Book> getBooksByLendingId(Integer id);
}
