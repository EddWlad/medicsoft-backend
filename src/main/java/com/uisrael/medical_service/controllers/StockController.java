package com.uisrael.medical_service.controllers;


import com.uisrael.medical_service.dtos.StockDTO;
import com.uisrael.medical_service.entities.Stock;
import com.uisrael.medical_service.services.IStockService;
import com.uisrael.medical_service.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("stocks")
@RequiredArgsConstructor
public class StockController {
    private final IStockService stockService;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<StockDTO>> findAll() throws Exception{
        List<StockDTO> list = mapperUtil.mapList(stockService.findAll(), StockDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> findById(@PathVariable("id") UUID id) throws Exception{
        StockDTO StockDTO = mapperUtil.map(stockService.findById(id), StockDTO.class);
        return ResponseEntity.ok(StockDTO);
    }

    @PostMapping
    public ResponseEntity<StockDTO> save(@RequestBody StockDTO StockDTO) throws Exception{
        Stock obj = stockService.save(mapperUtil.map(StockDTO, Stock.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getIdStock())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDTO> update(@PathVariable("id") UUID id, @RequestBody StockDTO StockDTO) throws Exception{
        Stock obj = stockService.update(mapperUtil.map(StockDTO, Stock.class), id);
        return ResponseEntity.ok(mapperUtil.map(obj, StockDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) throws Exception{
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
