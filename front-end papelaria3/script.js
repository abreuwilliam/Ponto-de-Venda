document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const usuarioInput = document.querySelector('#usuario');
    const senhaInput = document.querySelector('#senha');

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        
        const usuario = usuarioInput.value;
        const senha = senhaInput.value;
        
        // Enviar os dados para o backend via POST
        fetch('https://ponto-de-venda-1.onrender.com/auth/login', {
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            method: 'POST',
            body: JSON.stringify({
                username: usuario,
                password: senha
            })
        })
        .then(response => {
            console.log("Status da resposta:", response.status);
            if (response.ok) {
                return response.json(); // Processar como JSON
            } else if (response.status === 401) {
                throw new Error('Usuário ou senha incorretos'); // Erro de autenticação
            } else {
                throw new Error('Erro inesperado no servidor');
            }
        })
        .then(data => {
            console.log('Resposta do servidor:', data);
            
            if (data.token && data.role) {
                // Armazena o token no localStorage
                localStorage.setItem('authToken', data.token);
                console.log('Token armazenado após login:', localStorage.getItem('authToken'));
                
                // Redireciona com base no `role`
                if (data.role === 'ROLE_ADMIN') {
                    window.location.href = "menu.html";
                } else {
                    window.location.href = "menu2.html";
                }
            } else {
                throw new Error('Dados incompletos na resposta do servidor');
            }
        })
        .catch(error => {
            console.error('Erro na autenticação:', error.message);
            alert(error.message);
        });
    });
});
