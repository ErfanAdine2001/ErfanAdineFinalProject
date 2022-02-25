package com.example.erfanadinefinalproject.controller;


import com.example.erfanadinefinalproject.dto.in.work.SubServiceInDto;
import com.example.erfanadinefinalproject.dto.out.work.SubServiceOutDto;
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.entity.work.SubService;
import com.example.erfanadinefinalproject.service.MainService_Service;
import com.example.erfanadinefinalproject.service.SubService_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/subServices")
public class SubServiceController{

    private final SubService_Service subServiceService;
    private final MainService_Service mainServiceService;

    @PostMapping("/showAllSubServices")
    public ResponseEntity<List<SubService>> showAllSubServices(){

        List<SubService> subServiceList = subServiceService.showSubServices();

        return ResponseEntity.status(HttpStatus.OK)
                .body(subServiceList);

    }





    @PostMapping("/create")
    public ResponseEntity<SubServiceOutDto> create(@Valid @RequestBody SubServiceInDto request) {

        SubServiceOutDto subServiceOutDto = subServiceService.save(request);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subServiceOutDto);

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SubService>> findAll() {
        List<SubService> subServiceList = subServiceService.findAll();


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subServiceList);

    }

    @GetMapping("/findById/{subServiceId}")
    public ResponseEntity<SubServiceOutDto> findById(@PathVariable Long subServiceId) {
        SubService subService = subServiceService.findById(subServiceId);

        SubServiceOutDto subServiceOutDto = new SubServiceOutDto();
        subServiceOutDto.setId(subService.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subServiceOutDto);


    }

    @PutMapping("/update/{subServiceId}")
    public ResponseEntity<SubService> update(@PathVariable Long subServiceId, @RequestBody SubServiceInDto subServiceInDto) {

        List<MainService> mainServiceList = new ArrayList<>();
        for (Long s : subServiceInDto.getMainServiceId()) {
            MainService mainOrder = mainServiceService.findById(s);
            mainServiceList.add(mainOrder);

        }


        SubService subService = subServiceService.findById(subServiceId);

        subService.setMainService(mainServiceList);
        subService.setName(subServiceInDto.getName());
        subService.setUpdatedTime(new Date());
        subService.setBasePrice(subServiceInDto.getBasePrice());


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subService);


    }

    @DeleteMapping("/delete/{subServiceId}e")
    public ResponseEntity<String> delete(@PathVariable Long subServiceId) {
        subServiceService.delete(subServiceId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Deleted");


    }

}
