package com.example.erfanadinefinalproject.service;

import com.example.erfanadinefinalproject.dto.in.work.SubServiceInDto;
import com.example.erfanadinefinalproject.dto.out.work.SubServiceOutDto;
import com.example.erfanadinefinalproject.entity.work.MainService;
import com.example.erfanadinefinalproject.entity.work.SubService;
import com.example.erfanadinefinalproject.repo.MainServiceRepository;
import com.example.erfanadinefinalproject.repo.SubServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubService_ServiceImpl implements SubService_Service {

    private final SubServiceRepository experteRepository;
    private final MainServiceRepository dutyRepository;


    @Override
    public List<SubService> showSubServices() {
        List<SubService> subServiceList = new ArrayList<>();
        Iterable<SubService> result = experteRepository.findAll();
        result.forEach((element) -> subServiceList.add(element));

        return subServiceList;
    }

    @Override
    public SubServiceOutDto save(SubServiceInDto subService) {
        List<MainService> mainServiceList = new ArrayList<>();
        for (Long configurer : subService.getMainServiceId()) {

            MainService mainService = dutyRepository.findById(configurer).get();
            mainServiceList.add(mainService);
        }

        SubService service = new SubService();
        service.setName(subService.getName());
        service.setMainService(mainServiceList);
        service.setBasePrice(subService.getBasePrice());

        experteRepository.save(service);

        SubServiceOutDto subServiceOutDto = new SubServiceOutDto();
        subServiceOutDto.setId(service.getId());

        return subServiceOutDto;
    }

    @Override
    public SubService save(SubService subService) {
        return experteRepository.save(subService);
    }

    @Override
    public SubService findById(Long id) {
        return experteRepository.findById(id).get();
    }

    @Override
    public List<SubService> findAll() {
        return experteRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        experteRepository.delete(findById(id));
    }
}
