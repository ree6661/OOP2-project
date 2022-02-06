package bg.tu_varna.sit.group17.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import bg.tu_varna.sit.group17.validation.Valid;

class ValidationTest {
	
	@Test
	void usernameLength() {
		assertFalse(Valid.username("Uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"));
		assertTrue(Valid.username("Uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"));
		assertTrue(Valid.username("Uuuuuuuuuuuuuuuuu"));
	}
	@Test
	void usernameEmpty() {
		assertFalse(Valid.username(""));
		assertFalse(Valid.username(null));
		assertFalse(Valid.username(new String()));
	}
	@Test
	void usernameInvalidChars() {
		assertFalse(Valid.username("Susd^f"));
		assertFalse(Valid.username("Susdf&"));
		assertFalse(Valid.username("$Susdf"));
		assertFalse(Valid.username("*&@"));
		assertFalse(Valid.username("A1awq"));
		assertFalse(Valid.username("Sus3df"));
		assertFalse(Valid.username("Gwdio5"));
	}
	@Test
	void phoneNumberEmpty() {
		assertEquals(false, Valid.phoneNumber(""));
		assertEquals(false, Valid.phoneNumber(null));
		assertEquals(false, Valid.phoneNumber(new String()));
	}
	@Test
	void phoneNumberLength() {
		assertEquals(true, Valid.phoneNumber("123452234532"));
		assertEquals(false, Valid.phoneNumber("1234522345562"));
		assertEquals(false, Valid.phoneNumber("12345"));
		assertEquals(false, Valid.phoneNumber("12345232132342424"));
	}
	@Test
	void phoneNumberInvalidChars() {
		assertEquals(false, Valid.phoneNumber("*12345234532"));
		assertEquals(false, Valid.phoneNumber("12345234532("));
		assertEquals(false, Valid.phoneNumber("a12345234532"));
		assertEquals(false, Valid.phoneNumber("123452^34532"));
	}

	@Test
	void passwordEmpty() {
		assertEquals(false, Valid.password(""));
		assertEquals(false, Valid.password(null));
		assertEquals(false, Valid.password(new String()));
	}
	
	@Test
	void passwordLength() {
		assertEquals(false, Valid.password("*1234523453sdfsw453223452345322345234532"));
		assertEquals(true, Valid.password("234523wer4532(2453223r45234532"));
		assertEquals(true, Valid.password("a12345234532"));
		assertEquals(true, Valid.password("qwert"));
	}
	@Test
	void user() throws SQLException {
		String s = "", username = "Името не трябва да съдържа символи и да е твърде кратко",
				phone = "Телефона трябва да съдържа 12 цифри",
				passBig = "Паролата трябва да е между 5 и 30 символа",
				repeatPass = "Паролите не съвпадат";
		
		s = Valid.user("Ivan Ivanov", "098378291234", "12342", "12342");
		assertEquals("", s);
		s = Valid.user("Iv%an Ivanov", "098378291234", "1234", "1234");
		assertEquals(username, s);
		s = Valid.user("Ivan Ivanov", "09837u291234", "1234", "1234");
		assertEquals(phone, s);
		s = Valid.user("Ivan7Ivanov", "098378291234", "1234", "1234");
		assertEquals(username, s);
		s = Valid.user("Ivan Ivanov", "098378291234", "12234", "1234");
		assertEquals(repeatPass, s);
		s = Valid.user("Ivan7Ivanov", "098378291234", "12y34", "12y34");
		assertEquals(username, s);
		s = Valid.user("Ivan7Ivanov", "098378291234", "1234", "1234r");
		assertEquals(username, s);
		s = Valid.user(null, "098378291234", "1234", "1234r");
		assertEquals(username, s);
		s = Valid.user("Ivan Ivanov", null, "1234", "1234r");
		assertEquals(phone, s);
		s = Valid.user("Ivan Ivanov", "098378291234", null, "1234r");
		assertEquals(passBig, s);
		s = Valid.user("Ivan Ivanov", "098378291234", "12344", null);
		assertEquals(repeatPass, s);
	}
	@Test
	void date() {
		assertEquals(true, Valid.date("2021-12-27"));
		assertEquals(false, Valid.date("202112-27"));
		assertEquals(false, Valid.date("20621-12-27"));
		assertEquals(false, Valid.date("2021-12-78"));
		assertEquals(false, Valid.date("2021-13-18"));
		assertEquals(false, Valid.date("021-12-78"));
	}
	@Test
	void order() {
		assertEquals(true, Valid.order("098937281232", "239048273214", "2021-12-21", "2021-12-27"));
		assertEquals(true, Valid.order("098937281232", "239048273214", "2022-01-21", "2022-01-27"));
	}
	@Test
	void price() {
		assertEquals(true , Valid.price(1234));
		assertEquals(true , Valid.price(345.2));
		assertEquals(true , Valid.price(0));
		assertEquals(false , Valid.price(-1));
		assertEquals(false , Valid.price(-144));
	}
}
