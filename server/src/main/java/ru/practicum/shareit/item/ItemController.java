package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForCreate;

import java.util.List;

@RestController
@Validated
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDtoForCreate add(@RequestHeader("X-Sharer-User-Id") Long userId,
                                @RequestBody ItemDtoForCreate itemDtoForCreate) {
        return itemService.addNewItem(userId, itemDtoForCreate);
    }

    @PatchMapping("{itemId}")
    public ItemDtoForCreate patch(@PathVariable long itemId,
                                  @RequestHeader("X-Sharer-User-Id") Long userId,
                                  @RequestBody ItemDtoForCreate itemDtoForCreate) {
        return itemService.update(itemId, userId, itemDtoForCreate);
    }

    @GetMapping
    public List<ItemDto> getAllItemsOfOneUser(@RequestParam(required = false, defaultValue = "0")
                                              Integer from,
                                              @RequestParam(required = false, defaultValue = "20")
                                              Integer size,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItemsOfOneUser(userId, from, size);
    }

    @GetMapping("{itemId}")
    public ItemDto getItemById(@PathVariable long itemId,
                               @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return itemService.getById(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemDtoForCreate> search(@RequestParam(required = false, defaultValue = "0")
                                         Integer from,
                                         @RequestParam(required = false, defaultValue = "20")
                                         Integer size,
                                         @RequestParam(name = "text") String text) {
        return itemService.searchItem(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable long itemId,
                                 @RequestHeader(value = "X-Sharer-User-Id") Long userId,
                                 @RequestBody CommentDto commentDto) {
        return itemService.createComment(itemId, userId, commentDto);
    }
}


