package com.project.clinic;

import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.appointment.AppointmentService;
import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.brand.BrandRepository;
import com.project.clinic.brand.BrandService;
import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.external_api.products_api.ProductBootstrapper;
import com.project.clinic.ingredient.IngredientRepository;
import com.project.clinic.ingredient.IngredientService;
import com.project.clinic.ingredient.model.Ingredient;
import com.project.clinic.ingredient.model.dto.IngredientDTO;
import com.project.clinic.product.ProductMapper;
import com.project.clinic.product.ProductRepository;
import com.project.clinic.product.ProductService;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import com.project.clinic.security.AuthService;
import com.project.clinic.security.dto.SignupRequest;
import com.project.clinic.skin_color.model.ESkinColor;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.treatment.TreatmentCategoryRepository;
import com.project.clinic.treatment.TreatmentMapper;
import com.project.clinic.treatment.TreatmentRepository;
import com.project.clinic.treatment.TreatmentService;
import com.project.clinic.treatment.model.ETreatmentCategory;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.treatment.model.TreatmentCategory;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.user.UserRepository;
import com.project.clinic.user.UserService;
import com.project.clinic.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    
    private final AppointmentRepository appointmentRepository;
    private final BrandRepository brandRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;
    private final TreatmentRepository treatmentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SkinColorRepository skinColorRepository;
    private final TreatmentCategoryRepository treatmentCategoryRepository;
    private final ProductMapper productMapper;
    private final TreatmentMapper treatmentMapper;
    private final ProductBootstrapper productBootstrapper;

    private final AppointmentService appointmentService;
    private final BrandService brandService;
    private final IngredientService ingredientService;
    private final ProductService productService;
    private final TreatmentService treatmentService;
    private final UserService userService;
    private final AuthService authService;


    @Value("false")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap){
            productRepository.deleteAll();
            ingredientRepository.deleteAll();
            brandRepository.deleteAll();

            appointmentRepository.deleteAll();
            userRepository.deleteAll();
            treatmentRepository.deleteAll();
            roleRepository.deleteAll();
            skinColorRepository.deleteAll();
            treatmentCategoryRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            for (ESkinColor value: ESkinColor.values()){
                skinColorRepository.save(
                        SkinColor.builder()
                        .name(value)
                        .build()
                );
            }

            for (ETreatmentCategory value: ETreatmentCategory.values()){
                treatmentCategoryRepository.save(
                        TreatmentCategory.builder()
                                .name(value)
                                .build()
                );
            }

            BrandDTO brand1 = brandService.create(new BrandDTO(1L, "amorepacific", new HashSet<String>()));
            BrandDTO brand2 = brandService.create(new BrandDTO(2L, "belif", new HashSet<String>()));

            for(Long i = 1L; i < 10L; i++) {
                ProductJsonDTO product = productBootstrapper.getProductFromApi(i);
                for(String s: product.getIngredient_list()){
                    ingredientService.create(new IngredientDTO(1L, s));
                }

                productService.create(
                        new ProductJsonDTO().builder()
                                .name(product.getName())
                                .id(product.getId())
                                .price(new Random().nextInt(200))
                                .brandId(brand1.getId())
                                .ingredient_list(product.getIngredient_list())
                        .build()
                );
            }

            for(Long i = 50L; i < 55L; i++) {
                ProductJsonDTO product = productBootstrapper.getProductFromApi(i);
                for(String s: product.getIngredient_list()){
                    ingredientService.create(new IngredientDTO(1L, s));
                }

                Set<Ingredient> ingredients = Arrays.stream(product.getIngredient_list())
                        .map(ingredientService::findByTitle)
                        .collect(Collectors.toSet());

                product.setPrice(new Random().nextInt(200));

                productService.create(
                        new ProductJsonDTO().builder()
                                .name(product.getName())
                                .id(product.getId())
                                .price(new Random().nextInt(200))
                                .brandId(brand2.getId())
                                .ingredient_list(product.getIngredient_list())
                                .build()
                );

                TreatmentDTO treatmentDTO1 = treatmentService.create(new TreatmentDTO(1L, "Face extraction", new HashSet<User>(), "ACNE_PRONE", new Random().nextInt(20)));
                TreatmentDTO treatmentDTO2 = treatmentService.create(new TreatmentDTO(1L, "Face moisturizing treatment", new HashSet<User>(), "DRY", new Random().nextInt(20)));
                TreatmentDTO treatmentDTO3 = treatmentService.create(new TreatmentDTO(1L, "Face massage", new HashSet<User>(), "AGING", new Random().nextInt(20)));
                TreatmentDTO treatmentDTO4 = treatmentService.create(new TreatmentDTO(1L, "Allergy consultation", new HashSet<User>(), "ALLERGY", new Random().nextInt(20)));
                TreatmentDTO treatmentDTO5 = treatmentService.create(new TreatmentDTO(1L, "Sun Burn Consultation", new HashSet<User>(), "CONSULTATION", new Random().nextInt(20)));
                TreatmentDTO treatmentDTO6 = treatmentService.create(new TreatmentDTO(1L, "Rosacea", new HashSet<User>(), "DISEASE", new Random().nextInt(20)));

                HashSet<Treatment> treatments1 = new HashSet<>();
                treatments1.add(treatmentMapper.fromDto(treatmentDTO1));
                treatments1.add(treatmentMapper.fromDto(treatmentDTO2));

                authService.register(SignupRequest.builder()
                        .username("root")
                        .password("root!1")
                        .roles(Set.of("ADMIN"))
                        .skinColorId(skinColorRepository.findByName(ESkinColor.VANILLA).getId())
                        .treatments(new HashSet<>())
                        .build());
                authService.register(SignupRequest.builder()
                                .username("anto")
                        .password("anto!1")
                        .roles(Set.of("CUSTOMER"))
                        .skinColorId(skinColorRepository.findByName(ESkinColor.VANILLA).getId())
                        .treatments(new HashSet<>())
                        .build());
                authService.register(SignupRequest.builder()
                        .username("anto11")
                        .password("anto!11")
                        .roles(Set.of("PROFESSIONAL"))
                        .skinColorId(skinColorRepository.findByName(ESkinColor.GOLDEN).getId())
                        .treatments(new HashSet<>(treatments1))
                        .build());

                User customer = userRepository.findByUsername("anto").get();
                User dermatologist = userRepository.findByUsername("anto11").get();

                appointmentService.create(new AppointmentDTO(1L, dermatologist.getId(), customer.getId(), treatmentDTO1.getId(), dermatologist.getUsername(), treatmentDTO1.getTitle(), "2022-05-04" ));
                appointmentService.create(new AppointmentDTO(1L, dermatologist.getId(), customer.getId(), treatmentDTO2.getId(), dermatologist.getUsername(), treatmentDTO2.getTitle(), "2022-05-03"));
            }



        }
    }
}
