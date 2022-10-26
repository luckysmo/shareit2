package ru.practicum.shareit.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDtoForCreate;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ItemRequestDtoWithItems {
    private Long id;
    private String description;
    private UserDto requester;
    private LocalDateTime created;
    private List<ItemDtoForCreate> items;
}
