package com.spring.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealDTO {
	

	private double buyUserScore;

	private double sellUserScore;
	
}
