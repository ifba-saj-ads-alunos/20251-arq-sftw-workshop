import httpFactory from './httpFactory';

export async function fetchNotifications() {
  try {
    const res = await httpFactory.get('/api/v1/events/notifications');
    return res.data;
  } catch (err) {
    console.warn('Failed to fetch notifications, returning empty array:', err.message);
    return []; // Return empty array instead of throwing error
  }
}

export async function fetchUnreadCount() {
  try {
    const res = await httpFactory.get('/api/v1/events/notifications/unread-count');
    return res.data?.unread || 0;
  } catch (err) {
    console.warn('Failed to fetch unread count, returning 0:', err.message);
    return 0; // Return 0 instead of throwing error
  }
}

export async function markAsRead(notificationId) {
  await httpFactory.post(`/api/v1/events/notifications/${notificationId}/read`);
}

export async function markAllRead() {
  await httpFactory.post('/api/v1/events/notifications/mark-all-read');
}
