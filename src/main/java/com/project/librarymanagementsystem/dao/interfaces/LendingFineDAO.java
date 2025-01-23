package com.project.librarymanagementsystem.dao.interfaces;

import com.project.librarymanagementsystem.entities.LendingFine;
import jakarta.ejb.Local;

import java.util.List;
import java.util.Optional;

@Local
public interface LendingFineDAO {
    List<LendingFine> getAllFines();
    Optional<LendingFine> getFineById(Integer id);
    LendingFine createFine(LendingFine fine);
    LendingFine updateFine(LendingFine fine);
    void deleteFine(Integer id);
}
