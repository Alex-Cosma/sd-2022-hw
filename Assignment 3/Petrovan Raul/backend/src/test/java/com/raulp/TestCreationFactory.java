package com.raulp;

import com.raulp.flight.Airport;
import com.raulp.flight.Flight;
import com.raulp.flight.Plane;
import com.raulp.flight.dtos.AirportDTO;
import com.raulp.flight.dtos.FlightDTO;
import com.raulp.flight.dtos.PlaneDTO;
import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.dto.user.UserDetailsDTO;
import com.raulp.user.dto.user.UserListDTO;
import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.model.User;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Instructor.class)) {
            supplier = TestCreationFactory::newInstructor;
        } else if (cls.equals(UserMinimalDTO.class)) {
            supplier = TestCreationFactory::newUserMinimalDTO;
        }else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(UserDetailsDTO.class)) {
            supplier = TestCreationFactory::newUserDetailsDTO;
        }else if (cls.equals(Student.class)) {
            supplier = TestCreationFactory::newStudent;
        }
        else if (cls.equals(Flight.class)) {
            supplier = TestCreationFactory::newFlight;
        }else if (cls.equals(PlaneDTO.class)) {
            supplier = TestCreationFactory::newPlaneDTO;
        }else if (cls.equals(AirportDTO.class)) {
            supplier = TestCreationFactory::newAirportDTO;
        }else if (cls.equals(Plane.class)) {
            supplier = TestCreationFactory::newPlane;
        }else if (cls.equals(Airport.class)) {
            supplier = TestCreationFactory::newAirport;
        }else if (cls.equals(Student.class)) {
            supplier = TestCreationFactory::newStudent;
        }else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static Flight newFlight() {
        return Flight.builder()
                .id(new Random().nextLong())
                .departureTime(new Date())
                .arrivalTime(new Date())
                .departureAirport(new Airport())
                .arrivalAirport(new Airport())
                .airplane(new Plane())
                .build();
    }

    public static Plane newPlane() {
        return Plane.builder()
                .id(new Random().nextLong())
                .name("Plane")
                .build();
    }

    public static Airport newAirport() {
        return Airport.builder()
                .id(new Random().nextLong())
                .name("Airport")
                .icao("ICAO")
                .build();
    }

    public static FlightDTO newFlightDTO() {
        return FlightDTO.builder()
                .id(randomLong())
                .airplane(newPlaneDTO())
                .arrivalAirport(newAirportDTO())
                .departureAirport(newAirportDTO())
                .arrivalTime(randomDate())
                .departureTime(randomDate())
                .build();
    }



    public static PlaneDTO newPlaneDTO() {
        return PlaneDTO.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static AirportDTO newAirportDTO() {
        return AirportDTO.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static UserDetailsDTO newUserDetailsDTO() {
        return UserDetailsDTO.builder()
                .id(new Random().nextLong())
                .firstName("firstName")
                .lastName("lastName")
                .build();
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .build();
    }

    public static UserMinimalDTO newUserMinimalDTO() {
        return UserMinimalDTO.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static Student newStudent() {
        Instructor instructor = newInstructor();
        return Student.builder()
                .id(randomLong())
                .username(randomString())
                .firstName(randomString())
                .lastName(randomString())
                .email(randomEmail())
                .build();
    }

    public static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    public static Instructor newInstructor() {
        return Instructor.builder()
                .id(randomLong())
                .username(randomString())
                .firstName(randomString())
                .lastName(randomString())
                .email(randomEmail())
                .build();
    }


    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    private static Date randomDate() {
        return new Date(randomLong());
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static InstructorMinimalDTO newInstructorDTO() {
        return InstructorMinimalDTO.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static StudentMinimalDTO newStudentDTO() {
        return StudentMinimalDTO.builder()
                .id(randomLong())
                .name(randomString())
                .build();
    }

    public static FlightDTO newFlightDTOFull() {
        return FlightDTO.builder()
                .id(randomLong())
                .airplane(newPlaneDTO())
                .arrivalAirport(newAirportDTO())
                .departureAirport(newAirportDTO())
                .student(newStudentDTO())
                .instructor(newInstructorDTO())
                .arrivalTime(randomDate())
                .departureTime(randomDate())
                .build();
    }
}
