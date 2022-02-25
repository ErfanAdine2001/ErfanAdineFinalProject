package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.user.ShowAllOrdersByUserIdInDto;
import com.example.erfanadinefinalproject.dto.in.user.UserInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerOrUserSerchInDto;
import com.example.erfanadinefinalproject.dto.out.user.ShowAllOrdersByUserIdOutDto;
import com.example.erfanadinefinalproject.dto.out.user.UserOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.exception.EmailNotValidException;
import com.example.erfanadinefinalproject.exception.NameNotValidException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import com.example.erfanadinefinalproject.exception.PasswordNotValidException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User findUserByUsername(String username);
    User getUser(String username);

    List<User> getUsers();

    //*************
    public List<MainOrder> ServiceHistory(Long id);

    public void selectWorkersBySuggestionId(Long userId, Long suggestionId, Long orderId);

    public List<Suggestion> listOfSuggestionByOrderID(Long orderID);

    public void changePassword(User user) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException;

    public void delete(Long id);

    public List<User> findAll();

    public User findById(Long id);

    public Page<ShowAllOrdersByUserIdOutDto> showAllOrdersByUserIdS(ShowAllOrdersByUserIdInDto orders, Long id);

    public Page<WorkerOrUserSerchOutDto> findAllByFNameAndLNameAndEmailAndPassword(WorkerOrUserSerchInDto workerOrUserSerchInDto);


    Page<WorkerOrUserSerchOutDto> findAllByFNameAndLName(WorkerOrUserSerchInDto workerOrUserSerchInDto);

    public User save(User entity);

    public UserOutDto save(UserInDto entity);


}
