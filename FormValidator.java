import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// Annotation to check whether a field is blank
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

// Annotation to check maximum length
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

// Signup form with annotated fields
class SignupForm {

    @NotBlank
    @MaxLength(30)
    String name;

    @NotBlank
    @MaxLength(50)
    String email;

    @NotBlank
    @MaxLength(15)
    String password;

    SignupForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

// Validator using reflection
class Validator {

    static List<String> validate(Object obj)
            throws IllegalAccessException {

        List<String> errors = new ArrayList<>();

        Class<?> cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {

            field.setAccessible(true);
            Object fieldValue = field.get(obj);

            String value = fieldValue == null
                    ? null : fieldValue.toString();

            // Check @NotBlank
            if (field.isAnnotationPresent(NotBlank.class)) {
                if (value == null || value.trim().isEmpty()) {
                    errors.add(field.getName() + " must not be blank");
                }
            }

            // Check @MaxLength
            if (field.isAnnotationPresent(MaxLength.class)) {
                MaxLength annotation =
                        field.getAnnotation(MaxLength.class);

                if (value != null &&
                        value.length() > annotation.value()) {
                    errors.add(field.getName()
                            + " must not exceed "
                            + annotation.value() + " characters");
                }
            }
        }

        return errors;
    }
}

// Main class
public class FormValidator {

    public static void main(String[] args)
            throws IllegalAccessException {

        SignupForm form = new SignupForm(
                "",
                "student@example.com",
                "12345678901234567890"
        );

        List<String> errors = Validator.validate(form);

        if (errors.isEmpty()) {
            System.out.println("Form is valid!");
        } else {
            System.out.println("Validation Errors:");

            for (String error : errors) {
                System.out.println("- " + error);
            }
        }
    }
}
