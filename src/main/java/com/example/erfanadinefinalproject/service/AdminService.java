package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.security.RoleInDto;
import com.example.erfanadinefinalproject.dto.in.user.AdminInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerInDto;
import com.example.erfanadinefinalproject.dto.in.work.MainServiceInDto;
import com.example.erfanadinefinalproject.dto.out.security.RoleOutDto;
import com.example.erfanadinefinalproject.dto.out.user.AdminOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOutDto;
import com.example.erfanadinefinalproject.dto.out.work.MainServiceOutDto;
import com.example.erfanadinefinalproject.entity.product.message.Request;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.entity.work.SubService;
import com.example.erfanadinefinalproject.exception.BadEntryException;
import com.example.erfanadinefinalproject.exception.NullFieldException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;

public interface AdminService {

    void addRoleToAdmin(String username , String roleName);

    Admin getAdmin(String username);

    List<Admin> getAdmins();
//
//    UserDetails loadAdminByUsername(String username) throws UsernameNotFoundException;

    AdminOutDto saveAdminInDto(AdminInDto entity , String encode);

     List<Request> findByParameterMap(Map<String, String> parameterMap);

     void addExpert(SubService subService) throws NullFieldException, BadEntryException;

     MainServiceOutDto addDuty(MainServiceInDto mainServiceInDto);

     SubService findExpertByName();

     Worker findWorkerByName(String name);

     SubService addNewSubService(SubService subService);

     WorkerOutDto addNewWorker(WorkerInDto workerInDto);

     List<Worker> workersFiltering(String f, String l);

     List<User> usersFiltering(String f);

    void delete(Long id) throws NullFieldException;

     void updateAdminPassword(Long id, String password) throws NullFieldException;

     List<Admin> findAll() throws NullFieldException;

    Admin findById(Long id) throws NullFieldException;

    Admin saveUser(Admin admin);
    Role saveRole(Role role);
    void addRoleToUser(String username , String roleName);
    Admin getUser(String username);
    List<Admin> getUsers();
}
