package com.example.demo.PR4;

import com.example.demo.Model.Truck;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pr4/trucks")
@Api(tags = "Truck")
public class TruckRestController {

    @Autowired
    private TruckRestService truckRestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Добавление нового автомобиля")
    @ApiResponse(code = 200, message = "everything is ok")
    public void addNewOrder(@ApiParam(type = "Truck",
                        value = "truck",
                        example = "{\n" +
                                "    \"description\": \"Жёсткий 4м/2.2м/2м Груз 1500кг\",\n" +
                                "    \"carNumber\": \"С065МК\"\n" +
                                "}",
                        required = true)
                                @RequestBody Truck truck) {
        truckRestService.addNewTruck(truck);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение информации о всех автомобилях")
    @ApiResponse(code = 200, message = "everything is ok")
    public List<Truck> getAllTrucks() {
        return truckRestService.getAllTrucks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение информации об автомобиле по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "everything is ok"),
            @ApiResponse(code = 404, message = "Truck with this id doesn't exist")
    })
    public Truck getTruck(@ApiParam(type = "Long",
                        value = "id",
                        example = "5",
                        required = true)
                              @PathVariable Long id) {
        return truckRestService.getTruck(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Обновление информации об автомобиле по id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Truck with this id updated"),
            @ApiResponse(code = 404, message = "Truck with this id doesn't exist")
    })
    public Truck updateTruck(@ApiParam(type = "Long",
                        value = "id",
                        example = "5",
                        required = true)
                                 @PathVariable Long id,
                             @ApiParam(type = "Truck",
                                     value = "truck",
                                     example = "{\n" +
                                             "    \"description\": \"Жёсткий 4м/2.2м/2м Груз 1500кг\",\n" +
                                             "    \"carNumber\": \"С065МК\"\n" +
                                             "}",
                                     required = true)
                             @RequestBody Truck truck) {
        return truckRestService.updateTruck(id, truck);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Удаление автомобиля по id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Truck with this id deleted"),
            @ApiResponse(code = 404, message = "Truck with this id doesn't exist")
    })
    public void deleteTruck(@ApiParam(type = "Long",
                        value = "id",
                        example = "5",
                        required = true)
                                @PathVariable Long id) {
        truckRestService.deleteTruck(id);
    }
}
