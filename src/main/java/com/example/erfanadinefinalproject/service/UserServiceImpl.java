package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.user.ShowAllOrdersByUserIdInDto;
import com.example.erfanadinefinalproject.dto.in.user.UserInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerOrUserSerchInDto;
import com.example.erfanadinefinalproject.dto.out.user.ShowAllOrdersByUserIdOutDto;
import com.example.erfanadinefinalproject.dto.out.user.UserOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.exception.EmailNotValidException;
import com.example.erfanadinefinalproject.exception.NameNotValidException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import com.example.erfanadinefinalproject.exception.PasswordNotValidException;
import com.example.erfanadinefinalproject.repo.RoleRepo;
import com.example.erfanadinefinalproject.repo.UserRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService ,UserDetailsService {

    private final UserRepository userRepository;

    private final SuggestionService suggestionService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the databases" , user.getFName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database",role.getRoleName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user{} ",roleName,username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUser(String username) {
        log.info("fetching user {} ",username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching All user" );
        return (List<User>) userRepository.findAll();
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("user found in the database: {}",username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    @Override
    public List<MainOrder> ServiceHistory(Long id) {
        List<MainOrder> mainOrders = userRepository.serviceHistory(id);
        return mainOrders;
    }

    @Override
    public void selectWorkersBySuggestionId(Long userId, Long suggestionId, Long orderId) {
        Suggestion suggestion = suggestionService.findById(suggestionId);
        User user = findById(userId);

        List<MainOrder> orders = user.getOrders();
        for (MainOrder order : orders) {
            if (order.getId().equals(orderId)) {
                order.setSuggestion(suggestion);
                order.setStatus(OrderStatus.WAITING_FOR_EXPERT);
            }
        }
        userRepository.save(user);
    }

    @Override
    public List<Suggestion> listOfSuggestionByOrderID(Long orderID) {
        List<Suggestion> myList = new ArrayList<Suggestion>();
        List<Suggestion> all = suggestionService.findAll();
        for (Suggestion suggestion : all) {
            if (suggestion.getOrder().getId().equals(orderID)) {
                myList.add(suggestion);
            }
        }
        List<Suggestion> sortMyList = myList.stream().sorted().toList();
        return sortMyList;
    }

    @Override
    public void changePassword(User user) throws NameNotValidException, EmailNotValidException, PasswordNotValidException, NullFieldException {
        if (Validation.checkBaseCustomerIsValid(user)) {
            userRepository.save(user);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    @Override
    public List<User> findAll() {
        List<User> adminList = new ArrayList<>();
        userRepository.findAll().forEach((element) -> adminList.add(element));
        return adminList;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public Page<WorkerOrUserSerchOutDto> findAllByFNameAndLNameAndEmailAndPassword(WorkerOrUserSerchInDto workerOrUserSerchInDto) {
        Pageable pageable = PageRequest.of(workerOrUserSerchInDto.getPageNumber(), workerOrUserSerchInDto.getPageSize());
        return userRepository
                .findAllByFNameAndLNameAndEmailAndPassword(workerOrUserSerchInDto.getFName(), workerOrUserSerchInDto.getLName(), workerOrUserSerchInDto.getPassword(), workerOrUserSerchInDto.getEmail(), pageable);

    }

    @Override
    public Page<WorkerOrUserSerchOutDto> findAllByFNameAndLName(WorkerOrUserSerchInDto workerOrUserSerchInDto) {
        Pageable pageable = PageRequest.of(workerOrUserSerchInDto.getPageNumber(), workerOrUserSerchInDto.getPageSize());
        return userRepository.findAllByFNameAndLName(workerOrUserSerchInDto.getFName(), workerOrUserSerchInDto.getLName(), pageable);

    }

    @Override
    public User save(User entity) {

        return userRepository.save(entity);
    }

    @Override
    public UserOutDto save(UserInDto entity) {
        User user = new User();
        user.setEmail(entity.getEmail());
//        user.setImage(entity.getImage());
        user.setFName(entity.getFirstName());
        user.setLName(entity.getLastName());
        user.setPassword(entity.getPassword());

        userRepository.save(user);

        UserOutDto userOutDto = new UserOutDto();
        userOutDto.setId(user.getId());

        return userOutDto;

    }

    @Override
    public Page<ShowAllOrdersByUserIdOutDto> showAllOrdersByUserIdS(ShowAllOrdersByUserIdInDto orders, Long id) {
        Pageable pageable = PageRequest.of(orders.getPageNumber(), orders.getPageSize());
        return userRepository.showAllOrdersByUserIdR(id, pageable);
    }

    //********

}
