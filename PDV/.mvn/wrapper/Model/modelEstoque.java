
    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.Table;
public class modelEstoque {
    
    
    @Entity
    @Table(name = "estoque")
    public class ModelEstoque {
    
        
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
    
    
    
        @Column(name = "codigo_Produto")
        private Long codigo_Produto;
    
        @Column(name = "produto")
        private String produto;
    
        @Column(name = "preco")
        private double preco;
    
        @Column(name = "quantidade_Estoque")
        private int quantidade_Estoque;
    
        // Getters e setters
        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public Long getCodigo_Produto() {
            return codigo_Produto;
        }
    
        public void setCodigo_Produto(Long codigo_Produto) {
            this.codigo_Produto = codigo_Produto;
        }
    
        public String getProduto() {
            return produto;
        }
    
        public void setProduto(String produto) {
            this.produto = produto;
        }
    
        public double getPreco() {
            return preco;
        }
    
        public void setPreco(double preco) {
            this.preco = preco;
        }
    
        public int getQuantidade_Estoque() {
            return quantidade_Estoque;
        }
    
        public void setQuantidade_Estoque(int quantidade_Estoque) {
            this.quantidade_Estoque = quantidade_Estoque;
        }
    }
     
}
