package com.lab4.demo.user;

import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReportServiceFactory reportServiceFactory;

    @CrossOrigin
    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserListDTO user) {
        return userService.create(user);
    }

    @GetMapping(ENTITY)
    public UserListDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO user) {
        return userService.edit(id, user);
    }

    @CrossOrigin
    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable String type) throws IOException {
        if (type.equals("pdf")) {
            return reportServiceFactory.getReportService(ReportType.PDF).export();
        } else if (type.equals("csv")) {
            return reportServiceFactory.getReportService(ReportType.CSV).export();
        }
        else return "";

    }

}
