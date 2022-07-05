package by.kharchenko.cafe.validator.impl;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestParameter.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserValidatorImplTest {
    private final UserValidatorImpl validator = UserValidatorImpl.getInstance();

    @Test
    public void testValidateCorrectName() {
        String correctName = "Konstantin";
        assertTrue(validator.isCorrectName(correctName));
    }

    @Test
    public void testValidateInCorrectName() {
        String inCorrectName = "*Салат оливье";
        assertFalse(validator.isCorrectName(inCorrectName));
    }

    @Test
    public void testValidateCorrectBirthday() {
        String correctDate = "2000-12-12";
        assertTrue(validator.isCorrectAge(correctDate));
    }

    @Test
    public void testValidateInCorrectBirthday() {
        String inCorrectDate = "2020-12-12*";
        assertFalse(validator.isCorrectAge(inCorrectDate));
    }

    @Test
    public void testValidateCorrectLogin() {
        String correctLogin = "cat";
        assertTrue(validator.isCorrectLogin(correctLogin));
    }

    @Test
    public void testValidateInCorrectLogin() {
        String inCorrectLogin = "cat%^&*&";
        assertFalse(validator.isCorrectLogin(inCorrectLogin));
    }

    @Test
    public void testValidateCorrectSurname() {
        String correctSurname = "Kharchenko";
        assertTrue(validator.isCorrectSurname(correctSurname));
    }

    @Test
    public void testValidateInCorrectSurname() {
        String inCorrectSurname = "Kharchenko*";
        assertFalse(validator.isCorrectSurname(inCorrectSurname));
    }

    @Test
    public void testValidateCorrectPassword() {
        String correctPassword = "BPpb23-=";
        assertTrue(validator.isCorrectPassword(correctPassword));
    }

    @Test
    public void testValidateInCorrectPassword() {
        String inCorrectPassword = "bppb23-=";
        assertFalse(validator.isCorrectPassword(inCorrectPassword));
    }

    @Test
    public void testValidateCorrectPhoneNumber() {
        String correctPhoneNumber = "+375336206911";
        assertTrue(validator.isCorrectPhone(correctPhoneNumber));
    }

    @Test
    public void testValidateInCorrectPhoneNumber() {
        String inCorrectPhoneNumber = "+3753376273";
        assertFalse(validator.isCorrectPhone(inCorrectPhoneNumber));
    }

    @Test
    public void testValidateCorrectEmail() {
        String correctEmail = "abc@pl.ui";
        assertTrue(validator.isCorrectEmail(correctEmail));
    }

    @Test
    public void testValidateInCorrectEmail() {
        String inCorrectEmail = "@hji.oi";
        assertFalse(validator.isCorrectEmail(inCorrectEmail));
    }

    @Test
    public void testValidateCorrectIsBlock() {
        String correctIsBlock = "false";
        assertTrue(validator.isCorrectIsBlock(correctIsBlock));
    }

    @Test
    public void testValidateInCorrectIsBlock() {
        String inCorrectIsBlock = "0";
        assertFalse(validator.isCorrectIsBlock(inCorrectIsBlock));
    }

    @Test
    public void testValidateCorrectExperience() {
        String correctExperience = "1.4";
        assertTrue(validator.isCorrectExperience(correctExperience));
    }

    @Test
    public void testValidateInCorrectExperience() {
        String inCorrectExperience = "18*";
        assertFalse(validator.isCorrectExperience(inCorrectExperience));
    }

    @Test
    public void testValidateCorrectLoyaltyPoints() {
        String correctLoyaltyPoints = "1";
        assertTrue(validator.isCorrectLoyaltyPoints(correctLoyaltyPoints));
    }

    @Test
    public void testValidateInCorrectLoyaltyPoints() {
        String inCorrectLoyaltyPoints = "1.6";
        assertFalse(validator.isCorrectLoyaltyPoints(inCorrectLoyaltyPoints));
    }

    @Test
    public void testValidateCorrectStatus() {
        String correctStatus = "accepted";
        assertTrue(validator.isCorrectStatus(correctStatus));
    }

    @Test
    public void testValidateInCorrectStatus() {
        String inCorrectStatus = "accepted5";
        assertFalse(validator.isCorrectStatus(inCorrectStatus));
    }

    @Test
    public void testValidateCorrectRole() {
        String correctRole = "CLIENT";
        assertTrue(validator.isCorrectUserRole(correctRole));
    }

    @Test
    public void testValidateInCorrectRole() {
        String inCorrectRole = "CLIENT%";
        assertFalse(validator.isCorrectUserRole(inCorrectRole));
    }

    @Test
    public void testValidateCorrectPhoto() {
        String correctPhoto = "a.png";
        assertTrue(validator.isCorrectPhoto(correctPhoto));
    }

    @Test
    public void testValidateInCorrectPhoto() {
        String inCorrectPhoto = "a.exe";
        assertFalse(validator.isCorrectPhoto(inCorrectPhoto));
    }

    @Test
    public void testValidateCorrectRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Konstantin");
        data.put(SURNAME, "Kharchenko");
        data.put(LOGIN, "cat");
        data.put(PASSWORD, "BPpb23-=");
        data.put(PHONE_NUMBER, "+375336206911");
        data.put(EMAIL, "cat@gmail.com");
        data.put(ROLE, "CLIENT");
        data.put(BIRTHDAY, "2000-12-12");
        assertTrue(validator.isCorrectRegisterData(data));
    }

    @Test
    public void testValidateInCorrectRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Konstantin*");
        data.put(SURNAME, "Kharchenko*");
        data.put(LOGIN, "cat$%^&");
        data.put(PASSWORD, "pbpb23-=");
        data.put(PHONE_NUMBER, "+3753362011");
        data.put(EMAIL, "@gmail.com");
        data.put(ROLE, "CLIENT%");
        data.put(BIRTHDAY, "2000-12-12*");
        assertFalse(validator.isCorrectRegisterData(data));
    }

    @Test
    public void testValidateCorrectClientUpdateData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Konstantin");
        data.put(SURNAME, "Kharchenko");
        data.put(LOGIN, "cat");
        data.put(PHONE_NUMBER, "+375336206911");
        assertTrue(validator.isCorrectUpdateClientData(data));
    }

    @Test
    public void testValidateInCorrectClientUpdateData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Konstantin*");
        data.put(SURNAME, "Kharchenko*");
        data.put(LOGIN, "cat$%^&");
        data.put(PHONE_NUMBER, "+3753362011");
        assertFalse(validator.isCorrectUpdateClientData(data));
    }

    @Test
    public void testValidateCorrectAdminUpdateData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Konstantin");
        data.put(SURNAME, "Kharchenko");
        data.put(LOGIN, "cat");
        data.put(PHONE_NUMBER, "+375336206911");
        data.put(EXPERIENCE, "3.4");
        assertTrue(validator.isCorrectUpdateClientData(data));
    }

    @Test
    public void testValidateInCorrectAdminUpdateData() {
        Map<String, String> data = new HashMap<>();
        data.put(NAME, "Konstantin*");
        data.put(SURNAME, "Kharchenko*");
        data.put(LOGIN, "cat$%^&");
        data.put(PHONE_NUMBER, "+3753362011");
        data.put(EXPERIENCE, "3.4*");
        assertFalse(validator.isCorrectUpdateClientData(data));
    }
}
