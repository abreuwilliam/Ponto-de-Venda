self.addEventListener('push', event => {
  console.log('[SW] 📬 Evento push recebido bruto:', event);

  event.waitUntil((async () => {
    let rawText = '';
    let data = {};

    try {
      if (event.data) {
        rawText = await event.data.text();
        console.log('[SW] 📦 Texto bruto recebido:', rawText);

        try {
          data = JSON.parse(rawText);
        } catch (e) {
          console.warn('[SW] ⚠️ JSON malformado, usando fallback.');
          data = {
            title: '🔔 Notificação de Teste',
            body: rawText || 'Sem conteúdo'
          };
        }
      } else {
        console.warn('[SW] ❌ Push recebido sem event.data — usando dados padrão.');
        data = {
          title: '📦 Estoque Baixo',
          body: 'Produto com Pouca quantidade no estoque.',
          icon: 'https://cdn-icons-png.flaticon.com/512/1827/1827392.png'
        };
      }

      const title = data.title || '🔔 Notificação';
      const body = data.body || 'Você recebeu uma nova notificação!';
      const icon = data.icon || 'https://cdn-icons-png.flaticon.com/512/1827/1827392.png';
      const badge = data.badge || icon;

      await self.registration.showNotification(title, {
        body,
        icon,
        badge
      });

      if ('BroadcastChannel' in self) {
        const bc = new BroadcastChannel('push-logs');
        bc.postMessage(`[SW] ✅ Notificação recebida: ${title}`);
      }

    } catch (e) {
      console.error('[SW] ❌ Erro inesperado no processamento do push:', e);
    }
  })());
});
