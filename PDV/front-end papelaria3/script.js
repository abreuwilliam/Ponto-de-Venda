const form = document.querySelector('form');
const usuarioInput = document.querySelector('#usuario');
const senhaInput = document.querySelector('#senha');

form.addEventListener('submit', (event) => {
    event.preventDefault();
    
    const usuario = usuarioInput.value;
    const senha = senhaInput.value;
    
    // Enviar os dados para o backend via POST
    fetch('http://localhost:8080/user', {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: 'POST',
        body: JSON.stringify({
            usuario: usuario,
            senha: senha
        })
    })
    .then(response => {
        console.log("Status da resposta:", response.status);
        if (response.ok) {
            return response.text(); // Retorna o token como texto
        } else if (response.status === 401) {
            throw new Error('Usuário ou senha incorretos'); // Erro de autenticação
        } else {
            throw new Error('Erro inesperado no servidor');
        }
    })
    .then(token => {
        console.log('Resposta do servidor:', token);
    
        // Armazena o token no localStorage
        localStorage.setItem('authToken', token);
        console.log('Token armazenado após login:', localStorage.getItem('authToken'));
    
        // Redireciona para o menu
        window.location.href = "menu.html";
    })
    .catch(error => {
        console.error('Erro na autenticação:', error.message);
        alert(error.message);
    });
    
});