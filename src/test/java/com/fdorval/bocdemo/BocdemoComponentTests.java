package com.fdorval.bocdemo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fdorval.bocdemo.model.Student;


/**
 * test de composant bouchonné : les données viennent de
 * com.fdorval.bocdemo.dao.stub.FireBaseDaoStub
 * @author françois
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BocdemoComponentTests {

	Logger LOG = LoggerFactory.getLogger(BocdemoComponentTests.class);

	 @LocalServerPort
	    private int port;

	    @Autowired
	    private TestRestTemplate restTemplate;
	    
	    @Test
	    public void shouldReturnStudents() throws Exception {
	    	Student[] students = this.restTemplate.getForObject("http://localhost:" + port + "/students",
	                Student[].class);
	    	assertThat(students.length==3);
	    }
	    
	    @Test
	    public void shouldReturnStudentsFiltered() throws Exception {
	    	Student[] students = this.restTemplate.getForObject("http://localhost:" + port + "/students?filterLimitGrade=8",
	                Student[].class);
	    	assertThat(students.length==2);
	    }
	        
	    
	    @Test
	    public void wrongUrlIn404() throws Exception {
	        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/kamoulox",
	                String.class).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	    }
}
