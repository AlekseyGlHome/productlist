package ru.glushenko.productlist.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glushenko.productlist.api.request.AddToListRequest;
import ru.glushenko.productlist.model.dto.ListDto;
import ru.glushenko.productlist.service.ListService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Api(value = "ListController", tags = {"List Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/lists")
public class ListController {

    private final ListService listService;

    @ApiOperation(value = "All lists", response = ArrayList.class, tags = "List Controller")
    @GetMapping()
    public ResponseEntity<List<ListDto>> getAllList() {

        return ResponseEntity.ok(listService.findAll());
    }

    @ApiOperation(value = "Get list by id ", response = ListDto.class, tags = "List Controller")
    @GetMapping("/{id}")
    public ResponseEntity<ListDto> getListById(
            @ApiParam("Id for list search") @PathVariable long id) {

        return ResponseEntity.ok(listService.findById(id));
    }

    @ApiOperation(value = "Add new list", response = ListDto.class, tags = "List Controller")
    @PostMapping("/new")
    public ResponseEntity<ListDto> addList(@Valid @RequestBody ListDto listDto) {

        return ResponseEntity.ok(listService.add(listDto));
    }

    @ApiOperation(value = "Add product to list", response = ListDto.class, tags = "List Controller")
    @PostMapping("/addtolist")
    public ResponseEntity<ListDto> addProductToList(@RequestBody AddToListRequest addToListRequest) {

        return ResponseEntity.ok(listService.addProductToLit(addToListRequest.getListId(),
                addToListRequest.getProductId()));
    }
}

