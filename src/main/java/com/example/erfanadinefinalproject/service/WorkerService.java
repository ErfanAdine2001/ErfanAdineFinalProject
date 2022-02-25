package com.example.erfanadinefinalproject.service;


import com.example.erfanadinefinalproject.dto.in.user.WorkerInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerOrUserSerchInDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOutDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.exception.EmailNotValidException;
import com.example.erfanadinefinalproject.exception.NameNotValidException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import com.example.erfanadinefinalproject.exception.PasswordNotValidException;
import org.springframework.data.domain.Page;
import java.util.List;

public interface WorkerService {

    Worker saveWorker(Worker user);
    Role saveRole(Role role);
    void addRoleToWorker(String username , String roleName);
    Worker getWorker(String username);
    List<Worker> getWorkers();


     List<MainOrder> serviceHistory(Long id);


     List<MainOrder> findAllOrderByStatusWateFOrSuggestions(OrderStatus status);

     Page<WorkerOrUserSerchOutDto> findAllByFNameAndLNameAndEmailAndPassword(WorkerOrUserSerchInDto workerOrUserSerchInDto);

    Page<WorkerOrUserSerchOutDto> findAllByFNameAndLName(WorkerOrUserSerchInDto workerOrUserSerchInDto);

     Worker findById(Long id);

     Worker findByName(String name);

     void changePassword(Worker worker) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException;

     void sendNewSuggestion(Suggestion suggestion, Long idOfMainOrder);

     void ConfirmationOfOrder(Long idOfMainOrder);

     void delete(Worker worker);

     WorkerOutDto save(Worker request);

     WorkerOutDto save(WorkerInDto request);

     void updateByMainService(Long worker_id, Long MainService_id);

     void removeByMainService(Long worker_id, Long MainService_id);

     void addMainService(Long worker_id, Long MainService_id);
}
