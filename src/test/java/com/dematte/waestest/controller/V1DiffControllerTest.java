package com.dematte.waestest.controller;

import com.dematte.waestest.model.dto.DiffResult;
import com.dematte.waestest.model.dto.ErrorDTO;
import com.dematte.waestest.model.dto.RecordDiffResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dematte.waestest.Base64Encoder.encode;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class V1DiffControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void saveDataLeft() {
		final byte[] bytes = encode("{ \"data\": 1}");
		final ResponseEntity<Void> post = post("/v1/diff/1/left", bytes);
		assertEquals(HttpStatus.OK, post.getStatusCode());
	}

	@Test
	public void saveDataLeftInvalid() {
		final byte[] bytes = "{invalid}".getBytes();
		final ResponseEntity<Void> post = post("/v1/diff/1/left", bytes);
		assertEquals(HttpStatus.BAD_REQUEST, post.getStatusCode());
	}

	@Test
	public void saveDataInvalidJson() {
		final byte[] bytes = encode("invalid-json");
		final ResponseEntity<Void> post = post("/v1/diff/1/left", bytes);
		assertEquals(HttpStatus.BAD_REQUEST, post.getStatusCode());
	}

	@Test
	public void saveDataRight() {
		final byte[] bytes = encode("{ \"data\": 1}");
		final ResponseEntity<Void> post = post("/v1/diff/2/right", bytes);
		assertEquals(HttpStatus.OK, post.getStatusCode());
	}

	@Test
	public void saveDataRightInvalid() {
		final byte[] bytes = "{invalid}".getBytes();
		final ResponseEntity<Void> post = post("/v1/diff/2/right", bytes);
		assertEquals(HttpStatus.BAD_REQUEST, post.getStatusCode());
	}

	@Test
	public void consultNotFound() {
		final ResponseEntity<ErrorDTO> get = restTemplate.getForEntity("/v1/diff/3", ErrorDTO.class);
		assertEquals(HttpStatus.NOT_FOUND, get.getStatusCode());
	}

	@Test
	public void consultJustRightSideData() {
		final byte[] bytes = encode("{ \"data\": 1}");
		final ResponseEntity<Void> post = post("/v1/diff/4/right", bytes);
		assertEquals(HttpStatus.OK, post.getStatusCode());

		final ResponseEntity<ErrorDTO> get = restTemplate.getForEntity("/v1/diff/4", ErrorDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, get.getStatusCode());

		final ErrorDTO errorDTO = get.getBody();
		assertNotNull(errorDTO);
		assertTrue(errorDTO.getMessage().contains("Left"));
	}

	@Test
	public void consultJustLeftSideData() {
		final byte[] bytes = encode("{ \"data\": 1}");
		final ResponseEntity<Void> post = post("/v1/diff/5/left", bytes);
		assertEquals(HttpStatus.OK, post.getStatusCode());

		final ResponseEntity<ErrorDTO> get = restTemplate.getForEntity("/v1/diff/5", ErrorDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, get.getStatusCode());

		final ErrorDTO errorDTO = get.getBody();
		assertNotNull(errorDTO);
		assertTrue(errorDTO.getMessage().contains("Right"));
	}

	@Test
	public void consultEquals() {
		final byte[] bytes = encode("{ \"data\": 1}");
		final ResponseEntity<Void> postLeft = post("/v1/diff/6/left", bytes);
		assertEquals(HttpStatus.OK, postLeft.getStatusCode());

		final ResponseEntity<Void> postRight = post("/v1/diff/6/right", bytes);
		assertEquals(HttpStatus.OK, postRight.getStatusCode());

		final ResponseEntity<RecordDiffResult> get = restTemplate.getForEntity("/v1/diff/6", RecordDiffResult.class);
		assertEquals(HttpStatus.OK, get.getStatusCode());

		final RecordDiffResult recordDiffResult = get.getBody();
		assertNotNull(recordDiffResult);
		assertEquals(DiffResult.EQUAL, recordDiffResult.getResult());
	}

	@Test
	public void consultNotEquals() {
		final ResponseEntity<Void> postLeft = post("/v1/diff/7/left", encode("{ \"data\": 1}"));
		assertEquals(HttpStatus.OK, postLeft.getStatusCode());

		final ResponseEntity<Void> postRight = post("/v1/diff/7/right", encode("{ \"data\": 2}"));
		assertEquals(HttpStatus.OK, postRight.getStatusCode());

		final ResponseEntity<RecordDiffResult> get = restTemplate.getForEntity("/v1/diff/7", RecordDiffResult.class);
		assertEquals(HttpStatus.OK, get.getStatusCode());

		final RecordDiffResult recordDiffResult = get.getBody();
		assertNotNull(recordDiffResult);
		assertEquals(DiffResult.NOT_EQUAL, recordDiffResult.getResult());
		assertEquals(1, recordDiffResult.getParts().size());
	}

	@Test
	public void consultDifferentLenght() {
		final ResponseEntity<Void> postLeft = post("/v1/diff/8/left", encode("{ \"datass\": 1}"));
		assertEquals(HttpStatus.OK, postLeft.getStatusCode());

		final ResponseEntity<Void> postRight = post("/v1/diff/8/right", encode("{ \"data\": 1}"));
		assertEquals(HttpStatus.OK, postRight.getStatusCode());

		final ResponseEntity<RecordDiffResult> get = restTemplate.getForEntity("/v1/diff/8", RecordDiffResult.class);
		assertEquals(HttpStatus.OK, get.getStatusCode());

		final RecordDiffResult recordDiffResult = get.getBody();
		assertNotNull(recordDiffResult);
		assertEquals(DiffResult.DIFFERENT_LENGTH, recordDiffResult.getResult());
	}

	private ResponseEntity<Void> post(String url, byte[] data) {
		return restTemplate.postForEntity(url, data, Void.class);
	}

}