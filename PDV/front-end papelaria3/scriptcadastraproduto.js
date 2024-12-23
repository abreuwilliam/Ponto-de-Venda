let produtos = [];
let editando = false;
let indiceEdicao = null;

// Função para adicionar ou editar um produto
function salvarProduto() {
    const id = document.getElementById("produtoId").value;
    const codigo = document.getElementById("codigo").value;
    const nome = document.getElementById("nome").value;
    const preco = document.getElementById("preco").value;
    const estoque = document.getElementById("estoque").value;

    if (editando) {
        // Atualiza o produto existente
        produtos[indiceEdicao] = { codigo, nome, preco, estoque };
        editando = false;
        indiceEdicao = null;
        document.getElementById("formTitle").innerText = "Adicionar Produto";
    } else {
        // Adiciona um novo produto
        produtos.push({ codigo, nome, preco, estoque });
    }
    enviarDados({ codigo, nome, preco, estoque }); // Passa os dados para enviar
    limparFormulario();
    atualizarTabela();
}

// Função para limpar o formulário
function limparFormulario() {
    document.getElementById("produtoForm").reset();
    document.getElementById("produtoId").value = "";
}



// Função para atualizar a tabela de produtos
function atualizarTabela() {
    const tabela = document.getElementById("tabelaProdutos").getElementsByTagName("tbody")[0];
    tabela.innerHTML = "";

    produtos.forEach((produto, index) => {
        let linha = tabela.insertRow();
        linha.insertCell(0).innerText = produto.codigo;
        linha.insertCell(1).innerText = produto.nome;
        linha.insertCell(2).innerText = produto.preco;
        linha.insertCell(3).innerText = produto.estoque;

      
    });
}

// Função para enviar os dados para o servidor
function enviarDados(produto) {
    const token = localStorage.getItem('authToken');
    fetch('http://localhost:8080/cadastro', {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
             "Authorization": `Bearer ${token}`
        },
        method: 'POST',
        body: JSON.stringify({
            codigo_Produto: produto.codigo,
            produto: produto.nome,
            preco: produto.preco,
            quantidade_Estoque: produto.estoque
        })
    })
    .then(response => response.text()) // Espera uma resposta de texto
    .then(data => {
        console.log('Resposta do servidor:', data);
    })
    .catch(error => {
        console.error('Erro ao enviar os dados:', error);
    });
}
