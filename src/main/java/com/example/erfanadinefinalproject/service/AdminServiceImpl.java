package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.user.AdminInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerInDto;
import com.example.erfanadinefinalproject.dto.in.work.MainServiceInDto;
import com.example.erfanadinefinalproject.dto.out.user.AdminOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOutDto;
import com.example.erfanadinefinalproject.dto.out.work.MainServiceOutDto;
import com.example.erfanadinefinalproject.entity.product.message.Request;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.entity.work.SubService;
import com.example.erfanadinefinalproject.exception.BadEntryException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import com.example.erfanadinefinalproject.repo.AdminRepository;
import com.example.erfanadinefinalproject.repo.RoleRepo;
import com.example.erfanadinefinalproject.service.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService, UserDetailsService {
    private final AdminRepository adminRepository;
    private final WorkerServiceImpl workerServiceImpl;
    private final MainService_ServiceImpl mainService_ServiceImpl;
    private final SubService_ServiceImpl subService_ServiceImpl;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;



    @Override
    public AdminOutDto saveAdminInDto(AdminInDto entity, String encode) {
        Admin admin = new Admin();
//        admin.setPassword(encode(entity.getPassword()));
        admin.setPassword(encode);
        admin.setEmail(entity.getEmail());
        admin.setFName(entity.getFirstName());
        admin.setLName(entity.getLastName());
        admin.setUsername(entity.getUserName());
        admin.setRoles(entity.getRole());
        admin.setIsEnable(entity.getIsEnable());
        admin.setCreatedTime(new Date());
        admin.setUpdatedTime(new Date());

        adminRepository.save(admin);

        AdminOutDto response = new AdminOutDto();
        response.setId(admin.getId());

        return response;
    }


    @Override
    public List<Request> findByParameterMap(Map<String, String> parameterMap) {
        return null;
    }

    @Override
    public void addExpert(SubService subService) throws NullFieldException, BadEntryException {
        if (Validation.expertIsValid(subService)) {
            subService_ServiceImpl.save(subService);
        }
    }

    @Override
    public MainServiceOutDto addDuty(MainServiceInDto mainServiceInDto) {
        MainService mainService = new MainService();
        mainService.setName(mainServiceInDto.getName());
        mainService.setCreatedTime(new Date());
        mainService.setUpdatedTime(new Date());
        mainService.setDescription(mainServiceInDto.getDescription());
        mainService_ServiceImpl.save(mainService);

        MainServiceOutDto result = new MainServiceOutDto();
        result.setId(mainService.getId());

        return result;

    }

    @Override
    public SubService findExpertByName() {
        return null;
    }

    @Override
    public Worker findWorkerByName(String name) {
        return workerServiceImpl.findByName(name);
    }

    @Override
    public SubService addNewSubService(SubService subService) {
        return subService_ServiceImpl.save(subService);
    }

    @Override
    public WorkerOutDto addNewWorker(WorkerInDto workerInDto) {
        return workerServiceImpl.save(workerInDto);
    }

    //todo 🤔🤔
    @Override
    public List<Worker> workersFiltering(String f, String l) {
        return adminRepository.findWorkerByName(f);
    }

    @Override
    public List<User> usersFiltering(String f) {
        return adminRepository.findByFName(f);
    }


    @Override
    public void delete(Long id) throws NullFieldException {
        adminRepository.delete(findById(id));

    }

    @Override
    public void updateAdminPassword(Long id, String password) throws NullFieldException {
        Admin a = findById(id);
        a.setPassword(password);
        adminRepository.save(a);
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> adminList = new ArrayList<>();
        adminList.addAll(adminRepository.findAll());

        return adminList;
    }


    @Override
    public Admin findById(Long id) throws NullFieldException {
        if (adminRepository.findById(id).isPresent()) {

            return adminRepository.findById(id).get();

        } else {
            throw new NullPointerException("is empty");
        }
    }

    @Override
    public Admin saveUser(Admin admin) {
        log.info("Saving new user {} to the databases", admin.getFName());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }


    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getRoleName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

    }

    @Override
    public Admin getUser(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public List<Admin> getUsers() {
        return adminRepository.findAll();
    }

    @Override
    public void addRoleToAdmin(String username, String roleName) {
        log.info("Adding role {} to admin{} ", roleName, username);
        Admin admin = adminRepository.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        admin.getRoles().add(role);
    }

    @Override
    public Admin getAdmin(String username) {
        log.info("fetching user {} ", username);
        return adminRepository.findByUsername(username);
    }

    @Override
    public List<Admin> getAdmins() {
        log.info("fetching All user");
        return adminRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("admin found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        admin.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), authorities);
    }
}
