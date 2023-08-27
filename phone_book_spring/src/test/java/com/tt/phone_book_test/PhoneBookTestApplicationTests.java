/**
 * Any other test should that should be done here?
 */
package com.tt.phone_book_test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tt.phone_book_test.controller.PhoneBookController;

@SpringBootTest
class PhoneBookTestApplicationTests
{
	@Autowired
	private PhoneBookController controller;

	@Test
	void contextLoads() throws Exception
	{
		assertThat(controller).isNotNull();
	}
}
