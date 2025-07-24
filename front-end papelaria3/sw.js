self.addEventListener('push', event => {
    const data = event.data.json();
    self.registration.showNotification(data.title, {
        body: data.body,
        icon: 'https://cdn-icons-png.flaticon.com/512/3144/3144460.png',
        badge: 'https://cdn-icons-png.flaticon.com/512/1827/1827392.png'
    });
});
