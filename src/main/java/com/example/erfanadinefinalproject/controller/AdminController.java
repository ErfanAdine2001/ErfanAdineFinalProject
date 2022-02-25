package com.example.erfanadinefinalproject.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.erfanadinefinalproject.dto.in.user.AdminInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerOrUserSerchInDto;
import com.example.erfanadinefinalproject.dto.in.work.MainServiceInDto;
import com.example.erfanadinefinalproject.dto.out.user.AdminOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOutDto;
import com.example.erfanadinefinalproject.dto.out.work.MainServiceOutDto;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.service.AdminServiceImpl;
import com.example.erfanadinefinalproject.service.MainService_ServiceImpl;
import com.example.erfanadinefinalproject.service.UserServiceImpl;
import com.example.erfanadinefinalproject.service.WorkerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class AdminController {
    private final AdminServiceImpl adminService;
    private final WorkerServiceImpl workerService;
    private final MainService_ServiceImpl mainServiceService;
    private  final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<AdminOutDto> create(@Valid @RequestBody AdminInDto request){

        String encode = passwordEncoder.encode(request.getPassword());

        AdminOutDto result = adminService.saveAdminInDto(request ,encode);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);

    }

    @PostMapping("/searchWorkerListByAllAttribute")
    public ResponseEntity<Page<WorkerOrUserSerchOutDto>> searchWorkers(@Valid @RequestBody WorkerOrUserSerchInDto request)  {


        Page<WorkerOrUserSerchOutDto> allByFNameAndLNameAndEmailAndPassword = workerService.findAllByFNameAndLNameAndEmailAndPassword(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allByFNameAndLNameAndEmailAndPassword);



    }

    @PostMapping("/searchAllWorkerByFNameAndLName")
    public ResponseEntity<Page<WorkerOrUserSerchOutDto>> searchAllWorkerByFNameAndLName(@Valid @RequestBody WorkerOrUserSerchInDto request){


        Page<WorkerOrUserSerchOutDto> findAllByFNameAndLName = workerService.findAllByFNameAndLName(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(findAllByFNameAndLName);

    }

    @PostMapping("/search-worker-ListByAllAttribute2")
    public ResponseEntity<Page<WorkerOrUserSerchOutDto>> searchUsers(@Valid @RequestBody WorkerOrUserSerchInDto request){


        Page<WorkerOrUserSerchOutDto> allByFNameAndLNameAndEmailAndPassword = userService.findAllByFNameAndLNameAndEmailAndPassword(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allByFNameAndLNameAndEmailAndPassword);




    }

    @PostMapping("/searchAllUserByFNameAndLName")
    public ResponseEntity<Page<WorkerOrUserSerchOutDto>> searchAllUserByFNameAndLName(@Valid @RequestBody WorkerOrUserSerchInDto request){


        Page<WorkerOrUserSerchOutDto> allByFNameAndLNameAndEmailAndPassword = userService.findAllByFNameAndLName(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allByFNameAndLNameAndEmailAndPassword);




    }

    // add Main Service
    @PostMapping("/addMainService")
    public ResponseEntity<MainServiceOutDto> addMainService(@Valid @RequestBody MainServiceInDto mainServiceInDto) {
        MainServiceOutDto result = mainServiceService.save(mainServiceInDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);

    }

    /**
     * <b>here we have to way :</b> <br>
     * 1) just set last name and find next add to main service <br>
     * 2) make a worker then save and then set to main service and save main service
     *
     * @param workerInDto
     */
    @PostMapping("/Appointment/toMainService/{id}")
    public void AppointmentOfASpecialistToTheService(@Valid @RequestBody WorkerInDto workerInDto, @PathVariable("id") Long mainServiceId) {
//        if (workerInDto.getMainServiceList() != null && workerInDto.getFirstName() != null) {
        if (isExistWork(workerInDto)) {
            WorkerOutDto workerOutDto = workerService.save(workerInDto);
            Worker w = workerService.findById(workerOutDto.getId());
//TOdO
            Set<Worker> workerList = new HashSet<>();
            workerList.add(w);
            addNewWorkerToMainService(workerList, mainServiceId);

        } else {

            String lastName = workerInDto.getLastName();
            Worker w = workerService.findByName(lastName);
            Set<Worker> workers = new HashSet<>();
            workers.add(w);
            addNewWorkerToMainService(workers, mainServiceId);


        }

    }


    public void addNewWorkerToMainService(Set<Worker> workers, Long mainServiceId) {
        MainService mainService = mainServiceService.findById(mainServiceId);

        MainServiceInDto mainServiceInDto = new MainServiceInDto();
        mainServiceInDto.setId(mainService.getId());
        mainServiceInDto.setWorkers(workers);
        mainServiceInDto.setName(mainService.getName());
        mainServiceInDto.setUpdatedTime(new Date());
        mainServiceService.save(mainServiceInDto);
    }


    public boolean isExistWork(WorkerInDto workerInDto) {
        Worker w = workerService.findById(workerInDto.getId());
        //TODO+
        if (w.getId().equals(null))
            return true;
        return false;

    }




    ///-------------------------------
    //todo pleas come here for --> fas 4    ;-)

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAdmins(){
        return ResponseEntity.ok().body(adminService.getAdmins());
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable String id){
        return ResponseEntity.ok().body(adminService.getAdmin(id));
    }

    @PostMapping("/admin/save")
    public ResponseEntity<User> saveAdmin(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser (@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(),form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Admin admin = adminService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(admin.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",admin.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception exception){
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }

        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}


@Data
class RoleToAdminForm {
    private String username;
    private String roleName;
}