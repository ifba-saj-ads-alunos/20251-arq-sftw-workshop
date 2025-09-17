import httpFactory from './httpFactory';

export async function fetchNotifications() {
  const res = await httpFactory.get('/api/v1/events/notifications');
  return res.data;
}

export async function fetchUnreadCount() {
  const res = await httpFactory.get('/api/v1/events/notifications/unread-count');
  return res.data?.unread || 0;
}

export async function markAsRead(notificationId) {
  await httpFactory.post(`/api/v1/events/notifications/${notificationId}/read`);
}

export async function markAllRead() {
  await httpFactory.post('/api/v1/events/notifications/mark-all-read');
}
