package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.amenities.model.Amenity;
import com.example.airbnb.user.UserService;
import com.example.airbnb.user.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class AccommodationService {
//    @Autowired
    private final JavaMailSender mailSender;

    private final AccommodationMapper accommodationMapper;
    private final AccommodationRepository accommodationRepository;
    private final UserService userService;

    public Page<Accommodation> findAll() {
        Pageable paging = PageRequest.of(0, 100);
        return accommodationRepository.findAll(paging);
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElseGet(null);
    }

    public void delete(Long id){
        accommodationRepository.deleteById(id);
    }

    public AccommodationDTO create(AccommodationDTO accommodationDTO){
        Accommodation accommodation = accommodationMapper.fromDto(accommodationDTO);
        accommodationMapper.populateAccommodationAddress(accommodation, accommodationDTO);
        accommodationMapper.populateAccommodationAmenities(accommodation, accommodationDTO);
        accommodationMapper.populateAccommodationImageURL(accommodation, accommodationDTO);

        User user = userService.findById(accommodationDTO.getUser_id());
        accommodation.setUser(user);

        AccommodationDTO accommodationDTO1 = accommodationMapper.toDto(accommodationRepository.save(accommodation));
        accommodationMapper.populateAccommodationDTOAddress(accommodationDTO1, accommodation);
        accommodationMapper.populateAccommodationDTOAmenities(accommodationDTO1, accommodation);
        accommodationMapper.populateAccommodationDTOImageURL(accommodationDTO1, accommodation);
        accommodationDTO1.setUser_id(user.getId());

        return accommodationDTO1;
    }

    public AccommodationDTO edit(Long id, AccommodationDTO accommodationDTO){
        Accommodation accommodation = findById(id);
        accommodation.setName(accommodationDTO.getName());
        accommodation.setDescription(accommodationDTO.getDescription());
        accommodation.setHouse_rules(accommodationDTO.getHouse_rules());
        accommodation.setProperty_type(accommodationDTO.getProperty_type());
        accommodation.setRoom_type(accommodationDTO.getRoom_type());
        accommodation.setBathrooms(Integer.parseInt(accommodationDTO.getBathrooms()));
        accommodation.setBedrooms(Integer.parseInt(accommodationDTO.getBedrooms()));
        accommodation.setBeds(Integer.parseInt(accommodationDTO.getBeds()));
        accommodation.setPrice(accommodationDTO.getPrice());
        User user = userService.findById(accommodationDTO.getUser_id());
        accommodation.setUser(user);

        accommodationMapper.populateAccommodationAddress(accommodation, accommodationDTO);
        accommodationMapper.populateAccommodationAmenities(accommodation, accommodationDTO);
        accommodationMapper.populateAccommodationImageURL(accommodation, accommodationDTO);

        AccommodationDTO accommodationDTO1 = accommodationMapper.toDto(accommodationRepository.save(accommodation));
        accommodationMapper.populateAccommodationDTOAddress(accommodationDTO1, accommodation);
        accommodationMapper.populateAccommodationDTOAmenities(accommodationDTO1, accommodation);
        accommodationMapper.populateAccommodationDTOImageURL(accommodationDTO1, accommodation);
        accommodationDTO1.setUser_id(user.getId());

        return accommodationDTO1;
    }

    public void save(Accommodation accommodation) {
        accommodationRepository.save(accommodation);
    }

    public ResponseEntity<byte[]> exportPDF(Long id) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        Accommodation accommodation = findById(id);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont( PDType1Font.TIMES_ROMAN, 16 );

        //Setting the leading
        contentStream.setLeading(14.5f);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 725);

        String name = "Name: " + accommodation.getName();
        String description = "Description: " + accommodation.getDescription();
        String house_rules = "House Rules: " + accommodation.getHouse_rules();
        String property_type = "Property Type: " + accommodation.getProperty_type();
        String room_type = "Room Type: " + accommodation.getRoom_type();
        String bathrooms = "Bathrooms: " + accommodation.getBathrooms();
        String bedrooms = "Bedrooms: " + accommodation.getBedrooms();
        String beds = "Beds: " + accommodation.getBeds();
        String amenities = "Amenities: ";
        if(accommodation.getAmenities() != null) {
            for (Amenity amenity : accommodation.getAmenities()) {
                amenities += amenity.getAmenity() + ", ";
            }
        }

        String price = "Price: " + accommodation.getPrice() + "$";
        String address = "Address: ";
        if(accommodation.getAddress() != null) {
            address += accommodation.getAddress().getStreet() + ", " + accommodation.getAddress().getCity();
        }

        //Adding text in the form of string
        contentStream.showText(name);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(description);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(house_rules);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(property_type);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(room_type);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(bathrooms);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(bedrooms);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(beds);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(amenities);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(price);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(address);

        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();

        String pathFile = "C:\\FACULTATE\\SD\\sd-2022-hw\\Assignment 3\\Horvat Diana Adriana\\Airbnb";
        byte[] contents = Files.readAllBytes(Paths.get(pathFile + "\\pdfBoxHelloWorld.pdf"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "pdfBoxHelloWorld.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    public void sendEmail(Long id){
        Accommodation accommodation = findById(id);
        User user = accommodation.getUser();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
//        message.setTo("horvat.diana2000@gmail.com");
        message.setSubject(accommodation.getName() + " Accommodation Details");

        String amenities = "Amenities: ";
        if(accommodation.getAmenities() != null){
            for (Amenity amenity : accommodation.getAmenities()) {
                amenities += amenity.getAmenity() + ", ";
            }
        }

        String address = "Address: ";
        if(accommodation.getAddress() != null){
            address += accommodation.getAddress().getStreet() + ", " + accommodation.getAddress().getCity();
        }

        message.setText("Description: " + accommodation.getDescription() + "\n" +
                "House Rules: " + accommodation.getHouse_rules() + "\n" +
                "Property Type: " + accommodation.getProperty_type() + "\n" +
                "Room Type: " + accommodation.getRoom_type() + "\n" +
                "Bathrooms: " + accommodation.getBathrooms() + "\n" +
                "Bedrooms: " + accommodation.getBedrooms() + "\n" +
                "Beds: " + accommodation.getBeds() + "\n" +
                 amenities + "\n" +
                "Price: " + accommodation.getPrice() + " $/night" + "\n" +
                address);

        mailSender.send(message);
    }


}
