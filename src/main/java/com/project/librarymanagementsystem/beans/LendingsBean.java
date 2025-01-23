package com.project.librarymanagementsystem.beans;

import com.project.librarymanagementsystem.entities.Roles;
import com.project.librarymanagementsystem.entities.Statuses;
import com.project.librarymanagementsystem.entities.*;
import com.project.librarymanagementsystem.dao.interfaces.BookDAO;
import com.project.librarymanagementsystem.dao.interfaces.LendingFineDAO;
import com.project.librarymanagementsystem.dao.interfaces.LendingDAO;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotEmpty;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Named("lendingsBean")
@SessionScoped
public class LendingsBean implements Serializable {

    @EJB
    private BookDAO bookDAO;

    @EJB
    private LendingDAO lendingDAO;

    @EJB
    private LendingFineDAO lendingFineDAO;

    @Inject
    private HomeBean homeBean;

    @NotEmpty
    private String Reason;

    @NotEmpty
    private BigDecimal Amount;

    @NotEmpty
    private Lending Lending;

    public List<Lending> getLendings() {
        if(Objects.equals(homeBean.getUser().getRole(), Roles.Normal.role)) {
            return lendingDAO.getUserLendings(homeBean.getUser().getId());
        }
        else {
            return lendingDAO.getAllLendings();
        }
    }

    public List<Lending> getLendingsWithoutFines(){
        List<Lending> lendingsFined = lendingFineDAO.getAllFines().stream().map(LendingFine::getLending).toList();
        return getLendings().stream().filter(lending -> {
            return !lendingsFined.contains(lending);
        }).toList();
    }

    public void ChangeStatus(Lending lending, String status) throws IOException {
        if(lending == null) {
            return;
        }

        if(homeBean.getUser().getRole().equals(Roles.Normal.role)) {
            if(!Objects.equals(homeBean.getUser().getId(), lending.getUserid().getId())) {
                return;
            }

            if(!status.equals(Statuses.Pending.status) && !status.equals(Statuses.PaymentSent.status)) {
                return;
            }
            lending.setStatus(status);
            lendingDAO.updateLending(lending);
            return;
        }

        if (homeBean.getUser().getRole().equals(Roles.Librarian.role)) {
            if(!status.equals(Statuses.Returned.status) &&
                    !status.equals(Statuses.Payed.status) &&
                    !status.equals(Statuses.Fined.status)) {
                return;
            }

            if(!status.equals(Statuses.Fined.status) && !status.equals(Statuses.Returned.status)) {
                Lending = lending;
                homeBean.goToLendingFineForm();
                return;
            }

            lending.setStatus(status);
            lendingDAO.updateLending(lending);
            return;
        }

        if (homeBean.getUser().getRole().equals(Roles.Administrator.role)) {
            lending.setStatus(status);
            lendingDAO.updateLending(lending);
            return;
        }
    }

    public void createLending(Book book)
    {
        if(!homeBean.getUser().getRole().equals(Roles.Normal.role)) {
            return;
        }

        if(book.getStock() <= 0)
        {
            return;
        }

        Lending lending = new Lending(
                homeBean.getUser(),
                book,
                LocalDate.now(),
                LocalDate.now().plusWeeks(2),
                Statuses.Lent.status);

        lendingDAO.createLending(lending);
        book.setStock(book.getStock() - 1);
        bookDAO.updateBook(book.getId(), book);
    }

    public void createFine() throws IOException {
        if(!homeBean.getUser().getRole().equals(Roles.Librarian.role)) {
            return;
        }

        LendingFine lendingFine = new LendingFine(Lending, Amount, Reason);

        lendingFineDAO.createFine(lendingFine);
        Lending.setStatus(Statuses.Fined.status);
        Lending = lendingDAO.updateLending(Lending);
        homeBean.goToLendings();
    }

    public void deleteLending(Lending lending) {
        lendingDAO.deleteLending(lending.getId());
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public Lending getLending() {
        return Lending;
    }

    public void setLending(Lending lending) {
        Lending = lending;
    }

    public List<String> getReasons() {
        return Arrays.stream(Reasons.values()).map(Enum::toString).collect(Collectors.toList());
    }
}
