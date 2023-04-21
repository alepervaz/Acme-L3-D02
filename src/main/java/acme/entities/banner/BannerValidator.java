
package acme.entities.banner;

import java.util.Calendar;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acme.framework.helpers.MomentHelper;

public class BannerValidator implements Validator {

	public static final int ONE_WEEK = 1;


	@Override
	public boolean supports(final Class<?> clazz) {
		return Banner.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final Banner banner = (Banner) target;
		final Date instant = banner.getInstant();
		final Date displayStart = banner.getDisplayStart();
		final Date displayEnd = banner.getDisplayEnd();

		final Calendar cal = Calendar.getInstance();
		cal.setTime(displayStart);
		cal.add(Calendar.WEEK_OF_YEAR, BannerValidator.ONE_WEEK);
		final Date inAWeekFromStart = cal.getTime();

		if (MomentHelper.isBefore(instant, displayStart))
			errors.rejectValue("displayStart", "displayStart.beforeInstant", "The display start date must be after the instantiation date");
		else if (MomentHelper.isBefore(displayEnd, inAWeekFromStart))
			errors.rejectValue("displayEnd", "displayEnd.beforeDisplayStart", "The display end date must be at least one week after the display start date");
	}
}
