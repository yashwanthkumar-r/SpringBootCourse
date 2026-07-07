package com.example.week_06_Testing;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class Week06TestingApplicationTests {
//
//	@BeforeEach
//	void preStart(){
//		System.out.println("Starting the test cases...");
//	}
//
//	@AfterEach
//	void postStop(){
//		System.out.println("tearing down the objects..");
//	}
//
//	@BeforeAll
//	static void setUp(){
//		System.out.println("_______before starting all run this method________");
//	}
//
//	@AfterAll
//	static void tearDown(){
//		System.out.println("_______After ending all run this method________");
//	}
//
//	@Test
//	@DisplayName("Test-1")
//	void contextLoads() {
//		System.out.println("This is test-1");
//		int result = addTwoNumbers(3,5);
//
//	//	Assertions.assertEquals(8,result);
//
//		Assertions.assertThat(result).isEqualTo(8)
//				.isCloseTo(9, Offset.offset(1));
//	}
//
//	@Test
//	void testExceptionForDivision(){
//		Assertions.assertThatThrownBy(()-> divideTwoNumbers(2,0))
//				.isInstanceOf(ArithmeticException.class)
//				.hasMessage("Arithmetic Exception occured: / by zero");
//	}
//
//	@Test
//	@DisplayName("Test-2")
//	void something(){
//		System.out.println("This is test-2");
//	}
//
//	int addTwoNumbers(int a, int b){
//		return a+b;
//	}
//
//	int divideTwoNumbers(int a, int b){
//		try{
//			return a/b;
//		}catch(ArithmeticException e){
//			log.error("Arithmetic Exception occured: " + e.getLocalizedMessage());
//			throw new ArithmeticException("Arithmetic Exception occured: / by zero");
//		}
//	}

}
