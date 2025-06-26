package com.pdv.papelaria.controller;


import com.pdv.papelaria.dto.PedidosDto;
import com.pdv.papelaria.service.PedidosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    PedidosService pedidosService;

    @Operation(summary = "cadastrat pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processado com sucesso")
    })
    @PostMapping
    public ResponseEntity<String> CadastrarPedido(@RequestBody PedidosDto pedidos) {
        try {
            pedidosService.salvarEstoque(pedidos);

            if (pedidos.getPedidos() != null) {
                return ResponseEntity.ok("Salvo com Sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pedidos vazios.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o estoque: " + e.getMessage());
        }
    }

    @Operation(summary = "Deletar Pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processado com sucesso")
    })
    @DeleteMapping
    public ResponseEntity<String> deletarTodosPedidos() {
        try {
            pedidosService.deletarTodosPedidos();
            return ResponseEntity.ok("Todos os pedidos foram deletados com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar os pedidos: " + e.getMessage());
        }
    }
}
