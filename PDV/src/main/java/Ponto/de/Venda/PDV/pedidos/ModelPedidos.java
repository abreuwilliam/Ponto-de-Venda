package Ponto.de.Venda.PDV.pedidos;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class ModelPedidos { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pedidos")
    private String pedidos; 

    // Getters e setters
    public String getpedidos() {
        return pedidos;
    }

    public void setPedidos(String pedidos) {
        this.pedidos = pedidos;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    
}
 
