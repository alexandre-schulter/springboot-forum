package br.com.alura.demospringboot.forum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
class ForumApplicationTests {

	@Test
	void contextLoads() {
		//Assert.assertTrue(true);
		Assertions.assertTrue(true);
	}

}
