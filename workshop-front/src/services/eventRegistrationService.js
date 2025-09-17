// Service to handle event registrations using localStorage
class EventRegistrationService {
  constructor() {
    this.STORAGE_KEY = 'userEventRegistrations';
  }

  // Get all registrations for current user
  getUserRegistrations(userId) {
    const allRegistrations = this.getAllRegistrations();
    return allRegistrations[userId] || [];
  }

  // Get all registrations from localStorage
  getAllRegistrations() {
    try {
      const data = localStorage.getItem(this.STORAGE_KEY);
      return data ? JSON.parse(data) : {};
    } catch (error) {
      console.error('Error reading registrations:', error);
      return {};
    }
  }

  // Save all registrations to localStorage
  saveAllRegistrations(registrations) {
    try {
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(registrations));
      return true;
    } catch (error) {
      console.error('Error saving registrations:', error);
      return false;
    }
  }

  // Register user for an event
  registerForEvent(userId, event) {
    const allRegistrations = this.getAllRegistrations();
    
    if (!allRegistrations[userId]) {
      allRegistrations[userId] = [];
    }

    // Check if already registered
    const alreadyRegistered = allRegistrations[userId].some(
      registration => registration.eventId === event.id
    );

    if (alreadyRegistered) {
      return { success: false, message: 'Já inscrito neste evento' };
    }

    // Add registration
    allRegistrations[userId].push({
      eventId: event.id,
      eventTitle: event.titulo,
      eventData: event,
      registrationDate: new Date().toISOString(),
      status: 'registered'
    });

    const saved = this.saveAllRegistrations(allRegistrations);
    
    if (saved) {
      return { success: true, message: 'Inscrição realizada com sucesso!' };
    } else {
      return { success: false, message: 'Erro ao salvar inscrição' };
    }
  }

  // Check if user is registered for an event
  isRegisteredForEvent(userId, eventId) {
    const userRegistrations = this.getUserRegistrations(userId);
    return userRegistrations.some(registration => registration.eventId === eventId);
  }

  // Unregister from an event
  unregisterFromEvent(userId, eventId) {
    const allRegistrations = this.getAllRegistrations();
    
    if (!allRegistrations[userId]) {
      return { success: false, message: 'Usuário não encontrado' };
    }

    const originalLength = allRegistrations[userId].length;
    allRegistrations[userId] = allRegistrations[userId].filter(
      registration => registration.eventId !== eventId
    );

    if (allRegistrations[userId].length === originalLength) {
      return { success: false, message: 'Inscrição não encontrada' };
    }

    const saved = this.saveAllRegistrations(allRegistrations);
    
    if (saved) {
      return { success: true, message: 'Inscrição cancelada com sucesso!' };
    } else {
      return { success: false, message: 'Erro ao cancelar inscrição' };
    }
  }

  // Get registered events with full event data
  getRegisteredEventsWithData(userId, allEvents) {
    const userRegistrations = this.getUserRegistrations(userId);
    
    return userRegistrations.map(registration => {
      // Find the current event data (in case it was updated)
      const currentEventData = allEvents.find(event => event.id === registration.eventId);
      
      return {
        ...registration,
        eventData: currentEventData || registration.eventData, // Fallback to stored data
        isEventStillAvailable: !!currentEventData
      };
    });
  }
}

const eventRegistrationService = new EventRegistrationService();
export default eventRegistrationService;