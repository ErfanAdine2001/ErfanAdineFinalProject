package com.example.erfanadinefinalproject.repo;


import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.message.BaseMessageStatus;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {


    Worker findByUsername(String username) ;

    @Query("update Request r set r.status=:baseMessageStatus")
    void ConfirmationOfOrder(BaseMessageStatus baseMessageStatus);

    @Query("select w from Worker w where w.fName=:firstName")
    Worker findByFirstNameName(String firstName);


    Page<WorkerOrUserSerchOutDto> findAllByFNameAndLNameAndEmailAndPassword(String fName, String lName, String Password, String email, Pageable pageable);


    Page<WorkerOrUserSerchOutDto> findAllByFNameAndLName(String fName, String lName, Pageable pageable);

    @Modifying
    @Query("select m from  MainOrder  m  where m.user.id=:id")
    List<MainOrder> serviceHistory(Long id);


}
