package com.spring.deal.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.deal.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemDTO {
	
	private Long itemId;
		
	private String itemName;
	
	private String itemDescription;
	
	private int itemPrice;
	
	private Timestamp createdAt;
	
	private Timestamp updatedAt;
	
	private boolean success;
	
	private UserDTO user;
	
	private List<CommentDTO> comments = new ArrayList<>();
	
	public static ItemDTO EntitiyToDTO(Item item) {
		ItemDTO itemDTO = ItemDTO.builder()
				.itemId(item.getItemId())
				.itemName(item.getItemName())
				.itemDescription(item.getItemDescription())
				.createdAt(item.getCreatedAt())
				.updatedAt(item.getUpdatedAt())
				.success(item.isSuccess())
				.user(UserDTO.EntitiyToDTO(item.getUser()))
				.comments(item.getComments().stream()
						.map(comment -> CommentDTO.EntityToDTO(comment))
						.collect(Collectors.toList()))
				.build();
		return itemDTO;
	}
}
