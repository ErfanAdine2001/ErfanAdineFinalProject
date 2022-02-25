package com.example.erfanadinefinalproject.repo;


import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    Admin findByUsername(String username) ;
    //**************************************
//    Optional<Admin> findByUsername(String username);

    //**************************************
    // -----------------------------------------
    /**
     * user and worker ->update
     *
     * @param id
     * @param password
     */
    @Modifying
    @Query("update User u set u.password=:password where u.id =:id ")
    void updatUserById(Long id, String password);

    @Modifying
    @Query("update Worker w set w.password=:password where w.id =:id ")
    void updateWorkerById(Long id, String password);
//    //TODO make 2 and 3 method in Service AdminInDto?

    /**
     * UserInDto and worker  -> delete
     *
     * @param id
     * @param password
     */
    @Modifying
    @Query("delete from User u where u.id=:id and u.password=:password")
    void removeUserById(Long id, String password);

    @Modifying
    @Query("delete from Worker w where w.id=:id and w.password=:password ")
    void removeWorkerById(Long id, String password);


    //------------------------------------------------------filter of users
    // TODO f1 ------------------ > 1-3  service
    @Query("select u from User u where u.fName=:firstName and u.lName=:lastName")
    User findUsersByfNameAndLName(String firstName, String lastName);

    @Modifying
    @Query("select w from Worker w where w.fName=:firstName")
    List<Worker> findWorkerByName(String firstName);

    @Modifying
    @Query("select u from User u where u.fName=:firstName and u.lName=:lastName")
    List<User> findUsersByfName(String firstName);
//
    //-------------------------------------------------------

    //-------------------------------------------------------adding new  mainService and  expert
    //TODO f1 -------------------> 1-4 repository is null
    //  this part dont need to repository

    //--------------------------------------------
    //todo f1 ----------------------> 1-5 Repository need
    @Modifying
    @Query("select w from Worker w where w.fName=:firstName and w.lName=:lastName")
    Worker findWorkerByfNameAndLName(String firstName, String lastName);


    //------------------------------------------------
    @Modifying
    @Query("select u from User u where u.fName=:name")
    List<User> findByFName(String name);



}
