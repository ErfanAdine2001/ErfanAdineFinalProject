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
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.exception.EmailNotValidException;
import com.example.erfanadinefinalproject.exception.NameNotValidException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import com.example.erfanadinefinalproject.exception.PasswordNotValidException;
import com.example.erfanadinefinalproject.repo.RoleRepo;
import com.example.erfanadinefinalproject.repo.WorkerRepository;
import com.example.erfanadinefinalproject.service.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkerServiceImpl implements WorkerService , UserDetailsService {
    private final WorkerRepository workerRepository;
    private final MainService_ServiceImpl dutyRepository;
    private final OrderServiceImpl mainOrderService;
    private final SuggestionServiceImpl suggestionService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private Validation validation;


    @Override
    public Worker saveWorker(Worker worker) {
        log.info("Saving new worker {} to the databases" , worker.getFName());
        worker.setPassword(passwordEncoder.encode(worker.getPassword()));
        return workerRepository.save(worker);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database",role.getRoleName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToWorker(String username, String roleName) {
        log.info("Adding role {} to worker{} ",roleName,username);
        Worker worker = workerRepository.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        worker.getRoles().add(role);
    }

    @Override
    public Worker getWorker(String username) {
        log.info("fetching worker {} ",username);
        return workerRepository.findByUsername(username);
    }

    @Override
    public List<Worker> getWorkers() {
        log.info("fetching All user" );
        return  workerRepository.findAll();
    }
//*****************
    @Override
    public List<MainOrder> serviceHistory(Long id) {
        List<MainOrder> mainOrders = workerRepository.serviceHistory(id);
        return mainOrders;
    }

    @Override
    public List<MainOrder> findAllOrderByStatusWateFOrSuggestions(OrderStatus status) {
        List<MainOrder> allOrderByStatusWateForSuggestion = mainOrderService.findAllOrderByStatusWateForSuggestion(status);
        return allOrderByStatusWateForSuggestion;

    }

    @Override
    public Page<WorkerOrUserSerchOutDto> findAllByFNameAndLNameAndEmailAndPassword(WorkerOrUserSerchInDto workerOrUserSerchInDto) {
        Pageable pageable = PageRequest.of(workerOrUserSerchInDto.getPageNumber(), workerOrUserSerchInDto.getPageSize());
        return workerRepository
                .findAllByFNameAndLNameAndEmailAndPassword(workerOrUserSerchInDto.getFName(), workerOrUserSerchInDto.getLName(), workerOrUserSerchInDto.getPassword(), workerOrUserSerchInDto.getEmail(), pageable);

    }

    @Override
    public Page<WorkerOrUserSerchOutDto> findAllByFNameAndLName(WorkerOrUserSerchInDto workerOrUserSerchInDto) {
        Pageable pageable = PageRequest.of(workerOrUserSerchInDto.getPageNumber(), workerOrUserSerchInDto.getPageSize());
        return workerRepository.findAllByFNameAndLName(workerOrUserSerchInDto.getFName(), workerOrUserSerchInDto.getLName(), pageable);

    }

    @Override
    public Worker findById(Long id) {
        return workerRepository.findById(id).get();
    }

    @Override
    public Worker findByName(String name) {
        return workerRepository.findByFirstNameName(name);

    }

    @Override
    public void changePassword(Worker worker) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException {
//
        if (Validation.checkBaseCustomerIsValid(worker)) {
            workerRepository.save(worker);
        }
    }

    @Override
    public void sendNewSuggestion(Suggestion suggestion, Long idOfMainOrder) {
        Suggestion s = suggestionService.save(suggestion);

        MainOrder mainOrder = mainOrderService.findById(idOfMainOrder);
        if ((mainOrder.getStatus().equals(OrderStatus.WAITING_FOR_SUGGESTION))) {
            mainOrder.setStatus(OrderStatus.ACCEPTED);
            mainOrderService.save(mainOrder);
        }
        s.setOrder(mainOrder);
    }

    @Override
    public void ConfirmationOfOrder(Long idOfMainOrder) {
        MainOrder mainOrder = mainOrderService.findById(idOfMainOrder);
        if ((mainOrder.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT))) {
            mainOrder.setStatus(OrderStatus.WAITING_FOR_SUGGESTION);
            mainOrderService.save(mainOrder);
        }

    }

    @Override
    public void delete(Worker worker) {
        workerRepository.delete(worker);
    }

    @Override
    public WorkerOutDto save(Worker request) {
        Worker save = workerRepository.save(request);

        WorkerOutDto workerOutDto = new WorkerOutDto();
        workerOutDto.setId(save.getId());

        return workerOutDto;

    }

    @Override
    public WorkerOutDto save(WorkerInDto request) {

        Worker worker = new Worker();
        worker.setFName(request.getFirstName());
        worker.setLName(request.getLastName());
        worker.setImage(request.getImage());
        worker.setCreatedTime(new Date());
        worker.setUpdatedTime(new Date());
        worker.setEmail(request.getEmail());
        worker.setMainService(request.getMainServiceList());
        workerRepository.save(worker);

        WorkerOutDto workerOutDto = new WorkerOutDto();
        workerOutDto.setId(worker.getId());

        return workerOutDto;

    }

    @Override
    public void updateByMainService(Long worker_id, Long MainService_id) {
        Worker byId = workerRepository.getById(worker_id);
        MainService byId1 = dutyRepository.findById(MainService_id);
        byId.getMainService().remove(byId1);
        workerRepository.save(byId);
    }

    @Override
    public void removeByMainService(Long worker_id, Long MainService_id) {
        Worker byId = workerRepository.getById(worker_id);
        MainService byId1 = dutyRepository.findById(MainService_id);
        byId.getMainService().remove(byId1);
        workerRepository.save(byId);
    }

    @Override
    public void addMainService(Long worker_id, Long MainService_id) {
        Worker byId = workerRepository.getById(worker_id);
        MainService byId1 = dutyRepository.findById(MainService_id);
        byId.getMainService().add(byId1);
        workerRepository.save(byId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Worker worker = workerRepository.findByUsername(username);

        if (worker == null) {
            log.error("worker not found in the database");
            throw new UsernameNotFoundException("worker not found in the database");
        } else {
            log.info("worker found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        worker.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(worker.getUsername(), worker.getPassword(), authorities);

    }
}
