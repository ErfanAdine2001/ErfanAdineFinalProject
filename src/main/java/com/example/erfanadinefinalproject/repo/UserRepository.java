package com.example.erfanadinefinalproject.repo;

import com.example.erfanadinefinalproject.dto.out.user.ShowAllOrdersByUserIdOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username) ;

    @Query("select e from User e group by e.id")
    List<User> GroupById();

    @Modifying
    @Query("update User u set u.password=:password where u.id =:id ")
    void removeById(Long id, String password);


    //TODO f2-3

    @Query("select s from Suggestion s group by s.id")
    Suggestion GroupSuggestionById();


    //TODO f2-4

    @Query("select s from Suggestion s where s.id=:id")
    Worker registerOfWorkerById(Long id);


    //***********************************
    //***********************************
    Page<WorkerOrUserSerchOutDto> findAllByFNameAndLNameAndEmailAndPassword(String fName, String lName, String Password, String email, Pageable pageable);


    Page<WorkerOrUserSerchOutDto> findAllByFNameAndLName(String fName, String lName, Pageable pageable);


//    @Modifying
    @Query("select m from MainOrder m where m.user.id=:id")
    Page<ShowAllOrdersByUserIdOutDto> showAllOrdersByUserIdR(Long id, Pageable pageable);


    //TODO check and make methods in Service layer

    @Modifying
    @Query("select m from  MainOrder  m  where m.user.id=:id")
    List<MainOrder> serviceHistory(Long id);


}



