package com.app.banking.controller;

import com.app.banking.data.dto.model.DepositDto;
import com.app.banking.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.app.banking.URLMapping.*;

@RestController
@RequestMapping(DEPOSITS)
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:*")
public class DepositController {
    private final DepositService depositService;

    @GetMapping("/my")
    public List<DepositDto> getMyDeposits(Principal principal) {
        return depositService.getMyDeposits(principal.getName());
    }

    @GetMapping
    public List<DepositDto> allCards(){
        return depositService.findAll();
    }

    @GetMapping(MY_DEPOSITS)
    public List<DepositDto> allCardsByOwner(@PathVariable Integer id){
        return depositService.allDepositsByOwner(id);
    }

    @PostMapping
    public DepositDto create(@RequestBody DepositDto deposit){
        return depositService.addDeposit(deposit);
    }

    @PatchMapping(ID)
    public DepositDto update(@PathVariable Integer id, @RequestBody DepositDto deposit) {
        return depositService.update(id, deposit);
    }

    @DeleteMapping(ID)
    public void delete(@PathVariable Integer id) {
        depositService.delete(id);
    }
}
