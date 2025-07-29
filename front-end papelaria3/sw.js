self.addEventListener('push', event => {
    console.log('[SW] 📬 Evento push recebido');

    if (!event.data) {
        console.warn('[SW] ⚠️ Nenhum dado recebido no evento push (event.data é null ou undefined)');
        return;
    }

    console.log('[SW] 🔍 Conteúdo bruto recebido do push:', event.data.text());

    event.waitUntil((async () => {
        let data;
        try {
            data = event.data.json();
            console.log('[SW] 📦 Dados da notificação (parsed JSON):', data);
        } catch (e) {
            console.error('[SW] ❌ Erro ao fazer parse do JSON da notificação:', e);
            data = {
                title: '🔔 Notificação (Fallback)',
                body: await event.data.text()
            };
            console.log('[SW] ⚠️ Usando fallback com texto bruto:', data.body);
        }

        try {
            const notificationOptions = {
                body: data.body || 'Você tem uma nova mensagem',
                icon: 'https://cdn-icons-png.flaticon.com/512/3144/3144460.png',
                badge: 'https://cdn-icons-png.flaticon.com/512/1827/1827392.png'
            };

            console.log('[SW] 🚀 Exibindo notificação com título:', data.title);
            await self.registration.showNotification(data.title || 'Nova Notificação', notificationOptions);
            console.log('[SW] ✅ Notificação exibida com sucesso.');
        } catch (error) {
            console.error('[SW] ❌ Erro ao exibir a notificação:', error);
        }
    })());
});
