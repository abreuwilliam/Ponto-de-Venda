let total = 0;
let aberturacaixa = localStorage.getItem('aberturacaixa') || "caixafechado";
let totalVendido = parseFloat(localStorage.getItem('totalVendido')) || 0;
let vendasDiarias = JSON.parse(localStorage.getItem('vendasDiarias')) || [];
let produtosNaVenda = [];

function verificarEstadoCaixa() {
    const botoes = document.querySelectorAll('.controls button');
    botoes.forEach(botao => {
        if (botao.textContent === "Abrir Caixa") return;
        botao.disabled = (aberturacaixa === "caixafechado");
    });
}

function abrirCaixa() {
    if (aberturacaixa === "caixaaberto") {
        alert("O caixa já está aberto.");
        return;
    }

    alert("Caixa aberto!");
    aberturacaixa = "caixaaberto";
    totalVendido = 0;
    vendasDiarias = [];
    verificarEstadoCaixa();
    localStorage.setItem('aberturacaixa', aberturacaixa);
    localStorage.setItem('totalVendido', totalVendido);
    localStorage.setItem('vendasDiarias', JSON.stringify(vendasDiarias));

    document.getElementById('totalVendasDia').style.display = 'none';
    document.getElementById('total').textContent = '0.00';
    document.getElementById('troco').textContent = '0.00';
    document.getElementById('descricaoProduto').textContent = '';
    document.getElementById('codigoProduto').value = '';
    document.getElementById('historicoProdutos').innerHTML = '';
    document.getElementById('historicoVendas').innerHTML = '';

    setTimeout(() => location.reload(), 100);
}

function atualizarHistoricoVendas() {
    const historicoVendas = document.getElementById('historicoVendas');
    historicoVendas.innerHTML = '';
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
    document.getElementById('totalVendasDia').style.display = 'block';
    document.getElementById('valorTotalDia').textContent = totalVendido.toFixed(2);
    localStorage.setItem('aberturacaixa', aberturacaixa);
    atualizarHistoricoVendas();
}

let timeoutBuscaProduto;

// Comportamento para ambos dispositivos
document.getElementById('codigoProduto').addEventListener('input', () => {
    clearTimeout(timeoutBuscaProduto);
    timeoutBuscaProduto = setTimeout(() => {
        if (document.getElementById('codigoProduto').value.trim() !== "") {
            adicionarProduto();
        }
    }, 2000); // Aguarda 2 segundos após parar de digitar
});


document.getElementById('codigoProduto').addEventListener('keyup', (e) => {
    if (window.innerWidth <= 768 && e.key === 'Enter') {
        adicionarProduto();
    }
});

let ultimoProduto = {};
let ultimaQuantidade = 1;
document.getElementById('quantidadeProduto').addEventListener('input', () => {
    ultimaQuantidade = parseInt(document.getElementById('quantidadeProduto').value) || 1;
});

function adicionarQuantidade() {
    const quantidadeInput = document.getElementById('quantidadeProduto');
    let quantidadeAtual = parseInt(quantidadeInput.value) || 1;
    quantidadeInput.value = ++quantidadeAtual;
    ultimaQuantidade = quantidadeAtual;
}

function adicionarProduto() {
    if (aberturacaixa === "caixafechado") {
        alert("O caixa está fechado. Não é possível adicionar produtos.");
        return;
    }
    const codigoProduto = document.getElementById('codigoProduto').value;
    if (!codigoProduto.trim()) return;
    enviarDados(codigoProduto, ultimaQuantidade);
    document.getElementById('codigoProduto').value = '';
    document.getElementById('quantidadeProduto').value = 1;
    ultimaQuantidade = 1;
}

function enviarDados(codigoProduto, quantidade) {
    const token = localStorage.getItem('authToken');
    fetch('https://ponto-de-venda-1.onrender.com/produto/caixa', {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        method: 'POST',
        body: JSON.stringify({ codigo_Produto: codigoProduto })
    })
    .then(response => {
        if (!response.ok) throw new Error("Produto não encontrado ou erro no servidor.");
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

function restaurarEstadoVenda() {
    produtosNaVenda = JSON.parse(localStorage.getItem('produtosNaVenda')) || [];
    total = parseFloat(localStorage.getItem('total')) || 0;
    document.getElementById('total').textContent = total.toFixed(2);
    atualizarHistoricoProdutos();
}

window.addEventListener('beforeunload', salvarEstadoVenda);
window.addEventListener('load', () => {
    restaurarEstadoVenda();
    if (window.innerWidth <= 768) {
        document.getElementById('inputManual').style.display = 'none';
        document.getElementById('scanMobile').style.display = 'block';
    }
});

function calcularTroco() {
    const valorPago = parseFloat(document.getElementById('valorPago').value);
    const totalVenda = parseFloat(document.getElementById('total').textContent);
    if (!isNaN(valorPago) && valorPago >= totalVenda) {
        const troco = valorPago - totalVenda;
        document.getElementById('troco').textContent = troco.toFixed(2);
    } else {
        document.getElementById('troco').textContent = '0.00';
    }
    setTimeout(() => {
        document.getElementById('troco').textContent = '0.00';
    }, 5000);
}

document.getElementById('valorPago').addEventListener('input', calcularTroco);

function cancelarVenda() {
    if (confirm("Tem certeza de que deseja cancelar a venda atual?")) {
        // Subtrai os valores da venda atual do totalVendido
        produtosNaVenda.forEach(produto => {
            totalVendido -= produto.preco * produto.quantidade;
        });

        localStorage.setItem('totalVendido', totalVendido);

        produtosNaVenda = [];
        total = 0;

        document.getElementById('total').textContent = '0.00';
        document.getElementById('troco').textContent = '0.00';
        document.getElementById('descricaoProduto').textContent = '';
        document.getElementById('historicoProdutos').innerHTML = '';
        alert("Venda cancelada com sucesso!");
    }
}

function removerProduto(index) {
    const produtoRemovido = produtosNaVenda.splice(index, 1)[0];
    const valorRemovido = produtoRemovido.preco * produtoRemovido.quantidade;
    total -= valorRemovido;
    totalVendido -= valorRemovido;
    localStorage.setItem('totalVendido', totalVendido);
    document.getElementById('total').textContent = total.toFixed(2);
    atualizarHistoricoProdutos();
}

function atualizarHistoricoProdutos() {
    const historicoProdutos = document.getElementById('historicoProdutos');
    historicoProdutos.innerHTML = '';
    produtosNaVenda.forEach((produto, index) => {
        const li = document.createElement('li');
        li.textContent = `${produto.descricao} - Preço Unitário: R$ ${produto.preco.toFixed(2)} - Quantidade: ${produto.quantidade} - Total: R$ ${(produto.preco * produto.quantidade).toFixed(2)}`;
        const btnRemover = document.createElement('button');
        btnRemover.textContent = "Remover";
        btnRemover.onclick = () => removerProduto(index);
        li.appendChild(btnRemover);
        historicoProdutos.appendChild(li);
    });
}

function finalizarVenda() {
    const token = localStorage.getItem('authToken');
    if (aberturacaixa === "caixaaberto") {
        alert(`Venda finalizada! Total: R$ ${total.toFixed(2)}`);
        vendasDiarias.push(total);
        total = 0;
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
        .then(data => console.log('Resposta do servidor:', data))
        .catch(error => console.error('Erro ao enviar produtos:', error));

        produtosNaVenda = [];
        localStorage.setItem('vendasDiarias', JSON.stringify(vendasDiarias));
        document.getElementById('total').textContent = '0.00';
        document.getElementById('troco').textContent = '0.00';
        document.getElementById('descricaoProduto').textContent = '';
        document.getElementById('codigoProduto').value = '';
        document.getElementById('historicoProdutos').innerHTML = '';
    } else {
        alert("O caixa está fechado. Não é possível finalizar a venda.");
    }
}
