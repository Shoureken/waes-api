package com.dematte.waestest.services;

import java.util.Optional;

import com.dematte.waestest.exception.InvalidData;
import com.dematte.waestest.exception.RecordNotFoundException;
import com.dematte.waestest.model.Side;
import com.dematte.waestest.model.entities.Record;
import com.dematte.waestest.repositories.RecordRepository;
import com.dematte.waestest.services.impl.RecordServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.dematte.waestest.Base64Encoder.encode;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTest {

	@Mock
	private RecordRepository repository;

	@InjectMocks
	private RecordServiceImpl recordService;

	@Before
	public void setUp() throws Exception {
		reset(repository);
	}

	@Test(expected = InvalidData.class)
	public void saveInvalidBase64() {
		final String id = "ID";
		final byte[] data = "{\"data\" : 10}".getBytes();
		recordService.save(id, Side.RIGHT, data);
	}

	@Test(expected = InvalidData.class)
	public void saveInvalidJson() {
		final String id = "ID";
		final byte[] data = encode("10");
		recordService.save(id, Side.RIGHT, data);
	}

	@Test
	public void saveRight() {
		final Record dummy = mock(Record.class);
		final ArgumentCaptor<Record> captor = ArgumentCaptor.forClass(Record.class);
		when(repository.save(captor.capture())).thenReturn(dummy);

		final String id = "ID";
		final String data = "{\"data\" : 10}";
		final byte[] dataEncoded = encode(data);

		final Record saved = recordService.save(id, Side.RIGHT, dataEncoded);
		assertSame(dummy, saved);

		final Record captured = captor.getValue();
		assertEquals(id, captured.getId());
		assertArrayEquals(data.getBytes(), captured.getRight());
		assertNull(captured.getLeft());
	}

	@Test
	public void saveLeft() {
		final Record dummy = mock(Record.class);
		final ArgumentCaptor<Record> captor = ArgumentCaptor.forClass(Record.class);
		when(repository.save(captor.capture())).thenReturn(dummy);

		final String id = "ID";
		final String data = "{\"data\" : 10}";
		final byte[] dataEncoded = encode(data);

		final Record saved = recordService.save(id, Side.LEFT, dataEncoded);
		assertSame(dummy, saved);

		final Record captured = captor.getValue();
		assertEquals(id, captured.getId());
		assertArrayEquals(data.getBytes(), captured.getLeft());
		assertNull(captured.getRight());
	}

	@Test
	public void get() {
		final Record dummy = new Record();
		when(repository.findById(anyString())).thenReturn(Optional.of(dummy));

		final Record saved = recordService.get("string");
		assertSame(dummy, saved);
	}

	@Test(expected = RecordNotFoundException.class)
	public void getNotFound() {
		when(repository.findById(anyString())).thenReturn(Optional.empty());
		recordService.get("string");
	}

}