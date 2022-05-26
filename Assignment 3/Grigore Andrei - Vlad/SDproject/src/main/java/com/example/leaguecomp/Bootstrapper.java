package com.example.leaguecomp;

import com.example.leaguecomp.api.OriannaData;
import com.example.leaguecomp.champion.ChampionRepository;
import com.example.leaguecomp.champion.ChampionService;
import com.example.leaguecomp.champion.ReportPdfService;
import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.mapper.ChampionMapper;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.ItemRepository;
import com.example.leaguecomp.item.Mapper.ItemMapper;
import com.example.leaguecomp.item.StatRepository;
import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.rune.model.Rune;
import com.example.leaguecomp.security.dto.SignupRequest;
import com.example.leaguecomp.summoner.RegionRepository;
import com.example.leaguecomp.summoner.SummonerRepository;
import com.example.leaguecomp.rune.RuneRepository;
import com.example.leaguecomp.security.AuthService;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.RoleRepository;
import com.example.leaguecomp.user.UserRepository;
import com.example.leaguecomp.user.UserService;
import com.example.leaguecomp.user.model.ERole;
import com.example.leaguecomp.user.model.Role;
import com.example.leaguecomp.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final OriannaData oriannaData;
    private final ReportPdfService reportPdfService;
    private final ChampionRepository championRepository;
    private final StatRepository statRepository;
    private final ItemRepository itemRepository;
    private final RuneRepository runeRepository;
    private final ItemMapper itemMapper;
    private final RegionRepository regionRepository;
    private final UserService userService;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final SummonerRepository summonerRepository;
    private final RoleRepository roleRepository;
    private final ChampionMapper championMapper;
    private final ChampionService championService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {
//            Champion champion = championRepository.findById(1L).orElseThrow();
//
//            ByteArrayOutputStream byteArrayOutputStream = null;
//            File resultFile = null;
//            try {
//                resultFile = File.createTempFile("Test",".pdf");
//                byteArrayOutputStream = reportPdfService.export(champion);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try(OutputStream outputStream = new FileOutputStream(resultFile)) {
//                try {
//                    byteArrayOutputStream.writeTo(outputStream);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("Please find your PDF File here: " + resultFile.getAbsolutePath());
        }
    }
}
