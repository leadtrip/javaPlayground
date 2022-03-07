package wood.mike.misc;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JavaBeanValidation {

    public static void main(String[] args) {
        RandomBean rb = new RandomBean();
        rb.alreadyHappened = ZonedDateTime.of( 3000, Month.APRIL.getValue(), 1, 1, 1, 1, 1,  ZoneId.systemDefault());
        rb.comingSoon = LocalDate.now().minusDays( 1 );
        rb.description = "Too short";
        rb.email = "incorrect@email";
        rb.mandatory = "";
        rb.name = null;
        rb.negative = 40L;
        rb.positive = -20;
        rb.noBlankStrings = List.of("okay", "fine", "", "good");
        rb.power = 99;
        rb.retro = false;
        rb.strList = Collections.emptyList();
        rb.theYear = Year.now().minusYears( 200 );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RandomBean>> violations = validator.validate( rb );

        for (ConstraintViolation<RandomBean> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }


    private static class RandomBean {
        @NotNull(message = "Please specify a name")
        private String name;

        @AssertTrue
        private boolean retro;

        @Size(min = 20, max = 100, message = "Please supply a descriptive message between {min} and {max} characters, you are ${min - validatedValue.length()} characters short with '${validatedValue}'")
        private String description;

        @Min(value = 100, message = "You must produce at least 100 watts")
        @Max(value = 500, message = "You are very powerful, maybe too powerful")
        private int power;

        @Email(message = "Please try harder")
        private String email;

        @NotEmpty
        private List<String> strList;

        private List<@NotBlank String> noBlankStrings;

        @NotBlank
        private String mandatory;

        @PositiveOrZero
        private Integer positive;

        @NegativeOrZero
        private Long negative;

        @Future
        private LocalDate comingSoon = LocalDate.now().minusDays(39);

        @Past()
        private ZonedDateTime alreadyHappened;

        @FutureOrPresent( message = "${validatedValue} is in the past, please specify this year or a year in the future")
        private Year theYear;
    }
}
