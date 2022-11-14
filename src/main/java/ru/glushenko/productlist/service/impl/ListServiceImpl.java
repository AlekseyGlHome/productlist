package ru.glushenko.productlist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.glushenko.productlist.Exception.DuplicateElement;
import ru.glushenko.productlist.Exception.ElementNotFound;
import ru.glushenko.productlist.model.dto.ListDto;
import ru.glushenko.productlist.model.entity.ListEntity;
import ru.glushenko.productlist.model.entity.ProductEntity;
import ru.glushenko.productlist.repository.ListRepository;
import ru.glushenko.productlist.repository.ProductRepository;
import ru.glushenko.productlist.service.ListService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListServiceImpl implements ListService {

    private final ListRepository listRepository;
    private final ProductRepository productRepository;

    @Override
    public ListDto findById(long id) {
        Optional<ListEntity> listEntity = listRepository.findById(id);
        return ListDto.getListDto(listEntity.orElseThrow(() -> new ElementNotFound("List with id= " + id + " not found")));
    }

    @Override
    public List<ListDto> findAll() {
        return listRepository.findAll().stream().map(ListDto::getListDto).collect(Collectors.toList());
    }

    @Override
    public ListDto add(ListDto listDto) {
        ListEntity listEntity = createList(listDto);
        listDto.setId(listEntity.getId());
        return listDto;
    }

    @Override
    public ListDto addProductToLit(long listId, long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ElementNotFound("Product with id= " + productId + " not found"));

        ListEntity listEntity = listRepository.findById(listId).orElseThrow(() -> new ElementNotFound("List with id= " + listId + " not found"));
        if (listEntity.getProducts().contains(product)) {
            throw new DuplicateElement("Product already added");
        }

        listEntity.getProducts().add(product);
        listRepository.save(listEntity);
        return ListDto.getListDto(listEntity);
    }

    private ListEntity createList(ListDto listDto) {
        ListEntity listEntity = new ListEntity();
        listEntity.setName(listDto.getName());
        listEntity = listRepository.save(listEntity);

        return listEntity;
    }
}
