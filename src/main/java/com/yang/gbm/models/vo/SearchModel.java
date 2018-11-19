package com.yang.gbm.models.vo;

import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchModel {
	private String key;
	private int pageSize;
	private int pageNo;
	private Direction order;
	private String sortColumn;
}
