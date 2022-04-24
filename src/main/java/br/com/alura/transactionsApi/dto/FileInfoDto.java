package br.com.alura.transactionsApi.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import br.com.alura.transactionsApi.entity.FileInfo;
import lombok.Data;

@Data
public class FileInfoDto {

	@NotNull(message = "Field must not be null.")
	private String fileName;

	private Instant createdAt;

	public FileInfo toEntity() {
		FileInfo fiInfo = new FileInfo();
		BeanUtils.copyProperties(this, fiInfo);
		return fiInfo;

	}

}
