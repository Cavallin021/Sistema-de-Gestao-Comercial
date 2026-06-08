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

import br.ceub.ProjetoFinal.model.Produto;
import br.ceub.ProjetoFinal.model.Venda;
import br.ceub.ProjetoFinal.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

@Autowired
	private VendaService vendaService;

	@PostMapping
	public ResponseEntity<Venda> createVenda(@RequestBody Venda venda) {
		Venda savedVenda = vendaService.save(venda);
		return ResponseEntity.ok(savedVenda);
	}

	@GetMapping
	public ResponseEntity<List<Venda>> getAllVenda() {
		List<Venda> Vendas = vendaService.findAll();
		return ResponseEntity.ok(Vendas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Venda> getVendaById(@PathVariable Integer id) {
		Optional<Venda> Venda = vendaService.findById(id);
		return Venda.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<Venda>> searchVendas(@RequestParam String nome) {
        List<Venda> Vendas = vendaService.findByNome(nome);
        return ResponseEntity.ok(Vendas);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Venda> updateVenda(@PathVariable Integer id, @RequestBody Venda vendaDetails) {
        Optional<Venda>optionalVenda = vendaService.findById(id);
        if (optionalVenda.isPresent()) {
            Venda venda = optionalVenda.get();
            venda.setData(vendaDetails.getData());
            venda.setValorTotal(vendaDetails.getValorTotal());
            venda.setClienteId(vendaDetails.getClienteId());
            venda.setUsuarioId(vendaDetails.getUsuarioId());
            venda.setQuantidade(vendaDetails.getQuantidade());
            Venda updatedVenda = vendaService.save(venda);
            return ResponseEntity.ok(updatedVenda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteVenda(@PathVariable Integer id) {
	        Optional<Venda> venda = vendaService.findById(id);
	        if (venda.isPresent()) {
	            vendaService.deleteById(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }


}
