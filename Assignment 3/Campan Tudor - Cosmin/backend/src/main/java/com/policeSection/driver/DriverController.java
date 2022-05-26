package com.policeSection.driver;

import com.policeSection.driver.model.dto.DriverDTO;
import com.policeSection.fine.FineService;
import com.policeSection.fine.Model.dto.FineDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.policeSection.UrlMapping.*;

@RestController
@RequestMapping(DRIVERS)
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public List<DriverDTO> allDrivers() {
        return driverService.findAll();
    }


    @PostMapping(CREATE_DRIVER)
    public DriverDTO create(@RequestBody DriverDTO driver) {
        return driverService.create(driver);
    }

    @DeleteMapping(DRIVERS_ID_PART)
    public void delete(@PathVariable Long id) {
        driverService.delete(id);
    }

    @PutMapping(DRIVERS_ID_PART)
    public DriverDTO edit(@PathVariable Long id, @RequestBody DriverDTO driver) {
        return driverService.edit(id, driver);
    }

    @PutMapping(GIVE_FINE)
    public DriverDTO giveFine(@PathVariable Long id, @RequestBody FineDTO fine) {
        return driverService.addFine(id, fine.getType());
    }

    @PutMapping(PAY_FINE)
    public DriverDTO payFine(@PathVariable Long id, @RequestBody FineDTO fine) {
        return driverService.removeFine(id, fine.getType());
    }
}
