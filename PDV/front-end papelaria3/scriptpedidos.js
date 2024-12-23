let pedidos = [];

// Função para registrar o pedido
function registrarPedido() {
    const descricao = document.getElementById("descricaoPedido").value;

    if (descricao.trim() === "") {
        alert("Por favor, insira uma descrição válida.");
        return; // Corrigido para retornar corretamente
    }

    pedidos.push(descricao);
    atualizarListaPedidos();
    enviarDados(descricao); // Envia o pedido registrado
    limparFormulario();
}

// Função para limpar o formulário após registrar um pedido
function limparFormulario() {
    document.getElementById("pedidoForm").reset();
}

// Função para atualizar a lista de pedidos registrados
function atualizarListaPedidos() {
    const lista = document.getElementById("listaPedidos");
    lista.innerHTML = ""; // Limpa a lista existente

    pedidos.forEach((pedido) => {
        let li = document.createElement("li");
        li.innerText = pedido; // Mostra a descrição do pedido
        lista.appendChild(li); // Adiciona cada pedido à lista
    });
}

// Função para enviar dados para a API
function enviarDados(descricao) {
    const dados = JSON.stringify({ pedidos: descricao });
    console.log('Dados enviados:', dados); // Para ver o JSON no console
    const token = localStorage.getItem('authToken');
    fetch('http://localhost:8080/pedidos', {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
             "Authorization": `Bearer ${token}`
        },
        method: 'POST',
        body: dados
    })
    .then(response => response.text())
    .then(data => {
        console.log('Resposta do servidor:', data);
    })
    .catch(error => {
        console.error('Erro ao enviar os dados:', error);
    });
}

// Função para listar pedidos da API
function listarPedidos() {
    const token = localStorage.getItem('authToken'); // Recupera o token JWT do localStorage

    fetch('http://localhost:8080/pedidos', {
        method: 'GET',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` // Adiciona o token no cabeçalho da requisição
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na rede ao buscar os pedidos.');
        }
        return response.json();
    })
    .then(data => {
        const pedidos = data.map(pedido => pedido.pedidos); // Extrai apenas as descrições dos pedidos
        console.log(pedidos); // Mostra a lista de pedidos no console
        atualizarListaPedidos(); // Atualiza a lista de pedidos na interface
    })
    .catch(error => {
        console.error('Erro ao listar os pedidos:', error);
    });
}
// Função para deletar todos os pedidos
function deletarTodosPedidos() {
    const token = localStorage.getItem('authToken');
    fetch('http://localhost:8080/pedidos', {
        method: 'DELETE',
        "Authorization": `Bearer ${token}`
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao deletar todos os pedidos.');
        }
        pedidos = []; // Limpa a lista de pedidos localmente
        atualizarListaPedidos(); // Atualiza a lista vazia na interface
        console.log('Todos os pedidos foram deletados com sucesso.');
    })
    .catch(error => {
        console.error('Erro ao deletar os pedidos:', error);
    });
}

// Chama a função listarPedidos quando a página é carregada
window.onload = listarPedidos;
