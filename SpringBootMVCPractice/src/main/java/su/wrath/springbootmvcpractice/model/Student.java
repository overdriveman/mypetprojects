package su.wrath.springbootmvcpractice.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotEmpty(message = "Имя обязательно к заполнено")
    @Size(min=2, max=50, message = "Имя должно состоять от 2 до 50 символов")
    private String name;

    @NotEmpty(message = "Электронная почта обязательна к заполнено")
    @Email(message = "Поле должно иметь формат электронной почты")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Возраст должен быть заполнен")
    @Min(value = 15, message = "Студент должен быть старше 15")
    private Integer age;

    @NotEmpty(message = "Название города обязательно к заполнению")
    @Size(min=2, max=30, message = "Город должен состоять от 2 до 30 символов")
    private String hometown;

}
