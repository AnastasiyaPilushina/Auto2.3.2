package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.Registration;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogInIfActiveValidUser() {
        Registration user = DataGenerator.generateNewActiveValidUser();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLogInIfBlockedUser() {
        Registration user = DataGenerator.generateNewBlockedUser();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Пользователь заблокирован")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfActiveUserInvalidLogin() {
        Registration user = DataGenerator.generateNewActiveUserInvalidLogin();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfActiveUserInvalidPassword() {
        Registration user = DataGenerator.generateNewActiveInvalidPassword();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfActiveUserEmptyLogin() {
        Registration user = DataGenerator.generateNewActiveValidUser();
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='login'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfActiveUserEmptyPassword() {
        Registration user = DataGenerator.generateNewActiveValidUser();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='password'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfActiveUserEmptyLoginAndPassword() {
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='login'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='password'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
    }
}