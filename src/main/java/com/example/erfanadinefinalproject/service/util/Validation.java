package com.example.erfanadinefinalproject.service.util;


import com.example.erfanadinefinalproject.entity.Transaction;
import com.example.erfanadinefinalproject.entity.core.BasePerson;
import com.example.erfanadinefinalproject.entity.product.MainOrder;
import com.example.erfanadinefinalproject.entity.product.message.Request;
import com.example.erfanadinefinalproject.entity.product.message.Suggestion;
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.entity.work.SubService;
import com.example.erfanadinefinalproject.exception.*;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Validation {
//    private static Role role;
//    private static StatusRole statusRole;


    /**
     * description :  this method set a <b>"BasePersonDto"</b>  and then return <b>"Boolean"</b>
     *
     * @param basePerson
     * @return boolean
     * @throws EmailNotValidException
     * @throws PasswordNotValidException
     * @throws NameNotValidException
     * @throws NullFieldException
     */
    public static Boolean checkBaseCustomerIsValid(BasePerson basePerson) throws EmailNotValidException, PasswordNotValidException, NameNotValidException, NullFieldException {
        if (basePerson == null)
            throw new NullFieldException("UserInDto or worker is null!");
        String email = basePerson.getEmail();

        if (!emailIsValid(email)) {
            throw new EmailNotValidException("Email is not valid!");
        }

        String password = basePerson.getPassword();
        if (!passwordIsValid(password)) {
            throw new PasswordNotValidException("Password is not valid!");
        }

        String fName = basePerson.getFName();
        String lName = basePerson.getLName();
        if (!nameIsValid(fName, lName)) {
            throw new NameNotValidException("Name is not Ture!!!!!!!!!!");
        }
        return true;
    }
//
//    public static Boolean RoleIsValid(Role role) throws RoleIsNullException {
//        if (role==null){
//            throw new RoleIsNullException("role is null ???????");
//        }
//        return true;
//    }

    public static Boolean SuggestionIsValid(Suggestion suggestion) throws SuggestionOfPriceIsNullException {
        if (suggestion.getPrice()==null)
            throw new SuggestionOfPriceIsNullException(" price of suggestion is null ???????");
        return true;
    }

    public static Boolean TransactionalIsVAlid(Transaction transaction) throws OrderOfTransactionIsNullExeption {
        if(transaction.getOrder()==null)
            throw new OrderOfTransactionIsNullExeption("order of transaction is null ????????");
        return true;
    }


    public static Boolean RequestServiceIsValid(Request request) throws AddressOfRequestIsNull, OrderOfRequestIsNullException {
        if (request.getAddress()==null)
            throw new AddressOfRequestIsNull("address of request is null ??????????");
        if (request.getPrice()==null)
            throw  new AddressOfRequestIsNull("price is ");
//        if (request.getOrder()==null)
//            throw new OrderOfRequestIsNullException("Order of RequestInDto is null ?????????");
        return true;

    }

    public static Boolean MainServiceIsValid(MainService mainService) throws NameOfMainServiceIsNull {
        if (mainService.getName() == null)
            throw new NameOfMainServiceIsNull("Name of MainServiceInDto is null");
        return true;
    }

    public static Boolean expertIsValid(SubService subService) throws NullFieldException, BadEntryException {
        if (subService == null)
            throw new NullFieldException("Service is null!");
        if (subService.getName() == null)
            throw new NullFieldException("Service name is null!");
        BigDecimal basePrice = subService.getBasePrice();
        if (basePrice.compareTo(new BigDecimal(0)) <= 0)
            throw new BadEntryException("The service price is not valid!");
        return true;
    }


    public static Boolean dutyIsValid(MainService mainService) throws NullFieldException {
        if (mainService == null)
            throw new NullFieldException("Service of MainServiceInDto is null!");
        if (mainService.getName() == null)
            throw new NullFieldException("Service name is null!");
//        BigDecimal basePrice = mainService.getBasePrice();
//        if (basePrice.compareTo(new BigDecimal(0)) <= 0)
//            throw new BadEntryException("The service price is not valid!");
        return true;
    }


    public static Boolean commentIsValid(String s) throws NullCommentException {
        if (s == null)
            throw new NullCommentException("CommentInDto is null ????????");
        return true;
    }

    public static Boolean mainServiceIsValid(MainOrder mainOrder) throws NullAddresOfMainOrderException {
        if (mainOrder.getAddres() == null)
            throw new NullAddresOfMainOrderException("Address of MainServiceInDto is null???????????");
        return true;
    }

    public static Boolean ExperteOrSubServiceIsValid(SubService subService) throws BasePriceOfSubServiceIsNull, NameOfSubServiceIsNull {
        if (subService.getBasePrice() == null)
            throw new NameOfSubServiceIsNull("basePrice of subService is null ??????????????????");
        if (subService.getName() == null)
            throw new BasePriceOfSubServiceIsNull("name of subService is null ??????????????????");
        return true;
    }



    public static Boolean emailIsValid(String email) {
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean matches = Pattern.matches(emailPattern, email);
        return matches;
//        pattern = Pattern.compile(emailPattern);
//        boolean emailIsValid = matcher.matches();
//        matcher = pattern.matcher(email);
    }

    public static Boolean passwordIsValid(String password) {
        String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{3,}$";
        return Pattern.matches(passwordPattern, password);
//        pattern = Pattern.compile(passwordPattern);
//        matcher = pattern.matcher(password);
//        boolean passwordIsValid = matcher.matches();
    }

    public static Boolean nameIsValid(String fName, String lName) {
        if (fName.length() < 3 || lName.length() < 3) {
            return false;
        }
        return true;
    }
}