package com.example.erfanadinefinalproject.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.erfanadinefinalproject.dto.in.BankCardInformationInDto;
import com.example.erfanadinefinalproject.dto.in.TransactionInDto;
import com.example.erfanadinefinalproject.dto.in.product.CommentInDto;
import com.example.erfanadinefinalproject.dto.in.product.MainOrderInDto;
import com.example.erfanadinefinalproject.dto.in.product.message.SuggestionInDto;
import com.example.erfanadinefinalproject.dto.in.user.ShowAllOrdersByUserIdInDto;
import com.example.erfanadinefinalproject.dto.in.user.UserInDto;
import com.example.erfanadinefinalproject.dto.in.user.WorkerOrUserSerchInDto;
import com.example.erfanadinefinalproject.dto.out.product.CommentOutDto;
import com.example.erfanadinefinalproject.dto.out.product.MainOrderOutDto;
import com.example.erfanadinefinalproject.dto.out.user.ShowAllOrdersByUserIdOutDto;
import com.example.erfanadinefinalproject.dto.out.user.UserOutDto;
import com.example.erfanadinefinalproject.dto.out.user.WorkerOrUserSerchOutDto;
import com.example.erfanadinefinalproject.entity.Transaction;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.OrderStatus;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.product.message.SuggestionStatus;
import com.example.erfanadinefinalproject.entity.security.Role;
import com.example.erfanadinefinalproject.entity.user.Admin;
import com.example.erfanadinefinalproject.entity.user.User;
import com.example.erfanadinefinalproject.entity.user.Worker;
import com.example.erfanadinefinalproject.exception.*;
import com.example.erfanadinefinalproject.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    private final SuggestionServiceImpl suggestionService;

    private final OrderServiceImpl mainOrderService;

    private final CommentServiceImpl commentService;

    private final TransactionServiceImpl transactionService;

    private final WorkerServiceImpl workerService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<UserOutDto> create(@Valid @RequestBody UserInDto request){

        UserOutDto result = userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);

    }


    @PostMapping("/findAllByFNameAndLNameAndEmailAndPassword")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<Page<WorkerOrUserSerchOutDto>> findAllByFNameAndLNameAndEmailAndPassword(@Valid @RequestBody WorkerOrUserSerchInDto request){

        Page<WorkerOrUserSerchOutDto> allByFNameAndLNameAndEmailAndPassword = userService.findAllByFNameAndLNameAndEmailAndPassword(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allByFNameAndLNameAndEmailAndPassword);

    }


    @PostMapping("/findAllByFNameAndLName")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<Page<WorkerOrUserSerchOutDto>> findAllByFNameAndLName(@Valid @RequestBody  WorkerOrUserSerchInDto request) {

        Page<WorkerOrUserSerchOutDto> allByFNameAndLNameAndEmailAndPassword = userService.findAllByFNameAndLName(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allByFNameAndLNameAndEmailAndPassword);

    }

    /**
     * <b>++++++++++++++++++++++++++++++++++</b>
     * <br>
     * <b>this method show all pagination Orders  of user by Id</b>
     *
     * @param request
     * @return
     */
    @PostMapping("/showAllOrders/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<Page<ShowAllOrdersByUserIdOutDto>> showAllOrders(@Valid @RequestBody ShowAllOrdersByUserIdInDto request, @PathVariable Long id) {

        Page<ShowAllOrdersByUserIdOutDto> showAllOrdersByUserIdOutDtos = userService.showAllOrdersByUserIdS(request, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(showAllOrdersByUserIdOutDtos);
    }



    /**
     * <B>this method add new Main Order</B>
     *
     * @param mainOrderInDto
     */
    @PostMapping("/selectSubServiceAndAddNewMainOrder")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<MainOrderOutDto> selectSubService(@Valid @RequestBody MainOrderInDto mainOrderInDto){
        MainOrderOutDto mainOrderOutDto = mainOrderService.save(mainOrderInDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mainOrderOutDto);
    }

    /**
     * <b>can find all "suggestion" if "Suggestion Status" equal with   " SuggestionStatus.ACCEPTED" </b>
     *
     * @param suggestion
     * @return
     */
    @PostMapping("/seeTheSuggestionsThatAreACCEPTED")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<Page<WorkerOrUserSerchOutDto>> seeTheSuggestionsThatAreACCEPTED(@Valid @RequestBody SuggestionInDto suggestion) {
        Pageable pageable = PageRequest.of(suggestion.getPageNumber(), suggestion.getPageSize());
        Page<WorkerOrUserSerchOutDto> allByStatusOrder = suggestionService.findAllByStatusOrder(pageable, SuggestionStatus.ACCEPTED);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allByStatusOrder);

    }

    /**
     * <b> here customer select his/her suggestion and then waiting for coming worker to place location </b>
     *
     * @param suggestionId
     */
    @PostMapping("/selectWorkerWithSuggestionIdAndWaitingForWorkerSelected/{suggestionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<String> selectWorkerWithSuggestionIdAndWaitingForWorkerSelectedU(@Valid @PathVariable Long suggestionId) {
        Suggestion s = suggestionService.findById(suggestionId);
        if (s.getSuggestionStatus().equals(SuggestionStatus.ACCEPTED)) {
            MainOrderInDto order = new MainOrderInDto();
            order.setId(s.getOrder().getId());
            order.setSuggestionId(s.getId());
            order.setStatus(OrderStatus.WAITING_FOR_COMING_WORKER);

            mainOrderService.save(order);


            return ResponseEntity.status(HttpStatus.OK)
                    .body("Waiting For Coming Worker ");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }


    // user can save his/her comment for order
    //TODO  ثبت نظرات
    @PostMapping("/addComment")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<CommentOutDto> addComment(@Valid @RequestBody CommentInDto commentInDto) {

        CommentOutDto commentOutDto = commentService.save(commentInDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentOutDto);
    }

    //TODO  مشاهده تاریخچه سفارشات و اعتبار
    @PostMapping("/findAllOrder")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<List<MainOrder>> findAllOrder(@Valid @RequestBody MainOrderInDto mainOrderInDto) {
        List<MainOrder> allOrderByStatusOfStatus = mainOrderService.findAllOrderByStatusOfStatus(mainOrderInDto.getStatus());

        return ResponseEntity.status(HttpStatus.OK)
                .body(allOrderByStatusOfStatus);

    }

    //TODO مشاهده تاریخچه سفارشات و اعتبار
    @GetMapping("/loadAmount/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<List<Transaction>> loadAmount(@Valid @PathVariable Long userId) {
        List<Transaction> transactionList = transactionService.findAllByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactionList);

    }

    //TODO    3-2 ----- پس از اعلام پایان  customer     @@@@@@@@@@@@@@@@@@@@@@@@@@@@  how can set time for come in page    and    how can i set captcha
    @PostMapping("/creditPayMoney")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<String> CreditPayMoney(@Valid @RequestBody TransactionInDto transactionInDto, @RequestBody BankCardInformationInDto bankCardInformationInDto) {


        if (mainOrderService.findById(transactionInDto.getOrderId()).getStatus().equals(OrderStatus.DONE) && checkBalanceAccount(transactionInDto)) {


            Transaction transaction = new Transaction();
            transaction.setOrder(mainOrderService.findById(transactionInDto.getOrderId()));
            transaction.setAmount(transactionInDto.getAmount());
            transaction.setWorker(workerService.findById(transactionInDto.getWorkerId()));
            transactionService.save(transaction);


            User user = userService.findById(transactionInDto.getId());
            user.setUserAccountBalance(user.getUserAccountBalance().subtract(transactionInDto.getAmount()));
            userService.save(user);

            MainOrder mainOrder = mainOrderService.findById(transactionInDto.getOrderId());
            mainOrder.setTransaction(transactionService.findById(transactionInDto.getId()));
            mainOrder.setStatus(OrderStatus.PAID);
            mainOrderService.save(mainOrder);

            Worker worker = workerService.findById(transactionInDto.getWorkerId());
            BigDecimal amount = transaction.getAmount();
            BigDecimal accountBalance = amount.subtract((amount.multiply(new BigDecimal(30).divide(new BigDecimal(100)))));
            worker.setAccountBalance(accountBalance);
            worker.setDebtToTheCompany(amount.subtract(accountBalance));


            workerService.save(worker);


        } else if (mainOrderService.findById(transactionInDto.getOrderId()).getStatus().equals(OrderStatus.DONE) && checkBalanceBank(transactionInDto, bankCardInformationInDto)) {

            Transaction transaction = new Transaction();
            transaction.setOrder(mainOrderService.findById(transactionInDto.getOrderId()));
            transaction.setAmount(transactionInDto.getAmount());
            transaction.setWorker(workerService.findById(transactionInDto.getWorkerId()));
            transactionService.save(transaction);

            //"subtract transaction amount"  from  " bank card  balance"
            bankCardInformationInDto.setBalance(bankCardInformationInDto.getBalance().subtract(transactionInDto.getAmount()));

            MainOrder mainOrder = mainOrderService.findById(transactionInDto.getOrderId());
            mainOrder.setTransaction(transactionService.findById(transactionInDto.getId()));
            mainOrder.setStatus(OrderStatus.PAID);
            mainOrderService.save(mainOrder);

            Worker worker = workerService.findById(transactionInDto.getWorkerId());
            BigDecimal amount = transaction.getAmount();
            BigDecimal accountBalance = amount.subtract((amount.multiply(new BigDecimal(30).divide(new BigDecimal(100)))));
            worker.setAccountBalance(accountBalance);
            worker.setDebtToTheCompany(amount.subtract(accountBalance));


            workerService.save(worker);


        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);

    }


    //TODO  3-2 ----- پرداخت      @@@@@@@@@@@@@
    public Boolean checkBalanceBank(TransactionInDto transactionInDto, BankCardInformationInDto bankCardInformationInDto) {

        BigDecimal balance = bankCardInformationInDto.getBalance();

        if (transactionInDto.getAmount().compareTo(balance) == -1) {
            return true;
        }
        return false;

    }

    public Boolean checkBalanceAccount(TransactionInDto transactionInDto) {

        User user = userService.findById(transactionInDto.getId());
        int b = user.getUserAccountBalance().compareTo(transactionInDto.getAmount());

        return b > 0;

    }


    //TODO    3-2 ----- پس از اعلام پایان  customer     @@@@@@@@@@@@@@@@@@@@@@@@@@@@-
    @PostMapping("/cashPayMoney")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<String> cashPayMoney(@RequestBody TransactionInDto transactionInDto)  {

        if (mainOrderService.findById(transactionInDto.getOrderId()).getStatus() == OrderStatus.DONE) {

            Transaction transaction = new Transaction();
            transaction.setOrder(mainOrderService.findById(transactionInDto.getOrderId()));
            transaction.setAmount(transactionInDto.getAmount());
            transactionService.save(transaction);

            MainOrder mainOrder = mainOrderService.findById(transactionInDto.getOrderId());
            mainOrder.setTransaction(transactionService.findById(transactionInDto.getId()));

            mainOrderService.save(mainOrder);


        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);

    }


    @GetMapping("/ServiceHistory/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('admin') or hasRole('user') or hasRole('USER')")
    public ResponseEntity<List<MainOrder>> serviceHistory(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.ServiceHistory(userId));

    }

    //***************************************************************************************************
    ///-------------------------------
    //todo pleas come here for --> fas 4    ;-)


    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAdmins(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getAdmin(@PathVariable String id){
        return ResponseEntity.ok().body(userService.getUser(id));
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
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
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
class RoleToUserForm {
    private String username;
    private String roleName;
}
