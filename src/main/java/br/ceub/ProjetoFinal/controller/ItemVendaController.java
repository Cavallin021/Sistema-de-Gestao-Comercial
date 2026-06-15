package br.ceub.ProjetoFinal.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ceub.ProjetoFinal.model.ItemVenda;
import br.ceub.ProjetoFinal.service.ItemVendaService;

@RestController
@RequestMapping("/api/itensvenda")
public class ItemVendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    @PostMapping
    public ResponseEntity<ItemVenda> createItemVenda(@RequestBody ItemVenda itemVenda) {
        ItemVenda savedItemVenda = itemVendaService.save(itemVenda);
        return ResponseEntity.ok(savedItemVenda);
    }

    @GetMapping
    public ResponseEntity<List<ItemVenda>> getAllItemVenda() {
        List<ItemVenda> ItemVendas = itemVendaService.findAll();
        return ResponseEntity.ok(ItemVendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVenda> getItemVendaById(@PathVariable Integer id) {
        Optional<ItemVenda> ItemVenda = itemVendaService.findById(id);
        return ItemVenda.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemVenda>> searchItemVendas(@RequestParam String nome) {
        List<ItemVenda> ItemVendas = itemVendaService.findByNome(nome);
        return ResponseEntity.ok(ItemVendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVenda> updateItemVenda(@PathVariable Integer id, @RequestBody ItemVenda itemVendaDetails) {
        Optional<ItemVenda> optionalItemVenda = itemVendaService.findById(id);
        if (optionalItemVenda.isPresent()) {
            ItemVenda itemVenda = optionalItemVenda.get();
            itemVenda.setProduto(itemVendaDetails.getProduto());
            itemVenda.setQuantidade(itemVendaDetails.getQuantidade());
            itemVenda.setPrecoUnitario(itemVendaDetails.getPrecoUnitario());
            ItemVenda updatedItemVenda = itemVendaService.save(itemVenda);
            return ResponseEntity.ok(updatedItemVenda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemVenda(@PathVariable Integer id) {
        Optional<ItemVenda> itemVenda = itemVendaService.findById(id);
        if (itemVenda.isPresent()) {
            itemVendaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
