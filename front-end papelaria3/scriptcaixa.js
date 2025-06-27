let total = 0;
let aberturacaixa = localStorage.getItem('aberturacaixa') || "caixafechado";
let totalVendido = parseFloat(localStorage.getItem('totalVendido')) || 0;
let vendasDiarias = JSON.parse(localStorage.getItem('vendasDiarias')) || [];
let produtosNaVenda = []; // Lista para os produtos na venda atual

// Função para verificar se o caixa está fechado e desativar botões
function verificarEstadoCaixa() {
    const botoes = document.querySelectorAll('.controls button');

    botoes.forEach(botao => {
        if (botao.textContent === "Abrir Caixa") return;
        botao.disabled = (aberturacaixa === "caixafechado");
    });
}

// Função para abrir o caixa

function abrirCaixa() {
    if (aberturacaixa === "caixaaberto") {
        alert("O caixa já está aberto.");
        return;
    }

    alert("Caixa aberto!");
    aberturacaixa = "caixaaberto";
    totalVendido = 0; // Reseta o total vendido quando o caixa é aberto
    vendasDiarias = []; // Limpa o histórico de vendas ao abrir o caixa
    verificarEstadoCaixa();

    // Atualiza o localStorage para o novo dia
    localStorage.setItem('aberturacaixa', aberturacaixa);
    localStorage.setItem('totalVendido', totalVendido);
    localStorage.setItem('vendasDiarias', JSON.stringify(vendasDiarias));

    // Esconde o total de vendas diárias ao abrir o caixa
    document.getElementById('totalVendasDia').style.display = 'none';

    // Limpa a interface e zera os valores mostrados
    document.getElementById('total').textContent = '0.00';
    document.getElementById('troco').textContent = '0.00';
    document.getElementById('descricaoProduto').textContent = '';
    document.getElementById('codigoProduto').value = '';
    document.getElementById('historicoProdutos').innerHTML = '';
    document.getElementById('historicoVendas').innerHTML = ''; // Limpa o histórico de vendas na interface

    // Recarrega a página para aplicar todas as mudanças
    setTimeout(() => {
        location.reload();
    }, 100); // 100 ms de atraso
}
function atualizarHistoricoVendas() {
    const historicoVendas = document.getElementById('historicoVendas');
    historicoVendas.innerHTML = ''; // Limpa o histórico antes de atualizar

    vendasDiarias.forEach((venda, index) => {
        const li = document.createElement('li');
        li.textContent = `Venda ${index + 1}: R$ ${venda.toFixed(2)}`;

        historicoVendas.appendChild(li);
    });
}

function fecharCaixa() {
    alert(`Caixa fechado! Total vendido no dia: R$ ${totalVendido.toFixed(2)}`);
    aberturacaixa = "caixafechado";
    verificarEstadoCaixa();
// Função para fechar o caixa

    // Atualiza o total de vendas do dia na tela
    const totalVendasDiaElement = document.getElementById('totalVendasDia');
    totalVendasDiaElement.style.display = 'block';
    document.getElementById('valorTotalDia').textContent = totalVendido.toFixed(2);


    // Atualiza o localStorage
    localStorage.setItem('aberturacaixa', aberturacaixa);

    // Atualiza o histórico de vendas na interface
    atualizarHistoricoVendas();
}

// Função para adicionar produto
let timeoutBuscaProduto;

// Função para monitorar o campo e iniciar a busca após pausa na digitação
document.getElementById('codigoProduto').addEventListener('input', () => {
    clearTimeout(timeoutBuscaProduto); // Limpa o timer anterior
    timeoutBuscaProduto = setTimeout(adicionarProduto, 2000); // Inicia um novo timer de 2 segundos
});

let ultimoProduto = {}; // Objeto para armazenar os dados do último produto adicionado
let ultimaQuantidade = 1;  // Variável para armazenar a última quantidade

// Atualiza 'ultimaQuantidade' toda vez que o valor no campo 'quantidadeProduto' muda
document.getElementById('quantidadeProduto').addEventListener('input', () => {
    ultimaQuantidade = parseInt(document.getElementById('quantidadeProduto').value) || 1;
});

// Função para adicionar quantidade ao clicar no botão "Adicionar Quantidade"
function adicionarQuantidade() {
    const quantidadeInput = document.getElementById('quantidadeProduto');
    let quantidadeAtual = parseInt(quantidadeInput.value) || 1;
    quantidadeAtual++;  // Incrementa a quantidade
    quantidadeInput.value = quantidadeAtual;  // Atualiza o campo com o novo valor
    ultimaQuantidade = quantidadeAtual;  // Atualiza a última quantidade
}

// Função para adicionar produto
function adicionarProduto() {
    if (aberturacaixa === "caixafechado") {
        alert("O caixa está fechado. Não é possível adicionar produtos.");
        return;
    }

    const codigoProduto = document.getElementById('codigoProduto').value;

    if (codigoProduto.trim() === "") {
        return;
    }

    // Usa 'ultimaQuantidade' ao invés do valor atual do campo de quantidade
    enviarDados(codigoProduto, ultimaQuantidade);

    // Limpa os campos para permitir nova entrada
    document.getElementById('codigoProduto').value = '';
    document.getElementById('quantidadeProduto').value = 1;  // Reseta para 1
    ultimaQuantidade = 1;  // Reseta a última quantidade para 1
}

// Função para enviar os dados
function enviarDados(codigoProduto, quantidade) {
    const token = localStorage.getItem('authToken');
    fetch('https://ponto-de-venda-1.onrender.com/produto/caixa', {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        method: 'POST',
        body: JSON.stringify({
            codigo_Produto: codigoProduto
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Produto não encontrado ou erro no servidor.");
        }
        return response.json();
    })
    .then(data => {
        const descricao = data.descricao;
        const precoProduto = parseFloat(data.preco);

        if (descricao && !isNaN(precoProduto)) {
            const valorTotalProduto = precoProduto * quantidade;
            total += valorTotalProduto;
            totalVendido += valorTotalProduto;

            ultimoProduto = { descricao, preco: precoProduto };
            produtosNaVenda.push({ descricao, preco: precoProduto, quantidade });
            atualizarHistoricoProdutos();

            localStorage.setItem('totalVendido', totalVendido);

            document.getElementById('total').textContent = total.toFixed(2);
            document.getElementById('descricaoProduto').textContent = `${descricao} - Quantidade: ${quantidade} - Total: R$ ${valorTotalProduto.toFixed(2)}`;
        } else {
            alert("Produto não encontrado ou dados inválidos.");
        }
    })
    .catch(error => {
        console.error('Erro ao enviar dados:', error);
        alert("Erro ao buscar produto.");
    });
}




function salvarEstadoVenda() {
    localStorage.setItem('produtosNaVenda', JSON.stringify(produtosNaVenda));
    localStorage.setItem('total', total);
}


// Função para restaurar o estado da venda atual ao carregar a página
function restaurarEstadoVenda() {
    produtosNaVenda = JSON.parse(localStorage.getItem('produtosNaVenda')) || [];
    total = parseFloat(localStorage.getItem('total')) || 0;
    document.getElementById('total').textContent = total.toFixed(2);
    atualizarHistoricoProdutos();
}

// Chame `salvarEstadoVenda` sempre que adicionar ou remover um produto
window.addEventListener('beforeunload', salvarEstadoVenda);

// Restaura a venda atual ao carregar a página
window.addEventListener('load', restaurarEstadoVenda);
function calcularTroco() {
    const valorPago = parseFloat(document.getElementById('valorPago').value);
    const totalVenda = parseFloat(document.getElementById('total').textContent);

    if (!isNaN(valorPago) && valorPago >= totalVenda) {
        const troco = valorPago - totalVenda;
        document.getElementById('troco').textContent = troco.toFixed(2);
    } else {
        document.getElementById('troco').textContent = '0.00';
    }

    // Limpa o troco após 10 segundos
    setTimeout(() => {
        document.getElementById('troco').textContent = '0.00';
    }, 5000); // 10000 milissegundos = 10 segundos
}

// Função para cancelar a venda atual
function cancelarVenda() {
    if (confirm("Tem certeza de que deseja cancelar a venda atual?")) {
        produtosNaVenda = []; // Limpa a lista de produtos
        total = 0; // Reseta o total da venda
        document.getElementById('total').textContent = '0.00';
        document.getElementById('troco').textContent = '0.00';
        document.getElementById('descricaoProduto').textContent = '';
        document.getElementById('historicoProdutos').innerHTML = '';
        alert("Venda cancelada com sucesso!");
    }
}

function removerProduto(index) {
    const produtoRemovido = produtosNaVenda.splice(index, 1)[0];
    
    // Calcula o valor total do produto removido (preço unitário * quantidade)
    const valorRemovido = produtoRemovido.preco * produtoRemovido.quantidade;

    // Atualiza o total e o total vendido subtraindo o valor total do produto removido
    total -= valorRemovido;
    totalVendido -= valorRemovido;

    // Atualiza o localStorage com o novo total vendido
    localStorage.setItem('totalVendido', totalVendido);

    // Atualiza a exibição do total e o histórico de produtos na interface
    document.getElementById('total').textContent = total.toFixed(2);
    atualizarHistoricoProdutos();
}

// Função para atualizar o histórico de produtos (sem enviar para o servidor)
function atualizarHistoricoProdutos() {
    const historicoProdutos = document.getElementById('historicoProdutos');
    historicoProdutos.innerHTML = '';  // Limpar o histórico antes de atualizar

    produtosNaVenda.forEach((produto, index) => {
        // Criando o item de lista para o produto
        const li = document.createElement('li');

        // Adicionando as informações do produto (descricao, preco unitário, quantidade e preço total)
        li.textContent = `${produto.descricao} - Preço Unitário: R$ ${produto.preco.toFixed(2)} - Quantidade: ${produto.quantidade} - Total: R$ ${(produto.preco * produto.quantidade).toFixed(2)}`;

        // Criar o botão de remoção
        const btnRemover = document.createElement('button');
        btnRemover.textContent = "Remover";
        btnRemover.onclick = () => removerProduto(index);

        // Adiciona o botão ao item da lista
        li.appendChild(btnRemover);

        // Adiciona o item no histórico
        historicoProdutos.appendChild(li);
    });
}
// Função para finalizar a venda e enviar lista de produtos para o servidor
// Evento para calcular o troco automaticamente ao inserir o valor pago
document.getElementById('valorPago').addEventListener('input', calcularTroco);

// Função para finalizar a venda e enviar lista de produtos para o servidor
function finalizarVenda() {
    const token = localStorage.getItem('authToken');
    if (aberturacaixa === "caixaaberto") {
        alert(`Venda finalizada! Total: R$ ${total.toFixed(2)}`);
        vendasDiarias.push(total); // Adiciona o total da venda ao histórico
        total = 0; // Reseta o total para a próxima venda
        console.log(produtosNaVenda);

        // Envia todos os produtos da venda atual
        fetch('https://ponto-de-venda-1.onrender.com/produto/baixa', {
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            method: 'POST',
            body: JSON.stringify(produtosNaVenda)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Resposta do servidor:', data);
        })
        .catch(error => {
            console.error('Erro ao enviar produtos:', error);
        });

        produtosNaVenda = []; // Limpa a lista de produtos na venda atual

        // Atualiza o localStorage
        localStorage.setItem('vendasDiarias', JSON.stringify(vendasDiarias));

        // Limpa a interface
        document.getElementById('total').textContent = '0.00';
        document.getElementById('troco').textContent = '0.00';
        document.getElementById('descricaoProduto').textContent = '';
        document.getElementById('codigoProduto').value = '';
        document.getElementById('historicoProdutos').innerHTML = '';
        document.getElementById('troco').textContent = '0.00';

        // Chama calcularTroco para garantir que o troco seja atualizado
        
    } else {
        alert("O caixa está fechado. Não é possível finalizar a venda.");
    }
}

// Outras funções continuam as mesmas...
