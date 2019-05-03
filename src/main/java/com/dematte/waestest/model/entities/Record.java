package com.dematte.waestest.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity that represents the "record" table, holding that data to be diff-ed
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Record {

	@Id
	private String id;
	private byte[] left;
	private byte[] right;

}
