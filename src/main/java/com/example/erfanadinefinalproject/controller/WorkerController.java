package com.example.erfanadinefinalproject.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.erfanadinefinalproject.dto.in.product.MainOrderInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerInDto;
import com.example.erfanadinefinalproject.dto.out.product.PointsCommetnsOutDto;
import com.example.erfanadinefinalproject.dto.out.product.message.SuggestionOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOutDto;
import com.example.erfanadinefinalproject.entity.product.Comment;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.product.message.SuggestionStatus;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.repo.RoleRepo;
import com.example.erfanadinefinalproject.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final SuggestionServiceImpl suggestionService;
    private final WorkerServiceImpl workerService;
    private final OrderServiceImpl mainOrderService;
    private final CommentServiceImpl commentService;
    private final RoleRepo roleRepo;
    private PointsCommetnsOutDto pointsCommetnsOutDto;

    @PostMapping("/create")
    public ResponseEntity<WorkerOutDto> create(@Valid @RequestBody WorkerInDto request) {

        WorkerOutDto workerOutDto = workerService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workerOutDto);

    }

    /**
     * <b>++++++++++++++++++++++++++++++++++</b>
     * <br>
     * <b>this method show all pagination Orders  of user by Id</b>
     */
    @PostMapping("/findSuggestionForMainOrder")
    public ResponseEntity<List<MainOrder>> findSuggestionForMainOrder(@RequestBody MainOrderInDto mainOrderInDto) {
        List<MainOrder> allOrderByStatusWateFOrSuggestions = workerService.findAllOrderByStatusWateFOrSuggestions(mainOrderInDto.getStatus());

        return ResponseEntity.status(HttpStatus.OK)
                .body(allOrderByStatusWateFOrSuggestions);
    }


    /**
     * <b>send suggestion for Main order by "OrderId"</b>
     *
     * @param orderId
     */
    @PostMapping("/sendSuggestion/{workerId}/{orderId}")
    public ResponseEntity<SuggestionOutDto> sendSuggestionForMainOrder(@PathVariable Long orderId, @RequestBody MainOrderInDto mainOrderInDto, @PathVariable Long workerId) {
        MainOrder m = mainOrderService.findById(orderId);
        Worker w = workerService.findById(workerId);
        Suggestion s = suggestionService.findById(mainOrderInDto.getSuggestionId());

        Suggestion suggestion = new Suggestion();
        suggestion.setWorker(w);
        suggestion.setOrder(m);
        suggestion.setSuggestionStatus(SuggestionStatus.ACCEPTED);
        suggestion.setPrice(s.getSuggestionPrice());

        m.setSuggestion(suggestion);

        suggestionService.save(suggestion);

        SuggestionOutDto suggestionOutDto = new SuggestionOutDto();
        suggestionOutDto.setId(suggestion.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(suggestionOutDto);

    }

    //TODO ثبت نظرات
    @PostMapping("/loadCommentsByOrderId/{orderId}")
    public ResponseEntity<PointsCommetnsOutDto> loadCommentsByOrderId(@PathVariable Long orderId) {

//        MainOrder mainOrder = mainOrderService.findById(orderId);
        List<Comment> commentList = commentService.findAll();
        List<Comment> finalList = new ArrayList<>();
        List<Integer> finalPointsList = new ArrayList<>();

        for (Comment comment : commentList) {
            if (comment.getOrder().getId().equals(orderId)) {
                finalList.add(comment);
            }
        }

        for (Comment comment : finalList) {
            finalPointsList.add(comment.getPoints());
        }
        pointsCommetnsOutDto.setPoint(finalPointsList);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pointsCommetnsOutDto);

    }

    //TODO  مشاهده تاریخچه سفارشات و اعتبار

    @PostMapping("/findAllOrder")
    public ResponseEntity<List<MainOrder>> findAllOrder(@RequestBody MainOrderInDto mainOrderInDto) {
        List<MainOrder> allOrderByStatusOfStatus = mainOrderService.findAllOrderByStatusOfStatus(mainOrderInDto.getStatus());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(allOrderByStatusOfStatus);

    }

    @PostMapping("/finishWork/{orderId}")
    public ResponseEntity<String> finishWork(@PathVariable Long orderId) {
        MainOrder mainOrder = mainOrderService.findById(orderId);
        mainOrder.setStatus(OrderStatus.DONE);
        mainOrderService.save(mainOrder);
//        ObjectUtils objectUtils
        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }

    @PreAuthorize("")
    @GetMapping("/ServiceHistory/{workerId}")
    public ResponseEntity<List<MainOrder>> serviceHistory(@PathVariable Long workerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(workerService.serviceHistory(workerId));

    }

    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    ///-------------------------------
    //todo pleas come here for --> fas 4    ;-)

    @GetMapping("/workers")
    public ResponseEntity<List<Worker>> getUsers(){
        return ResponseEntity.ok().body(workerService.getWorkers());
    }

    @PostMapping("/worker/save")
    public ResponseEntity<Worker> saveUser(@RequestBody Worker worker){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/worker/save").toUriString());
        return ResponseEntity.created(uri).body(workerService.saveWorker(worker));
    }

    @GetMapping("/worker/{id}")
    public ResponseEntity<Worker> getAdmin(@PathVariable String id){
        return ResponseEntity.ok().body(workerService.getWorker(id));
    }


    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleRepo.save(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser (@RequestBody RoleToWorkForm form){
        workerService.addRoleToWorker(form.getUsername(),form.getRoleName());
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
                Worker worker = workerService.getWorker(username);
                String access_token = JWT.create()
                        .withSubject(worker.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",worker.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
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




    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------



}


@Data
class RoleToWorkForm {
    private String username;
    private String roleName;
}