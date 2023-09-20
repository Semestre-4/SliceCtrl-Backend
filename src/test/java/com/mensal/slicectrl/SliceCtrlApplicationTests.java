package com.mensal.slicectrl;

import com.mensal.slicectrl.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SliceCtrlApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();

		assertThat(applicationContext.getBean(PizzaService.class)).isNotNull();

	}

}
