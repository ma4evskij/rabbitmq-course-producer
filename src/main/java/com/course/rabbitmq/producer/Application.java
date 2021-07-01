package com.course.rabbitmq.producer;

import com.course.rabbitmq.producer.entity.Picture;
import com.course.rabbitmq.producer.producer.RetryPictureProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
//@EnableScheduling
public class Application implements CommandLineRunner {

	@Autowired
	private RetryPictureProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private List<String> SOURCES = List.of("mobile", "web");

	private List<String> TYPES = List.of("jpg", "png", "svg");

	@Override
	public void run(String... args) throws Exception {
		for(int i=0;i < 3; i++) {
			var picture = new Picture();

			picture.setName("Picture" + i);
			picture.setSource(SOURCES.get(i % SOURCES.size()));
			picture.setType(TYPES.get(i % TYPES.size()));
			picture.setSize(ThreadLocalRandom.current().nextLong(9000, 10000));

			producer.send(picture);
		}
	}
}
