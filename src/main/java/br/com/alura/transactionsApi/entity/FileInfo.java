package br.com.alura.transactionsApi.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "file")
public class FileInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;
	@Column(name = "file_name")
	private String fileName;
	@CreationTimestamp
	private Instant createdAt;

}
