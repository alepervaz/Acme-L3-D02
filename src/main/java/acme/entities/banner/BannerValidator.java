
package acme.entities.banner;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BannerValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return Banner.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final Banner banner = (Banner) target;
		final ZoneId zoneId = ZoneId.systemDefault();
		final LocalDateTime instant = LocalDateTime.ofInstant(banner.getInstant().toInstant(), zoneId);
		final LocalDateTime displayStart = LocalDateTime.ofInstant(banner.getDisplayStart().toInstant(), zoneId);
		final LocalDateTime displayEnd = LocalDateTime.ofInstant(banner.getDisplayEnd().toInstant(), zoneId);

		if (displayStart.isBefore(instant))
			errors.rejectValue("displayStart", "displayStart.beforeInstant", "The display start date must be after the instantiation date");
		if (displayEnd.isBefore(displayStart.plusWeeks(1)))
			errors.rejectValue("displayEnd", "displayEnd.beforeDisplayStart", "The display end date must be at least one week after the display start date");
	}
}
