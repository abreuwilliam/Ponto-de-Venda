document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const usuarioInput = document.querySelector('#usuario');
    const senhaInput = document.querySelector('#senha');
    const loader = document.getElementById('loader');

    form.addEventListener('submit', (event) => {
        event.preventDefault();

        const usuario = usuarioInput.value;
        const senha = senhaInput.value;

        // Mostrar o loader
        loader.style.display = 'flex';

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
            if (response.ok) {
                return response.json();
            } else {
                // Silenciosamente ignora o erro
                return;
            }
        })
        .then(data => {
            loader.style.display = 'none';

            if (data && data.token && data.role) {
                localStorage.setItem('authToken', data.token);
                if (data.role === 'ROLE_ADMIN') {
                    window.location.href = "menu.html";
                } else {
                    window.location.href = "menu2.html";
                }
            }
            // Não faz nada se `data` for inválido
        })
        .catch(() => {
            loader.style.display = 'none';
            // Erro ignorado silenciosamente
        });
    });
});
