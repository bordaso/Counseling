package org.Backend.DAOs;

import java.util.List;

import org.Backend.Entities.Bookings;
import org.Backend.Entities.NotificationSetup;

public interface NotificationSetupDao {

	void saveNotificationSetup(NotificationSetup input);

	void updateNotificationSetup(NotificationSetup toBeUpdated);

	void deleteNotificationSetup(Long id);

	NotificationSetup selectNotificationSetupById(Long id);

	NotificationSetup selectNotificationSetupByBooking(Bookings booking);

	List<NotificationSetup> selectAllNotificationSetup();

	void updateNotificationSetupBookings(Long id, Bookings booking);

	void updateNotificationSetupRecurringTime(Long id, long newTime);

	void updateNotificationSetupContent(Long id, String content);

	void updateNotificationSetupEmail(Long id, boolean newValue);

	void updateNotificationSetupSms(Long id, boolean newValue);

	
	void clearNotificationSetup();

	void setMyProxy(NotificationSetupDao proxy);

}
