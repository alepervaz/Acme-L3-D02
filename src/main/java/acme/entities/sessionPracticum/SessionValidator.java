
package acme.entities.sessionPracticum;

import java.util.Calendar;
import java.util.Date;

import acme.framework.helpers.MomentHelper;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SessionValidator implements Validator {

	public static final int ONE_WEEK = 1;

	@Override
	public boolean supports(final Class<?> clazz) {
		return SessionPracticum.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final SessionPracticum session = (SessionPracticum) target;
		final Date start = session.getStart();
		final Date end = session.getEnd();
		final Date now = MomentHelper.getCurrentMoment();

		final Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.WEEK_OF_YEAR, ONE_WEEK);
		final Date inAWeekFromNow = cal.getTime();

		cal.setTime(start);
		cal.add(Calendar.WEEK_OF_YEAR, ONE_WEEK);
		final Date inAWeekFromStart = cal.getTime();

		if (MomentHelper.isBefore(start, inAWeekFromNow))
			errors.rejectValue("start", "start.beforeNow", "Start date must be after the current date");
		else if (MomentHelper.isBefore(end, inAWeekFromStart))
			errors.rejectValue("end", "end.beforeStart", "End date must be at least one week after the start date");
	}
}
