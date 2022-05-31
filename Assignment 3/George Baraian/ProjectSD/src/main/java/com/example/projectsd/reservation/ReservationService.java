package com.example.projectsd.reservation;

import com.example.projectsd.court.CourtMapper;
import com.example.projectsd.court.CourtService;
import com.example.projectsd.court.model.dto.CourtDTO;
import com.example.projectsd.email.EmailSenderService;
import com.example.projectsd.reservation.model.Reservation;
import com.example.projectsd.reservation.model.dto.ReservationDTO;
import com.example.projectsd.user.UserService;
import com.example.projectsd.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final CourtService courtService;
    private final CourtMapper courtMapper;
    private final ReservationMapper reservationMapper;
    private final EmailSenderService emailSenderService;


    public void create(String username, Long courtId) {
        User client = userService.findByUsername(username);

        CourtDTO courtDTO = courtService.findById(courtId);

        Reservation reservation = Reservation.builder()
                .startTime(new Date(System.currentTimeMillis() + 18*100000))
                .endTime(new Date(System.currentTimeMillis() + 2*18*100000))
                .client(client)
                .court(courtMapper.fromDto((courtDTO)))
                .build();
        reservationRepository.save(reservation);

        courtDTO.setAvailability("BUSY");
        courtService.update(courtDTO);

            emailSenderService.sendEmail("georgesdspring@gmail.com",
                    "You have successfully made a reservation at the " + reservation.getCourt().getName()
                            + " court starting " + reservation.getStartTime()
                            + " and ending at " + reservation.getEndTime(),
                    "Confirmation of reservation");


    }

    public void download() throws IOException {
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findAll();
        for(Reservation reservation : reservations) {
            ReservationDTO reservationDTO = reservationMapper.toDto(reservation);
            User user = reservation.getClient();
            reservationDTO.setUsername(user.getUsername());
            reservationDTOS.add(reservationDTO);
        }


        List<String> dataLinesInFile = new ArrayList<>();
        for (ReservationDTO reservationDTO : reservationDTOS) {
            String s = reservationDTO.toString();
            dataLinesInFile.add(s);
        }

        File csvOutputFile = new File("./reservations.csv");
        PrintWriter printWriter = new PrintWriter(csvOutputFile);
        String header = "ID, StartTime, EndTime, Username, Court name";
        printWriter.println(header);
        for (String dataLine : dataLinesInFile) {
            printWriter.println(dataLine);
        }
        printWriter.close();
    }
}
