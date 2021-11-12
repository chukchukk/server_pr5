package com.example.demo.PR4;

import com.example.demo.Model.Truck;
import com.example.demo.Repositories.EmployeeRepository;
import com.example.demo.Repositories.OrderRepository;
import com.example.demo.Repositories.TruckRepository;
import com.example.demo.Services.EmployeeService;
import com.example.demo.Services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TruckRestService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TruckService truckService;

    @Autowired
    private TruckRepository truckRepository;

    public void addNewTruck(Truck truck) {
        truckRepository.save(truck);
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public Truck getTruck(Long id) {
        Truck truck = truckRepository.findTruckById(id);
        if (truck == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "грузовика с таким id не существует");
        return truck;
    }

    public Truck updateTruck(Long id, Truck truck) {
        Truck currentTruck = getTruck(id);
        currentTruck.setCarNumber(truck.getCarNumber());
        currentTruck.setDescription(truck.getDescription());
        truckRepository.save(currentTruck);
        return currentTruck;
    }
    public void deleteTruck(Long id) {
        Truck currentTruck = getTruck(id);
        truckRepository.delete(currentTruck);
    }
}
