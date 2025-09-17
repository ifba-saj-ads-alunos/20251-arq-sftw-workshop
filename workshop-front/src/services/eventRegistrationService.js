// Service to manage event registrations locally
const REGISTRATIONS_KEY = 'eventRegistrations';

export const eventRegistrationService = {
  // Get all registered events for current user
  getRegisteredEvents: () => {
    const registrations = localStorage.getItem(REGISTRATIONS_KEY);
    return registrations ? JSON.parse(registrations) : [];
  },

  // Check if user is registered for a specific event
  isRegisteredForEvent: (eventId) => {
    const registrations = eventRegistrationService.getRegisteredEvents();
    return registrations.some(reg => reg.eventId === eventId);
  },

  // Register user for an event
  registerForEvent: (event) => {
    const registrations = eventRegistrationService.getRegisteredEvents();
    const registration = {
      eventId: event.id,
      event: event,
      registeredAt: new Date().toISOString()
    };
    
    // Check if already registered
    if (!eventRegistrationService.isRegisteredForEvent(event.id)) {
      registrations.push(registration);
      localStorage.setItem(REGISTRATIONS_KEY, JSON.stringify(registrations));
    }
  },

  // Unregister from an event (if needed later)
  unregisterFromEvent: (eventId) => {
    const registrations = eventRegistrationService.getRegisteredEvents();
    const filteredRegistrations = registrations.filter(reg => reg.eventId !== eventId);
    localStorage.setItem(REGISTRATIONS_KEY, JSON.stringify(filteredRegistrations));
  }
};

export default eventRegistrationService;