package jblog.guohai.org;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("jblog.guohai.org.dao")
public class JblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JblogApplication.class, args);
	}

}
