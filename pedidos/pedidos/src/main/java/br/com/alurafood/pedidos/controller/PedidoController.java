package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.ItemDoPedidoDto;
import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.model.ItemDoPedido;
import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;
import br.com.alurafood.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {


        // teste commit
        @Autowired
        private PedidoService service;

        @GetMapping()
        public List<PedidoDto> listarTodos() {
            return service.obterTodos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<PedidoDto> listarPorId(@PathVariable @NotNull Long id) {
            PedidoDto dto = service.obterPorId(id);

            return  ResponseEntity.ok(dto);
        }

        @GetMapping("/porta")
        public String retornaPorta(@Value("${local.server.port}") String porta){
            return String.format("requisicao respondida pela instancia na porta %s", porta);
        }

        @PostMapping()
        public ResponseEntity<PedidoDto> realizaPedido(@RequestBody @Valid PedidoDto dto, UriComponentsBuilder uriBuilder) {
            PedidoDto pedidoRealizado = service.criarPedido(dto);

            URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(pedidoRealizado);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<PedidoDto> atualizaStatus(@PathVariable Long id, @RequestBody StatusDto status){
           PedidoDto dto = service.atualizaStatus(id, status);

            return ResponseEntity.ok(dto);
        }

        @PutMapping("/{id}/pago")
        public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id) {
            service.aprovaPagamentoPedido(id);

            return ResponseEntity.ok().build();

        }
}
