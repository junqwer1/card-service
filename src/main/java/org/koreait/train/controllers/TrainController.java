package org.koreait.train.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.train.entities.TrainData;
import org.koreait.train.services.PredictService;
import org.koreait.train.services.TrainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final PredictService predictService;

    @GetMapping("/train/{mode}")
    public List<TrainData> train(@PathVariable("mode") String mode) {
        return trainService.getList(mode.equals("all"));
    }

    @GetMapping("/predict")
    public List<Integer> predict(@RequestParam("data") List<Double> items) {
        return predictService.predict(items);
    }
}
