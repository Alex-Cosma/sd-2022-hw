package com.project.clinic;

import com.project.clinic.appointment.model.Appointment;
import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.appointment.model.dto.AppointmentMinimalDTO;
import com.project.clinic.brand.model.Brand;
import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.ingredient.model.Ingredient;
import com.project.clinic.ingredient.model.dto.IngredientDTO;
import com.project.clinic.product.model.Product;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import com.project.clinic.purchase.model.Purchase;
import com.project.clinic.skin_color.model.ESkinColor;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.treatment.model.ETreatmentCategory;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.treatment.model.TreatmentCategory;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.dto.UserMinimalDTO;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.user.model.*;

import java.math.BigInteger;
import java.sql.Date;
import java.util.*;
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

        if (cls.equals(ProductJsonDTO.class)) {
            supplier = TestCreationFactory::newProductJsonDTO;
        } else if (cls.equals(Product.class)) {
            supplier = TestCreationFactory::newProduct;
        } else if (cls.equals(BrandDTO.class)) {
            supplier = TestCreationFactory::newBrandDTO;
        } else if (cls.equals(Brand.class)) {
            supplier = TestCreationFactory::newBrand;
        } else if (cls.equals(IngredientDTO.class)) {
            supplier = TestCreationFactory::newIngredientDTO;
        } else if (cls.equals(Ingredient.class)) {
            supplier = TestCreationFactory::newIngredient;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(UserRegisterDTO.class)) {
            supplier = TestCreationFactory::newUserRegisterDTO;
        } else if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(UserMinimalDTO.class)) {
        supplier = TestCreationFactory::newUserMinimalDTO;
        } else if (cls.equals(Treatment.class)) {
            supplier = TestCreationFactory::newTreatment;
        } else if (cls.equals(TreatmentDTO.class)) {
            supplier = TestCreationFactory::newTreatmentDTO;
        } else if (cls.equals(SkinColor.class)) {
            supplier = TestCreationFactory::newSkinColor;
        } else if (cls.equals(Role.class)) {
            supplier = TestCreationFactory::newRole;
        } else if (cls.equals(TreatmentCategory.class)) {
            supplier = TestCreationFactory::newTreatmentCategory;
        } else if (cls.equals(Appointment.class)) {
            supplier = TestCreationFactory::newAppointment;
        } else if (cls.equals(AppointmentDTO.class)) {
            supplier = TestCreationFactory::newAppointmentDTO;
        } else if (cls.equals(AppointmentMinimalDTO.class)) {
            supplier = TestCreationFactory::newAppointmentMinimalDTO;
        } else if (cls.equals(Purchase.class)) {
            supplier = TestCreationFactory::newPurchase;
        }
        else if (cls.equals(UserListDTO.class)) {
                supplier = TestCreationFactory::newUserListDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());

    }

    public static Purchase newPurchase(){
        return Purchase.builder()
                .id(randomBigInteger())
                .userId(randomLong())
                .productId(randomLong())
                .price(new Random().nextInt())
                .build();
    }

    public static Appointment newAppointment(){
        User dermatologist = newUser();
        dermatologist.setRoles(Collections.singleton(Role.builder()
                        .name(ERole.valueOf("PROFESSIONAL"))
                        .build())
        );
        return Appointment.builder()
                .id(randomLong())
                .treatment(newTreatment())
                .customer(newUser())
                .date(Date.valueOf("2022-02-02"))
                .dermatologist(dermatologist)
                .build();
    }

    public static AppointmentDTO newAppointmentDTO(){
        return AppointmentDTO.builder()
                .id(randomLong())
                .customerId(randomLong())
                .dermatologistId(randomLong())
                .date("2022-02-02")
                .dermatologistUsername(randomString())
                .treatmentId(randomLong())
                .treatmentTitle(randomString())
                .build();
    }

    public static AppointmentMinimalDTO newAppointmentMinimalDTO(){
        return AppointmentMinimalDTO.builder()
                .customerId(randomLong())
                .dermatologistId(randomLong())
                .treatmentId(randomLong())
                .build();
    }

    public static SkinColor newSkinColor(){
        return SkinColor.builder()
                .name(ESkinColor.valueOf("VANILLA"))
                .build();
    }

    public static Role newRole(){
        return Role.builder()
                .id(randomLong())
                .name(ERole.valueOf("CUSTOMER"))
                .build();
    }

    public static TreatmentDTO newTreatmentDTO(){
        return TreatmentDTO.builder()
                .id(randomLong())
                .category("ACNE_PRONE")
                .points(new Random().nextInt(100))
                .professionals(new HashSet<>())
                .title(randomString())
                .build();
    }

    public static TreatmentCategory newTreatmentCategory(){
        return TreatmentCategory.builder()
                .id(randomLong())
                .name(ETreatmentCategory.ACNE_PRONE)
                .build();
    }

    public static Treatment newTreatment(){
        return Treatment.builder()
                .id(randomLong())
                .title(randomString())
                .category(newTreatmentCategory())
                .points(new Random().nextInt(100))
                .build();
    }

    public static User newUser() {
        return User.builder()
                .id(randomLong())
                .password(randomString())
                .username(randomString())
                .skinColor(randomSkinColor())
                .points(0)
                .roles(
                    Collections.singleton(Role.builder()
                            .name(ERole.valueOf("CUSTOMER"))
                            .build())
                    )
                .treatments(new HashSet<>())
                .build();
    }

    public static UserMinimalDTO newUserMinimalDTO(){
        return UserMinimalDTO.builder()
                .id(randomLong())
                .name(randomString())
                .points(0)
                .build();
    }

    public static UserRegisterDTO newUserRegisterDTO(){
        return UserRegisterDTO.builder()
                .id(randomLong())
                .name(randomString())
                .password(randomString())
                .points(0)
                .roles(Collections.singleton("CUSTOMER"))
                .skinColor("VANILLA")
                .treatments(new HashSet<>())
                .build();
    }

    public static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .points(0)
                .treatments(new HashSet<>())
                .build();
    }

    public static ProductJsonDTO newProductJsonDTO() {
        return ProductJsonDTO.builder()
                .id(randomLong())
                .brand(randomString())
                .brandId(randomLong())
                .name(randomString())
                .price(new Random().nextInt(1000))
                .ingredient_list(new String[0])
                .build();
    }

    public static Product newProduct(){
        Set<Ingredient> ingredients = new HashSet<>();
        for(int i = 0; i < 5; i++){
            ingredients.add(newIngredient());
        }

        return Product.builder()
                .id(randomLong())
                .price(new Random().nextInt(1000))
                .name(randomString())
                .brand(newBrand())
                .ingredients(ingredients)
                .build();
    }

    public static Brand newBrand(){
        return Brand.builder()
                .id(randomLong())
                .name(randomString())
                .products(new HashSet<>())
                .build();
    }

    public static BrandDTO newBrandDTO(){
        Set<String> products = new HashSet<>();
        for(int i = 0; i < 5; i++){
            products.add(randomString());
        }

        return BrandDTO.builder()
                .id(randomLong())
                .name(randomString())
                .products(products)
                .build();
    }

    public static IngredientDTO newIngredientDTO(){
        return IngredientDTO.builder()
                .id(randomLong())
                .title(randomString())
                .build();
    }

    public static Ingredient newIngredient(){
        return Ingredient.builder()
                .id(randomLong())
                .title(randomString())
                .build();
    }

    public static SkinColor randomSkinColor(){
        ArrayList<SkinColor> skinColors = new ArrayList<>();
        for (ESkinColor value: ESkinColor.values()){
            skinColors.add(
                    SkinColor.builder()
                            .name(value)
                            .build()
            );
        }

        return skinColors.get(new Random().nextInt(skinColors.size()));
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static double randomDouble(){
        return new Random().nextDouble();
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static BigInteger randomBigInteger() {
        return new BigInteger(String.valueOf(new Random().nextInt()));
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
}
