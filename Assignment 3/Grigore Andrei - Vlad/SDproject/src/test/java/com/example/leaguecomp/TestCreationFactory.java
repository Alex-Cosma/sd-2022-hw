package com.example.leaguecomp;

import com.example.leaguecomp.champion.dto.ChampionDTO;
import com.example.leaguecomp.champion.model.Champion;
import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.dto.ItemMinimalDTO;
import com.example.leaguecomp.item.model.Item;
import com.example.leaguecomp.item.model.Stat;
import com.example.leaguecomp.rune.dto.RuneDTO;
import com.example.leaguecomp.rune.model.Rune;
import com.example.leaguecomp.summoner.dto.SummonerDTO;
import com.example.leaguecomp.summoner.model.ERegion;
import com.example.leaguecomp.summoner.model.Region;
import com.example.leaguecomp.summoner.model.Summoner;
import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.dto.UserListDTO;
import com.example.leaguecomp.user.model.Role;
import com.example.leaguecomp.user.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.leaguecomp.user.model.ERole.ADMINISTRATOR;
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
        } else if (cls.equals(ItemListDTO.class)) {
            supplier = TestCreationFactory::newItemDTO;
        } else if (cls.equals(ItemMinimalDTO.class)) {
            supplier = TestCreationFactory::newItemMinimalDTO;
        } else if (cls.equals(RuneDTO.class)) {
            supplier = TestCreationFactory::newRuneDTO;
        } else if (cls.equals(Rune.class)) {
            supplier = TestCreationFactory::newRune;
        } else if (cls.equals(Item.class)) {
            supplier = TestCreationFactory::newItem;
        }else if (cls.equals(Champion.class)) {
            supplier = TestCreationFactory::newChampion;
        }else if (cls.equals(ChampionDTO.class)) {
            supplier = TestCreationFactory::newChampionDTO;
        }else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        }
        else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static Rune newRune(){
        return Rune.builder()
                .description(randomString())
                .name(randomString())
                .build();
    }

    public static Champion newChampion(){
        return Champion.builder()
                .id((long)randomBoundedInt(159))
                .description(randomString())
                .image(randomBytes())
                .name(randomString())
                .damage(randomBoundedInt(10))
                .crowdControl(randomBoundedInt(10))
                .toughness(randomBoundedInt(10))
                .mobility(randomBoundedInt(10))
                .utility(randomBoundedInt(10))
                .build();
    }

    public static ChampionDTO newChampionDTO(){
        return ChampionDTO.builder()
                .id((long)randomBoundedInt(159))
                .description(randomString())
                .image(randomBytes())
                .name(randomString())
                .damage(randomBoundedInt(10))
                .crowdControl(randomBoundedInt(10))
                .toughness(randomBoundedInt(10))
                .mobility(randomBoundedInt(10))
                .utility(randomBoundedInt(10))
                .build();
    }
    public static Summoner newSummoner(){
        return Summoner.builder()
                .id(randomLong())
                .name(randomString())
                .region(randomRegion())
                .league(randomString())
                .build();
    }

    public static SummonerDTO newSummonerDTO(){
        return SummonerDTO.builder()
                .id(randomLong())
                .name(randomString())
                .region(randomRegion())
                .league(randomString())
                .build();
    }

    private static RuneDTO newRuneDTO(){
        return RuneDTO.builder()
                .description(randomString())
                .name(randomString())
                .build();
    }

    public static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .roles(Set.of("EMPLOYEE"))
                .build();
    }

    public static User newUser() {
        Summoner summoner = newSummoner();
        Set<Summoner> followed = new HashSet<>();
        followed.add(summoner);
        return User.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .roles(Set.of(Role.builder().role(ADMINISTRATOR).build()))
                .followedSummoners(followed)
                .build();
    }

    public static Role newRole(){
        return Role.builder()
                .id(randomBoundedInt(100))
                .role(ADMINISTRATOR)
                .build();
    }

    private static Item newItem() {
        return Item.builder()
                .id(randomLong())
                .name(randomString())
                .image(randomString())
                .stats(Set.of(randomStat()))
                .build();
    }

    private static ItemListDTO newItemDTO() {
        return ItemListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .image(randomString())
                .stats(Set.of(randomString()))
                .build();
    }

    private static ItemMinimalDTO newItemMinimalDTO() {
        return ItemMinimalDTO.builder()
                .id(randomLong())
                .name(randomString())
                .image(randomString())
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

    public static Stat randomStat(){
        return Stat.builder()
                .stat(randomString())
                .value(randomLong())
                .build();

    }

    public static Region randomRegion(){
        return Region.builder()
                .id(randomLong())
                .region(ERegion.EUROPE_NORTH_EAST)
                .build();
    }
}
